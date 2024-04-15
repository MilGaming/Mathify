package com.example.myapplication.scripts

import kotlin.math.log10
import kotlin.math.min
import kotlin.random.Random

var scoreAvg = 3.32
var baseScore = 50
var averageMMR = 1500
var adjustmentFactor = 0.1
fun increaseScore(streak: Int, time: Int, mmr: Int, placePoint :Int): Int {
    val streakMultiplier = (log10((streak + 2).toDouble()) +0.5) // decreases as streak increases
    val timeBonus = if (time <= 5) (5 - time) * 10 else 0 // larger bonus for time less than 5 seconds
    var points = mmr

    var score = min(((baseScore + timeBonus) * streakMultiplier).toInt(), 150)

    if(placePoint < 5) {
        score = (score * scoreAvg).toInt()
    }

    score = if (mmr < averageMMR) {
        (score * (1 + ((averageMMR - mmr) / averageMMR.toDouble() * adjustmentFactor))).toInt()
    } else {
        (score * (1 - ((mmr - averageMMR) / averageMMR.toDouble() * adjustmentFactor))).toInt()
    }

    points += score

    points = min(points, 3000) // ensure the score doesn't go above 3000

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

    penalty = if (mmr > averageMMR) {
        (penalty * (1 + ((mmr - averageMMR) / averageMMR.toDouble() * adjustmentFactor))).toInt()
    } else {
        (penalty * (1 - ((averageMMR - mmr) / averageMMR.toDouble() * adjustmentFactor))).toInt()
    }

    points -= penalty // decrease the score

    points = maxOf(points, 0) // ensure the score doesn't go below zero

    return points
}

//Update math question based on MMR
//Question scalabililty------------------------------------------------------------
fun updateAddQuestion(mmr: Int, random: Random): Pair<Int, Int> {
    return when {
        mmr >= 2500 -> Pair(random.nextInt(41,101), random.nextInt(41,101))
        mmr >= 2000 -> Pair(random.nextInt(26,51), random.nextInt(26,51)) // Overlap with above and below
        mmr >= 1500 -> Pair(random.nextInt(11,31), random.nextInt(11,31)) // Overlap with above and below
        mmr >= 1000 -> Pair(random.nextInt(8,16), random.nextInt(8,16)) // Overlap with above and below
        mmr >= 500 -> Pair(random.nextInt(5,10), random.nextInt(5,10)) // Overlap with above and below
        else -> Pair(random.nextInt(6), random.nextInt(6))
    }
}

// Update subtraction question based on MMR
fun updateSubQuestion(mmr: Int, random: Random): Pair<Int, Int> {
    return when {
        mmr >= 2500 -> {
            val num1 = random.nextInt(41, 101)
            val num2 = random.nextInt(41, min(num1, 101))
            Pair(num1, num2)
        }
        mmr >= 2000 -> {
            val num1 = random.nextInt(26, 51)
            val num2 = random.nextInt(26, min(num1, 51))
            Pair(num1, num2)
        }
        mmr >= 1500 -> {
            val num1 = random.nextInt(11, 31)
            val num2 = random.nextInt(11, min(num1, 31))
            Pair(num1, num2)
        }
        mmr >= 1000 -> {
            val num1 = random.nextInt(8, 16)
            val num2 = random.nextInt(8, min(num1, 16))
            Pair(num1, num2)
        }
        mmr >= 500 -> {
            val num1 = random.nextInt(5, 10)
            val num2 = random.nextInt(5, min(num1, 10))
            Pair(num1, num2)
        }
        else -> {
            val num1 = random.nextInt(6)
            val num2 = random.nextInt(min(num1, 6))
            Pair(num1, num2)
        }
    }
}

// Update multiplication question based on MMR
fun updateMulQuestion(mmr: Int, random: Random): Pair<Int, Int> {
    return when {
        mmr >= 2500 -> Pair(random.nextInt(11, 16), random.nextInt(11, 16))
        mmr >= 2000 -> Pair(random.nextInt(11, 15), random.nextInt(6, 10))
        mmr >= 1500 -> Pair(random.nextInt(6, 10), random.nextInt(4, 10))
        mmr >= 1000 -> Pair(random.nextInt(4, 8), random.nextInt(3, 5))
        mmr >= 500 -> Pair(random.nextInt(2, 6), random.nextInt(2, 4))
        else -> Pair(random.nextInt(2, 4), random.nextInt(1, 4))
    }
}

// Update division question based on MMR
fun updateDivQuestion(mmr: Int, random: Random): Pair<Int, Int> {
    return when {
        mmr >= 2500 -> {
            val num2 = random.nextInt(9, 16)
            val num1 = num2 * random.nextInt(3,15 )
            Pair(num1, num2)
        }
        mmr >= 2000 -> {
            val num2 = random.nextInt(6, 11)
            val num1 = num2 * random.nextInt(2, 10)
            Pair(num1, num2)
        }
        mmr >= 1500 -> {
            val num2 = random.nextInt(4, 8)
            val num1 = num2 * random.nextInt(2, 7)
            Pair(num1, num2)
        }
        mmr >= 1000 -> {
            val num2 = random.nextInt(3, 6)
            val num1 = num2 * random.nextInt(2, 5)
            Pair(num1, num2)
        }
        mmr >= 500 -> {
            val num2 = random.nextInt(2, 5)
            val num1 = num2 * random.nextInt(2, 5)
            Pair(num1, num2)
        }
        else -> {
            val num2 = random.nextInt(1, 4)
            val num1 = num2 * random.nextInt(2, 3)
            Pair(num1, num2)
        }
    }
}