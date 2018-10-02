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

    def getBoats(boatsList: List[Boat],boatsNumber: Int): List[Boat] = {
        if(boatsNumber<5){
            println("Boat " + boatsNumber+", chose the size of your ship")
            val size = getUserInput().toInt
            println("Boat " + boatsNumber+", chose the X position of your ship")
            val xPos = getUserInput().toInt
            println("Boat " + boatsNumber+", chose the Y position of your ship")
            val yPos = getUserInput().toInt
            println("Boat " + boatsNumber+", chose the orientation of your ship ('L', 'R', 'U', 'D')")
            val orientation = getUserInput()

            val newPos = createPosition(size, xPos, yPos, orientation, List())

            printList(newPos.get)

            val newBoat = new Boat(1,newPos.get)
            val newBoatsList = newBoat :: boatsList
            val newBoatsNumber = boatsNumber+1
            
            getBoats(newBoatsList, newBoatsNumber)
        }
        else{
            return boatsList
        }
    } 

    def createPosition(size: Int, xPos: Int, yPos: Int, orientation: String, cells: List[Cell]): Option[List[Cell]] = {
        if(size==0){
            return Some(cells)
        }
        else{
            orientation match {
                case "L" => {
                    val newSize = size-1
                    val newCells = new Cell(xPos,yPos) :: cells
                    createPosition(newSize, xPos-1,yPos, orientation, newCells)
                }
                case "R" => {
                    val newSize = size-1
                    val newCells = new Cell(xPos,yPos) :: cells
                    createPosition(newSize, xPos+1,yPos, orientation, newCells)
                }
                case "U" => {
                    val newSize = size-1
                    val newCells = new Cell(xPos,yPos) :: cells
                    createPosition(newSize, xPos,yPos+1, orientation, newCells)
                }
                case "D" => {
                    val newSize = size-1
                    val newCells = new Cell(xPos,yPos) :: cells
                    createPosition(newSize, xPos,yPos-1, orientation, newCells)
                }
                case _ => None
            }
        }
    }
}

object Player{
}