# Video Application

## Features
* Fetch and display playlist from remote API, with offline cache.
* Play streaming video using [ExoPlayer](https://github.com/google/ExoPlayer).
* Support portrait and landscape layouts on both playlist and player view.

## Requirements
* Android SDK 27
* Android Plugin for Gradle 3.0.1
* Gradle 4.1

## How to build
First, update `videoApiBaseUrl` field in `./gradle.properties` to match your environment.

```
videoApiBaseUrl=http://localhost:8080/jsons/
```

Then build using following command:

```
./gradlew build
```

## How to test
```
./gradlew check
```

## How to integration test
Requires device or emulator

```
./gradlew connectedAndroidTest
```
