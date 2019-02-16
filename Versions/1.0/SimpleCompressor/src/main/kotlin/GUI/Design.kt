package GUI

import javafx.geometry.Pos
import tornadofx.*
class Design:Stylesheet(){
    companion object {
        val mainView by cssclass()
    }
    init{
        mainView{
            alignment = Pos.TOP_LEFT
        }
    }
}