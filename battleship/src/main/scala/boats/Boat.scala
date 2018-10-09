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

    /** Function that checks if a boat is sunk
    *
    *  @return boolean, true if the boat is sunk (if it has no more cells).

    */
    def isSunk(): Boolean = {
        return this.position.length==0
    }

    /** Function that returns the number of a boat.
    *  @return int, the number of the boat.
    */
    def getNum():Int = {
        return this.num
    }

    /** Function that returns the size of a boat.
    *  @return int, the size of the boat.
    */
    def getSize():Int = {
        return this.size
    }
    override def toString(): String = {
        return  this.getPosition() mkString ";"
    }


    /** Function that check if a cell of the boat is hit.
    *
    *  @param cell : The cell aimed
    *  @return Option[Cell], the cell hit if the cell aimed matches a cell of the boat, None if it doesn't match
    */
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