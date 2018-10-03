package battleship

import scala.collection.immutable
import boats._
import player._

object Game extends App{


    def printList(args: List[_]): Unit = {
        args.foreach(println)
    }


    def initiateGame(): Unit = {
    
        val player1 = new Player(1, "Player 1", Nil)
        val player2 = new Player(2,"Player 2", Nil)


        println("Player 1 \n")
        val boatsPlayer1 = player1.getBoats(List(),0,5,List())
        println("Player 2 \n")
        val boatsPlayer2 = player2.getBoats(List(),0,5,List())


        val newPlayer1 = player1.createFleet(boatsPlayer1)
        val newPlayer2 = player2.createFleet(boatsPlayer2)

        GameUtils.displayGrid(0,0, newPlayer1.getFleet())

    }

    def startGame(begin: Int=1):Unit = {
        val g = GameState(0,1)
    }

    initiateGame()

}
