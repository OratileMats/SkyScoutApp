# 🐦 SkyScoutApp

SkyScoutApp is a mobile bird tracking application designed for bird enthusiasts in and around **Johannesburg, South Africa**. The app allows users to record information about different bird species, discover bird hotspots, and view their current location on an interactive map. SkyScoutApp integrates modern location services and cloud storage to provide a seamless birdwatching experience.

---

## 📱 Features

* **Bird Records**

  * Store information about different bird species
  * Save sightings for future reference

* **Bird Hotspots Around Johannesburg**

  * View known birdwatching hotspots on Google Maps
  * Discover nearby locations popular for bird sightings

* **Live Location Tracking**

  * Access the user’s current location using Google Maps
  * Navigate and explore hotspots in real time

* **Cloud Data Storage**

  * Uses **Firebase Realtime Database / Firestore** to store bird data securely
  * Ensures data persistence across sessions
  * Data about the birds can be retrieved and displayed from the database

* **Metric System Toggle**

  * Users can switch between different metric systems (e.g. kilometers / miles)

---

## 🗺️ Technologies Used

* **Android (Java / Kotlin)**
* **Firebase Database** – cloud-based data storage
* **Google Maps API** – location services and hotspot visualization
* **Google Location Services** – access to user’s current location

---

## 🔧 Installation & Setup

1. **Clone the repository**
   git clone https://github.com/OratileMats/SkyScoutApp.git
   

2. **Open the project**

   * Open **Android Studio**
   * Select **Open an existing project**
   * Navigate to the cloned folder

3. **Firebase Setup**

   * Create a Firebase project in the Firebase Console
   * Add an Android app to the Firebase project
   * Download the `google-services.json` file
   * Place it in the app-level directory

4. **Google Maps API Key**

   * Create an API key from the Google Cloud Console
   * Enable **Maps SDK for Android**
   * Add the API key to your `local.properties` or `AndroidManifest.xml`

5. **Run the App**

   * Connect an Android device or start an emulator
   * Click **Run** in Android Studio

---

## 📍 Permissions Required

The app requires the following permissions:

* Internet access
* Location access (Fine / Coarse)
* Google Maps services

These permissions are used strictly for app functionality such as locating the user and displaying bird hotspots.

---

## 🚀 Future Enhancements

* User authentication
* Upload bird photos
* Offline hotspot access
* Advanced filtering for bird species
* Community-shared sightings

---

## 👤 Author

**Oratile Mats**
GitHub: [OratileMats](https://github.com/OratileMats)

---

## 📄 License

This project is for educational purposes. Licensing information can be added if the project is extended or published publicly.

---

Happy birdwatching with **SkyScoutApp** 🐦🌍
