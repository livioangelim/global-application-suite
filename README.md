<strong> **DO NOT DISTRIBUTE OR PUBLICLY POST SOLUTIONS TO THESE LABS. MAKE ALL FORKS OF THIS REPOSITORY WITH SOLUTION CODE PRIVATE. PLEASE REFER TO THE STUDENT CODE OF CONDUCT AND ETHICAL EXPECTATIONS FOR COLLEGE OF INFORMATION TECHNOLOGY STUDENTS FOR SPECIFICS. ** </strong>

# WESTERN GOVERNORS UNIVERSITY: D387 – ADVANCED JAVA

# TASKS

## A.  Create your subgroup and project in GitLab using the provided web link and the "GitLab How-To" web link by doing the following:
> * Clone the project to the IDE.
> * Commit with a message and push when you complete each requirement listed in parts B1, B2, B3, and C1.
>> Note: You may commit and push whenever you want to back up your changes, even if a requirement is not yet complete.
>
> * Submit a copy of the GitLab repository URL in the "Comments to Evaluator" section when you submit this assessment.
> * Submit a copy of the repository branch history retrieved from your repository, which must include the commit messages and dates.
>> Note: Wait until you have completed all the following prompts before you create your copy of the repository branch history.

> MODIFICATIONS:
> * Updated and structured README.md with step-by-step documentation of task requirements aiming to enhance professional communication (Task D).
> * Configured the project to use Maven for build management and integrated Angular for the front-end framework. 

## B.  Modify the Landon Hotel scheduling application for localization and internationalization by doing the following:
### B1. Install the Landon Hotel scheduling application in your integrated development environment (IDE). Modify the Java classes of application to display a welcome message by doing the following:
> * a.  Build resource bundles for both English and French (languages required by Canadian law). Include a welcome message in the language resource bundles.
> * b.  Display the welcome message in both English and French by applying the resource bundles using a different thread for each language.
>> Note: You may use Google Translate for the wording of your welcome message.
 
> MODIFICATIONS:
> * Created resource bundles "welcome" to handle welcome messages in English (welcome_en_US) and French (welcome_fr_CA).
> * Created LocaleResourceReader.java in the /rest package to handle localization of welcome messages in different languages (English and French) using Java ResourceBundles.
> * Added ResourcesController.java in the /rest package to define a REST endpoint /welcome that returns localized welcome messages. The controller uses a multi-threaded approach to concurrently fetch messages in both english and french languages.
> * Modified app.component.ts and app.component.html to display the retrieved welcome messages on the front end.

### B2. Modify the front end to display the price for a reservation in currency rates for U.S. dollars ($), Canadian dollars (C$), and euros (€) on different lines.
> Note: It is not necessary to convert the values of the prices.

> MODIFICATIONS:
> * Updated app.component.html to include additional lines for displaying room prices in USD, Canadian dollars, and euros. Each price is shown on a separate line for better clarity.
> * Modified app.component.ts to support the display of room prices in multiple currencies converting the values. Utilized Angular's Math library to handle potential formatting requirements.

### B3.  Display the time for an online live presentation held at the Landon Hotel by doing the following:
> * a.  Write a Java method to convert times between eastern time (ET), mountain time (MT), and coordinated universal time (UTC) zones.
> * b.  Use the time zone conversion method from part B3a to display a message stating the time in all three times zones in hours and minutes for an online, live presentation held at the Landon Hotel. The times should be displayed as ET, MT, and UTC.

> MODIFICATIONS:
> * Created TimeZoneController.java, that uses a Java method to handle time zone conversions to Eastern Time (ET), Mountain Time (MT), and Coordinated Universal Time (UTC).
> * Modified app.component.html and  to display the online presentation times for ET, MT, and UTC.
> * Updated app.component.ts to fetch presentation times from the backend on initialization and store them in the presentationTimes array.

## C.  Explain how you would deploy the Spring application with a Java back end and an Angular front end to cloud services and create a Dockerfile using the attached supporting document "How to Create a Docker Account" by doing the following:
### C1. Build the Dockerfile to create a single image that includes all code, including modifications made in parts B1 to B3. Commit and push the final Dockerfile to GitLab.

> MODIFICATIONS:
> * Added Dockerfile for Spring Boot and Angular application.

### C2. Test the Dockerfile by doing the following:
> * Create a Docker image of the current multithreaded Spring application.
> * Run the Docker image in a container and give the container a name that includes D387_[student ID].
> * Submit a screenshot capture of the running application with evidence it is running in the container.

> MODIFICATIONS:
> * Added JAR file and backup version of the Spring Boot application, D387_sample_code-0.0.2-SNAPSHOT.jar
> * Added image into /screenshot with proof of application running in the container. 

### C3.  Describe how you would deploy the current multithreaded Spring application to the cloud. Include the name of the cloud service provider you would use.
> Note: Remember to commit and push your changes to GitLab.

> MODIFICATIONS:
> Created the Step-by-Step Guide to Deploying on Azure text into /deploying-on-cloud

## D.  Demonstrate professional communication in the content and presentation of your submission.


