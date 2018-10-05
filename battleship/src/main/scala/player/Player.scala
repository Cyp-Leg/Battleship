package player

import boats._
import scala.io.StdIn.readLine
import scala.util.Random

class Player(num: Int, name: String, fleet: List[Boat], hits: List[Cell], miss: List[Cell], aiLevel: Int = 0){


    def createFleet(newFleet: List[Boat], hits: List[Cell], miss: List[Cell], iaLevel: Int): Player = {
        return new Player(this.num, this.name, newFleet, hits, miss, iaLevel)
    }

    def getNum():Int = {
        return this.num
    }

    def getFleet(): List[Boat] = {
        return this.fleet
    }

    def getHits(): List[Cell] = {
        return this.hits
    }       

    def getMiss(): List[Cell] = {
        return this.miss
    }

    def getAILevel(): Int = {
        return this.aiLevel
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

    def checkPosition(cell: Cell, allPositions: List[Cell]): Boolean = {
        allPositions.foreach{elt=>
            if((elt.getX() == cell.getX()) && (elt.getY() == cell.getY())){
                return false
            }
            
        }
        if ( (cell.getX()<0) || (cell.getY()<0) || (cell.getX()>=10) || (cell.getY()>=10)){
            return false
        }        
        return true
    }

    def getBoats(boatsList: List[Boat],boatsNumber: Int, size: Int, allPositions:List[Cell]): List[Boat] = {
        if(boatsNumber<5){
            val shipName = getShipName(size)
            println("Chose the X position of your " + shipName + " (" + size + " cells)")
            val xPos = if(this.aiLevel==0) getUserInput().toInt else Random.nextInt(10)

            println("Chose the Y position of your " + shipName + " (" + size + " cells)")
            val yPos = if(this.aiLevel==0) getUserInput().toInt else Random.nextInt(10)
            

            println("Chose the orientation of your " + shipName + " ('L','R','U','D')")
            
            val orientationList = List("U","D","L","R")
            val randomIndex = Random.nextInt(orientationList.length)

            val orientation = if(this.aiLevel == 0) getUserInput() else orientationList(randomIndex)

            val newPos = createPosition(size, xPos, yPos, orientation, List(), allPositions)


            if(newPos == None){
                println("\n\nERROR : Position out of game or already occupied. Please chose an other position\n\n")
                getBoats(boatsList, boatsNumber, size, allPositions)

            }
            else{
                val newAllPositions = newPos.get ::: allPositions

                val newBoatsNumber = boatsNumber+1

                val newBoat = new Boat(size,newPos.get, newBoatsNumber)
                
                val newBoatsList = newBoat :: boatsList

                
                if(boatsNumber == 2){
                    getBoats(newBoatsList, newBoatsNumber, size, newAllPositions)
                }
                else{
                    val newSize = size-1
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

    def checkBoatsHits(player: Player, cellHit: Cell): Player = {
        player.getFleet().foreach{boat=>
            boat.isHit(cellHit) match{
                case None => {
                }
                case Some(x) => {
                    //println("\n\nYou hit a boat! \n")
                    
                    val newPos = boat.getPosition().dropRight((boat.getPosition().length)-(boat.getPosition().indexOf(x))) ::: boat.getPosition().drop(boat.getPosition().indexOf(x)+1)
                    
                    val size = boat.getSize()
                    val num = boat.getNum()
                    if(newPos.length!=0){
                        val newBoat = new Boat(size,newPos,num)
                        val newFleet = newBoat :: player.getFleet().dropRight((player.getFleet().length)-(player.getFleet.indexOf(boat))) ::: player.getFleet().drop(player.getFleet.indexOf(boat)+1)
                        val newHits = x :: player.getHits()
                        val newPlayer = player.createFleet(newFleet, newHits, player.getMiss(), player.getAILevel())
                        return newPlayer
                    }
                    else{
                        val newFleet = player.getFleet().dropRight((player.getFleet().length)-(player.getFleet.indexOf(boat))) ::: player.getFleet().drop(player.getFleet.indexOf(boat)+1)
                        val newHits = x :: player.getHits()
                        val newPlayer = player.createFleet(newFleet, newHits, player.getMiss(), player.getAILevel())
                        return newPlayer
                    }
                }

            }
        }
        //println("\n\nFail !\n")
        val newMiss = cellHit :: player.getMiss()

        val newPlayer = player.createFleet(player.getFleet(), player.getHits(), newMiss, player.getAILevel)
        return newPlayer
    }


    def checkAttackedPos(attacker: Player, attacked: Player, cellAttacked: Cell): Boolean = {
        val shotCells = attacked.getMiss() ::: attacked.getHits()
            
        shotCells.foreach{checkCell=>
            if(checkCell.getX()==cellAttacked.getX() && checkCell.getY() == cellAttacked.getY()){
                return false
            }
        }
        return true
    }

    def attack(attacker: Player, attacked: Player): Player = {
        //println("\nEnter the X position of your attack")
        val xPos = if(attacker.getAILevel()==0) getUserInput.toInt 
        else Random.nextInt(10)
        //println("\nEnter the Y position of your attack")
        val yPos = if(attacker.getAILevel()==0) getUserInput.toInt else Random.nextInt(10)

        
        val cellAttacked = new Cell(xPos, yPos)
        if(attacker.getAILevel()>=2 && !checkAttackedPos(attacker,attacked,cellAttacked)){
            attack(attacker,attacked)
        }    
        else checkBoatsHits(attacked, cellAttacked)
    }
}

object Player{
}