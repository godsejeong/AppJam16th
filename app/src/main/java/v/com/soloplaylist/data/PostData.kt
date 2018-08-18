package v.com.soloplaylist.data

class PostData(id : String,content : String,profileimg : String,name : String,rank : String,like : Int,comment : Int){
    var id = ""
    var comment = 0
    var content = ""
    var name = ""
    var rank = ""
    var like = 0
    var profileimg = ""
    init {
        this.id = id
        this.comment = comment
        this.content = content
        this.name = name
        this.rank = rank
        this.like = like
        this.profileimg = profileimg
    }
}