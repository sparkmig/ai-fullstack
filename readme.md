# Project Overview

This project is a full-stack AI-powered application consisting of a React frontend and a Java backend, orchestrated with Docker Compose for easy deployment. The backend is designed using Clean Architecture principles and features a custom implementation of the CQRS (Command Query Responsibility Segregation) pattern.

## Frontend

The frontend is built with React and Vite, providing a modern, responsive user interface. It communicates with the backend via REST APIs and includes features such as:
- Chat interface
- Product upload functionality
- Home and welcome screens

The frontend code is located in the `ai-frontend/` directory.

## Backend

The backend is a Java Spring Boot application organized using Clean Architecture. It is split into several modules:
- **ai-backend-api**: Exposes REST endpoints and handles HTTP requests.
- **ai-backend-application**: Contains business logic, commands, queries, and a custom CQRS pipeline for separating read and write operations.
- **ai-backend-domain**: Defines core domain entities and business rules.
- **ai-backend-infrastructure**: Handles external integrations (e.g., databases, external APIs).

The backend leverages a custom CQRS pipeline, allowing commands (write operations) and queries (read operations) to be processed independently, improving scalability and maintainability.

## Running with Docker Compose

To start the entire application stack (frontend and backend) using Docker Compose:

1. Ensure Docker and Docker Compose are installed on your system.

2. Create a `.env` file in the project root directory. This file should contain all required environment variables for both the frontend and backend. Example keys you may need to provide:

	```env
	# Example .env file
	OPENAI_API_KEY=your_openai_api_key_here
	GEMINI_API_KEY=your_gemini_api_key_here
	PINECONE_API_KEY=your_pinecone_api_key_here
	# Add any other required keys as specified in the documentation or docker-compose.yaml
	```

3. In the project root directory, run:

	```powershell
	docker-compose up --build
	```

4. The frontend and backend services will be built and started automatically. Access the frontend via your browser at the address shown in the Docker output (typically `http://localhost:3000`).

5. To stop the services, press `Ctrl+C` in the terminal and run:

	```powershell
	docker-compose down
	```

---
This architecture ensures a clear separation of concerns, scalability, and ease of development for both frontend and backend teams.
