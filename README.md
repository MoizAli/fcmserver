# FCM Java Server

A java springboot application which uses Firebase Cloud Messaging (FCM) to send notifications to Android clients when an Image is uploaded through an Androidthings IOT device.


![Diagram] (https://drive.google.com/open?id=167VfCmtkaosrp12_JFJlMMMCM1mhP9j5)

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


### Installation

Install the dependencies and devDependencies and start the server.
* Install MySql and follow the link to create a database [MySql](https://spring.io/guides/gs/accessing-data-mysql/)
* Create a folder gcloud in root folder. Add your serviceAccountKey.json file. Follow [FCM Server](https://firebase.google.com/docs/cloud-messaging/migrate-v1) for more information on how to create the key.
    * Set your database url in FirebaseConfig class.
* Import pom.xml from source folder and run reimport dependencies
* Create new java application from the configuration menu to run the project:
    *  copy enviornment variables from src -> main -> resources -> application.properties

Enjoy :)
