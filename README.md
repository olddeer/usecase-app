# BI4Tourism - Spring REST API

**BI4Tourism** is a Spring-based REST API designed to streamline and enhance tourism data analysis workflows. This backend application provides endpoints to save user-defined use cases, capturing essential analytics components such as dimensions, measures, time dimensions, and filters. Additionally, it tracks and records user actions for improved insights into user behavior and preferences.

---

## Features

### Core Functionalities

1. **Save User Use Cases**  
   - Users can define and save their specific analytics use cases.  
   - Each use case can include:
     - **Dimensions**: Key attributes for data slicing (e.g., region, activity type).
     - **Measures**: Metrics for analysis (e.g., revenue, visitor count).
     - **Time Dimensions**: Temporal aspects of the data (e.g., year, quarter, month).
     - **Filters**: Conditions to narrow down the dataset (e.g., "country = USA" or "year > 2020").

2. **Track User Actions**  
   - Capture and save user interactions with the app.  
   - Recorded actions can include:
     - Query submissions.
     - Report generation.
     - Data exploration paths.
   - Useful for analytics, audit trails, and improving the user experience.

---

## API Endpoints

### Use Case Management
- **POST /api/usecases**  
  Save a user-defined use case including dimensions, measures, time dimensions, and filters.  
  **Request Body Example**:
  ```json
  {
    "name": "Top Destinations Analysis",
    "dimensions": ["Region", "Tour Type"],
    "measures": ["Revenue", "Visitors"],
    "timeDimensions": ["Year", "Month"],
    "filters": {
      "country": "USA",
      "year": { "operator": ">", "value": 2020 }
    }
  }
