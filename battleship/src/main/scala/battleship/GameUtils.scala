package battleship

import scala.collection.immutable
import boats._
import scala.annotation.tailrec

object GameUtils{

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