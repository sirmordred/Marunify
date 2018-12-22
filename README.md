# Marunify2
Spotify-like music player app which using MSSQL database

# How-to-open

* Download this repo as zip file
* Extract somewhere
* Create new/empty android project on your Android Studio (ExampleApp)
* Go to your ExampleApp directory
* Open the following files via texteditor and compare between them:
- ExampleApp/gradle/wrapper/gradle-wrapper.properties and Marunify2/gradle/wrapper/gradle-wrapper.properties
- ExampleApp/build.gradle and Marunify2/build.gradle
- ExampleApp/app/build.gradle and Marunify2/app/build.gradle
And make necessary changes from ExampleApp gradle definitions to MArunify2 gradle definitions
For example: ExampleApp/app/build.gradle has 'com.android.support:appcompat-v7:28.0.0' but Marunify2/app/build.gradle has 'com.android.support:appcompat-v7:14.0.0'
then change 'com.android.support:appcompat-v7:14.0.0' to 'com.android.support:appcompat-v7:28.0.0' in Marunify2/app/build.gradle
