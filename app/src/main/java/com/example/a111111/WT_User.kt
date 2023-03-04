package com.example.a111111

class WT_User {
    var username: String? = null
    var password: String? = null
    var identity: String? = null // 将 identity 设为可选类型

    constructor(username: String?, password: String?) {
        this.username = username
        this.password = password
    }
}