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

    def getSize():Int = {
        return this.size
    }

    override def toString(): String = {
        return  this.getPosition() mkString ";"
    }

    def isHit(cell: Cell):Option[Cell] = {
        this.position.foreach{pos =>
            if(pos.getX() == cell.getX() && pos.getY() == cell.getY()){
                return Some(pos)
            }
        }
        return None
    }
}

object Boat{
}