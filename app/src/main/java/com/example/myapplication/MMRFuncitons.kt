package com.example.myapplication

import kotlin.math.log10
import kotlin.math.min
import kotlin.random.Random

fun increaseScore(streak: Int, time: Int, mmr: Int): Int {

    val baseScore = 50
    val streakMultiplier = (log10((streak + 2).toDouble()) +0.5) // decreases as streak increases
    val timeBonus = if (time <= 5) (5 - time) * 10 else 0 // larger bonus for time less than 5 seconds
    var points = mmr

    val score = min(((baseScore + timeBonus) * streakMultiplier).toInt(), 150)


    points += score
    return points
}

fun decreaseScore(streak: Int, time: Int, mmr: Int): Int {
    val basePenalty = 50
    val streakPenalty = (log10((streak + 2).toDouble()) +0.8) // increases as streak increases
    val timePenalty = if (time <= 5) time * 2 else 10 // smaller penalty for time less than 5 seconds
    var points = mmr

    val penalty = min(((basePenalty + timePenalty) * streakPenalty).toInt(),100)
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
        mmr >= 1500 -> Pair(random.nextInt(50,100), random.nextInt(50,100))
        mmr >= 1150 -> Pair(random.nextInt(25,50), random.nextInt(25,50))
        mmr >= 800 -> Pair(random.nextInt(10,25), random.nextInt(10,25))
        mmr >= 350 -> Pair(random.nextInt(5,10), random.nextInt(5,10))
        else -> Pair(random.nextInt(5), random.nextInt(5))
    }
}

// Update subtraction question based on MMR
fun updateSubQuestion(mmr: Int, random: Random): Pair<Int, Int> {
    return when {
        mmr >= 1500 -> {
            val num1 = random.nextInt(100, 150)
            val num2 = random.nextInt(50, min(num1, 100))
            Pair(num1, num2)
        }
        mmr >= 1150 -> {
            val num1 = random.nextInt(50, 100)
            val num2 = random.nextInt(25, min(num1, 50))
            Pair(num1, num2)
        }
        mmr >= 800 -> {
            val num1 = random.nextInt(25, 50)
            val num2 = random.nextInt(10, min(num1, 25))
            Pair(num1, num2)
        }
        mmr >= 350 -> {
            val num1 = random.nextInt(10, 25)
            val num2 = random.nextInt(5, min(num1, 10))
            Pair(num1, num2)
        }
        else -> {
            val num1 = random.nextInt(10)
            val num2 = random.nextInt(min(num1, 5))
            Pair(num1, num2)
        }
    }
}

// Update multiplication question based on MMR
fun updateMulQuestion(mmr: Int, random: Random): Pair<Int, Int> {
    return when {
        mmr >= 1500 -> Pair(random.nextInt(10, 20), random.nextInt(10, 20))
        mmr >= 1150 -> Pair(random.nextInt(5, 10), random.nextInt(5, 10))
        mmr >= 800 -> Pair(random.nextInt(2, 5), random.nextInt(2, 5))
        mmr >= 350 -> Pair(random.nextInt(1, 3), random.nextInt(1, 3))
        else -> Pair(random.nextInt(1, 3), random.nextInt(1, 3))
    }
}

// Update division question based on MMR
fun updateDivQuestion(mmr: Int, random: Random): Pair<Int, Int> {
    return when {
        mmr >= 1500 -> {
            val num2 = random.nextInt(10, 20)
            val num1 = num2 * random.nextInt(10, 20)
            Pair(num1, num2)
        }
        mmr >= 1150 -> {
            val num2 = random.nextInt(5, 10)
            val num1 = num2 * random.nextInt(10, 20)
            Pair(num1, num2)
        }
        mmr >= 800 -> {
            val num2 = random.nextInt(2, 5)
            val num1 = num2 * random.nextInt(10, 20)
            Pair(num1, num2)
        }
        mmr >= 350 -> {
            val num2 = random.nextInt(1, 3)
            val num1 = num2 * random.nextInt(10, 20)
            Pair(num1, num2)
        }
        else -> {
            val num2 = random.nextInt(1, 3)
            val num1 = num2 * random.nextInt(5, 10)
            Pair(num1, num2)
        }
    }
}