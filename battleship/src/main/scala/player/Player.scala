package battleship

import boats._
import scala.io.StdIn.readLine

class Player(num: Int, name: String, fleet: List[Boat]){

    def createFleet(boat1: Boat, boat2: Boat, boat3: Boat, boat4: Boat, boat5: Boat): Player = {
        val newFleet = List(boat1,boat2,boat3,boat4,boat5)
        return new Player(this.num, this.name, newFleet)
    }

    override def toString(): String = {
        return this.name + ", num " + this.num + ", boats : \n" + this.fleet
    }

    def getUserInput(): String = readLine.trim.toUpperCase

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
            case "L" => Some(new Cell(xPos-1,yPos) :: cells)
            case "R" => Some(new Cell(xPos+1,yPos) :: cells)
            case "U" => Some(new Cell(xPos,yPos+1) :: cells)
            case "D" => Some(new Cell(xPos,yPos-1) :: cells)
            case _ => None
        }
    }
}
}

object Player{
}