package conway

import scala.util.Random

object Simulation {
    // TODO should I seed random?
    def random(sizeX: Int, sizeY: Int, freq:Double):Simulation = {
    	val rand = new Random
        val board = (for {
            i <- 0 to sizeX
            val row = for {
                j <- 0 to sizeY
            } yield new Cell(i, j, rand.nextDouble() < freq)
        } yield row.toArray).toArray
        new Simulation(board)
    }
}

class Simulation(grid:Array[Array[Cell]]) {
    var board = grid
    /**
     * Takes a board and transitions it to the next step in life
     */
	def step:Unit = {
	    board = for (cols <- board) yield for (cell <- cols) yield cellStep(cell, board)
	}
	def step2:Simulation = {
	    val grid = for (cols <- board) yield for (cell <- cols) yield cellStep(cell, board)
	    new Simulation(grid)
	}
	val sizeX = board.size
	val sizeY = board(0).size
	/**
	 * Provides the cell to transition to given a cell and a board
	 */
	def cellStep(cell:Cell, board:Array[Array[Cell]]):Cell = {
	    val count = numLivingNeighbors(cell, board)
        if (!cell.alive && count == 3)
            new Cell(cell.row, cell.col, true)
        else if (cell.alive && (count < 2 || count >3))
            new Cell(cell.row, cell.col, false)
        else
            cell
	}
	
	/**
	 * Returns the number of living neighbors adjacent to the Cell on the Board
	 */
	def numLivingNeighbors(cell:Cell, board:Array[Array[Cell]]) = {
	    val neigh = neighbors(cell) 
	    neigh.map(pt => board(pt._1)(pt._2)).filter(c => c.alive).size
	}
	/**
	 * Returns all 8 neighbors in this Toroid
	 */
	def neighbors(cell:Cell) = (for {
		i <- cell.row - 1 to cell.row + 1
		j <- cell.col - 1 to cell.col + 1
		if (!(cell.row == i && cell.col == j))
	} yield ((i + sizeX) % sizeX, (j + sizeY) % sizeY)).toList
	
	/**
	 * Returns true if the Cell at that grid point is alive, false if not
	 */
	def isAlive(x:Int, y:Int):Boolean = board(x)(y).alive
	
	/**
	 * returns printable version
	 */
	override def toString:String = {
//	    def arrStr(acc:String, cells:Array[Cell]):String = 
//	        cells.foldLeft(acc)(cellStr) + "\n"
	    def cellStr(acc:String, cell:Cell):String = {
	        if (cell.alive) acc + "T"
	        else acc + "F"
	    }
	    (for (arr <- grid) yield arr.foldLeft("")(cellStr)).mkString("\n")
//	    grid.foldLeft("")(arrStr)
	}
}