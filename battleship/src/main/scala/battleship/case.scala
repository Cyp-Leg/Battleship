package battleship

class boatCase(x: Int, y: Int){

    var isTouched: Boolean = false

    def touched(): Unit = {
        this.isTouched = true
    }
}

