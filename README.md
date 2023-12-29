# FadingTextView
[![](https://jitpack.io/v/rosenpin/fading-text-view.svg)](https://jitpack.io/#rosenpin/fading-text-view)
<a target="_blank" href="https://rosenpin.github.io/fading-text-view/fadingtextview/com.tomer.fadingtextview/-fading-text-view/index.html"><img src="https://img.shields.io/badge/docs-FadingTextView-yellowgreen.svg" alt="DOCS" /></a> 
<a target="_blank" href="https://developer.android.com/reference/android/os/Build.VERSION_CODES.html#GINGERBREAD"><img src="https://img.shields.io/badge/API-15%2B-blue.svg?style=flat" alt="API" /></a> 
<a target="_blank" href="https://www.paypal.com/donate/?hosted_button_id=9M8LL9GJXELBN" title="Donate using PayPal"><img src="https://img.shields.io/badge/paypal-donate-yellow.svg" /></a>

A TextView that changes its content automatically every few seconds

![example](https://lh3.googleusercontent.com/VG5EYCttms05zRFrOxRk8FLz9t3rvR0Qi1dX12q6PCi_ZslrX0laeGAEIG1uNYFouKREnFrJ5HUSNroa8GO_N5FHpLsRlDw0otDIoU_GRP909fFA8lNXo4mwuaZkduomeOU7TeNIdD9VM7C27HytVQhZigfGxrFI9rDDQ-2fEx7ApA8L_bqlIfF494fNf4Ds6-IqHeitakZS3uFyazskihYmmJJcwto2p9kuUnpou3bTtENMf04ZeV3DFt7PikYGuBVB13zy-0TPJd_w1RkGx7AS2acNiGo4xgYksP8PBrmv8TxWm4wXH9uD82BbeDgszgWBzOA71LnT0Bh4OAWm8ln7uQTHGX9cOhm1q57_KRnRpLFiOR_aKVoaJbDAylzofsAXLQeSi9JMeh6iqHWAUJcWEJsQ4jRs5r-IDzYWZxzN2N8OAhoCcVJaXR1Y5HkpZFppso8S8OBdWJrS9J9DF4fiMNwEhi-qQ-LBitj8vVN3Gv8xUA5e9cRw4Duf2boqTumhP8jI5n98-rpDmXv2UoZGBEqSn9Gxx_m0b8FDmpCBjl04lEvcj20KM8TLRijLxeeRfNfCoctpKBOGLJRQj8lMqSzVQVkP1pgyweRArHc-_2rsugRiTB5RYv1GN9XhNM0upLsRHvHvbqFZ1qJOR0qAKjktyfvcBcvFuN9zjYg=w800-h231-no)

## Usage

### Download
FadingTextView is available on [JitPack](https://jitpack.io/#rosenpin/fading-text-view/3.3)

Step 1. Add the JitPack repository to your root build.gradle
``` gradle
allprojects {
  repositories {
     ...
     maven { url 'https://jitpack.io' }
  }
}
```

Step 2. Add the dependency
``` gradle
dependencies {
    implementation 'com.github.rosenpin:fading-text-view:3.3'
}
 ```

### Texts

First, you need to create a string-array in your values folder like so:

``` xml
<string-array name="examples">
     <item>Hello</item>
     <item>Fading TextView</item>
</string-array>
```

Then in your layout

```  xml
<com.tomer.fadingtextview.FadingTextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:texts="@array/examples" />
```

### Timeout

You can set the amount of time that each text is visible by using the timeout attribute and by specifying the length of time in milliseconds. Like so:

``` xml
app:timeout="500"
```

```  xml
<com.tomer.fadingtextview.FadingTextView
            android:id="@+id/fadingTextView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:timeout="500"
            app:texts="@array/examples" />
```
### Updating the view dynamically - Kotlin
To set the text dynamically, you can use

```kotlin
val texts = arrayOf("text1", "text2", "text3")
val fadingTextView = findViewById<FadingTextView>(R.id.fadingTextView)
fadingTextView.setTexts(texts) // You can use an array resource or a string array as the parameter
//fadingTextView.setTexts(R.array.examples)
```

To set the timeout between text changes you can use:

```kotlin
//For text change once every hour
fadingTextView.setTimeout(60.minutes)

//For text change once every half a minute
fadingTextView.setTimeout(0.5.minutes)

//For text change every 10 seconds
fadingTextView.setTimeout(10.seconds)

//For text change every 500 milliseconds (0.5 seconds)
fadingTextView.setTimeout(500.milliseconds)
```

Or you can shuffle texts that you set

```kotlin
fadingTextView.shuffle()
```

### Updating the view dynamically - JAVA
To set the text dynamically, you can use

```java
String[] texts = {"text1","text2","text3"};
FadingTextView FTV = (FadingTextView) findViewById(R.id.fadingTextView);
FTV.setTexts(texts); //You can use an array resource or a string array as the parameter
```

To set the timeout between text changes you can use:

```java
//For text change once every hour
FTV.setTimeout(60, MINUTES);

//For text change once every half a minute
FTV.setTimeout(0.5, MINUTES);

//For text change every 10 seconds
FTV.setTimeout(10, SECONDS);

//For text change every 500 milliseconds (0.5 seconds)
FTV.setTimeout(500, MILLISECONDS);
```

### Shuffle
You can randomize the order of the strings using the `shuffle` method\
Note: you will need to run the `shuffle` method after each time you update the view\
Example:
```
FTV.setTexts(texts);
FTV.shuffle();
```

## Full Documentation

[Click me](https://rosenpin.github.io/fading-text-view/) for the full documentation 

## License

```
Copyright (c) Tomer Rosenfeld 2016-2017

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
