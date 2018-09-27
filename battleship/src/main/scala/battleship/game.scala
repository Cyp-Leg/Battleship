package battleship

import scala.collection.immutable

object Game extends App{

    val g = GameState(0,10)

    val grid = new List[boatCase]

    grid = new boatCase(1,1) :: grid

    val pos1 = new Position(grid,1)

    val boat1 = new Boat(1,pos1)
}