package com.hp.printercontro.utils

sealed class State<out T>
object Empty : State<Nothing>()
object Blocked : State<Nothing>()
data class Loaded<T>(val data: T) : State<T>()