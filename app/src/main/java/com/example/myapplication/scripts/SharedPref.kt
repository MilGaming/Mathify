package com.example.myapplication.scripts

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferencesManager(context: Context) {
        private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MathPrefs", Context.MODE_PRIVATE)


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

