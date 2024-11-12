# About

## Weather API

### List of APIs

1. **/import**:  
   HTTP GET API that imports weather data from CSV format into an in-memory database.

2. **/stations**:  
   HTTP GET API to get a list of all weather stations from the database.

3. **/station/{id}**:  
   HTTP GET API to get specific weather station details by ID.

---

### Tech Stack

- **Programming Language**: Kotlin
- **Frameworks**: Spring Boot, Data JPA, Hibernate
- **Database**: H2 (In-memory, for brevity and demo)
- **Server**: Tomcat (Embedded)