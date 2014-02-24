package example

import scala.swing._
import java.awt.Color

object Draft1App extends SimpleSwingApplication {
    def top = new MainFrame {
      title = "Draft Swing App"
      size = new Dimension(400,400)
    }
	// main panel in the display
	val panel = new Panel { 
		override def paint(g : Graphics2D) {
			g.setPaint(Color.WHITE)
			// fill entire space with white
			g.fillRect(0, 0, size.width, size.height)
			
			// iterate through grid and fill each live grid point
//			if (blinkOn) {
//				g.setPaint(Color.BLACK)
//				g.fillRect(size.width/4, size.height/4, size.width/2, size.height/2)
//			}
		}
	}

}