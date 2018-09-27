/** A person who uses our application.
 *
 *  @constructor create a new boat with a size and a position.
 *  @param size the size of the boat
 *  @param position the position of the boat
 */

class Boat(size: Int, position: Position){ 
    var sunk: Boolean  = false

    def sink(): Unit = {
        this.sunk = true
    }
}


object Boat

