package com.example.yhq.db

import com.example.yolharakatiqoidalari.models.Znak
import com.example.yolharakatiqoidalari.models.ZnakLeek

interface DbService {
    fun addZnak(znak: Znak)


    fun editeZnak(znak: Znak)


    fun deleteZnak(znak: Znak)



    fun getAllZnak():List<Znak>

}