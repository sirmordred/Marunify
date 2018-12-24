# Marunify
Spotify-like music player app which using MSSQL database

## Android Settings:

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

After making all necessary changes open Android Studio again and click 'Open an existing Android Studio Project' find and open Marunify2

## SQL Server Settings:
- Use Microsoft SSMS 2018 Version
- Download MSSQL_backup.sql database backup and restore in SSMS
- Right-click "Security/logins" on object explorer and Create "New user", select as "SQL Server connection" instead of "Windows authorization" and give username as "marunex" and password as "marunex123", and select "MARUNIFY" as default database of that user and give all the 'privileges' on security section
- Type "sq" in search bar of windows OS, open "SQL Server 20XX Configuration Manager" and find "protocols for SQLEXPRESS", right-click TCP/IP and select "Properties", disable ipv5 and ipv6, ipv4 should be enabled, and in "IPAll" section dynamic port should be empty, and static port should be 1433
- Thats all

XXX NOTE: Only run this app from Android Emulator, and make sure you have a user as 'marunex' and password as 'marunex123' and database as 'MARUNIFY' on SSMS (SQL server management studio)
