package battleship

import scala.collection.immutable
import boats._
import player._

object Game extends App{


    def printList(args: List[_]): Unit = {
        args.foreach(println)
    }


    def initiateGame(): Unit = {
    
        val player1 = new Player(1, "Player 1", Nil, Nil, Nil)
        val player2 = new Player(2,"Player 2", Nil, Nil, Nil)


        println("\nPlayer 1 \n")
        val boatsPlayer1 = player1.getBoats(List(),0,5,List())
        println("\nPlayer 2 \n")
        val boatsPlayer2 = player2.getBoats(List(),0,5,List())


        val newPlayer1 = player1.createFleet(boatsPlayer1, Nil, Nil)
        val newPlayer2 = player2.createFleet(boatsPlayer2, Nil, Nil)


        play(newPlayer1,newPlayer2)

    }

    def play(player1: Player, player2: Player, begin: Int=1):Unit = {
        val g = GameState(0,1)
        if(begin==1){

            GameUtils.clearConsole()
            println("Player 1, your turn!")

            println("\nYour fleet : \n")
            GameUtils.displayGrid(0,0, player1.getFleet())

            println("\nYour hit board : \n")
            PlayerBoard.displayHitBoard(0,0,player2.getHits(), player1.getMiss())

            val newPlayer2 = player1.attack(player2)
            if(newPlayer2.getFleet.length!=0){
                play(player1, newPlayer2, 2)
            }
            else{
                println("Player 2's fleet has been sunk. Good job!")
            }

        }
        else{

            GameUtils.clearConsole()
            println("Player 2, your turn!")

            println("\nYour fleet : \n")            
            GameUtils.displayGrid(0,0, player2.getFleet())

            println("\nYour hit board : \n")
            PlayerBoard.displayHitBoard(0,0,player1.getHits(), player2.getMiss())

            val newPlayer1 = player2.attack(player1)
            if(newPlayer1.getFleet().length!=0){
                play(newPlayer1, player2, 1)
            }
            else{
                println("Player 1's fleet has been sunk. Good job!")
            }
        }

    }

    initiateGame()

}
