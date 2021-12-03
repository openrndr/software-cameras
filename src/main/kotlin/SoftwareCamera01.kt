import org.openrndr.application
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.compositor.post
import org.openrndr.extra.fx.blur.FrameBlur
import org.openrndr.extras.imageFit.imageFit
import org.openrndr.ffmpeg.loadVideoDevice

fun main() = application {
    configure {
        width = 720
        height = 720
    }

    program {
        val camera = loadVideoDevice()
        camera.play()

        val comp = compose {
            layer {
                draw {
                    val cameraImage = camera.colorBuffer
                    if (cameraImage != null) {
                        drawer.imageFit(cameraImage, drawer.bounds)
                    }
                }
            }
        }

        extend(Screenshots())
        extend {
            camera.draw(drawer, blind = false)
            comp.draw(drawer)
        }
    }
}
