package th.co.mfec.androidsecuritydemo

import java.util.*

class Test {

    fun main(args: Array<String>) {
        var arrayList = arrayListOf<Int>()
        val roundOfZero = 30
        val roundOfMaxValue = 10

        val maxValue = 100

        val item = arrayOf(0,10,20,30,40,50,60,70,80,90,100)
        val listItem = arrayListOf(item)

        for (x in 0..100) {
            if (x < roundOfZero) {
                arrayList.add(0)
                if (x == roundOfZero - 1){

                }
            } else {
                if (x < roundOfZero + roundOfMaxValue) {
                    arrayList.add(maxValue)
                } else {
                    arrayList.add(10)
                }
            }
        }
    }
}