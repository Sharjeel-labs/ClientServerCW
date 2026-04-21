# ClientServerCW

### Part 1: Service Architecture & Setup (10 Marks)
## 1. Project & Application Configuration (5 Marks)
# In your report, explain the default lifecycle of a JAX-RS Resource class. Is a new instance instantiated for every incoming request, or does the runtime treat it as a singleton? Elaborate on how this architectural decision impacts the way you manage and synchronize your in-memory data structures (maps/lists) to prevent data loss or race conditions
## answer : 
.

## 2. The ”Discovery” Endpoint (5 Marks)
# Question: Why is the provision of ”Hypermedia” (links and navigation within responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does this approach benefit client developers compared to static documentation?




## Part 2: Room Management (20 Marks)
# 1. Room Resource Implementation (10 Marks):
# Question: When returning a list of rooms, what are the implications of returning only IDs versus returning the full room objects? Consider network bandwidth and client side processing.

## 2. Room Deletion & Safety Logic (10 Marks):
# - Question: Is the DELETE operation idempotent in your implementation? Provide a detailed justification by describing what happens if a client mistakenly sends the exact same DELETE request for a room multiple times.

## Part 3: Sensor Operations & Linking (20 Marks)
# 1. Sensor Resource & Integrity (10 Marks): 
#  Question: We explicitly use the @Consumes (MediaType.APPLICATION_JSON) annotation on the POST method. Explain the technical consequences if a client attempts to send data in a different format, such as text/plain or application/xml. How does JAX-RS handle this mismatch?

# 2. Filtered Retrieval & Search (10 Marks):
# Question: You implemented this filtering using @QueryParam. Contrast this with an alternative design where the type is part of the URL path (e.g., /api/vl/sensors/type/CO2). Why is the query parameter approach generally considered superior for filtering and searching collections?

## Part 4: Deep Nesting with Sub - Resources (20 Marks)
# 1. The Sub-Resource Locator Pattern (10 Marks):
# Question: Discuss the architectural benefits of the Sub-Resource Locator pattern. How does delegating logic to separate classes help manage complexity in large APIs compared to defining every nested path (e.g., sensors/{id}/readings/{rid}) in one massive controller class



## Part 5: Advanced Error Handling, Exception Mapping & Logging (30 Marks)
# 2. Dependency Validation (422 Unprocessable Entity) (10 Marks):
# Question: Why is HTTP 422 often considered more semantically accurate than a standard 404 when the issue is a missing reference inside a valid JSON payload?

# 4. The Global Safety Net (500) (5 Marks):
# From a cybersecurity standpoint, explain the risks associated with exposing internal Java stack traces to external API consumers. What specific information could an attacker gather from such a trace?

# 5. API Request & Response Logging Filters (5 Marks)
# Question: Why is it advantageous to use JAX-RS filters for cross-cutting concerns likelogging, rather than manually inserting Logger.info() statements inside every single resource method?
