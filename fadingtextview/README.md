# FadingTextView
A textview that changes its content automatically every few seconds

![example](https://lh3.googleusercontent.com/VG5EYCttms05zRFrOxRk8FLz9t3rvR0Qi1dX12q6PCi_ZslrX0laeGAEIG1uNYFouKREnFrJ5HUSNroa8GO_N5FHpLsRlDw0otDIoU_GRP909fFA8lNXo4mwuaZkduomeOU7TeNIdD9VM7C27HytVQhZigfGxrFI9rDDQ-2fEx7ApA8L_bqlIfF494fNf4Ds6-IqHeitakZS3uFyazskihYmmJJcwto2p9kuUnpou3bTtENMf04ZeV3DFt7PikYGuBVB13zy-0TPJd_w1RkGx7AS2acNiGo4xgYksP8PBrmv8TxWm4wXH9uD82BbeDgszgWBzOA71LnT0Bh4OAWm8ln7uQTHGX9cOhm1q57_KRnRpLFiOR_aKVoaJbDAylzofsAXLQeSi9JMeh6iqHWAUJcWEJsQ4jRs5r-IDzYWZxzN2N8OAhoCcVJaXR1Y5HkpZFppso8S8OBdWJrS9J9DF4fiMNwEhi-qQ-LBitj8vVN3Gv8xUA5e9cRw4Duf2boqTumhP8jI5n98-rpDmXv2UoZGBEqSn9Gxx_m0b8FDmpCBjl04lEvcj20KM8TLRijLxeeRfNfCoctpKBOGLJRQj8lMqSzVQVkP1pgyweRArHc-_2rsugRiTB5RYv1GN9XhNM0upLsRHvHvbqFZ1qJOR0qAKjktyfvcBcvFuN9zjYg=w800-h231-no)

##Usage

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
            android:height="64dp"
            app:texts="@array/examples" />
```

###Timeout

You can set the time you want each text to appear by using the timeout attribute and specify the length of time in milliseconds. Like so:

``` xml
app:timeout="500"
```

```  xml
<com.tomer.fadingtextview.FadingTextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:height="64dp"
            app:timeout="500"
            app:texts="@array/examples" />
```
