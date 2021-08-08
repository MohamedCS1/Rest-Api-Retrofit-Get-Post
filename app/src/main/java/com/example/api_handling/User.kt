package com.example.api_handling

class User {

    var userId:Int? = null
    var id:Int? = null
    var title:String? = null
    var body:String? = null

    constructor(userId:Int,id:Int,title:String,body:String)
    {
        this.userId = userId
        this.id = id
        this.body = body
        this.title = title
    }

    constructor(userId:Int,title:String,body:String)
    {
        this.userId = userId
        this.body = body
        this.title = title
    }


}