# FCM Java Server

A basic java springboot application which uses Firebase Cloud Messaging (FCM) to send notifications to Android clients when an Image is uploaded through an Androidthings IOT device.

Main functionality includes:
  - Save and FCM push tokens from Android clients
  - Send push tokens to Android clients
  - Saves images from IOT device

### Main Controllers

There are two main controllers. One for providing image upload/download related service.
The other controller for saving and retrieving FCM push tokens

* Main Controller
    *   @PostMapping("/uploadFile")
    *   @GetMapping("/downloadFile/{fileName:.+}")
* Notification Controller
    * @PostMapping(path = "/subscribe")
    * @GetMapping(path = "/all")

And of course Dillinger itself is open source with a [public repository][dill]
 on GitHub.

### Installation

Install the dependencies and devDependencies and start the server.
* Install MySql and follow the link to create a database [MySql](https://spring.io/guides/gs/accessing-data-mysql/)
* Import pom.xml from source folder and run reimport dependencies
* Create new java application from the configuration menu to run the project:
    *  copy enviornment variables from src -> main -> resources -> application.properties

Enjoy :)