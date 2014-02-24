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

class Simulation(board:Array[Array[Cell]]) {
    /**
     * Takes a board and transitions it to the next step in life
     */
	def step(board:Array[Array[Cell]]):Array[Array[Cell]] =
	    for {
	        cols <- board
	        val col:Array[Cell] = for (cell <- cols) yield cellStep(cell, board)
	    } yield col
	
	val sizeX = board.size
	val sizeY = board(0).size
	/**
	 * Provides the cell to transition to given a cell and a board
	 */
	def cellStep(cell:Cell, board:Array[Array[Cell]]):Cell = {
	    def count = numLivingNeighbors(cell, board)
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
	def numLivingNeighbors(cell:Cell, board:Array[Array[Cell]]) =
	    neighbors(cell).map(pt => board(pt._1)(pt._2)).filter(c => c.alive).size
	def neighbors(cell:Cell) = (for {
		i <- cell.row - 1 to cell.row + 1
		j <- cell.col - 1 to cell.col + 1
		if (cell.row != i && cell.col != j)
	} yield (i % sizeX, j % sizeY)).toList
}