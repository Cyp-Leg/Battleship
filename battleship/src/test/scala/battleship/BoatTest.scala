import org.scalatest.FunSuite
import org.scalatest.Matchers._
import battleship._
import boats._
import player._


class BoatTest extends FunSuite{

    test("Testing the Boat class : isSunk"){
        val boat = new Boat(0,List(),1)

        assert(boat.isSunk())
    }
        
    test("Testing the Boat class : isHit"){
        val boat = new Boat(1,List(new Cell(1,1)),1)

        assert(boat.isHit(new Cell(1,1)).getOrElse("None").toString() == new Cell(1,1).toString() )
    }

}