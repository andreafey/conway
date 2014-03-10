package visualization

import swing._
import java.awt.Color
import conway.Simulation
import conway.Cell
import javax.swing.Timer

    // TODO cease stepping on completion
object Launcher extends SimpleSwingApplication {
    var args:Array[String] = null
    var sim:Simulation = null
    var ticks:Int = 0
    
    override def startup(args:Array[String]):Unit = {
        sim = simFromArgs(args)
        this.args = args
        super.startup(args)
    }
    def simFromArgs(args:Array[String]):Simulation = args.length match {
	        case 1 => Simulation.fromFile(args(0))
	        case 2 => Simulation.random(args(0).toInt, args(1).toInt, 0.3)
	        case 3 => Simulation.random(args(0).toInt
	                             , args(1).toInt, args(2).toFloat)
	        case _ => Simulation.random(15, 10, 0.3)
        }
    
    def top = new MainFrame {
    	var simulation = sim
	    title = "Conway's Game of Life"
	    /* width, height - not sure why I need both of these, but without first,
	     * grid doesn't display, and without second, app starts as the size of 
	     * the buttons
	     */ 
	    size = new Dimension(600,600)
	    preferredSize = new Dimension(600,600)
	    val buffer = 20
	    val margin = 10
	    val exitButton =  new Button(Action("Exit")( quit ))   { text = "Exit" }
	    val goButton =    new Button(Action("Start")( go ))    { text = "Run"  }
	    val stepButton =  new Button(Action("Step")( step ))   { text = "Step" }
	    val stopButton =  new Button(Action("Stop")( stop ))   { text = "Stop" }
	    val resetButton = new Button(Action("Reset")( reset )) { text = "Reset"}
	    
	    val buttonPanel = new BoxPanel(Orientation.Horizontal) {
            val tks = new BoxPanel(Orientation.Horizontal) {
            	val ticksLabel = new Label("Ticks: ")
            	val ticksField = new Label("0")
            	contents += ticksLabel
            	contents += ticksField
            }
	        contents += goButton
	    	contents += stopButton
	    	contents += stepButton
	    	contents += resetButton
	    	contents += exitButton
	    }
	    
        val statsPanel = new BoxPanel(Orientation.Horizontal) {
            val tks = new BoxPanel(Orientation.Horizontal) {
            	val ticksLabel = new Label("Ticks: ")
            	val ticksField = new Label("0")
            	contents += ticksLabel
            	contents += ticksField
            }
            val living = new BoxPanel(Orientation.Horizontal) {
            	val liveLabel = new Label("Living: ")
            	val liveField = new Label(simulation.count.toString)
            	contents += liveLabel
            	contents += liveField
            }
        }
	    val cellSize:Int = {
    		// max pixel size of a grid point
	        val max = 80
    		val n = simulation.board.size
	        val h = (size.getHeight().toInt - 2*buffer - margin) / n
	        val w = (size.getWidth().toInt - 2*buffer - margin) /n
	        List(h, w, max).min
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
                            g.fillRect(c.col*cellSize + buffer
                                     , c.row*cellSize + buffer
                                     , cellSize, cellSize)
						    }
                            g.setPaint(Color.LIGHT_GRAY)
                            g.drawRect(c.col*cellSize + buffer
                                     , c.row*cellSize + buffer
                                     , cellSize, cellSize) }
                    }
                }
                g.setPaint(Color.BLUE)
				g.drawString("Ticks: " + ticks.toString, 5, buffer - 5)
				g.drawString("Alive: " + simulation.count.toString, size.width - 75, buffer - 5)
            }
        }

	    contents = new BoxPanel(Orientation.Vertical) {
	        contents += gridPanel
	        contents += statsPanel
	        contents += buttonPanel
	    }
	    listenTo(exitButton, goButton, stepButton, stopButton)
	    
	    def step = {
	        if (simulation.count > 0) {
	        	simulation = simulation.step
    			ticks = ticks + 1
    			statsPanel.living.liveField.text = simulation.count.toString
    			statsPanel.tks.ticksField.text = ticks.toString
    			gridPanel.repaint()
	        }
        }
        def go = timer.start
        def stop = timer.stop
        def reset = {
	    	stop
            simulation = simFromArgs(args)
            ticks = 0
    		gridPanel.repaint()
        }
        val timer=new Timer(250, Swing.ActionListener(e => { step }))
   }

}