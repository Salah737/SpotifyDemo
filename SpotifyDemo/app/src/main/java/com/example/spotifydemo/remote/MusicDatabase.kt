package com.example.spotifydemo.remote

import com.example.spotifydemo.entities.Song
import com.example.spotifydemo.other.Constants.SONG_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MusicDatabase {
    private val fireStore=FirebaseFirestore.getInstance()
    private val songCollection=fireStore.collection(SONG_COLLECTION)


    suspend fun getAllSongs():List<Song>{
        return try{
            songCollection.get().await().toObjects(Song::class.java)
        }catch (e :Exception){
            emptyList()
        }
    }
}