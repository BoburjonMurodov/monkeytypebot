package bot.boboor.monkeytype

import net.sourceforge.tess4j.Tesseract
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Graphics
import java.awt.GraphicsEnvironment
import java.awt.Point
import java.awt.Rectangle
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseMotionAdapter
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JWindow


fun main() {
    val frame = JFrame("Monkeytype Bot UI").apply {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        size = Dimension(400, 200)
        layout = FlowLayout()
    }
    var selectedRect: Rectangle? = null
    val teserract = Tesseract()

    teserract.setDatapath("tess/")
    teserract.setLanguage("eng")



    val screenshotButton = JButton("📸 Screenshot").apply {
        addActionListener {
            showScreenshotSelector { rect ->
                selectedRect = rect
                val bot = Robot()
                val image = bot.createScreenCapture(rect)


                val text = teserract.doOCR(image)
                println(text)
            }
        }
    }

    val startTypingButton = JButton("⌨️ Start Typing").apply {
        addActionListener {
            val rect = selectedRect ?: return@addActionListener

            val bot = Robot()
            val centerX = rect.x + rect.width / 2
            val centerY = rect.y + rect.height / 2

            bot.mouseMove(centerX, centerY)
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)

            bot.mouseMove(0, 0)
            Thread.sleep(100)

            Thread.sleep(500)
            val image = bot.createScreenCapture(rect)
            ImageIO.write(image, "png", File("screenshot${System.currentTimeMillis()}.png"))


            val text = teserract.doOCR(image)


            val cleanedText = text
                .replace("\n", " ")
                .replace(Regex("\\s+"), " ")
                .trim()

            println("Typing: $cleanedText")

            val words = cleanedText.split(" ")


            for (word in words) {
                for (char in word) {
                    val time = System.currentTimeMillis()

                    val keyCode = KeyEvent.getExtendedKeyCodeForChar(char.code)
//                    if (keyCode != KeyEvent.VK_UNDEFINED) {
                        bot.keyPress(keyCode)
                        bot.keyRelease(keyCode)
//                    }

                    println("time spent: ${System.currentTimeMillis() - time}")
                }

                bot.keyPress(KeyEvent.VK_SPACE)
                bot.keyRelease(KeyEvent.VK_SPACE)
            }
        }
    }
    frame.add(screenshotButton)
    frame.add(startTypingButton)

    frame.isVisible = true
}


fun showScreenshotSelector(onSelected: (Rectangle) -> Unit) {
    object : JWindow() {
        var start: Point? = null
        var end: Point? = null

        init {
            background = Color(0, 0, 0, 30)
            bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds
            isAlwaysOnTop = true
            isVisible = true

            val panel = object : JPanel() {
                override fun paintComponent(g: Graphics) {
                    super.paintComponent(g)
                    if (start != null && end != null) {
                        val x = minOf(start!!.x, end!!.x)
                        val y = minOf(start!!.y, end!!.y)
                        val w = Math.abs(end!!.x - start!!.x)
                        val h = Math.abs(end!!.y - start!!.y)

                        g.color = Color.RED
                        g.drawRect(x, y, w, h)
                    }
                }
            }

            panel.isOpaque = false
            panel.addMouseListener(object : MouseAdapter() {
                override fun mousePressed(e: MouseEvent) {
                    start = e.locationOnScreen
                }

                override fun mouseReleased(e: MouseEvent) {
                    end = e.locationOnScreen

                    val x = minOf(start!!.x, end!!.x)
                    val y = minOf(start!!.y, end!!.y)
                    val w = Math.abs(end!!.x - start!!.x)
                    val h = Math.abs(end!!.y - start!!.y)

                    val rect = Rectangle(x, y, w, h)
                    dispose()
                    onSelected(rect)
                }
            })

            panel.addMouseMotionListener(object : MouseMotionAdapter() {
                override fun mouseDragged(e: MouseEvent) {
                    end = e.point
                    panel.repaint()
                }
            })

            contentPane = panel
        }
    }
}