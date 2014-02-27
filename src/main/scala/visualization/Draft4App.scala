package visualization

import swing._
import java.awt.Color
import conway.Simulation
import conway.Cell

object Draft4App extends SimpleSwingApplication {
    var sim:Option[Simulation] = None
    
    override def startup(args:Array[String]):Unit = {
        args.length match {
	        case 1 => sim = Some(Simulation.fromFile(args(0)))
	        case 2 => sim = Some(Simulation.random(args(0).toInt, args(1).toInt, 0.3))
	        case 3 => sim = Some(Simulation.random(args(0).toInt, args(1).toInt, args(2).toFloat))
	        case _ => sim = Some(Simulation.random(15, 10, 0.3))
        }
        super.startup(args)
//    	var simulation = new Simulation(
//	        Array(Array(new Cell(0,0,true),  new Cell(0,1,false))
//	        	 ,Array(new Cell(1,0,false), new Cell(1,1,true)))
//	        	 )
    }
    
    def top = new MainFrame {
//        var simulation = sim
//        var simulation = Simulation.random(15, 10, 0.3)
    	var simulation = sim match {
    	    case None => throw new IllegalArgumentException("invalid args")
    	    case Some(s) => s
    	}
//	    title = "Conway's Game of Life"
	    // width, height - not sure why I need both of these, but without first, grid doesn't
	    // display, and without second, app starts as the size of the buttons
	    size = new Dimension(600,500)
	    preferredSize = new Dimension(600,500)
	    val buffer = 20
	    val exitButton = new Button(Action("Exit")( quit )) { text = "Exit" }
	    val goButton =   new Button(Action("Start")( go ))  { text = "Go"   }
	    val stepButton = new Button(Action("Step")( step )) { text = "Step" }
	    val stopButton = new Button(Action("Stop")( stop )) { text = "Stop" }
	    val buttonPanel = new BoxPanel(Orientation.Horizontal) {
	        contents += goButton
	    	contents += exitButton
	    	contents += stopButton
	    	contents += stepButton
	    }
	    val cellSize:Int = {
    		// max pixel size of a grid point
	        val max = 80
	        val h = size.getHeight().toInt - buffer
	        val w = size.getWidth().toInt - buffer
	        val n = simulation.board.size
	        List(h/n, w/n, max).min
	    }
	    val gridPanel = new Panel {
			override def paint(g : Graphics2D) {
				g.setPaint(Color.WHITE)
				g.fillRect(0, 0, size.width, size.height)
				// iterate through grid and fill each live grid point
				// step thru and fillRect
				// fill location computed from cell.x, cell.y
				for (row <- simulation.board) {
				    row.foreach {
				        // fill each active each cell, draw grid for others
				        c => { if (c.alive) {
  					        	  g.setPaint(Color.BLACK)
						          g.fillRect(c.row*cellSize + buffer, c.col*cellSize + buffer
						            		, cellSize, cellSize)
						       }
				               g.setPaint(Color.LIGHT_GRAY)
				               g.drawRect(c.row*cellSize + buffer, c.col*cellSize + buffer
				                  , cellSize, cellSize) }
				    }
				}
			}
	    }
	    
//	    contents = new BorderPanel {
//	    	layout(buttonPanel) = BorderPanel.Position.East
//	    	layout(gridPanel) = BorderPanel.Position.West
//	    	layout += (buttonPanel -> BorderPanel.Position.East)
//	    	layout += (gridPanel -> BorderPanel.Position.West)
//	    }
	    contents = new BoxPanel(Orientation.Vertical) {
	        contents += gridPanel
	        contents += buttonPanel
	    }
	    listenTo(exitButton)
	    listenTo(goButton)
	    listenTo(stepButton)
	    listenTo(stopButton)
	    
	   def step = {
	       simulation = simulation.step2
	       gridPanel.repaint()
	   }
       def go = timer.start
       def stop = timer.stop
       val timer=new javax.swing.Timer(250, Swing.ActionListener(e =>
		{
			step
		}
		))
   }

}