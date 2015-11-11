import java.awt.{Graphics2D, Color}
import scala.swing.Panel

class DisplayPanel(initialData: Array[Array[Tile]]) extends Panel {
  var data = mapToColors(initialData)

  def mapToColors(newData: Array[Array[Tile]]) = {
    newData.map(_.map {
      case Wall() => Color.BLACK
      case Grass() => Color.GREEN
      case Troll() => Color.RED
      case _ => Color.WHITE
    })
  }

  def setData(newData: Array[Array[Tile]]): Unit = {
    data = mapToColors(newData)
    repaint()
  }

  override def paintComponent(g: Graphics2D) {
    val dx = g.getClipBounds.width.toFloat  / data.length
    val dy = g.getClipBounds.height.toFloat / data.map(_.length).max
    for {
      x <- data.indices
      y <- data(x).indices
      x1 = (x * dx).toInt
      y1 = (y * dy).toInt
      x2 = ((x + 1) * dx).toInt
      y2 = ((y + 1) * dy).toInt
    } {
      data(x)(y) match {
        case c: Color => g.setColor(c)
        case _ => g.setColor(Color.WHITE)
      }
      g.fillRect(x1, y1, x2 - x1, y2 - y1)
    }
  }
}
