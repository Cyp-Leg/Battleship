package battleship

import scala.collection.immutable
import boats._
import player._

object Game extends App{

    var p1win: Int=0
    var p2win: Int=0

    def printList(args: List[_]): Unit = {
        args.foreach(println)
    }


    def initiateGame(): Unit = {
    
        val player1 = new Player(1, "Player 1", Nil, Nil, Nil,1)
        val player2 = new Player(2,"Player 2", Nil, Nil, Nil,1)


        println("\nPlayer 1 \n")
        val boatsPlayer1 = player1.getBoats(List(),0,5,List())
        println("\nPlayer 2 \n")
        val boatsPlayer2 = player2.getBoats(List(),0,5,List())


        val newPlayer1 = player1.createFleet(boatsPlayer1, Nil, Nil,1)
        val newPlayer2 = player2.createFleet(boatsPlayer2, Nil, Nil,1)

        

        play(newPlayer1,newPlayer2)

    }

    def play(player1: Player, player2: Player, begin: Int=1):Unit = {
        val g = GameState(0,1)
        if(begin==1){

            //GameUtils.clearConsole()
            println("Player 1, your turn!")

            println("\nYour fleet : \n")
            GameUtils.displayGrid(0,0, player1.getFleet())

            player1.getFleet.foreach{boat=>
                boat.getPosition().foreach{pos=>
                    println(pos)
                }
            }

            println("\nYour hit board : \n")
            PlayerBoard.displayHitBoard(0,0,player2.getHits(), player2.getMiss())

            val newPlayer2 = player1.attack(player1,player2)

            if(newPlayer2.getFleet().length!=0){
                println(newPlayer2.getFleet().length)
                play(player1, newPlayer2, 2)
            }
            else{
                println("Player 2's fleet has been sunk. Good job!")
                p1win = p1win+1
            }

        }
        else{

            //GameUtils.clearConsole()
            println("Player 2, your turn!")

            println("\nYour fleet : \n")            
            GameUtils.displayGrid(0,0, player2.getFleet())

            player2.getFleet.foreach{boat=>
                boat.getPosition().foreach{pos=>
                    println(pos)
                }
            }

            println("\nYour hit board : \n")
            PlayerBoard.displayHitBoard(0,0,player1.getHits(), player1.getMiss())

            val newPlayer1 = player2.attack(player2,player1)

            if(newPlayer1.getFleet().length!=0){
                play(newPlayer1, player2, 1)
            }
            else{
                println("Player 1's fleet has been sunk. Good job!")
                p2win = p2win+1
            }
        }

    }


    var x=0
    for(x <- 1 to 100){
        initiateGame()
    } 

    println("Player 1 won " + p1win)
    println("Player 2 won " +p2win)

}
