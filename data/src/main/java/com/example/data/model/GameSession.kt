package com.example.data.model

data class GameSession(
    val playerMoves : MutableMap<String,String>,
    val currentTurn : String,
    val lastPlayerId : String,
    val firstPlayerId : String,
    val secondPlayerId : String,
    var hasWon : Boolean = false,
    var timer : Int = 30

){
    constructor() : this(
        mutableMapOf(),"CROSS","","",""
    )
}

data class RematchCall(
    val playerId : String,
    val askingRematch : Boolean,
    var requestAcceptedByOtherPlayer : Boolean
){
    constructor() : this("",false,false)
}