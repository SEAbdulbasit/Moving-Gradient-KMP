package com.abdulbasit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform