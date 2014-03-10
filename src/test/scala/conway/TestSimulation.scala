package conway

import org.scalatest._
import org.scalatest.Assertions._
import scala.io.Source

class TestSimulation extends FlatSpec {

	val sim = Simulation.fromFile("src/main/resources/osc2-blinker5.txt")
	/*
  0 1 2 3 4
0 . . . . .
1 . . . . .
2 . T T T .
3 . . . . .
4 . . . . .
	 */
  // TODO make test suite so can share grid
  
    "neighbors" should "always return 8 cells since we are in a toroid" in {
	    sim.board.flatten.foreach (cell => assert(sim.neighbors(cell).size == 8))
    }
	"neighbors" should "return correct adjacent cells" in {
	    val neighbors11 = sim.neighbors(sim.board(1)(1))
	    neighbors11.contains(new Cell(0,0,false))
	    neighbors11.contains(new Cell(0,1,false))
	    neighbors11.contains(new Cell(0,2,false))
	    neighbors11.contains(new Cell(1,0,false))
	    neighbors11.contains(new Cell(1,2,false))
	    neighbors11.contains(new Cell(2,0,false))
	    neighbors11.contains(new Cell(2,1,true))
	    neighbors11.contains(new Cell(2,2,true))
	    
	    val neighbors44 = sim.neighbors(sim.board(4)(4))
	    neighbors11.contains(new Cell(4,3,false))
	    neighbors11.contains(new Cell(3,4,false))
	    neighbors11.contains(new Cell(3,4,false))
	    neighbors11.contains(new Cell(0,3,false))
	    neighbors11.contains(new Cell(0,4,false))
	    neighbors11.contains(new Cell(3,0,false))
	    neighbors11.contains(new Cell(4,0,false))
	    neighbors11.contains(new Cell(0,0,false))
	}
	"livingNeighbors" should "return the correct count of the adjacent alive cells" in {
	    val count = sim.numLivingNeighbors(sim.board(1)(1))
	    assert(count == 2)
	}
	"isAlive" should "correctly return the state of the cell at that position" in {
	    assert(sim.isAlive(1,1) == false)
	    assert(sim.isAlive(1,2) == false)
	    assert(sim.isAlive(2,1) == true)
	    assert(sim.isAlive(2,2) == true)
	    assert(sim.isAlive(2,3) == true)
	    assert(sim.isAlive(3,2) == false)
	    assert(sim.isAlive(0,2) == false)
	}
  }
