import tornadofx.*
import javafx.stage.Stage

class Main: App(GUI.MainView::class, GUI.Design::class){
    override fun start(st:Stage){
        st.isResizable = false
        super.start(st)
    }
}
fun main(args:Array<String>){
    launch<Main>(args)
}
