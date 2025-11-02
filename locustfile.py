import random
import time
import requests
from locust import HttpUser, task, between, events

# --- Configuration ---
NUM_MOVIES = 5
NUM_SHOWS_PER_MOVIE = 20
NUM_SEATS_PER_SHOW = 300
# ---------------------

@events.test_start.add_listener
def on_test_start(environment, **kwargs):
    """
    This function runs once when the test is started. 
    It sets up all the test data and attaches the created show IDs to the environment object
    so that users can access them.
    """
    print("---- Starting Test Data Setup ----")
    
    host = environment.host
    show_ids = [] # Local list for setup

    try:
        response = requests.post(f"{host}/auditoriums", json={"location": "Main Auditorium"})
        response.raise_for_status()
        auditorium_id = response.json()["id"]
        print(f"Created Auditorium with ID: {auditorium_id}")

        for i in range(NUM_MOVIES):
            movie_payload = {"title": f"Load Test Movie {i + 1}"}
            response = requests.post(f"{host}/movies", json=movie_payload)
            response.raise_for_status()
            movie_id = response.json()["id"]
            print(f"  Creating Movie #{i+1} with ID: {movie_id}")

            for j in range(NUM_SHOWS_PER_MOVIE):
                show_time = time.strftime('%Y-%m-%dT%H:%M:%S', time.localtime(time.time() + random.randint(3600, 7 * 24 * 3600)))
                seats = [{"seatNumber": f"A{k:03d}", "cost": 100} for k in range(1, NUM_SEATS_PER_SHOW + 1)]
                
                show_payload = {
                    "showTime": show_time,
                    "movie": {"id": movie_id},
                    "auditorium": {"id": auditorium_id},
                    "seats": seats
                }
                response = requests.post(f"{host}/shows", json=show_payload)
                response.raise_for_status()
                show_id = response.json()["id"]
                show_ids.append(show_id)
        
        # Attach the list of show IDs to the environment for users to access
        environment.show_ids = show_ids
        print(f"---- Test Data Setup Complete. {len(environment.show_ids)} shows created. ----")
        print("---- Starting Booking Simulation ----")

    except requests.exceptions.RequestException as e:
        print(f"!!!! Test Data Setup FAILED: {e} !!!!")
        environment.runner.quit()


class MovieBookingUser(HttpUser):
    wait_time = between(1, 5)

    @task
    def book_ticket_scenario(self):
        # print("User task started.")
        # Check if the show_ids list exists on the environment and is not empty
        if not hasattr(self.environment, "show_ids") or not self.environment.show_ids:
            # This print will appear in the terminal if users can't find any shows.
            print("!!!! CRITICAL: User cannot find show_ids data. Skipping task. !!!!")
            time.sleep(1) # Wait a second to avoid spamming the logs
            return

        show_id = random.choice(self.environment.show_ids)

        with self.client.get(f"/shows/{show_id}", name="/shows/[id]", catch_response=True) as response:
            if not response.ok:
                response.failure("Could not fetch show details")
                return
            
            show_data = response.json()
            # Correctly find available seats by checking the 'booked' boolean field
            available_seats = [seat["seatNumber"] for seat in show_data.get("seats", []) if seat.get("booked") is False]

        if not available_seats:
            print(f"No available seats for show {show_id}. Skipping booking.")
            return

        num_seats_to_book = random.randint(1, 6)
        seats_to_book = random.sample(available_seats, min(len(available_seats), num_seats_to_book))

        if not seats_to_book:
            return

        booking_payload = {
            "showId": show_id,
            "seatNumbers": seats_to_book
        }

        for attempt in range(2):
            with self.client.post("/bookings", json=booking_payload, name="/bookings", catch_response=True) as response:
                if response.status_code == 201:
                    print(f"Successfully booked {len(seats_to_book)} seats for show {show_id}")
                    response.success()
                    return
                else:
                    print(f"Booking failed for show {show_id} on attempt {attempt + 1}")
                    response.failure(f"Booking failed on attempt {attempt + 1}")
