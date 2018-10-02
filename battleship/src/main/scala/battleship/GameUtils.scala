package battleship

import scala.collection.immutable
import boats._

object GameUtils{



    def enterPosition(playerNum: Int, boatSize: Int, newCase: BoatCase, positions: List[BoatCase]){
        if(positions.length == 0){
            val posList = List()        
            val newPosList = BoatCase :: posList
        }
        else{
            val newPosList = BoatCase :: positions
        }
    }

}