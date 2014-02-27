package visualization

import scala.io.Source
import conway.Cell
import conway.Simulation

object Launcher {

    def main(args: Array[String]): Unit = {
        // read in file of booleans
      println("Following is the content read:" )

      Source.fromFile(args(0)).foreach{ 
         print
      }
//      val lines:Iterator[String] = Source.fromFile(args(0)).getLines()
      val boolgrid:Array[Array[Boolean]] = 
          (for {
              line <- Source.fromFile(args(0)).getLines()
           } yield bools(line)).toArray
           
//      val cells = for {
//          (row, r) <- boolgrid.zipWithIndex
//          (truth, c) <- row.zipWithIndex
//      } yield new Cell(r, c, truth)
      
      val grid:Array[Array[Cell]] = for ((row, r) <- boolgrid.zipWithIndex)
          yield for ((truth, c) <- row.zipWithIndex) 
          yield new Cell(r, c, truth)
      
      var sim:Simulation = new Simulation(grid)
      println
      println(sim)
      sim = sim.step2
      println
      println(sim)
    }
    /**
     * Remove whitespace from T F strings and convert to booleans
     */
    def bools(str:String):Array[Boolean] = {
        // break into chars, filter out non 'T' or 'F' chars
        val filtered = str.filter(c => (c == 'T' || c == 'F'))
        (filtered.map(c => c match {
            case 'T' => true
            case _ => false
        })).toArray
    }
}