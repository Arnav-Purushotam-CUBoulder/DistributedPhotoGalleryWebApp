# Photo App

This is a simple web application for uploading and viewing photos. It's built with a Java Spring Boot backend and a simple HTML/JavaScript frontend.

![Photo App Screenshot](https://i.imgur.com/your-screenshot.png)

## Features

*   **Upload Photos:** Easily upload your favorite photos.
*   **View Photos:** View your uploaded photos one by one.
*   **Random Photo:** Get a random photo from the collection.
*   **Previous/Next:** Navigate through the photo history.

## Technologies Used

*   **Backend:**
    *   Java
    *   Spring Boot
    *   Spring Data JPA
    *   Minio (for object storage)
    *   SQLite (for photo indexing)
*   **Frontend:**
    *   HTML
    *   JavaScript

## Getting Started

### Prerequisites

*   Java 17 or later
*   Maven
*   Docker (for running Minio)

### Installation

1.  **Clone the repository:**

    ```bash
    git clone https://github.com/your-username/photo-app.git
    cd photo-app
    ```

2.  **Start the Minio server:**

    ```bash
    docker run -d -p 9000:9000 -p 9001:9001 --name minio \
      -e "MINIO_ROOT_USER=minioadmin" \
      -e "MINIO_ROOT_PASSWORD=minioadmin" \
      minio/minio server /data --console-address ":9001"
    ```

3.  **Run the Spring Boot application:**

    ```bash
    mvn spring-boot:run
    ```

4.  **Open your browser and navigate to `http://localhost:8080`**

## API Endpoints

*   `GET /api/photos/{id}`: Fetches a photo by its ID.
*   `GET /api/photos/random`: Fetches a random photo.
*   `POST /api/photos/upload`: Uploads a new photo.

## How It Works

1.  When you upload a photo, it's sent to the backend.
2.  The backend saves the photo to a Minio bucket.
3.  The backend stores the photo's ID and Minio object name in a SQLite database.
4.  When you request a photo, the backend retrieves the object name from the database and fetches the photo from Minio.

## Future Improvements

*   User authentication
*   Photo albums
*   Photo tagging and search
*   More advanced frontend with a modern framework like React or Vue.js
