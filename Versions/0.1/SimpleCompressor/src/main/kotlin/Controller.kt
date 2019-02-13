import GUI.*
import Model.*
import java.io.File

class Controller(v:MainView) {
    val view = v
    fun compressFile(path:File){
        MainProcess(this).compressStart(path)
    }
    fun compressComplete(fin:File){
        view.changeFinishText(fin)
    }
}