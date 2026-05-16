import ui.swing.OthelloSwingApp
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater {
        OthelloSwingApp().createAndShowGUI()
    }
}