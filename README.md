# Blood Donor Finder - Backend

A Spring Boot application for managing blood donors and emergency requests.

## Requirements
- Java 17
- Maven 3.9+
- Docker (for containerization)

## Local Development

### Build
```bash
mvn clean package
```

### Run
```bash
mvn spring-boot:run
```

The application runs on `http://localhost:8080`

### Access H2 Console
Navigate to `http://localhost:8080/h2-console` to view the in-memory database.

## Docker Setup

### Build Docker Image
```bash
docker build -t blood-donor-app:latest .
```

### Run with Docker
```bash
docker run -p 8080:8080 blood-donor-app:latest
```

### Using Docker Compose
```bash
docker-compose up -d
```

To stop the container:
```bash
docker-compose down
```

## API Endpoints

- **Donors**
  - GET `/api/donors` - Get all donors
  - POST `/api/donors` - Create a new donor
  - GET `/api/donors/{id}` - Get donor by ID
  - PUT `/api/donors/{id}` - Update donor
  - DELETE `/api/donors/{id}` - Delete donor

- **Emergency Requests**
  - GET `/api/emergency-requests` - Get all requests
  - POST `/api/emergency-requests` - Create new request
  - GET `/api/emergency-requests/{id}` - Get request by ID
  - PUT `/api/emergency-requests/{id}` - Update request
  - DELETE `/api/emergency-requests/{id}` - Delete request

## Database

The application uses an H2 in-memory database configured in `application.properties`.

## Configuration

Edit `src/main/resources/application.properties` to configure:
- Server port (default: 8080)
- Database connection
- JPA/Hibernate settings

## Development

### IDE Setup
Import as Maven project in your IDE (IntelliJ, VS Code, Eclipse)

### Hot Reload
During development, use spring-boot-devtools for automatic restart on file changes.

## License

[Add your license here]
