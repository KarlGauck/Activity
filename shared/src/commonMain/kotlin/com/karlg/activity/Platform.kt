package com.karlg.activity

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform