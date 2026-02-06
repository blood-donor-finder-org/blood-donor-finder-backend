Blood Donor Finder – Backend

This is the Spring Boot backend for the Blood Donor Finder System.
It manages donor registration, blood availability tracking, search functionality, and data management for the application.

🚀 Live Backend URL (vercel – Free Plan)

👉 http://localhost:8006/api/donors

(Wait a few seconds for the server to start. Backend should return [] or donor data when opened.)

⚠️ Important Note (vercel Free Plan)

This backend is hosted on Render Free Plan.

If the backend is idle, it may automatically go to sleep

When opening the frontend, backend may take 30–60 seconds to wake up

Please open the backend URL once before using the frontend

📊 Project Presentation

👉 Download Project PPT
(Add your PPT link here if uploaded to Drive/GitHub)

🛠️ Tech Stack

Java

Spring Boot

Spring Data JPA

H2 Database

Maven

Docker

SonarQube / SonarCloud

Render (Backend Deployment)

▶️ Run Locally
git clone https://github.com/vasanth005-117/blood-donor-finder-backend.git
cd blood-donor-backend
mvn spring-boot:run


Backend runs at:

👉 http://localhost:8080

🔍 SonarQube / SonarCloud

Code quality analysis integrated using CI Pipeline

Automated scanning using GitHub Actions

Quality Gate Passed Successfully ✅

🐳 Backend Dockerization

Docker is used to containerize the backend for easy deployment.

Build Docker Image
docker build -t blood-donor-backend .

Run Docker Container
docker run -p 8080:8080 blood-donor-backend

🌐 Frontend

Frontend is built using React and deployed on Vercel.

👉 Frontend Repository:
https://github.com/vasanth005-117/blood-donor-finder-frontend.git

👉 Live Frontend URL:
https://blood-donor-finder-frontend.vercel.app
