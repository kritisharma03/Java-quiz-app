# Java Quiz Application

## 📌 Project Overview
The **Java Quiz Application** is a desktop-based application developed using **Core Java** and **Java Swing**.  
It allows users to take a multiple-choice quiz with a timer, automatically evaluates answers, and displays the final score.

The project uses **PostgreSQL** for database connectivity through **JDBC** and follows proper **package-based Java project structure**.

This application demonstrates strong fundamentals in **Java, Object-Oriented Programming (OOP), GUI development, and database integration**.

---

## 🎯 Features
- Java Swing–based graphical user interface
- User login before starting the quiz
- Multiple-choice questions
- Timer-based quiz execution
- Automatic score calculation
- Result display after quiz completion
- PostgreSQL database connectivity using JDBC
- Modular and maintainable OOP-based design

---

## 🧱 Technology Stack

| Category | Technology |
|--------|------------|
| Programming Language | Core Java |
| GUI | Java Swing, AWT |
| Database | PostgreSQL |
| Database Connectivity | JDBC |
| IDE | VS Code / IntelliJ IDEA |
| Build Tool | Manual (`javac`, `java`) |

---
## 📂 Project Structure
```
QuizApplication
│
├── lib
│ └── postgresql-42.7.8.jar
│
├── src
│ └── quizApplication
│ ├── Main.java
│ ├── DBConnection.java
│ ├── Login.java
│ ├── Next.java
│ ├── Quiz.java
│ ├── QuestionBank.java
│ ├── Score.java
│ ├── TestQuestions.java
|
├── database.sql
├── .gitignore
└── README.md
```

## ▶️ How to Run the Project
✅ Prerequisites

- Java JDK 8 or above
- PostgreSQL installed and running
- PostgreSQL JDBC Driver (`.jar` file)
- Git (optional, for cloning the repository)
- VS Code / IntelliJ IDEA

---

 🔹 Step 1: Clone the Repository
 ```bash
git clone https://github.com/kritisharma03/Java-quiz-app.git
cd Java-quiz-app
```
🔹 Step 2: Set Up PostgreSQL Database
Create a database:
```sql
CREATE DATABASE quizdb;
```
(Add required tables based on your application logic)

🔹 Step 3: Configure Database Credentials
```bash
PG_HOST=localhost
PG_PORT=5432
PG_DB=quizdb
PG_USER=quizuser
PG_PASS=quizpass
```

🔹 Step 4: Add PostgreSQL JDBC Driver

Download from:
[PostgreSQL JDBC Driver Download: ](https://jdbc.postgresql.org/download/)   

Place the .jar file inside the lib folder:
 ```bash
lib/postgresql-42.7.8.jar
```
🔹 Step 5: Compile the Project

Run the following command from the project root directory:
> ⚠️ On Linux/macOS, replace `;` with `:` in the classpath.
 ```bash
javac -cp ".;lib/postgresql-42.7.8.jar" -d . src/quizApplication/*.java
```
🔹 Step 6: Run the Application
 ```bash
java -cp ".;lib/postgresql-42.7.8.jar" quizApplication.Main
```
## 🚀 Future Enhancements

- Admin panel for managing quiz questions
- Store user scores in the database
- User authentication with password support
- Convert project to Maven or Gradle
- Enhanced UI/UX design


