package com.example.aquario.utils

import com.example.aquario.data.model.AquariumDetail
import com.example.aquario.data.model.AquariumInfo

object GlobalUser {
    lateinit var email:String
    lateinit var id: String
    lateinit var token:String
    var currentAquarium = 0
    lateinit var currentAquariumDetails : AquariumDetail
    var aquariums = ArrayList<AquariumInfo>()
}