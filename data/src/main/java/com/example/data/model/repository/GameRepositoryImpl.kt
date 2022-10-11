package com.example.data.model.repository

import android.util.Log
import com.example.data.model.GameSession
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random


class GameRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : GameRepository {
    override fun updateGamePlayData(sessionId : String,gamePlayData : GameSession) {
        firestore.collection("game_session").document(sessionId).set(
            gamePlayData
        ).addOnSuccessListener {
        }.addOnCanceledListener {

        }.addOnFailureListener {
            Log.i("JAPAN", "addOnFailureListener: ${it}")
        }
    }

    override fun observeOtherPlayerMoves(gameSessionId: String): Flow<GameSession> {
        return callbackFlow {
            firestore.collection("game_session").document(gameSessionId).addSnapshotListener { value, error ->
                if(error != null){
                    cancel()
                }

                if(value != null && value.exists()){
                    val mappedBoard = value.toObject<GameSession>()
                    mappedBoard?.let {
                        trySend(it)
                    }
                }
            }
            awaitClose { close() }
        }
    }
}