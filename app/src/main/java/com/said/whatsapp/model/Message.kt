package com.said.whatsapp.model

data class Message(val from:String?,val to:String?,val text:String?,val data:String?){
    constructor():this(null,null,null,null)
}
