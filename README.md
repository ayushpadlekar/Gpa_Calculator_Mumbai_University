# MU GPA Calculator Android App

<div align="left">
    <img src="Screenshots/Mu_Gpa_Logo.png" alt="App Logo" height="100">
</div>

**( MU = Mumbai University )**

A GPA calculator Android app to calculate gpa based on Mumbai University Results. </br>
- The app has easy to use & modern UI design.
- You can calculate the CGPA, SGPA by entering the values.
- You can view Bar Graph Chart for better visualization of data.
- You can Save the calculated results and view it anytime.

<p><b> Download & test the App in your phone ⬇️</b> </br>
    https://github.com/ayushpadlekar/Gpa_Calculator_Mumbai_University/releases/tag/v0.0.1 </p>

</br>

## Utilized Technologies 🛠️
 - **Programming:** Jetpack Compose & Kotlin

 - **APIs and Libraries:**
   - [**Google Maps API**](https://developers.google.com/maps/documentation/android-sdk) : for Location, Maps, Markers, Info window & Satellite view
   - [**Open Weather API**](https://openweathermap.org/api) : to get current weather updates in a particular area (in this app - Mumbai)
   - [**World Time Api**](https://worldtimeapi.org) : to fetch Universal Timestamps independently
   - [**Firebase**](https://firebase.google.com) : for Authentication, Realtime Database, Storage & Analytics
   - [**Async-Http Client**](https://github.com/android-async-http/android-async-http) : to make asynchronous HTTP requests and handle Json responses
   - [**Zxing**](https://github.com/zxing/zxing) : for QR code scanning with camera
   - [**Lottie**](https://lottiefiles.com) : for Animations throughout the app
   - [**Picasso**](https://github.com/square/picasso) : for easy Image Loading and Caching.

 - **Development Tools:** Android Studio, Figma, Github

</br>

## Screenshots 📸

CGPA Screen &nbsp;|&nbsp; CGPA Screen with Input & Results &nbsp;|&nbsp; Saved Screen &nbsp;|&nbsp; SGPA Screen

<div style="display: flex; overflow-x: auto; white-space: nowrap;">
  <img src="Screenshots/CGPA_Screen1.png" style="flex: 1 0 auto; width: 15%;">
    &nbsp;
  <img src="Screenshots/CGPA_Screen2.png" style="flex: 1 0 auto; width: 15%;">
    &nbsp;
  <img src="Screenshots/Saved_Screen.png" style="flex: 1 0 auto; width: 15%;">
    &nbsp;
  <img src="Screenshots/SGPA_Screen.png" style="flex: 1 0 auto; width: 15%;">
</div>

</br>

## Features 💡

1. **User Interface**
    - Thematic Colors, Fonts & Backgrounds
    - Splash Screen Animations & Onboarding Screens
    - Bottom Navigation Bar to navigate between various fragments smoothly

2. **User Authentication and Details**
    - Google Sign-In with Firebase Authentication for secure and fast user login
    - Getting details of user like - Name, Phone, Birth date, Photo and storing it in Firebase Database

3. **Map & Nearby Bicycle Points**
    - Integrated Google Map with Satellite view and GPS locations
    - Added custom markers on map as Bicycle Points
    - Info Window showing information of each bicycle point (showed on clicking any marker)
    - 'Get Directions' Button redirecting to Gmaps app showing route from user location to Bicycle point

4. **Current Weather Info**
    - Displayed live weather information in a small view on the top right corner
    - Current Temperature, current condition and it's weather icon is showed

5. **Bicycle Options**
    - Sponsored Bicycles category with options from many different bicyle brands
    - Multiple bicycle types like MTB, Geared, Electric, Road or Cargo
    - Card Flip Views to show all the information of each bicycle

6. **Rent A Bicycle**
    - Bottom Sheet with 3 options - Scan QR Code, Enter Bicycle Number & Choose Bicycle Options
    - Bicycle Unlocks and Ride starts only when all the permissions are ON, especially bluetooth
    - The Rental details and bicycle Status is then updated in firebase database

7. **Ride Tracking & Updates**
    - Real-Time ride tracking with Chronometer timer for duration
    - World Time Api for syncing time irrespective of user's device time
    - Live Amount updates on calculation based on ride duration and bicycle rate per min
    - Ending ride updates rental details like start-time, end-time, duration, amount and wallet balance in Firebase

7. **Wallet**
    - Simulated Wallet features with Firebase
    - Add or withdraw money in wallet balance or pay Security deposit
    - Show all transactions with timestamp and details

</br>

## Demonstration 📲

### • Exploring the App / Walkthrough ⬇️
&nbsp; &nbsp; Sign-Up, Map, Bicycle Options, Profile & Wallet

https://github.com/user-attachments/assets/16ab6b86-8b41-41cb-a145-d2f303678418

</br>
