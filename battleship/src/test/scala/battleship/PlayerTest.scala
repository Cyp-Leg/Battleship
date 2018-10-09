import org.scalatest.FunSuite
import org.scalatest.Matchers._
import battleship._
import boats._
import player._


class PlayerTest extends FunSuite{
    test("Testing the Player class : createPosition "){
        val cell = new Cell(0,0)
        val cell1 = new Cell(11,0)
        val cell2 = new Cell(0,11)
        val cell3 = new Cell(5,5)
        val cell4 = new Cell(-1,2)
        val cell5 = new Cell(1,2)

        val player1 = new Player(1, "Player 1", Nil, Nil, Nil, null, 0)

        assert( player1.createPosition(1,cell.getX(),cell.getY(),"U",List(),List()).getOrElse("None").toString == List(new Cell(cell.getX(),cell.getY())).toString() )
        assert( player1.createPosition(1,cell1.getX(),cell1.getY(),"U",List(),List()).getOrElse("None") == "None" )
        assert( player1.createPosition(1,cell2.getX(),cell2.getY(),"U",List(),List()).getOrElse("None") == "None" )
        assert( player1.createPosition(1,cell3.getX(),cell3.getY(),"L",List(),List()).getOrElse("None") != "None" )
        assert( player1.createPosition(1,cell4.getX(),cell4.getY(),"U",List(),List()).getOrElse("None") == "None" )
        assert( player1.createPosition(1,cell5.getX(),cell5.getY(),"K",List(),List()).getOrElse("None") == "None" )
    }

    test("Testing the Player class : checkBoatsHits"){
        val cell1 = new Cell(3,3)
        val cell2 = new Cell(2,3)
        val cell3 = new Cell(4,4)
        val cell4 = new Cell(4,3)
        
        val cellList1 = List(cell1, cell2)
        val cellList2 = List(cell3,cell4)

        val boat1 = new Boat(2,cellList1,1)
        val boat2 = new Boat(2,cellList2,2)
        val newBoat1 = new Boat(2,List(cell2),1)

        val player1 = new Player(1,"Player 1",List(boat1,boat2), List(), List(), null, 0) // Player 1 with 2 boats, no hits & no missed shots

        assert( player1.checkBoatsHits(player1, new Cell(3,3)).toString() == new Player(1,"Player 1",List(newBoat1,boat2), List(cell1), List(), null, 0).toString() )
    }

    test("Testing the Player class : checkPosition"){
        val cell1 = new Cell(1,1)
        val cell2 = new Cell(2,2)
        val cell3 = new Cell(10,1)
        val cell4 = new Cell(2,2)
        val cell5 = new Cell(3,3)
        val cell6 = new Cell(5,3)


        val position = List(cell1,cell2)

        val player1 = new Player(1,"Player 1",List(), List(), List(), null, 0) // Player 1 with no boats, no hits & no missed shots

        assert(!player1.checkPosition(cell1,position))
        assert(!player1.checkPosition(cell2,position))
        assert(!player1.checkPosition(cell4,position))
        assert(!player1.checkPosition(cell3,position))
        assert(player1.checkPosition(cell5,position))
        assert(player1.checkPosition(cell6,position))
    }

    test("Testing the Player class : compareCells"){
        val cell1 = new Cell(1,1)
        val cell2 = new Cell(1,1)
        val cell3 = new Cell(3,3)


        assert(Player.compareCells(cell1,cell2))
        assert(Player.compareCells(cell1,cell1))
        assert(!Player.compareCells(cell1,cell3))
        assert(!Player.compareCells(cell2,cell3))
    }

    test("Testing the Player class : isValid"){
        val cell1 = new Cell(1,1)
        val cell2 = new Cell(3,5)
        val cell3 = new Cell(10,0)
        val cell4 = new Cell(1,12)


        assert(Player.isValid(cell1))
        assert(Player.isValid(cell2))
        assert(!Player.isValid(cell3))
        assert(!Player.isValid(cell4))
    }

    test("Testing the Player class : createFleet"){
        val cell1 = new Cell(3,3)
        val cell2 = new Cell(3,4)
        val cell3 = new Cell(0,0)
        
        val boat1 = new Boat(2,List(cell1,cell2),1)
        val boat2 = new Boat(1,List(cell3),2)

        val player1 = new Player(1, "Player 1", Nil, Nil, Nil, null, 0)

        assert(player1.createFleet(List(boat1,boat2), List(), List(), null, 0).toString() == new Player(1, "Player 1", List(boat1,boat2), List(), List(), null, 0).toString())
    }

    test("Testing the Player class : checkHitsList"){

        val player1 = new Player(1, "Player 1", Nil, Nil, Nil, null, 0)
        val player2 = new Player(2, "Player 2", Nil, Nil, Nil, null, 0)

        val cell1 = new Cell(1,1)
        val hitsList = List(cell1)

        assert(player1.checkHitsList(hitsList, player1,player2).toString() == new Cell(cell1.getX()+1,cell1.getY()).toString()) // assert that the cell(x+1,y) is returned
    }

    test("Testing the Player class : checkAttackedPos"){

        val cell1 = new Cell(1,1)
        val cell2 = new Cell(2,1)
        val cell3 = new Cell(3,1)

        val player1 = new Player(1, "Player 1", Nil, List(cell1,cell2), List(cell3), null, 0)
        val player2 = new Player(2, "Player 2", Nil, Nil, Nil, null, 0)

        assert(player1.checkAttackedPos(player1, player2, cell1))
        assert(player1.checkAttackedPos(player1, player2, cell2))
        assert(player1.checkAttackedPos(player1, player2, cell3))

        assert(!player1.checkAttackedPos(player2, player1, cell1))
    } 
}