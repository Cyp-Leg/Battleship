package battleship

import scala.collection.immutable
import boats._
import player._

object Game extends App{

    val g = GameState(0,0,10)

    def initiateGame(): Unit = {    
        val player1 = new Player(1, "Player 1", Nil)
        val player2 = new Player(2,"Player 2", Nil)


        println("Player 1 \n")
        val boatsPlayer1 = player1.getBoats(List(),0,5,List())
        println("Player 2 \n")
        val boatsPlayer2 = player2.getBoats(List(),0,5,List())


        val newPlayer1 = player1.createFleet(boatsPlayer1)
        val newPlayer2 = player2.createFleet(boatsPlayer2)
        println(newPlayer1)
        println(newPlayer2)

        var x = 0
        for(x <- 1 until 10){
            println("|_|_|_|_|_|_|_|_|_|_|")
        }

    }

    initiateGame()

}
