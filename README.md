# School Planner App (Mobile Android App)

## About
This mobile Android application allows users to create a school term, then associate courses and assesments with that term. The information can be saved, updated, and deleted. Notifications can also be set for upcoming start and end dates in the app. All data is stored locally on the device using the Room framework that is built on a SQLite database. 

## Development
The development process involved gathering application requirements, then doing a simple wireframe and storyboard of the application flow. Android studio was used as the IDE to develop the app using Java. Java was used over Kotlin simply due to language familiarity, although Kotlin certainly would have been a less verbose language to use. The application mostly sticks to a Model View Controller design layout. An emulator was used for development rather than an actual device due to availability constraints. . After this a signed APK was generated to be used for app deployment.

## Android Vesioning 
The appplication targets Android versions 8 -12 (SDK 26 - 32). 

## Roadmap
1. The application could be improved be having a better navigation system that allows usings to not have to drill down one piece at a time. 
2. Better design and styling could be used throughout. Branding, coloring, and a better logo would improve the application from the user's perspective.
3. Most of the inputs do not have any sort of validation or error handling. Updating this could be very beneficial for the application.
4. A search feature that allows for users to search for a piece of information on the app could also be useful.
5. Testing on a development device would also be beneficial.
6. Finally the app could be made "DRY" by avoiding much of the repetetive code and turning it into separate helper methods.


![Storyboard](https://github.com/mallen13/school-planner-android/blob/master/storyboard.png)