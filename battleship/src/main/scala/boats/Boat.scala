package boats

/** A person who uses our application.
 *
 *  @constructor create a new boat with a size and a position.
 *  @param size the size of the boat
 *  @param position the position of the boat
 */

class Boat(size: Int, position: List[Cell]){
    def getPosition(): List[Cell] = {
        return this.position
    }

    def isSunk(): Boolean = {
        return this.position.length==0
    }
}

object Boat{
    var sunk: Boolean  = false
    implicit def sink(): Unit = {
        this.sunk = true
    }
}