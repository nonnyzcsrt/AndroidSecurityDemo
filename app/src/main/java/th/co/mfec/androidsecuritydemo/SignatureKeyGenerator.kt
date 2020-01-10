package th.co.mfec.androidsecuritydemo

import th.co.mfec.androidsecuritydemo.utils.EncryptUtil


fun main(args: Array<String>) {

    //val shaSecure = "1000:197cfdf54ec44be15ab8a870939ff98e:b799a5379a14714c4875de7efdafc886248ff30c19d2285d9abe71796807830b19b0bac0bbccfcaf69ce9a97ac938de83f23e6c36aa59660c01ccd906f2638c6"
    //val shaKeystore = "5E8036578C4E691B696FF6F7E74DE734C312E39A"

    val shaSecure = "1000:1277c46aa8ec51b05e3bf14fb4119a87:31e95d15fe7433651395fa9dab906a27300ca3c5c07f14c3f9d2edd8a1a7e4eb284cb3afd3652d481d64ba25e47affba79bf1df2c5fb5ce9d4e1ee7072aca14e"
    val shaKeystore = "3A51BC8DF0D73CDAF92DED7B722463801B25AB29"

    val s1 = EncryptUtil.generateStrongPasswordHash(shaKeystore)
    println("generateStrongPasswordHash : " + s1)

    val s2 = EncryptUtil.validatePassword(shaKeystore, shaSecure)
    println("validatePassword : " + s2 + "")


}