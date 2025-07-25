# 🎟️ Ticket Booking System (Java CLI Application)

A Java-based command-line Ticket Management System inspired by core functionalities of platforms like IRCTC. This project demonstrates key concepts like Object-Oriented Programming, secure authentication, and file-based data persistence using JSON.

---

## 🚀 Features

- 🔐 **User Authentication System**
  - Secure password handling with BCrypt

- 🚆 **Train Management**
  - Admin can add, update train details
  - View all available trains

- 🎫 **Ticket Booking**
  - User can search a train by their source and destination city.
  - Users can select a train and book a particular seat on the train (if the seat is not already booked).
  - Users can fetch all of their bookings.
  - Users can cancel tickets by using their ticket Id.
  - Booking details stored persistently.

- 💾 **Persistent Storage**
  - Uses JSON files (`users.json`, `trains.json`) for storing user and train data
  - Handled using Jackson Databind

---

## ⚙️ Tech Stack

- **Java 24 **
- **Gradle Build System**
- **Jackson (JSON parsing)**
- **BCrypt (Password hashing)**

---

## 📁 Project Structure
```
app/
├── build/                        # Auto-generated build files (by Gradle)
│   ├── classes/
│   ├── generated/
│   └── tmp/

├── src/
│   └── main/
│       ├── java/
│       │   └── org/
│       │       └── ticket/
│       │           └── booking/
│       │               ├── entities/               # Core models
│       │               │   ├── Ticket.java
│       │               │   ├── Train.java
│       │               │   └── User.java
│       │               │
│       │               ├── localDb/                # Local database (JSON files)
│       │               │   ├── trains.json
│       │               │   └── users.json
│       │               │
│       │               ├── services/               # Business logic
│       │               │   ├── TrainService.java
│       │               │   └── userBookingService.java
│       │               │
│       │               ├── Util/                   # helper classes
│       │               │   └── userServiceUtil.java
│       │               │
│       │               └── App.java                # Main entry point
│       │
│       └── resources/                             
```
---

## 🛠️ Getting Started

### ✅ Prerequisites

- Java 8 or higher
- Gradle

### 🔧 Build & Run

```bash
# Clone the repository
git clone https://github.com/shantanu-bit/Ticket-Booking-System.git
cd Ticket-Booking-System

# Build the project
./gradlew build

# Run the application
./gradlew run

