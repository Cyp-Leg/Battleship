package battleship

import scala.collection.immutable
import boats._
import player._
import battleship._
import scala.annotation.tailrec


object Game extends App{

    var p1win: Int=0
    var p2win: Int=0

    def printList(args: List[_]): Unit = {
        args.foreach(println)
    }

    def displayMenu():Unit = {
        println("Welcome to the Battleship game.")
        println("Please chose your game mode : ")
        println("(1) Player Versus Player")
        println("(2) Player Versus AI")
        println("(3) AI performance test (generates a CSV file)")
    }

    def displayAIChoice():Unit = {
        println("Please chose the level of your AI")
        println("(1) Level 1 (Easy)")
        println("(2) Level 2 (Medium)")
        println("(3) Level 3 (Hard)")
    }

    def displayTestChoice():Unit = {
        println("Please chose the level of your second AI")
        println("(1) Level 1 (Easy)")
        println("(2) Level 2 (Medium)")
        println("(3) Level 3 (Hard)")
    }

    @tailrec
    def getMenuChoice():List[Int] = {
        displayMenu()
        val userChoice = GameUtils.getUserInput().toInt
        if(userChoice==2){
            displayAIChoice()
            val aiChoice = GameUtils.getUserInput().toInt
            return List(userChoice,aiChoice,0)
        }
        else if(userChoice==3){
            displayAIChoice()
            val aiChoice = GameUtils.getUserInput().toInt
            displayTestChoice()
            val testChoice = GameUtils.getUserInput().toInt
            return List(userChoice,aiChoice,testChoice)
        }
        else if(userChoice==1){
            return List(userChoice,0,0)
        }
        else{
            println("Wrong input !")
            getMenuChoice()
        }
    }

    def initiateGame(menuChoice: List[Int]): Unit = {

        if(menuChoice(0)!=3){
            val player1 = new Player(1, "Player 1", Nil, Nil, Nil, null, menuChoice(1))
            val player2 = new Player(2,"Player 2", Nil, Nil, Nil, null, menuChoice(1))


            val boatsPlayer1 = player1.getBoats(List(),0,5,List())
            val boatsPlayer2 = player2.getBoats(List(),0,5,List())


            val newPlayer1 = player1.createFleet(boatsPlayer1, Nil, Nil,null, menuChoice(1))
            val newPlayer2 = player2.createFleet(boatsPlayer2, Nil, Nil,null, menuChoice(1))

            

            play(newPlayer1,newPlayer2)
        }

    }

    def play(player1: Player, player2: Player, begin: Int=1):Unit = {
        val g = GameState(0,1)
        if(begin==1){
            if(player1.getAILevel()==0){
                GameUtils.clearConsole()
                println("Player 1, your turn!")

                println("\nYour fleet : \n")
                GameUtils.displayGrid(0,0, player1.getFleet())

                println("\nYour hit board : \n")
                PlayerBoard.displayHitBoard(0,0,player2.getHits(), player2.getMiss())
            }

            val newPlayer2 = player1.attack(player1,player2)

            if(newPlayer2.getFleet().length!=0){
                play(player1, newPlayer2, 2)
            }
            else{
           //     println("Player 2's fleet has been sunk. Good job!")
                p1win = p1win+1
            }

        }
        else{
            
            if(player2.getAILevel()==0){
                GameUtils.clearConsole()
                println("Player 2, your turn!")

                println("\nYour fleet : \n")            
                GameUtils.displayGrid(0,0, player2.getFleet())


                println("\nYour hit board : \n")
                PlayerBoard.displayHitBoard(0,0,player1.getHits(), player1.getMiss())
            }

            val newPlayer1 = player2.attack(player2,player1)


            if(newPlayer1.getFleet().length!=0){
                play(newPlayer1, player2, 1)
            }
            else{
            //    println("Player 1's fleet has been sunk. Good job!")
                p2win = p2win+1
            }
        }

    }


    def launchTests(acc: Int): Unit={
        val choice = getMenuChoice()
        if(acc==0){
            println("Game over !")
        }
        else{
            initiateGame(choice)
            val newAcc = acc-1
            launchTests(newAcc)
        }
    }


    launchTests(1000)

    println("Player 1 won " + p1win)
    println("Player 2 won " +p2win)

}
