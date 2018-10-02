package battleship

import scala.collection.immutable
import boats._

object GameUtils{



    def enterPosition(playerNum: Int, boatSize: Int, newCase: Cell, positions: List[Cell]){
        if(positions.length == 0){
            val posList = List()        
            val newPosList = Cell :: posList
        }
        else{
            val newPosList = Cell :: positions
        }
    }

}