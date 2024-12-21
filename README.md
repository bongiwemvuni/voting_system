# Voting System

A command-line voting system application built with Java and SQLite. This application allows voters to register, login, and cast votes for different political parties.

## Features

- User Registration with unique username and email
- Secure Login System
- Vote casting for registered parties
- View election results
- SQLite database for data persistence
- Duplicate prevention for usernames and emails
- Single vote per user enforcement

## Prerequisites

- Java JDK 8 or higher
- Maven for dependency management

## Dependencies

- SQLite JDBC Driver (3.43.0.0)

## Project Structure

```
voting_poll/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── votingsystem/
│       │           ├── dao/
│       │           │   ├── PartyDAO.java
│       │           │   └── VoterDAO.java
│       │           ├── model/
│       │           │   ├── Party.java
│       │           │   └── Voter.java
│       │           ├── util/
│       │           │   └── DBConnection.java
│       │           └── Main.java
│       └── resources/
│           └── database.sql
└── pom.xml
```

## Database Schema

### Voters Table
```sql
CREATE TABLE voters (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    has_voted INTEGER DEFAULT 0
);
```

### Parties Table
```sql
CREATE TABLE parties (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT UNIQUE NOT NULL,
    symbol TEXT NOT NULL,
    vote_count INTEGER DEFAULT 0
);
```

## How to Run

1. Clone the repository:
```bash
git clone [repository-url]
cd voting_poll
```

2. Build the project using Maven:
```bash
mvn clean install
```

3. Run the application:
```bash
java -jar target/voting_poll-1.0.jar
```

## Usage

1. **Register as a New Voter**
   - Select option 1 from the main menu
   - Enter username, password, and email
   - Each username and email must be unique

2. **Login**
   - Select option 2 from the main menu
   - Enter your username and password

3. **Cast Your Vote**
   - After logging in, you'll see the list of available parties
   - Select a party by entering its number
   - You can only vote once

4. **View Results**
   - Select option 3 from the main menu
   - See the current vote count for each party

## Default Political Parties

The system comes with four pre-configured parties:
- Purple Party
- Orange Party
- Green Party
- Independent Party

## Security Features

- Unique usernames and email addresses
- One vote per registered user
- SQLite database with UNIQUE constraints
- Transaction management for data integrity



