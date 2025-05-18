# Employee Management System

A comprehensive CRUD application for managing employee data without relying on a persistent database. The application demonstrates modern web development patterns using Spring Boot and Thymeleaf.

## Technologies Used

### Backend
- **Spring Boot 2.x** - Main framework for building the application
- **Spring MVC** - Web layer implementation
- **Spring REST** - For building RESTful services
- **In-memory Data Storage** - Repository pattern implementation without a database

### Frontend
- **Thymeleaf** - Server-side Java template engine
- **Bootstrap 5** - CSS framework for responsive design
- **jQuery** - JavaScript library for DOM manipulation
- **DataTables** - Enhanced interactive HTML tables
- **Chart.js** - JavaScript charting for the dashboard
- **Bootstrap Icons** - Icon library

## Project Structure

The application follows a standard layered architecture:

1. **Controllers**
   - `EmployeeController` - REST endpoints for employee operations
   - `EmployeeMvcController` - Thymeleaf view controllers
   - `RestClientController` - Client-side REST communication

2. **Services**
   - `EmployeeService` - Business logic implementation
   - `IEmployeeService` - Service interface
   - `ClientService` - Client-side service implementation

3. **Models**
   - `EmployeeModel` - Core domain entity

4. **Repository**
   - `EmployeeRepository` - In-memory data access implementation
   - `IEmployeeRepository` - Repository interface

5. **Views**
   - Dashboard - Visual representation of employee statistics
   - List - Employee directory with CRUD operations
   - Details - Detailed employee information
   - Form - Create/Edit employee data

## Features

- **Employee CRUD Operations**
  - Create new employees
  - Read employee details
  - Update existing employees
  - Delete employees with confirmation

- **Dashboard**
  - Visual statistics of employee data
  - Department distribution
  - Salary ranges
  - Status overview

- **Data Visualization**
  - Interactive charts and graphs
  - Tabular data with sorting and filtering

- **Responsive Design**
  - Mobile-friendly interface
  - Adapts to different screen sizes

## Usage

The application serves as:

1. A learning resource for Spring Boot and Thymeleaf integration
2. A demonstration of modern web application architecture
3. A template for building CRUD applications without database setup
4. An example of clean separation of concerns in web development

This project is ideal for:
- Beginners learning Spring Boot
- Developers exploring Thymeleaf
- Projects requiring a quick prototype without database configuration
- Reference implementation for CRUD operations with clean architecture
