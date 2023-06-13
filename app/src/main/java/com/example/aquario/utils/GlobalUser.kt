package com.example.aquario.utils

import com.example.aquario.data.model.AquariumInfo

object GlobalUser {
    lateinit var email:String
    lateinit var id: String
    lateinit var token:String
    var currentAquarium = 0
    var aquariums = ArrayList<AquariumInfo>()
}