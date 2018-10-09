package player

import boats._

object PlayerBoard{


    /** Function that displays the user's hit board
    *
    *  @param line : Number of lines of the grid
    *  @param col : Number of cols of the grid
    *  @param strikes : list of cells contained in a boat that the opponent has hit
    *  @param missed : list of cells not contained in a boat that the opponent has shot
    *  
    */
    def displayHitBoard(line: Int, col: Int, strikes:List[Cell], missed:List[Cell]): Unit = {
        if(col<10 && line<10){
        var cell:String = ""
            missed.foreach{miss=>
                if(miss.getX()==col && miss.getY() == 9-line){
                    cell = Console.BLUE + " |o| " + Console.RESET
                }
            }
            
            strikes.foreach{strike=>
                if(strike.getX()==col && strike.getY() == 9-line){
                    cell = Console.RED + " |x| " + Console.RESET
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