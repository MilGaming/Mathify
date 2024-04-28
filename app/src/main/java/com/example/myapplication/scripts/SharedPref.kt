package com.example.myapplication.scripts

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.mathPages.AddUserStats
import com.example.myapplication.mathPages.DivUserStats
import com.example.myapplication.mathPages.MulUserStats
import com.example.myapplication.mathPages.SubUserStats
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferencesManager(context: Context) {
        private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MathPrefs", Context.MODE_PRIVATE)

        // Add User stats
        fun saveAddStats(userStatsList: List<AddUserStats>) {
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(userStatsList)
            editor.putString("ADD_USER_STATS_LIST", json)
            editor.apply()
        }
        fun getAddStats(): List<AddUserStats> {
            val gson = Gson()
            val json = sharedPreferences.getString("ADD_USER_STATS_LIST", null)
            val type = object : TypeToken<List<AddUserStats>>() {}.type
            return gson.fromJson(json, type) ?: listOf()
        }

        // Sub User stats
        fun saveSubStats(userStatsList: List<SubUserStats>) {
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(userStatsList)
            editor.putString("SUB_USER_STATS_LIST", json)
            editor.apply()
        }
        fun getSubStats(): List<SubUserStats> {
            val gson = Gson()
            val json = sharedPreferences.getString("SUB_USER_STATS_LIST", null)
            val type = object : TypeToken<List<SubUserStats>>() {}.type
            return gson.fromJson(json, type) ?: listOf()
        }

        // Mul User stats
        fun saveMulStats(userStatsList: List<MulUserStats>) {
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(userStatsList)
            editor.putString("MUL_USER_STATS_LIST", json)
            editor.apply()
        }
        fun getMulStats(): List<MulUserStats> {
            val gson = Gson()
            val json = sharedPreferences.getString("MUL_USER_STATS_LIST", null)
            val type = object : TypeToken<List<MulUserStats>>() {}.type
            return gson.fromJson(json, type) ?: listOf()
        }

        // Div User stats
        fun saveDivStats(userStatsList: List<DivUserStats>) {
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(userStatsList)
            editor.putString("DIV_USER_STATS_LIST", json)
            editor.apply()
        }
        fun getDivStats(): List<DivUserStats> {
            val gson = Gson()
            val json = sharedPreferences.getString("DIV_USER_STATS_LIST", null)
            val type = object : TypeToken<List<DivUserStats>>() {}.type
            return gson.fromJson(json, type) ?: listOf()
        }

        // Addition points
        fun saveAdditionPoints(points: Int) {
            val editor = sharedPreferences.edit()
            editor.putInt("ADDITION_POINTS", points)
            editor.apply()
        }

        fun getAdditionPoints(): Int {
            return sharedPreferences.getInt("ADDITION_POINTS", 0)
        }

        // Subtraction points
        fun saveSubtractionPoints(points: Int) {
            val editor = sharedPreferences.edit()
            editor.putInt("SUBTRACTION_POINTS", points)
            editor.apply()
        }

        fun getSubtractionPoints(): Int {
            return sharedPreferences.getInt("SUBTRACTION_POINTS", 0)
        }

        // Multiplication points
        fun saveMultiplicationPoints(points: Int) {
            val editor = sharedPreferences.edit()
            editor.putInt("MULTIPLICATION_POINTS", points)
            editor.apply()
        }

        fun getMultiplicationPoints(): Int {
            return sharedPreferences.getInt("MULTIPLICATION_POINTS", 0)
        }

        // Division points
        fun saveDivisionPoints(points: Int) {
            val editor = sharedPreferences.edit()
            editor.putInt("DIVISION_POINTS", points)
            editor.apply()
        }

        fun getDivisionPoints(): Int {
            return sharedPreferences.getInt("DIVISION_POINTS", 0)
        }

        //emilkode//
        fun saveAddMMR(mmr: Int) {
            val editor = sharedPreferences.edit()
            editor.putInt("AddMMR", mmr)
            editor.apply()
        }

        fun getAddMMR(): Int {
            return sharedPreferences.getInt("AddMMR", 0)
        }
        fun saveMulMMR(mmr: Int) {
            val editor = sharedPreferences.edit()
            editor.putInt("MulMMR", mmr)
            editor.apply()
        }
         fun getMulMMR(): Int {
             return sharedPreferences.getInt("MulMMR", 0)
         }
         fun saveSubMMR(mmr: Int) {
             val editor = sharedPreferences.edit()
             editor.putInt("SubMMR", mmr)
             editor.apply()
         }

         fun getSubMMR(): Int {
             return sharedPreferences.getInt("SubMMR", 0)
         }
         fun saveDivMMR(mmr: Int) {
             val editor = sharedPreferences.edit()
             editor.putInt("DivMMR", mmr)
             editor.apply()
         }
         fun getDivMMR(): Int {
             return sharedPreferences.getInt("DivMMR", 0)
         }
         //emilkode//

         //andersKode//
         fun saveFirstLogin(firstLogin: Boolean) {
             val editor=sharedPreferences.edit()
             editor.putBoolean("FIRST_LOGIN", firstLogin)
             editor.apply()
         }

         fun getFirstLogin(): Boolean {
             return sharedPreferences.getBoolean("FIRST_LOGIN", true)
         }
         //andersKode//
         fun clearPreferences() {
             val editor = sharedPreferences.edit()
             editor.clear()
             editor.apply()
         }

        //Achievment//
        fun saveAchievements(achievements: List<Achievement>) {
            val editor = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(achievements)
            editor.putString("achievements", json)
            editor.apply()
        }
         fun getAchievements(): List<Achievement> {
             val gson = Gson()
             val json = sharedPreferences.getString("achievements", null)
             val type = object : TypeToken<List<Achievement>>() {}.type
             return gson.fromJson(json, type) ?: listOf()
         }
         // Save the index of the last achievement added, to make sure every index is unique
         fun saveAchievementIndex(index: Int) {
             val editor = sharedPreferences.edit()
             editor.putInt("achievementIndex", index)
             editor.apply()
         }
         fun getAchievementIndex(): Int {
             return sharedPreferences.getInt("achievementIndex", 0)
         }

     }

