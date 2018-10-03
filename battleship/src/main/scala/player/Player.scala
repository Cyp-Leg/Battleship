package player

import boats._
import scala.io.StdIn.readLine

class Player(num: Int, name: String, fleet: List[Boat]){

    def createFleet(newFleet: List[Boat]): Player = {
        return new Player(this.num, this.name, newFleet)
    }

    override def toString(): String = {
        return this.name + ", num " + this.num + ", boats : \n" + this.fleet
    }

    def getUserInput(): String = readLine.trim.toUpperCase

    def printList(args: List[_]): Unit = {
        args.foreach(println)
    }

    def getShipName(size: Int): String = {
        size match{
            case 5 => "Carrier"
            case 4 => "Battleship"
            case 3 => "Cruiser"
            case 2 => "Destroyer"
        }
    }

    def checkPosition(cell: Cell, allPositions: List[Cell]): Boolean = { // TO BE MODIFIED
        allPositions.foreach{elt=>
            if((elt.getX() == cell.getX()) && (elt.getY() == cell.getY())){
                return false
            }
            
        }
        if ( (cell.getX()<0) || (cell.getY()<0) || (cell.getX()>10) || (cell.getY()>10)){
            return false
        }        
        return true
    }

    def getBoats(boatsList: List[Boat],boatsNumber: Int, size: Int, allPositions:List[Cell]): List[Boat] = {
        if(boatsNumber<5){
            val shipName = getShipName(size)
            println("Chose the X position of your " + shipName + " (" + size + " cells)")
            val xPos = getUserInput().toInt
            println("Chose the Y position of your " + shipName + " (" + size + " cells)")
            val yPos = getUserInput().toInt
            println("Chose the orientation of your " + shipName + " ('L','R','U','D')")
            val orientation = getUserInput()

            val newPos = createPosition(size, xPos, yPos, orientation, List(), allPositions)


            if(newPos == None){
                println("\n\nERROR : Position out of game or already occupied. Please chose an other position\n\n")
                getBoats(boatsList, boatsNumber, size, allPositions)

            }
            else{
                val newAllPositions = newPos.get ::: allPositions

                printList(newPos.get)


                val newBoat = new Boat(size,newPos.get)
                val newBoatsList = newBoat :: boatsList
                

                if(boatsNumber == 2){
                    val newBoatsNumber = boatsNumber+1
                    println("\n\nAll positions : ")
                    printList(newAllPositions)
                    getBoats(newBoatsList, newBoatsNumber, size, newAllPositions)
                }
                else{
                    val newBoatsNumber = boatsNumber+1
                    val newSize = size-1

                    println("\n\nAll positions : ")
                    printList(newAllPositions)
                    getBoats(newBoatsList, newBoatsNumber, newSize, newAllPositions)
                }
            }
        }
        else{
            return boatsList
        }
    } 

    def createPosition(size: Int, xPos: Int, yPos: Int, orientation: String, cells: List[Cell], allPositions: List[Cell]): Option[List[Cell]] = {
        if(size==0){
            return Some(cells)
        }
        else{
            orientation match {
                case "L" => {
                    val newSize = size-1
                    val newCell = new Cell(xPos,yPos)
                    val acceptedPos = checkPosition(newCell,allPositions)
                    val newCells = newCell :: cells
                    if(acceptedPos){
                        createPosition(newSize, xPos-1,yPos, orientation, newCells, allPositions)
                    }
                    else return None
                }
                case "R" => {
                    val newSize = size-1
                    val newCell = new Cell(xPos,yPos)
                    val acceptedPos = checkPosition(newCell,allPositions)
                    val newCells = newCell :: cells
                    if(acceptedPos){
                        createPosition(newSize, xPos+1,yPos, orientation, newCells, allPositions)
                    }
                    else return None
                }
                case "U" => {
                    val newSize = size-1
                    val newCell = new Cell(xPos,yPos)
                    val acceptedPos = checkPosition(newCell,allPositions)
                    val newCells = newCell :: cells
                    if(acceptedPos){
                        createPosition(newSize, xPos,yPos+1, orientation, newCells, allPositions)
                    }
                    else return None
                }
                case "D" => {
                    val newSize = size-1
                    val newCell = new Cell(xPos,yPos)
                    val acceptedPos = checkPosition(newCell,allPositions)
                    val newCells = newCell :: cells
                    if(acceptedPos){
                        createPosition(newSize, xPos,yPos-1, orientation, newCells, allPositions)
                    }
                    else return None
                }
                case _ => None
            }
        }
    }
}

object Player{
}