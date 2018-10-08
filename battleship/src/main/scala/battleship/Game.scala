package battleship

import scala.collection.immutable
import boats._
import player._
import battleship._
import scala.annotation.tailrec


object Game extends App{


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

    @tailrec
    def getMenuChoice():List[Int] = {
        displayMenu()
        val userChoice = GameUtils.getUserInput().toInt
        if(userChoice==2){
            displayAIChoice()
            val aiChoice = GameUtils.getUserInput().toInt
            return List(userChoice,0,aiChoice)
        }
        else if(userChoice==3){
            return List(userChoice,0,0)
        }
        else if(userChoice==1){
            return List(userChoice,0,0)
        }
        else{
            println("Wrong input !")
            getMenuChoice()
        }
    }

    def initiateGame(menuChoice: List[Int], gameState: GameState): Unit = {
        
        if(gameState.gamesNb==0){
            GameUtils.endGame(gameState.p1wins, gameState.p2wins, menuChoice)
        }
        else if(gameState.gamesNb>0){
            val player1 = new Player(1, "Player 1", Nil, Nil, Nil, null, menuChoice(1))
            val player2 = new Player(2,"Player 2", Nil, Nil, Nil, null, menuChoice(2))


            val boatsPlayer1 = player1.getBoats(List(),0,5,List())
            val boatsPlayer2 = player2.getBoats(List(),0,5,List())


            val newPlayer1 = player1.createFleet(boatsPlayer1, Nil, Nil,null, menuChoice(1))
            val newPlayer2 = player2.createFleet(boatsPlayer2, Nil, Nil,null, menuChoice(2))

            play(newPlayer1,newPlayer2, gameState, menuChoice)
        }
    }


    def play(player1: Player, player2: Player,gameState: GameState, menuChoice: List[Int]):Unit = {
        
        if(gameState.playerTurn==1){
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
                val newRound = gameState.round+1
                val newTurn = if(gameState.playerTurn==1) 2 else 1
                val newGameState = new GameState(newRound, newTurn, gameState.p1wins, gameState.p2wins, gameState.startingPlayer, gameState.gamesNb)

                play(player1, newPlayer2, newGameState, menuChoice)
            }
            else{
                if(player1.getAILevel==0){
                    GameUtils.gameOver(player2.getNum())
                }
                val newRound = gameState.round+1
                val newStarting = if(gameState.startingPlayer==1) 2 else 1
                val p1wins = gameState.p1wins+1
                val newGameNb = gameState.gamesNb-1
                val newGameState = new GameState(0, newStarting, p1wins, gameState.p2wins, newStarting, newGameNb)
                initiateGame(menuChoice, newGameState)
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
                
                val newRound = gameState.round+1
                val newTurn = if(gameState.playerTurn==1) 2 else 1
                val newGameState = new GameState(newRound, newTurn, gameState.p1wins, gameState.p2wins, gameState.startingPlayer, gameState.gamesNb)

                play(newPlayer1, player2, newGameState, menuChoice)
            }
            else{
                if(player2.getAILevel==0){
                    GameUtils.gameOver(player1.getNum())
                }
                val newRound = gameState.round+1
                val newStarting = if(gameState.startingPlayer==1) 2 else 1
                val p2wins = gameState.p2wins+1
                val newGameNb = gameState.gamesNb-1
                val newGameState = new GameState(newRound, newStarting, gameState.p1wins, p2wins, newStarting, newGameNb)
                initiateGame(menuChoice, newGameState)

            }
        }
    }

    def launch(): Unit={
        val choice = getMenuChoice()
        val gameState = if(choice(0)==3){
            new GameState(0,1,0,0,0,100)
        }
        else new GameState(0,1,0,0,0,1)
        
        if(choice(0)!=3){
            initiateGame(choice, gameState)
        }
        if(choice(0)==3){
            GameUtils.writeToFile("ai_proof.csv","IA Name; score; IA Name2; score2")
            val secGameState = new GameState(0,1,0,0,0,100)
            val secChoice = List(3,3,1) // Choice 3, IA1 level 3, IA2 level 1
            initiateGame(secChoice, secGameState)
        
            val thirdGameState = new GameState(0,1,0,0,0,100) // Level 3 vs 2
            val thirdChoice = List(3,3,2)
            println("IA 1 Level : 3")
            println("IA 2 Level : 2")
            initiateGame(thirdChoice, thirdGameState)
    
        
            val fourthGameState = new GameState(0,1,0,0,0,100)// Level 2 vs 1
            val fourthChoice = List(3,2,1)
            println("IA 1 Level : 2")
            println("IA 2 Level : 1")
            initiateGame(fourthChoice,  fourthGameState)
        
        }
    }

    launch()


}
