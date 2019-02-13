package GUI

import GUI.Design.Companion.mainView
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.stage.FileChooser
import tornadofx.*
import Controller
import java.io.File

class MainView:View("Simple Compressor by Greimul") {
    val myself = this
    val filterArr = arrayOf<FileChooser.ExtensionFilter>(FileChooser.ExtensionFilter("All files","*.*"))
    var filename = SimpleStringProperty("File not selected")
    var filepath:String = ""
    var processText = SimpleStringProperty("...")
    lateinit var cfile:File
    var filesize = SimpleStringProperty("0")
    var isCompress = SimpleBooleanProperty(true)
    var isDecompress = SimpleBooleanProperty(true)
    override val root = vbox(10.0){
        addClass(mainView)
        hbox {
            label("")
            label("Simple Compressor"){
                style{
                    fontSize = 20.px
                }
            }
        }
        button("Choose File"){
            action{
                val dir = chooseFile("Select File",filterArr)
                if(!dir.isEmpty()) {
                    filepath = dir[0].path
                    cfile = dir[0]
                    filename.set(dir[0].name)
                    filesize.set(dir[0].length().toString())
                    isCompress.set(false)
                    if(dir[0].extension=="grml")
                        isDecompress.set(false)
                }
            }
        }
        hbox {
            label("File Name:")
            label() {
                textProperty().bind(filename)
            }
            label(){
                hboxConstraints {
                    marginLeft = 10.0
                }
                textProperty().bind(filesize)
            }
            label("Bytes")
        }
        hbox {
            vbox {
                button("Compress!") {
                    disableProperty().bind(isCompress)
                    action {
                        Controller(myself).compressFile(cfile)
                    }
                }
                button("Decompress!") {
                    disableProperty().bind(isDecompress)
                }
            }
            label(){
                hboxConstraints {
                    marginLeft = 10.0
                }
                textProperty().bind(processText)
            }
        }
        label("Greimul"){
            vboxConstraints {
                marginLeft = 300.0
            }
        }
    }
    fun changeFinishText(fin:File){
        println("Finish " + filesize + "Bytes ->" + fin.length().toString())
        processText.set("Finish " + filesize.getValue() + "Bytes -> " + fin.length().toString()+"Bytes")
    }
}