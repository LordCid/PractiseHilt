package com.example.someapp.domain

import com.example.someapp.data.DataNetworkModel

fun DataNetworkModel.toDomain() = DataModel(
    userId = this.userId ?:0,
    id =  this.id ?:0,
    title = this.title ?:"",
    body = this.body ?:""
)