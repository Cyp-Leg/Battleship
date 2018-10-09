package player

import boats._
import scala.annotation.tailrec

import battleship._
import scala.util.Random

class Player(num: Int, name: String, fleet: List[Boat], hits: List[Cell], miss: List[Cell], lastHit: Cell, aiLevel: Int = 0){


    /** Function that creates a player's fleet
    *
    *  @param newFleet : list of boats to be added to the player's fleet
    *  @param hits : cells hit by the opponent
    *  @param miss : cells shot by the opponent but where no boats are located
    *  @param lastHit : last cell hit by the opponent
    *  @param aiLevel : level of the AI of the player (0 if human)
    *  @return Player : a player with the same attributes at the caller, but with the new fleet

    * 
    */
    def createFleet(newFleet: List[Boat], hits: List[Cell], miss: List[Cell], lastHit: Cell, iaLevel: Int): Player = {
        return new Player(this.num, this.name, newFleet, hits, miss, lastHit, iaLevel)
    }

    /** Functions that return the attributes of the player
    *
    * @return the attribute asked
    *
    */
    
    def getName(): String = {
        return this.name
    }

    def getNum():Int = {
        return this.num
    }

    def getFleet(): List[Boat] = {
        return this.fleet
    }

    def getLastHit(): Cell = {
        return this.lastHit
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

    /** Function that checks if 2 players are equals
    *
    *  @param player2 : the player to be compared with
    *  @return Boolean : true if the 2 players are the same
    * 
    */
    def equals(player2: Player): Boolean = {
        return (this.getNum()==player2.getNum() && this.name == player2.getName() && this.getFleet() == player2.getFleet() && this.getHits() == player2.getHits() && this.getLastHit() == player2.getLastHit() && this.getAILevel() == player2.getAILevel())
    }

    /** Function that checks if a cell is contained in the fleet of a player
    *
    *  @param cell : cell to be compared with the list of cells, to check if it is contained inside
    *  @param allPositions : list of all the 
    *  @return Player : a player with the same attributes at the caller, but with the new fleet
    * 
    */
    def checkPosition(cell: Cell, allPositions: List[Cell]): Boolean = { // Check positions of the boats of a player (false if the cell is contained in allPositions)
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

    
    /** Function that creates the boats with user inputs
    *
    *  @param boatsList : list of already created boats
    *  @param boatsNumber : number of boats created
    *  @param size : size of the boat
    *  @param allPositions : all the positions of the player's boats
    *  @return List[Boat] : list of the boats created by the player
    * 
    */
    def getBoats(boatsList: List[Boat],boatsNumber: Int, size: Int, allPositions:List[Cell]): List[Boat] = {
        if(boatsNumber<5){
            val shipName = getShipName(size)
            if(this.aiLevel==0){
                GameUtils.displayXPosition(this.num, shipName, size)
            }
            val xPos = if(this.aiLevel==0) GameUtils.getIntInput() else Random.nextInt(10)

            if(this.aiLevel==0){
                GameUtils.displayXPosition(this.num, shipName, size)
            }
            val yPos = if(this.aiLevel==0) GameUtils.getIntInput().toInt else Random.nextInt(10)
            

            if(this.aiLevel==0){
                GameUtils.displayOrientation(this.num, shipName)
            }
            
            val orientationList = List("U","D","L","R")
            val randomIndex = Random.nextInt(orientationList.length)

            val orientation = if(this.aiLevel == 0) GameUtils.getStringInput() else orientationList(randomIndex)

            val newPos = createPosition(size, xPos, yPos, orientation, List(), allPositions)


            if(newPos == None){
                if(this.aiLevel==0){
                    println("\n\nERROR : Position out of game or already occupied. Please chose an other position\n\n")
                }
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

    /** Function that creates positions for the boats, with cells and orientation
    *
    *  @param size : size of the position (number of cells)
    *  @param xPos : X coordinate of the cell
    *  @param yPos : Y coordinate of the cell
    *  @param orientation : orientation of the position ("U" = up, "D" = down, "L" = left, "R" = right)
    *  @param cells : list of the cells that will compose the position
    *  @param allPositions : list of all the cells already placed on the grid
    *  @return Option[List[Cell]] : list of the cells composing the position if they are available, None if they are out of the grid or if they overlapse
    * 
    */
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

    /** Function that checks if a boat of a player is hit by an opponent's attack
    *
    *  @param player : player whose boats have to be checked
    *  @param cellHit : cell that has been shot by the opponent, to be tested
    *  @return Player : a player with the same attribute as the player checked, but with a new fleet if a boat has been hit
    * 
    */
    def checkBoatsHits(player: Player, cellHit: Cell): Player = {
        player.getFleet().foreach{boat=>
            boat.isHit(cellHit) match{
                case None => {
                }
                case Some(x) => {
                    
                    val newPos = boat.getPosition().dropRight((boat.getPosition().length)-(boat.getPosition().indexOf(x))) ::: boat.getPosition().drop(boat.getPosition().indexOf(x)+1)
                    
                    val size = boat.getSize()
                    val num = boat.getNum()
                    if(newPos.length!=0){ // there is still some cells on this boat
                        val newBoat = new Boat(size,newPos,num)
                        val newFleet = newBoat :: player.getFleet().dropRight((player.getFleet().length)-(player.getFleet.indexOf(boat))) ::: player.getFleet().drop(player.getFleet.indexOf(boat)+1)
                        val cellHit = new Cell(x.getX(),x.getY(),true)
                        val newHits = cellHit :: player.getHits()

                        val newPlayer = player.createFleet(newFleet, newHits, player.getMiss(), cellHit, player.getAILevel())
                        
                        
                        return newPlayer
                    }
                    else{ // 0 cells left on the boat
                        val newFleet = player.getFleet().dropRight((player.getFleet().length)-(player.getFleet.indexOf(boat))) ::: player.getFleet().drop(player.getFleet.indexOf(boat)+1)
                        val cellHit = new Cell(x.getX(),x.getY(),true)

                        val newHits = cellHit :: player.getHits()


                        val newPlayer = player.createFleet(newFleet, newHits, player.getMiss(), cellHit, player.getAILevel())
                      
                        return newPlayer
                    }
                }

            }
        }
        val newMiss = cellHit :: player.getMiss()

        val newPlayer = player.createFleet(player.getFleet(), player.getHits(), newMiss, player.getLastHit(), player.getAILevel)
        
       
        return newPlayer
    }

    /** Function that determines the next cell to shoot for AI level Hard
    *
    *  @param hitsList : List of the cells that have already been shot by the AI
    *  @param attacker : the player who attacks (here AI level hard)
    *  @param attacked : the player attacked
    *  @return Cell : the cell that will be shot by the AI during its attack
    * 
    */
  
    def checkHitsList(hitsList: List[Cell], attacker: Player, attacked: Player): Cell = {
        if(hitsList.length>0){ // At least 1 boat hit
            val hit = hitsList(0) // Check the first cell of the list
  
            val newXCell = new Cell(hit.getX()+1,hit.getY()) // neighboor 1
            val newYCell = new Cell(hit.getX(),hit.getY()-1) // 2 
            val newXCell2 = new Cell(hit.getX()-1,hit.getY()) // 3
            val newYCell2 = new Cell(hit.getX(),hit.getY()+1) // 4

            if(checkAttackedPos(attacker,attacked,newXCell) && Player.isValid(newXCell)){ // 1st cell available (not already hit + not outside the grid)
                return newXCell
            }
            else if(checkAttackedPos(attacker,attacked,newXCell2) && Player.isValid(newXCell2)){
                return newXCell2
            }
            else if(checkAttackedPos(attacker,attacked,newYCell) && Player.isValid(newYCell)){
                return newYCell
            }
            else if(checkAttackedPos(attacker,attacked,newYCell2) && Player.isValid(newYCell2)){
                return newYCell2
            }
            else if(hitsList.tail.length>0){ // if there is more than 1 element in the list && no cells available around the 1st cell of hitsList
                val newHitsList = hitsList.drop(1)
                checkHitsList(newHitsList, attacker, attacked)
            }
            else { // empty hitsList
                val newCell = new Cell(Random.nextInt(10),Random.nextInt(10))
                if(checkAttackedPos(attacker, attacked, newCell)){
                    return newCell
                }
                else checkHitsList(hitsList, attacker, attacked)
            }
        }
        else {
            val newCell = new Cell(Random.nextInt(10),Random.nextInt(10))
            if(checkAttackedPos(attacker, attacked, newCell)){
                return newCell
            }
            else checkHitsList(hitsList, attacker, attacked)
        }
    }


    /** Function that determines if a cell has already been shot (hit or missed)
    *
    *  @param attacker : the player who attacks (here AI level hard or medium)
    *  @param attacked : the player attacked
    *  @param cellAttacked : cell that is aimed by the AI
    *  @return Boolean : true if the cell aimed has not been shot yet
    * 
    */
    def checkAttackedPos(attacker: Player, attacked: Player, cellAttacked: Cell): Boolean = {
        val shotCells = attacked.getMiss() ::: attacked.getHits()
       
        shotCells.foreach{checkCell=>
            if(checkCell.getX()==cellAttacked.getX() && checkCell.getY() == cellAttacked.getY()){
                return false
            }
        }
        return true
    }


    /** Function that initiates the attack of a player to an other player
    *
    *  @param attacker : the player who attacks
    *  @param attacked : the player attacked
    *  @return Player : the attacked player with a new hitsList or missList, and a new fleet if he has been hit
    * 
    */
    def attack(attacker: Player, attacked: Player): Player = {
        if(attacker.getAILevel() == 0){
            println("\nEnter the X position of your attack")
        }
        val unTouchedCells = GameUtils.getFullGrid(0,0,Nil).diff(attacked.getHits())
        val emptyCells = unTouchedCells.diff(attacked.getMiss())

        val randCell = if(attacker.getAILevel()==1){
            new Cell(Random.nextInt(10),Random.nextInt(10))
        }
        else emptyCells(Random.nextInt(emptyCells.length))
        
        
        val xPos = if(attacker.getAILevel()==0){
            GameUtils.getIntInput()
        }
        else{
            Random.nextInt(10)
        }
        
        if(attacker.getAILevel() == 0){
            println("\nEnter the Y position of your attack")
        }        
        val yPos = if(attacker.getAILevel()==0) GameUtils.getIntInput else Random.nextInt(10)

        
        val cellAttacked = new Cell(xPos, yPos)
        
        if(attacker.getAILevel()>2){
            val hitsList = attacked.getHits()
            val smartCell = checkHitsList(hitsList,attacker,attacked)
            return checkBoatsHits(attacked, smartCell)
        }
        if(attacker.getAILevel()>=2 && !checkAttackedPos(attacker,attacked,cellAttacked)){
            attack(attacker,attacked)
        }
        
        else checkBoatsHits(attacked, cellAttacked)
    }
}

object Player{

    /** Function that compares the coordinates of 2 cells
    *
    *  @param cell1 : the first cell to be compared
    *  @param cell2 : the second cell to be compared
    *  @return Boolean : true if the cells have the same coordinates
    * 
    */

    def compareCells(cell1: Cell, cell2: Cell): Boolean = {
        return (cell1.getX()==cell2.getX() && cell1.getY()==cell2.getY())
    }

    /** Function that checks if a value fits in the grid
    *
    *  @param cell : the cell to be checked
    *  @return Boolean : true if the cell fits in the grid
    * 
    */
    def isValid(cell: Cell): Boolean = {
        return (cell.getX()>=0 && cell.getX()<10 && cell.getY()>=0 && cell.getY()<10)
    }
}