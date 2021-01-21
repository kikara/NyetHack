package com.bignerdranch.nyethack

import java.io.File

class Player(_name :String,
             override var healthPoints: Int = 100,
             var isBlessed: Boolean,
             val isImmortal: Boolean): Fightable {

    override val diceCount: Int = 3
    override val diceSides: Int = 6


    var name = _name
        get() = "${field.capitalize()} of $homeTown"
        set(value) {
            field = value.trim()
        }

    val homeTown by lazy {selectHometown()}

    var currentPosition = Coordinate(0,0)

    override fun attack(opponent: Fightable): Int {
        val damageDealt = if (isBlessed) {
            damageRoll * 2
        } else {
            damageRoll
        }
        opponent.healthPoints -= damageDealt
        return damageDealt
    }

    init {
        require(healthPoints >0, {"healthPoints must be greater than zero"})
        require(name.isNotBlank(), {"Player must have a name."})
    }


    constructor(name: String) : this(name,
            healthPoints=100,
            isBlessed = true,
            isImmortal = false
            )

    fun castFireBall(numFireBalls: Int = 2) =
            println("A glass of Fireball springs into existence. (x$numFireBalls)")

    fun auraColor():String {
        val auraVisible = isBlessed && healthPoints>50 ||isImmortal
        return if (auraVisible) "Green" else "NONE"
    }

    fun formatHealthStatus() =
            when (healthPoints) {
                100 -> "is in excellent condition!"
                in 90..99 -> "has a few scratches."
                in 75..89 -> if(isBlessed) {
                    "has some minor wounds, but is healing quite quickly"
                }
                else {
                    "has some minor wounds."
                }
                in 15..74 -> "looks pretty hurt."
                else -> "is in awful condition!"
            }

    private fun selectHometown() =
        File("E:\\IdeaProjects\\NyetHack\\src\\data\\towns.txt").readText().split("\n").random()


}