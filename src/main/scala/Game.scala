import java.util.Timer

import swing.{MainFrame, SimpleSwingApplication}
import java.awt.Dimension

object Game extends SimpleSwingApplication {
  def top = new MainFrame {
    val gameLogic = new GameLogic()

    val panel = new DisplayPanel(gameLogic.nextState()) {
      preferredSize = new Dimension(300, 300)
    }
    contents = panel

    val fps = 3
    val timer = new Timer()
    timer.schedule(new GameLoop(), 0, 1000 / fps)
    class GameLoop extends java.util.TimerTask
    {
      def run() = panel.setData(gameLogic.nextState())
    }
  }
}
