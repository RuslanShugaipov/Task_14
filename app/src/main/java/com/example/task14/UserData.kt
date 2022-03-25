package com.example.task14

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val login: String, val surname: String,
    val name: String, val email: String, val password: String
)
