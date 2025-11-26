# MediTrack — Clinic & Appointment Management System (Core Java, OOP Practice)

MediTrack is a modular, object-oriented Clinic & Appointment Management System implemented in Core Java. The system
models patients, doctors, appointments, and billing; demonstrates strong OOP design, SOLID principles, standard Java
features (collections, exceptions, I/O, serialization), and optional advanced topics (concurrency, design patterns,
streams, cloning). The project is ideal for mentees to practice Java fundamentals, architecture, collaboration, and
documentation.

## Why this project?

- Practice clean object-oriented design with real-world healthcare concepts.
- Learn SOLID principles by applying them to domain models and services.
- Use core Java features: collections, enums, exceptions, I/O, and (optionally) serialization and concurrency.
- Explore design patterns (e.g., strategy for billing), layering, and separation of concerns.
- Collaborate via a clear package structure and extensible APIs.

## High-level Architecture

The codebase is organized into cohesive packages:

- entities: Domain models for clinic concepts (e.g., persons, patients, doctors, appointments, billing).
- services: Business logic for CRUD, searching, and workflows like scheduling and billing.
- interfaces: Abstractions for behaviors such as searching or pluggable billing strategies.
- util: Helpers such as validation and reusable strategies/utilities.
- constants: Domain enums (e.g., appointment statuses, specializations, genders, payment states).
- exceptions: Custom exception types for domain-specific error handling.
- entry point: A simple main program to exercise the API and demonstrate flows.

This layered layout enables high cohesion within packages and loose coupling across them.

## OOP Principles Practiced

- Encapsulation: Domain objects expose intent-revealing methods and validate state through controlled access.
- Inheritance: Common person attributes are abstracted and specialized for different roles (e.g., patient vs. doctor).
- Abstraction: Interfaces model behaviors (searching, billing calculation) without leaking implementation details.
- Polymorphism: Interchangeable strategies and behaviors at runtime (e.g., different billing strategies).
- Composition & Aggregation: Appointments and billing aggregate multiple domain objects.
- Immutability (where sensible): Use of final or controlled mutation to maintain invariants and reduce bugs.

## SOLID in Practice

- Single Responsibility Principle (SRP): Entities hold data + invariants; services coordinate operations and workflows.
- Open/Closed Principle (OCP): Add new billing calculations or search strategies without modifying core logic.
- Liskov Substitution Principle (LSP): Interface implementations can be swapped without breaking consumers.
- Interface Segregation Principle (ISP): Narrow, purpose-fit interfaces (e.g., searching, billing) reduce coupling.
- Dependency Inversion Principle (DIP): High-level modules depend on abstractions (e.g., strategy interfaces) rather
  than concrete implementations.

## Core Features

- Patient management: Create, read, update, delete; keyword-based search; validation.
- Doctor management: Register and list; specialization-based organization; keyword search.
- Appointment management: Schedule, track status, and cancel; associate patients and doctors; use typed statuses.
- Billing: Compute bills for appointments/visits; support different calculation strategies; track payment status.
- Validation & error handling: Guard against invalid inputs, with domain-specific exceptions.
- Extensibility: Add new specializations, billing rules, search behaviors, or persistence mechanisms with minimal
  friction.

## Java Concepts Demonstrated

- Collections and generics for in-memory repositories and searches.
- Enums for strong domain typing (statuses, categories).
- Exceptions for robust error handling; custom exception types for domain clarity.
- I/O and serialization (optional) for persistence.
- Streams and functional style (optional) for expressive collection processing.
- Concurrency (optional) for safe appointment scheduling in multi-threaded contexts.
- Design patterns: Strategy (e.g., pluggable billing calculation) and service-style orchestration.

## Getting Started

Prerequisites:

- A recent JDK (e.g., 17 or later) installed and available on your PATH.
- Gradle wrapper is included; no separate Gradle install is required.

Build:

- Unix/macOS: `./gradlew clean build`
- Windows: `gradlew.bat clean build`

Run:

- From an IDE: Open the project and run the main entry point.
- From CLI (if an application plugin is configured): `./gradlew run`
- Alternatively, compile and run the main class via your IDE or `java` command.

## Usage Overview (What you can try)

When you run the `Main` class, you’ll interact with a **menu-driven console UI** that lets you manage every part of the
MediTrack system.

### 🏥 Main Menu

- Manage Appointments
- Manage Doctors
- Manage Patients
- Manage Billing
- Exit

---

### 👨‍⚕️ Doctor Management

Perform full CRUD operations and name/specialization-based search.

You can:

- Add doctors with full Person details (name, email, phone, address, DOB, gender).
- Select specialization from an enum-driven list.
- View or search doctors by ID, name, or specialization.
- Update doctor name.
- Delete doctor records.

---

### 🧍 Patient Management

Manage all patient-related actions.

You can:

- Add patients with Person details.
- View all or search by ID.
- Update patient name.
- Delete patient records.
- Search patients by name keyword.

---

### 📅 Appointment Management

Schedule and manage appointments with built-in conflict detection (prevents double-booking within 30 minutes).

You can:

- Create an appointment.
- Update appointment date/time.
- List all appointments.
- Filter by doctor or patient.
- Update appointment status (PENDING / COMPLETED / CANCELLED).
- Delete appointments.

---

### 💵 Billing Management

Generate bills with different tax strategies using the Strategy Pattern.

You can:

- Add bills for patients.
- Choose between:
    - GST Billing Strategy (18%)
    - Senior Citizen Billing Strategy (5%)
- Generate bill summaries.
- Mark payments as PAID.
- View all bills.

## Project Structure (at a glance)

- Domain entities for clinic concepts
- Services encapsulating business workflows
- Interfaces for searching and billing strategies
- Utilities for validation and shared logic
- Enums/constants for domain states and types
- Custom exceptions
- Main entry point for demos