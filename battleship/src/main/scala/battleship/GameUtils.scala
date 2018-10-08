package battleship

import scala.collection.immutable
import boats._
import scala.annotation.tailrec
import scala.io.StdIn.readLine


object GameUtils{


    def getUserInput(): String = readLine.trim.toUpperCase


    def endGame(p1wins: Int, p2wins: Int){
        println("End of the game !")
        println("Player 1 : "+p1wins+" wins")
        println("Player 2 : "+p2wins+" wins")
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

    @tailrec
    def displayGrid(line: Int, col: Int, fleet:List[Boat]): Unit = {
        if(col<10 && line<10){
        var cell:String = ""
            fleet.foreach{boat=>
                boat.getPosition().foreach{pos=>
                    if(pos.getX()==col && pos.getY() == 9-line){
                        cell = Console.RED + " |" + boat.getNum() + "| " + Console.RESET
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