package player

import boats._

object PlayerBoard{


    def displayHitBoard(line: Int, col: Int, strikes:List[Cell], missed:List[Cell]): Unit = {
        if(col<10 && line<10){
        var cell:String = ""
            strikes.foreach{strike=>
                if(strike.getX()==col && strike.getY() == 9-line){
                    cell = Console.RED + " |x| " + Console.RESET
                }
            }
        
            missed.foreach{miss=>
                if(miss.getX()==col && miss.getY() == 9-line){
                    cell = Console.BLUE + " |o| " + Console.RESET
                }
            }


            if(cell.length==0){
                cell = " | | "
            }
            print(cell)
            val newCol = col+1
            displayHitBoard(line,newCol,strikes, missed)
        }
        else if(line<10){
            print("\n")
            val newCol = 0
            val newLine = line+1
            displayHitBoard(newLine,newCol,strikes, missed)
        }
    }
}