package battleship

import scala.collection.immutable
import boats._

object Game extends App{

    val g = GameState(0,0,10)

    def initiateGame(): Unit = {    
        val player1 = new Player(1, "Player 1", Nil)
        val player2 = new Player(2,"Player 2", Nil)

        val boatsPlayer1 = player1.getBoats(List(),0)
        val boatsPlayer2 = player2.getBoats(List(),0)

        val boat11 = boatsPlayer1.head
        val boat12 = boatsPlayer1.tail.head
        val boat13 = boatsPlayer1.tail.tail.head
        val boat14 = boatsPlayer1.tail.tail.tail.head
        val boat15 = boatsPlayer1.tail.tail.tail.tail.head


        val boat21 = boatsPlayer2.head
        val boat22 = boatsPlayer2.tail.head
        val boat23 = boatsPlayer2.tail.tail.head
        val boat24 = boatsPlayer2.tail.tail.tail.head
        val boat25 = boatsPlayer2.tail.tail.tail.tail.head


        val newPlayer1 = player1.createFleet(boat11,boat12,boat13,boat14,boat15)
        val newPlayer2 = player2.createFleet(boat21,boat22,boat23,boat14,boat25)
        println(newPlayer1)
        println(newPlayer2)

    }
    //val grid = List()

    val grid = List(new BoatCase(1,1)) //:: grid

    val case1 = new BoatCase(1,1)

    val pos1 = List(case1)

    val boat1 = new Boat(1,pos1)

    initiateGame()

}
