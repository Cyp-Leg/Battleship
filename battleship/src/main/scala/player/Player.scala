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
            println("Chose the X position of your ship")
            val Xpos = getUserInput()
            println("Chose the Y position of your ship")
            val Ypos = getUserInput()
            :: boatsList
            val newBoatsNumber = boatsNumber+1
            getBoats(newBoatsList, newBoatsNumber)
        }
        else{
            return Nil
        }
    } 
}

object Player{
}