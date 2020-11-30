package com.alpine12.spotifyclone.other

open class Event<out T>(private val data: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNOtHanded() :T? {
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            data
        }
    }

    fun peekContent() = data
}