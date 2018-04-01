package io.terminus.dice.test.model

class Student {
    var name: String? = null
        get(){
            print("get is invoke")
            return field?:"this is null name"
        }
    var age:Int?=null
    //test

}