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
    implicit def touchedCase(cell: Cell): Cell = {
        new Cell(cell.getX(), cell.getY(), true)
    }
}