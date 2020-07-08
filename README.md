# react-native-fawry-sdk

## Getting started

`$ npm install react-native-fawry-sdk --save`

OR

`$ yarn add react-native-fawry-sdk`

### Mostly automatic installation RN < 0.60

`$ react-native link react-native-fawry-sdk`

#### Extra android setup

add this line to build.gradle

```
allprojects {
    repositories {
        ...
        flatDir {
            dirs "$rootDir/../node_modules/react-native-fawry-sdk/android/libs"
        }
    }
}
```

## Usage

```javascript
import FawrySdk from "react-native-fawry-sdk";

// TODO: What to do with the module?
FawrySdk;
```
