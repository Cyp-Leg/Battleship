package battleship

import scala.collection.immutable
import boats._
import scala.annotation.tailrec
import scala.io.StdIn.readLine
import java.io._
import java.lang._

object GameUtils{


    /** Function that takes a user input and checks it validity
    *
    *  @return String, the String input from the user
    *  
    */
    def getStringInput(): String = {
        try {
            val userInput = readLine.trim.toUpperCase()
            return userInput
        }
        catch {
            case e: Exception => {
                println("Please enter a letter!")
                getStringInput()
            }
        }
    }


    /** Function that takes a user input and checks it validity
    *
    *  @return Int, the Int input from the user
    *  
    */
    def getIntInput(): Int = {
        try {
            val userInput = scala.io.StdIn.readInt()
            return userInput
        }
        catch {
            case e: NumberFormatException => {
                println("Please enter a number!")
                getIntInput()
            }
        }
    }

    /**
      * Function that appends at the end of a file the content put as parameters
      * @param location: String: name and location of the file on the computer
      * @param content: String: Content to write into the file
      */
    def appendToFile(location: String, content: String): Unit = {
        val bw = new BufferedWriter(new FileWriter(location,true))
        bw.append(content+"\r\n")
        bw.flush()
        bw.close()
    }
    
    /**
      * Function that writes at the end of a file the content put as parameters
      * @param location: String: name and location of the file on the computer
      * @param content: String: Content to write into the file
      */
    def writeToFile(location: String, content: String): Unit = {
        val bw = new BufferedWriter(new FileWriter(location,false))
        bw.append(content+"\r\n")
        bw.flush()
        bw.close()
    }

    /** Function that takes a user input and checks it validity
    *
    *  @param p1wins : Number of wins of the player 1 when this function is called
    *  @param p2wins : Number of wins of the player 2 when this function is called
    *  @param chosenMode, the mode chosen by the user (PvP, PvAI, AIvAI)
    *  
    */
    def endGame(p1wins: Int, p2wins: Int, chosenMode: List[Int]): Unit = {
        println("End of the game !")
        if(chosenMode(0)!=3){
            println("Player 1 : "+p1wins+" win(s)")
            println("Player 2 : "+p2wins+" win(s)")
        }
        if(chosenMode(0)==3){
            val firstAIName = if(chosenMode(1)==1){
            "Level Beginner"
            }
            else if(chosenMode(1)==2){
            "Level Medium"
            }
            else "Level Hard"
            val secondAIName = if(chosenMode(2)==1){
            "Level Beginner"
            }
            else if(chosenMode(2)==2){
            "Level Medium"
            }
            else "Level Hard"
            appendToFile("ai_proof.csv",firstAIName + ";" + p1wins + ";" + secondAIName + ";" + p2wins)
        }
        if(chosenMode(0)!=3){
            println("Do you want to start an other game?")
            println("(1) Yes")
            println("(Other) No")
        }
    }


    def endGame(): Unit = {
        println("Good bye!")
    }
    
    def gameOver(playerNum: Int): Unit = {
        println("Player "+playerNum+"'s fleet has been sunk. Good job!")
    }

    def displayXPosition(playerNum: Int, shipName: String, size: Int):Unit = {
        println("Player "+playerNum+ ", chose the X position of your " + shipName + " (" + size + " cells)")
    }

                
    def displayYPosition(playerNum: Int, shipName: String, size: Int):Unit = {
        println("Player "+playerNum+ ", chose the Y position of your " + shipName + " (" + size + " cells)")
    }

    def displayOrientation(playerNum: Int, shipName: String):Unit = {
        println("Player "+playerNum+", chose the orientation of your " + shipName + " ('L','R','U','D')")
    }

    /** Function that displays the user's fleet on the grid
    *
    *  @param line : Number of lines of the grid
    *  @param col : Number of cols of the grid
    *  @param fleet : The fleet of the player
    *  
    */
    @tailrec
    def displayGrid(line: Int, col: Int, fleet:List[Boat]): Unit = {
        if(col<10 && line<10){
        var cell:String = ""
            fleet.foreach{boat=>
                boat.getPosition().foreach{pos=>
                    if(pos.getX()==col && pos.getY() == 9-line){
                        cell = scala.Console.RED + " |" + boat.getNum() + "| " + scala.Console.RESET
                    }
                }
            }
            if(cell.length==0){
                cell = " | | "
            }
            print(cell)
            val newCol = col+1
            displayGrid(line,newCol,fleet)
        }
        else if(line<10){
            print("\n")
            val newCol = 0
            val newLine = line+1
            displayGrid(newLine,newCol,fleet)
        }
    }

    def clearConsole(){
        print("\033[H\033[2J")
    }

    /** Function that displays a full grid
    *
    *  @param line : Number of lines of the grid
    *  @param col : Number of cols of the grid
    *  @param cellList : The list of cells already displayed, used for the recursion
    *  
    */

    @tailrec
    def getFullGrid(col: Int, line: Int, cellList: List[Cell]): List[Cell] = {
        if(col<10 && line<10){
            val newCell = new Cell(col, line)
            val newCellList = newCell :: cellList
            val newCol = col+1
            getFullGrid(newCol,line,newCellList)
        }
        else if(line<10){
            val newCol = 0
            val newLine = line+1
            getFullGrid(newCol, newLine, cellList)
        }
        else{
            return cellList
        }
    }


    def printList(args: List[_]): Unit = {
        args.foreach(println)
    }


}