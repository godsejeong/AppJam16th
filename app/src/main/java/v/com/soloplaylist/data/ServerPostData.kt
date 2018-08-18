package v.com.soloplaylist.data

class ServerPostData(){
    var likes : List<String>? = null
    var comments : List<String>? = null
    var _id : String = ""
    var title : String = ""
    var content : String = ""
    var createdAt : String = ""
    var updatedAt : String = ""
    var user : UserData? = null
}