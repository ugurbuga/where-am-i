# Where Am I?

Download
--------
Gradle:

```gradle
repositories {
  maven { url "https://jitpack.io" }
}

dependencies {
  implementation 'com.github.bumptech.glide:glide:4.15.1'
}
```

How do I use "WhereAmI"?
-------------------
You need to add init method to application class.<br/>
init method has 2 property.<br/>
First parameter, you need set application class.<br/>
Second parameter, you can close/open logcat loging.

Simple use cases will look something like this:

```kotlin
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        WhereAmI.init(application = this, logEnabled = true)
    }
}
```
