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
        println("\n\n***********\n")
        println("BATTLESHIP")
        println("\n***********\n\n")
        println("Please chose your game mode : ")
        println("(1) Player Versus Player")
        println("(2) Player Versus AI")
        println("(3) AI performance test (generates a CSV file)")
        println("(4) Quit the game")
    }

    def displayAIChoice():Unit = {
        println("Please chose the level of your AI")
        println("(1) Level 1 (Easy)")
        println("(2) Level 2 (Medium)")
        println("(3) Level 3 (Hard)")
        println("(4) Quit the game")
    }

    /** Function that gets the user choice for the game (PvP, PvAI, AIvAI) and possibly the AI level
    *
    *  @return List[Int] : List of the choices of the user (List(1) = gameMod, PvP, PvAI or AIvAI, List(2) = AI1 level, List(3) = AI2 level)
    *  
    */
    @tailrec
    def getMenuChoice():List[Int] = {
        displayMenu()
        val userChoice = GameUtils.getIntInput()
        if(userChoice==4){
            return List(userChoice,0,0)
        }
        if(userChoice==2){
            displayAIChoice()
            val aiChoice = GameUtils.getIntInput()
            if(aiChoice==4){
                return List(aiChoice,0,0)
            }
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

    /** Function that initiates the game (instantiates the players and asks for their boats)
    *
    *  @param menuChoice : List of the user choices for the game
    *  @param gameState : GameState containing the state of the game such as round numbers, player 1 wins number, player starting, etc...
    *  
    */
    def initiateGame(menuChoice: List[Int], gameState: GameState): Unit = {
        if(menuChoice(0)==4){
            GameUtils.endGame()
        }
        else{
            if(gameState.gamesNb==0){
                GameUtils.endGame(gameState.p1wins, gameState.p2wins, menuChoice)
                if(menuChoice(0)!=3){
                    val choice = GameUtils.getIntInput()
                    if(choice==1){
                        launch()
                    }
                    else GameUtils.endGame()
                }
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
    }

    /** Function that starts the game (once players and boats have been instanciated)
    *
    *  @param player1 : first player
    *  @param player2 : second player
    *  @param gameState : GameState containing the state of the game such as round numbers, player 1 wins number, player starting, etc...
    *  @param menuChoice : List of the user choices for the game
    *
    */
    def play(player1: Player, player2: Player,gameState: GameState, menuChoice: List[Int]):Unit = {
        
        if(gameState.playerTurn==1){
            if(player1.getAILevel()==0){
                GameUtils.clearConsole()
                println("Player 1, your turn!")

                println("\nYour fleet : \n")
                GameUtils.displayCols()
                GameUtils.displayGrid(0,0, player1.getFleet())

                println("\nYour hit board : \n")
                GameUtils.displayCols()
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
                GameUtils.displayCols()
                GameUtils.displayGrid(0,0, player2.getFleet())


                println("\nYour hit board : \n")
                GameUtils.displayCols()
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
    
    /** Function that launches the game
    *
    */

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
            println("IA 1 Level : 3")
            println("IA 2 Level : 1")
            println("Fighting...")
            initiateGame(secChoice, secGameState)
        
            val thirdGameState = new GameState(0,1,0,0,0,100) // Level 3 vs 2
            val thirdChoice = List(3,3,2)
            println("IA 1 Level : 3")
            println("IA 2 Level : 2")
            println("Fighting...")
            initiateGame(thirdChoice, thirdGameState)
    
        
            val fourthGameState = new GameState(0,1,0,0,0,100)// Level 2 vs 1
            val fourthChoice = List(3,2,1)
            println("IA 1 Level : 2")
            println("IA 2 Level : 1")
            println("Fighting...")
            initiateGame(fourthChoice, fourthGameState)

            println("CSV file generated")
        }
    }

    launch()


}
