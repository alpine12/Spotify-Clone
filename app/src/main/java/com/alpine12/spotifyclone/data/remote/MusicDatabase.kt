package com.alpine12.spotifyclone.data.remote

import com.alpine12.spotifyclone.data.entities.Song
import com.alpine12.spotifyclone.other.Constant.SONG_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class MusicDatabase {

    private val fireStore = FirebaseFirestore.getInstance()

    private val songCollection = fireStore.collection(SONG_COLLECTION)

    suspend fun getAllSongs() : List<Song>{
        return try {
            songCollection.get().await().toObjects(Song::class.java)
        } catch (e : Exception){
            emptyList()
        }
    }

}