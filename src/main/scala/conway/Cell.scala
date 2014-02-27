package conway

object Cell {
}
class Cell(i:Int, j:Int, living:Boolean) {
    def row = i
    def col = j
    def alive = living
    override def toString = "[cell " + i + "," + j + " " + (if (alive) "T" else "F") + "]"
}