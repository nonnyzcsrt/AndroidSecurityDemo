package th.co.mfec.androidsecuritydemo

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun apiKey(): String
    external fun signatureKey(): String
}