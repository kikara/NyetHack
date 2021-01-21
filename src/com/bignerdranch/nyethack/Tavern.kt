package com.bignerdranch.nyethack

import java.io.File

const val TAVERN_NAME = "Taernyl's Folly"


var totalPurse = 0.0

val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastname = listOf("IronFoot","Fernsworth", "Baggins")

val readOnlyPatronList = patronList.toList()

val menuList = File("E:\\IdeaProjects\\NyetHack\\src\\data\\tavern_menu_items.txt").readText().split("\n")

val uniquePatrons = mutableSetOf<String>()

val patronGold = mutableMapOf<String, Double>()


fun main() {
    if(patronList.contains(("Eli"))) {
        println("The tavern master says: Eli's in the back playing cards.")
    }
    else {
        println("The tavern master says: Eli isn't here.")
    }
    if (patronList.containsAll(listOf("Sophie", "Mordoc"))) {
        println("The tavern master says: Yea, they're seated by the stew kettle.")
    }
    else println("The tavern master says: Nay, they departed hours ago.")


    (0..9).forEach {
        val first = patronList.random()
        val last = lastname.random()
        val name = "$first $last"
        uniquePatrons += name
    }

    uniquePatrons.forEach() {
        patronGold[it] = 6.0
    }

    var orderCount = 0
    while(orderCount<=9) {
        placeOrder(uniquePatrons.random(), menuList.random())
        orderCount++
    }
    displayPatronBalances()

}

fun placeOrder(patronName: String, menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)

    println("$patronName speaks with $tavernMaster about their order.")

    val (type, name, price) = menuData.split(',')

    val message = "$patronName buys a $name ($type) for $price."

    println(message)

    performPurchase(price.toDouble(), patronName)

    val phrase = if(name == "Dragon's breath") {
        "$patronName exclaims ${toDragonSpeak("Ah, delicious $name")}"
    }
    else {
        "$patronName says: Thanks for the $name."
    }

    println(phrase)
}

fun performPurchase(price: Double, patronName: String) {
    val totalPurse = patronGold.getValue(patronName)
    patronGold[patronName] = totalPurse - price
}

fun toDragonSpeak(phrase:String) =
    phrase.toLowerCase().replace(Regex("[aeiou]")) {
        when (it.value) {
            "a" -> "4"
            "e" -> "3"
            "i" -> "1"
            "o" -> "0"
            "u" -> "|_|"
            else -> it.value
        }
    }

fun displayPatronBalances() {
    patronGold.forEach() { patron, balance ->
        println("$patron, balance: ${"%.2f".format(balance)}")
    }
}
