# react-native-fawry-sdk

**iOS still under development.**

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

Check [example](https://github.com/MahmoudAliIbrahim/react-native-fawry-sdk/tree/main/example)
