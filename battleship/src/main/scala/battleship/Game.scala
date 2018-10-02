package battleship

import scala.collection.immutable
import boats._

object Game extends App{

    val g = GameState(0,0,10)

    def initiateGame(): Unit = {    
        val player1 = new Player(1, "Player 1", Nil)
        val player2 = new Player(2,"Player 2", Nil)

        val boatCase1 = List(new BoatCase(1,1))

        val boatCase2 = List(new BoatCase(1,1))

        val boatCase3 = List(new BoatCase(1,1))

        val boatCase4 = List(new BoatCase(1,1))

        val boatCase5 = List(new BoatCase(1,1))

        val boatCase6 = List(new BoatCase(1,1))

        val boatCase7 = List(new BoatCase(1,1))

        val boatCase8 = List(new BoatCase(1,1))

        val boatCase9 = List(new BoatCase(1,1))

        val boatCase10 = List(new BoatCase(1,1))

        val boat11 = new Boat(1,boatCase1)
        val boat12 = new Boat(1,boatCase2)
        val boat13 = new Boat(1,boatCase3)
        val boat14 = new Boat(1,boatCase4)
        val boat15 = new Boat(1,boatCase5)
        val boat21 = new Boat(1,boatCase6)
        val boat22 = new Boat(1,boatCase7)
        val boat23 = new Boat(1,boatCase8)
        val boat24 = new Boat(1,boatCase9)
        val boat25 = new Boat(1,boatCase10)

        val newPlayer1 = player1.createFleet(boat11,boat12,boat13,boat14,boat15)
        val newPlayer2 = player2.createFleet(boat21,boat22,boat23,boat14,boat25)

        println(newPlayer1)
        println(newPlayer2)


        println("AH")
    }
    //val grid = List()

    val grid = List(new BoatCase(1,1)) //:: grid

    val case1 = new BoatCase(1,1)

    val pos1 = List(case1)

    val boat1 = new Boat(1,pos1)

    initiateGame()

}
