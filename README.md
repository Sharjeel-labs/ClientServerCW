# ClientServerCW
 
A RESTful API built using JAX-RS (Jersey) and Apache Tomcat for managing campus rooms and sensors as part of the Smart Campus initiative.
 
---
 
## API Overview
 
The API is accessible under /api/v1 and provides two main resource collections:
 
* /api/v1/rooms — create and manage campus rooms
* /api/v1/sensors — register sensors and log their readings
All data is stored in-memory using static lists and maps. No database is used.
 
---
 
## How to Build and Run
 
### Prerequisites
 
* Java 8 or above
* Apache Maven
* Apache Tomcat 9 (included in the repository under apache-tomcat-9.0.100/)
### Steps
 
1. Clone the repository and navigate into the project folder.
2. Run the following Maven command to build the project:
   mvn clean package
3. The API will be available at http://localhost:8080/ClientServerCW/api/v1
---
 
## Report
 
---
 
# Part 1: Service Architecture & Setup
 
## 1. Project & Application Configuration
 
**Question:** In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions.
 
**Answer:** By default JAX-RS creates a new instance of the resource class for every request, so it is not a singleton. This means any data stored as a normal variable inside the class would be lost after each request. To fix this, static fields are used to store the data so it stays in memory across all requests. The risk with this is that if two requests come in at the same time they could both try to change the data at once and cause issues, but for this project it works fine.
 
---
 
## 2. The "Discovery" Endpoint
 
**Question:** Why is the provision of "Hypermedia" (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?
 
**Answer:** HATEOAS means the API includes links in its responses so the client knows where to go next without having to look it up elsewhere. For example the discovery endpoint returns links to /api/v1/rooms and /api/v1/sensors. This is better than static documentation because if a URL changes the client just follows the updated link in the response instead of relying on outdated docs.
 
---
 
# Part 2: Room Management
 
## 1. Room Resource Implementation
 
**Question:** When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.
 
**Answer:** Returning only IDs keeps the response small but then the client has to make extra requests to get the details for each room. Returning full objects means a bigger response but the client gets everything it needs straight away without extra calls. This project returns full room objects which is more practical since clients usually need the full details.
 
---
 
## 2. Room Deletion & Safety Logic
 
**Question:** Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.
 
**Answer:** Yes it is idempotent. The first time the request is sent the room gets deleted and a 200 is returned. If the same request is sent again the room is already gone so a 404 is returned. Either way the end result is the same, the room does not exist in the system, so sending the request multiple times does not cause any extra damage.
 
---
 
# Part 3: Sensor Operations & Linking
 
## 1. Sensor Resource & Integrity
 
**Question:** We explicitly use the @Consumes(MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?
 
**Answer:** The @Consumes annotation tells JAX-RS what type of data the endpoint accepts. If a client sends something other than application/json, for example text/plain, JAX-RS will automatically reject the request and return a 415 Unsupported Media Type error. The method does not even run. This means you do not have to write any extra checks yourself, the framework handles it.
 
---
 
## 2. Filtered Retrieval & Search
 
**Question:** You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/v1/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?
 
**Answer:** Using a query parameter like ?type=CO2 means the filter is optional, so GET /api/v1/sensors still works without it and just returns everything. If the type was part of the URL path you would need a completely separate route just for filtered requests. Query parameters are also easier to extend, for example you could add more filters like ?type=CO2&status=ACTIVE without changing the URL structure at all.
 
---
 
# Part 4: Deep Nesting with Sub-Resources
 
## 1. The Sub-Resource Locator Pattern
 
**Question:** Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class?
 
**Answer:** Instead of putting all the logic in one big class, the sub-resource locator pattern lets you split it into smaller focused classes. In this project SensorResource hands off to SensorReadingResource when the readings path is called. This keeps things organised, easier to read, and easier to change without breaking other parts. If everything was in one class it would get very messy very quickly as the API grows.
 
---
 
# Part 5: Advanced Error Handling, Exception Mapping & Logging
 
## 2. Dependency Validation (422 Unprocessable Entity)
 
**Question:** Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?
 
**Answer:** A 404 means the URL was not found, but in this case the URL /api/v1/sensors is valid and working fine. The problem is that the roomId inside the request body does not match any existing room. A 422 is more accurate because it tells the client that the request was understood and the URL was correct, but something in the data was wrong. A 404 would just confuse the client into thinking they used the wrong endpoint.
 
---
 
## 4. The Global Safety Net (500)
 
**Question:** From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?
 
**Answer:** A stack trace reveals internal details about the application like class names, method names, line numbers, and library versions. An attacker could use this to understand how the code works and find vulnerabilities to exploit. It can also reveal file paths on the server. To prevent this a global exception mapper is used to catch all unexpected errors and return a plain 500 message with no technical details, while the actual error is still logged on the server for the developer.
 
---
 
## 5. API Request & Response Logging Filters
 
**Question:** Why is it advantageous to use JAX-RS filters for cross-cutting concerns like logging, rather than manually inserting Logger.info() statements inside every single resource method?
 
**Answer:** If you add logging manually to every method you end up repeating the same code everywhere and if you ever need to change it you have to update every single method. A JAX-RS filter handles logging in one place and automatically applies to every request and response across the whole API. It keeps the resource methods clean and makes the logging much easier to maintain.
 
