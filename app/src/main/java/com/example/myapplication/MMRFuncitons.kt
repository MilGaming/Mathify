package com.example.myapplication

import kotlin.math.log10
import kotlin.math.min
import kotlin.random.Random

var scoreAvg = 3.32
var baseScore = 50
fun increaseScore(streak: Int, time: Int, mmr: Int, placePoint :Int): Int {
    val streakMultiplier = (log10((streak + 2).toDouble()) +0.5) // decreases as streak increases
    val timeBonus = if (time <= 5) (5 - time) * 10 else 0 // larger bonus for time less than 5 seconds
    var points = mmr

    var score = min(((baseScore + timeBonus) * streakMultiplier).toInt(), 150)

    if(placePoint < 5) {
        score = (score * scoreAvg).toInt()
    }

    points += score

    return points
}

fun decreaseScore(streak: Int, time: Int, mmr: Int , placePoint :Int): Int {
    val streakPenalty = (log10((streak + 2).toDouble()) +0.8) // increases as streak increases
    val timePenalty = if (time <= 5) time * 2 else 10 // smaller penalty for time less than 5 seconds
    var points = mmr

    var penalty = min(((baseScore + timePenalty) * streakPenalty).toInt(),100)

    if(placePoint <= 5) {
        penalty = (penalty * scoreAvg).toInt()
    }

    points -= penalty // decrease the score

    if (points < 0) {
        points = 0 // ensure the score doesn't go below zero
    }
    return points
}

//Update math question based on MMR
//Question scalabililty------------------------------------------------------------
fun updateAddQuestion(mmr: Int, random: Random): Pair<Int, Int> {
    return when {
        mmr >= 2500 -> Pair(random.nextInt(100,200), random.nextInt(100,200))
        mmr >= 2000 -> Pair(random.nextInt(80,180), random.nextInt(80,180)) // Overlap with above and below
        mmr >= 1500 -> Pair(random.nextInt(60,160), random.nextInt(60,160)) // Overlap with above and below
        mmr >= 1000 -> Pair(random.nextInt(40,140), random.nextInt(40,140)) // Overlap with above and below
        mmr >= 500 -> Pair(random.nextInt(20,120), random.nextInt(20,120)) // Overlap with above and below
        else -> Pair(random.nextInt(20), random.nextInt(20))
    }
}

// Update subtraction question based on MMR
fun updateSubQuestion(mmr: Int, random: Random): Pair<Int, Int> {
    return when {
        mmr >= 2500 -> {
            val num1 = random.nextInt(100, 200)
            val num2 = random.nextInt(50, min(num1, 100))
            Pair(num1, num2)
        }
        mmr >= 2000 -> {
            val num1 = random.nextInt(80, 180)
            val num2 = random.nextInt(40, min(num1, 90))
            Pair(num1, num2)
        }
        mmr >= 1500 -> {
            val num1 = random.nextInt(60, 160)
            val num2 = random.nextInt(30, min(num1, 80))
            Pair(num1, num2)
        }
        mmr >= 1000 -> {
            val num1 = random.nextInt(40, 140)
            val num2 = random.nextInt(20, min(num1, 70))
            Pair(num1, num2)
        }
        mmr >= 500 -> {
            val num1 = random.nextInt(20, 120)
            val num2 = random.nextInt(10, min(num1, 60))
            Pair(num1, num2)
        }
        else -> {
            val num1 = random.nextInt(20)
            val num2 = random.nextInt(min(num1, 20))
            Pair(num1, num2)
        }
    }
}

// Update multiplication question based on MMR
fun updateMulQuestion(mmr: Int, random: Random): Pair<Int, Int> {
    return when {
        mmr >= 2500 -> Pair(random.nextInt(10, 20), random.nextInt(10, 20))
        mmr >= 2000 -> Pair(random.nextInt(9, 19), random.nextInt(9, 19))
        mmr >= 1500 -> Pair(random.nextInt(8, 18), random.nextInt(8, 18))
        mmr >= 1000 -> Pair(random.nextInt(7, 17), random.nextInt(7, 17))
        mmr >= 500 -> Pair(random.nextInt(6, 16), random.nextInt(6, 16))
        else -> Pair(random.nextInt(5, 15), random.nextInt(5, 15))
    }
}

// Update division question based on MMR
fun updateDivQuestion(mmr: Int, random: Random): Pair<Int, Int> {
    return when {
        mmr >= 2500 -> {
            val num2 = random.nextInt(10, 20)
            val num1 = num2 * random.nextInt(10, 20)
            Pair(num1, num2)
        }
        mmr >= 2000 -> {
            val num2 = random.nextInt(9, 19)
            val num1 = num2 * random.nextInt(9, 19)
            Pair(num1, num2)
        }
        mmr >= 1500 -> {
            val num2 = random.nextInt(8, 18)
            val num1 = num2 * random.nextInt(8, 18)
            Pair(num1, num2)
        }
        mmr >= 1000 -> {
            val num2 = random.nextInt(7, 17)
            val num1 = num2 * random.nextInt(7, 17)
            Pair(num1, num2)
        }
        mmr >= 500 -> {
            val num2 = random.nextInt(6, 16)
            val num1 = num2 * random.nextInt(6, 16)
            Pair(num1, num2)
        }
        else -> {
            val num2 = random.nextInt(5, 15)
            val num1 = num2 * random.nextInt(5, 15)
            Pair(num1, num2)
        }
    }
}