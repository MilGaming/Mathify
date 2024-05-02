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

   return Pair(random.nextInt(26,51), random.nextInt(26,51)) // Overlap with above and below
}

// Update subtraction question based on MMR
fun updateSubQuestion(mmr: Int, random: Random): Pair<Int, Int> {
    val num1 = random.nextInt(26, 51)
    val num2 = random.nextInt(15, min(num1+1, 52))
    return Pair(num1, num2)
}

// Update multiplication question based on MMR
fun updateMulQuestion(mmr: Int, random: Random): Pair<Int, Int> {

        return Pair(random.nextInt(11, 15), random.nextInt(6, 10))

}

// Update division question based on MMR
fun updateDivQuestion(mmr: Int, random: Random): Pair<Int, Int> {
            val num2 = random.nextInt(6, 11)
            val num1 = num2 * random.nextInt(2, 10)
            return Pair(num1, num2)
}