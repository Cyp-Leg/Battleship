package player

import boats._

class PlayerBoard(){

    val strikes: List[Cell] = Nil
    def addStrike(cell: Cell): List[Cell] = {
        return cell :: strikes
    }
}