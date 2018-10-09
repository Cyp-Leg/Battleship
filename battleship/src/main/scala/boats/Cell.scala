package boats


class Cell(x: Int, y: Int, isTouched: Boolean = false){
    def getX(): Int = {
        return this.x
    }
    def getY(): Int = {
        return this.y
    }
    override def toString(): String = {
        return "X : " + this.x + ", Y : " + this.y
    }
}

object Cell{

    /** Function that make a cell touched
    *
    *  @param cell : cell to be checked
    *  @return cell : The cell with its new state "touched"
    *  
    */
    implicit def touchedCase(cell: Cell): Cell = {
        new Cell(cell.getX(), cell.getY(), true)
    }
}