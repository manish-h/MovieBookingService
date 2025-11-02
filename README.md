# Movie Booking Service

This is a Spring Boot application for a movie booking service.

## Prerequisites

* Java 21
* Gradle
* PostgreSQL

## Setup

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   ```
2. **Build the project:**
   ```bash
   ./gradlew build
   ```

## Running the application

To run the Spring Boot server, execute the following command:

```bash
./gradlew bootRun
```

The server will start on the default port 8080.

## API Endpoints

### Movies

*   **Create a new movie:**

    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"title": "The Matrix", "duration": 136}' http://localhost:8080/movies
    ```

*   **Get all movies:**

    ```bash
    curl http://localhost:8080/movies
    ```

*   **Search for movies by title:**

    ```bash
    curl "http://localhost:8080/movies/search?keyword=The%20Matrix"
    ```

### Shows

*   **Create a new show:**

    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"showTime": "2024-05-20T20:00:00", "movie": {"id": 1}, "auditorium": {"id": 1}}' http://localhost:8080/shows
    ```

*   **Get all shows:**

    ```bash
    curl http://localhost:8080/shows
    ```

*   **Get a show by ID:**

    ```bash
    curl http://localhost:8080/shows/1
    ```

*   **Update a show:**

    ```bash
    curl -X PUT -H "Content-Type: application/json" -d '{"showTime": "2024-05-20T21:00:00", "movie": {"id": 1}, "auditorium": {"id": 1}}' http://localhost:8080/shows/1
    ```

*   **Delete a show:**

    ```bash
    curl -X DELETE http://localhost:8080/shows/1
    ```

### Auditoriums

*   **Create a new auditorium:**

    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"location": "Main Street Cinema"}' http://localhost:8080/auditoriums
    ```

*   **Get all auditoriums:**

    ```bash
    curl http://localhost:8080/auditoriums
    ```

*   **Get an auditorium by ID:**

    ```bash
    curl http://localhost:8080/auditoriums/1
    ```

*   **Update an auditorium:**

    ```bash
    curl -X PUT -H "Content-Type: application/json" -d '{"location": "Uptown Cinema"}' http://localhost:8080/auditoriums/1
    ```

*   **Delete an auditorium:**

    ```bash
    curl -X DELETE http://localhost:8080/auditoriums/1
    ```
*   **Create a new booking:**

    ```sh
    curl -X POST -H "Content-Type: application/json" -d '{"showId": 1, "seatNumbers": ["A1", "A2"], "customerName": "John Doe"}' http://localhost:8080/bookings
    ```
    
## Database

To connect to the PostgreSQL database, use the following command:

```bash
psql -h localhost -U postgres -W
```

Once connected, you can switch to the `showtime` database:

```sql
\c showtime
```

### Sample Queries

*   **Get all movies:**

    ```sql
    SELECT * FROM movie;
    ```

*   **Get all auditoriums:**

    ```sql
    SELECT * FROM auditorium;
    ```

*   **Get all shows:**

    ```sql
    SELECT * FROM show;
    ```

*   **Insert a new movie:**

    ```sql
    INSERT INTO movie (title) VALUES ('Inception');
    ```

## Load Testing

The project includes a load testing script using [Locust](https://locust.io/). This script simulates user behavior for booking movie tickets.

### 1. Setup Python Environment

It is highly recommended to use a Python virtual environment to manage dependencies and avoid conflicts with system-wide packages.

**a. Create a virtual environment:**

From the project root directory, run:
```bash
python3 -m venv venv
```

**b. Activate the virtual environment:**

```bash
source venv/bin/activate
```
Your terminal prompt should now be prefixed with `(venv)`.

**c. Install dependencies:**

With the virtual environment active, install the required Python libraries:
```bash
pip install locust requests
```

### 2. Running the Load Test

**a. Start the Spring Boot application:**

Make sure your MovieBookingService is running:
```bash
./gradlew bootRun
```

**b. Start Locust:**

In a new terminal (with the virtual environment still active), run the following command:
```bash
locust -H http://localhost:8080
```

**c. Open the Locust Web Interface:**

Open your web browser and navigate to **http://localhost:8089**.

*   Enter the **Number of users** you want to simulate (e.g., `100`).
*   Enter a **Spawn rate** (e.g., `10`), which is the number of users to start per second.
*   Click the **"Start swarming"** button.

Locust will first run a setup phase to create movies and shows (you will see log output for this in the terminal where you ran the `locust` command). After the setup is complete, it will start the booking simulation, and you can monitor the performance of the application in the web interface.

### 3. Stopping the Test

*   To stop the load test, click the "Stop" button in the web interface.
*   To shut down the Locust process, go to its terminal and press `Ctrl+C`.
*   To deactivate the Python virtual environment, simply type `deactivate`.
