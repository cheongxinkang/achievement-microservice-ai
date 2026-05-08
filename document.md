# Documentation of AI-generated microservice

This is a record of my time using AI to generate this microservice.
We are using gemini with antigravity ide

First prompt:

Generate a prompt for antigravity to create a new microservice that will handle an achievement system using Spring Boot, Java for the Backend, and Thymeleaf to serve the Frontend. Focus on implementing the following features:
1. Create an achievement.
1a. The creation form for the achievement is from another microservice called Template Service. Mock this temporarily, this mock template will have two text fields. It will ask "What is the name of the achievement?" and "What is the description of the achievement?".
2. List created achievements.
3. Allow user to edit an achievement.
4. Allow user to complete achievements.

Output:
Please create a new Spring Boot microservice to handle an Achievement System. 

**Technology Stack:**
- **Backend:** Java, Spring Boot
- **Frontend/Views:** Thymeleaf
- **Database:** H2 (in-memory) with Spring Data JPA for rapid development
- **Build Tool:** Gradle

**Core Entity:**
Create an `Achievement` entity with the following fields:
- `id` (Long, Primary Key, Auto-generated)
- `name` (String)
- `description` (String)
- `completed` (Boolean, default `false`)

**Features to Implement:**

1. **Create an Achievement (Mocked Template Service Integration):**
   - The actual creation form is intended to come from another microservice called "Template Service". 
   - **Task:** Mock this temporarily. Create a Thymeleaf page (`mock-create.html`) with a form that asks two specific questions using text fields: "What is the name of the achievement?" and "What is the description of the achievement?".
   - This form should submit via POST to a controller endpoint that saves the new `Achievement` to the database and redirects back to the list view.

2. **List Created Achievements:**
   - Create a Thymeleaf view (`list.html` or `index.html`) that fetches and displays all created achievements from the database in a clean table or list layout.
   - For each achievement, display its name, description, and whether it is completed.

3. **Edit an Achievement:**
   - On the list view, add an "Edit" button for each achievement.
   - Clicking this should navigate to an edit form (`edit.html`) populated with the achievement's current data.
   - Allow the user to modify the `name` and `description`, and implement the backend endpoint to save these updates.

4. **Complete Achievements:**
   - On the list view, provide a "Mark Complete" button for achievements that have `completed == false`.
   - When clicked, this should call an endpoint that updates the achievement's `completed` status to `true`.
   - The UI should reflect this change immediately (e.g., show a "Completed" badge instead of the button).

**Execution Instructions:**
- Initialize the necessary architectural layers: `Achievement` (Entity), `AchievementRepository` (Interface extending JpaRepository), `AchievementService` (Business logic), and `AchievementController` (Handling HTTP requests).
- Write the Thymeleaf templates with some basic CSS styling so the interface is clean, readable, and functional.
- Please place the classes in a standard Spring Boot package structure (e.g., `com.example.achievement.controller`, `.service`, `.model`, etc.).

We run the output as the next prompt. At this point, we've spent about 10 mins to set all this up. From creating our git repo to writing the prompt out. Took about 7mins to generate the whole project.

Issue #1: Tells me to run ./gradlew bootrun but it doesn't have gradlew.
Issue #2: Wrong compile version. The gradle provided by antigravity cannot support Java 21. Had to go Spring INitializr to get the correct one.
- Still not working, attempt to fix this via AI
- The fix was to install JDK 21 globally
- Still not working
- Asked Gemini, found the fix as to set Build Tool -> Gradle to JDK 21

We see the output now. It's pretty basic but has enough functionality. Now, we want to integrate it with our Template Microservice and to do that we need to figure out how we can get Gemini to read it.