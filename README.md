# Questionnaire - A Profile Creator

Questionnaire is a profile creator app which takes information from app users and curates them.


## Architecture & Design Pattern

Reach follows a Model-View-Presenter design pattern. A clear segregation among the three elements helps in achieving a loosely coupled but highly cohesive application.
In order to create a highly testable piece of code, Dependency Injection has been used. Dagger2 helps in facilitating this.

#### Rule of Thumb

1. Presenter should be free from Android framework classes. This help in testing Presenter layer on development machine without hardware device.
2. All communications between MVP layers should be implemented using Interfaces.


### Design Philosophy
User Interface has been kept simple and intuitive. 

* User can log new answers and can view previous entries.
* Since some of the questions are dependent on previous ones, user have been provided with facility to view previous 2 questions.

### Testing Framework
1. Local Unit Test
These tests are compiled to run locally to run on local JVM. All static methods have been provided with Unit Tests.
* Extension: Architecture allows it to extended using Robolectric or mock objects to emulate dependencies' behavior.

2. Instrumented tests
These tests have access to instrumentation information, such as the Context for the app under test. All activities and fragments have been provided with Instrumentation tests using AndroidJUnitRunner and Espresso.
* Extension: Can be extended further to be used with Robolectric or Mockito.

3. Firebase Test Lab
Questionnaire App has been tested on Firebase Test Lab using Robo Test and Instrumentation test. Robo script and test results available inside project folder.


## 3rd Party Libraries

* Android Support Libraries (v 28.0.3)
* AndroidX (v 1.0.0)
* Dagger (v 2.19)
* Gson (v 2.7)
* Espresso (v 3.3.0-alpha02)
* Firebase Realtime Database (v ktx:19.2.0)
* Firebase Crashlytics (v 2.10.1)


## Credits & Acknowledgments

* Dribbble
* GitHub
* StackOverflow
* Medium
