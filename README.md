# Room Notes
A simple note Android application using Room, Live Data, and MVVM. This application built with:

#### Programming Language
- Language Name : Java
- Version : 11.0.6

#### IDE (Integrated Development Environment)
- IDE Name : Android Studio
- Version : 3.5

#### Java Build Tools
- Java Build Tools : Gradle
- Java Build Tools Version : 5.4.1

#### SDK Version and SDK Tools
- Target SDK Version : 29
- Min SDK Version : 22
- Android SDK Tools : 26.1.1

#### AndroidX
- Migrate to AndroidX : Yes

#### Dependencies
##### By Gradle
        - implementation fileTree(dir: 'libs', include: ['*.jar'])
        - implementation 'androidx.appcompat:appcompat:1.1.0'
        - implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
        - testImplementation 'junit:junit:4.12'
        - androidTestImplementation 'androidx.test:runner:1.2.0'
        - androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

##### By Third Parties        
- Lifecycle (MVVM and LiveData)

        - implementation "androidx.lifecycle:lifecycle-viewmodel:2.2.0"
        - annotationProcessor "androidx.lifecycle:lifecycle-compiler:2.2.0"
        - implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"

- Room persistence

        - implementation "androidx.room:room-runtime:2.2.4"
        - annotationProcessor "androidx.room:room-compiler:2.2.4"

- Material design
    
        - implementation 'com.google.android.material:material:1.1.0'

# Screnshoot

|<img src=home.gif  align="center" height="400" width="248" ></a> |<img src=add_note.gif  align="center" height="400" width="248" ></a>|<img src=edit_note.gif  align="center" height="400" width="248" ></a>|
|:-----------:|:--------:|:--------:|
| *dashboard* | *adding note*|*editing note* |

|<img src=delete_delete_all_notes.gif  align="center" height="400" width="248" ></a> |<img src=cancel_add_note.gif  align="center" height="400" width="248" ></a>|<img src=cancel_edit_note.gif  align="center" height="400" width="248" ></a>|
|:-----------:|:--------:|:--------:|
| *delete and delete all notes* | *cancel adding note*|*cancel editing note* |
