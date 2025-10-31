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
