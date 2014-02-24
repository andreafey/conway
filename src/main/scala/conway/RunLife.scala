package conway

import scala.swing._

object RunLife extends SimpleSwingApplication {
  def simulation = Simulation.random(2, 4, 0.4)

  def top = new MainFrame {
    title = "Conway's Game of Life"
//	contents = new BorderPanel {
//		layout += (blinkPanel -> BorderPanel.Position.Center)
//	}
//	menuBar = new MenuBar {
//		contents += fileMenu
//	}
	size = new Dimension(600,400)
  }
}