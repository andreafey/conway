package conway

import scala.util.Random
import scala.io.Source

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
    
    def fromFile(file:String):Simulation = {
	    /**
	     * Remove whitespace from T F,. strings and convert to booleans
	     */
	    def bools(str:String):Array[Boolean] = {
	        // break into chars, filter out non 'T' or 'F' chars
	        val filtered = str.filter(c => (c == 'T' || c == '.' || c == 'F'))
	        (filtered.map(c => c match {
	            case 'T' => true
	            case _ => false
	        })).toArray
	    }
		/**
		 * Read truth grid from file
		 */
		val boolgrid:Array[Array[Boolean]] = 
            (for (line <- Source.fromFile(file).getLines())
                yield bools(line)).toArray
		/**
		 * Transform truth grid into Simulation grid
		 */
		val grid:Array[Array[Cell]] = for ((row, r) <- boolgrid.zipWithIndex)
			yield for ((truth, c) <- row.zipWithIndex) 
				yield new Cell(r, c, truth)
      
        new Simulation(grid)
    }
}

class Simulation(grid:Array[Array[Cell]]) {
    var board = grid
	val sizeX = board.size
	val sizeY = board(0).size
    val count = board.flatten.foldLeft(0)(
            (acc,cell) => if (cell.alive) acc + 1 else acc)
    /**
     * Transitions this Simulation to the next step in life
     */
	def step:Simulation = {
	    val grid = for (rows <- board) yield for (cell <- rows) 
	        			yield cellStep(cell)
	    new Simulation(grid)
	}
	/**
	 * Provides the cell to transition to given a cell and a board
	 */
	private def cellStep(cell:Cell):Cell = {
	    val count = numLivingNeighbors(cell)
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
	def numLivingNeighbors(cell:Cell) = {
	    val neigh = neighbors(cell) 
	    neigh.map(pt => board(pt._1)(pt._2)).filter(c => c.alive).size
	}
	/**
	 * Returns all 8 neighbors in this torus
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
	    def cellStr(acc:String, cell:Cell):String =
	        if (cell.alive) acc + "T "
	        else acc + ". "
	    (for (arr <- grid) yield arr.foldLeft("")(cellStr)).mkString("\n")
	}
}