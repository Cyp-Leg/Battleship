package boats

/** A person who uses our application.
 *
 *  @constructor create a new boat with a size and a position.
 *  @param size the size of the boat
 *  @param position the position of the boat
 */

class Boat(size: Int, position: List[Cell], num: Int){
    def getPosition(): List[Cell] = {
        return this.position
    }

    def isSunk(): Boolean = {
        return this.position.length==0
    }

    def getNum():Int = {
        return this.num
    }

    def isHit(cell: Cell):Boolean = {
        this.position.foreach{pos =>
            if(pos.getX() == cell.getX() && pos.getY() == cell.getY()){
                return true
            }
        }
        return false
    }
}

object Boat{
}