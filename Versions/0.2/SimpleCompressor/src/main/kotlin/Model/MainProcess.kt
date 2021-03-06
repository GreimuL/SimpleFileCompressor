package Model

import java.io.File
import Model.Parts.*
import Controller
import tornadofx.getProperty
import java.io.FileWriter
import java.util.*

class MainProcess (c:Controller){
    val control = c
    fun compressStart(cfile:File){
        val path:String = cfile.path
        val content = File(path).readBytes()
        val headNode:Node = CompAlgo().makeCode(DataProcess().makeTable(content))
        val codeMap:HashMap<Int,String> = CompAlgo().getCodeMap(headNode)
        var str:String = ""
        var sbuild:StringBuilder = StringBuilder()
        content.forEach{sbuild.append(codeMap[it.toInt()])}
        str = sbuild.toString()
        var bittest: BitSet = BitSet(str.length)
        var ind = 0
        var ran:Int = str.length/8
        var offset:Int =0
        if(str.length%8!=0){
            ran++
            offset = ran*8-str.length
        }
        for(i in 0..ran-1){
            for(j in 0..7) {
                if(i * 8 + 7 - j>=str.length){
                    bittest.set(i*8+j,false)
                }
                else {
                    bittest.set(i * 8 + j, { ch: Char ->
                        when {
                            ch == '1' -> true
                            else -> false
                        }
                    }(str[i * 8 + 7 - j]))
                }
            }
        }
        var headBytearr:ByteArray = DataProcess().makeMaptoByte(codeMap)
        var headlen:ByteArray = (headBytearr.size.toString()+" ").toByteArray()
        var finallen:Int = bittest.toByteArray().size+ headBytearr.size+headlen.size
        var finalArr:ByteArray = ByteArray(finallen)
        var piv:Int = 0

        headlen.forEach{
            finalArr[piv]= it
            piv++
        }
        headBytearr.forEach{
            finalArr[piv] = it
            piv++
        }
        bittest.toByteArray().forEach{
            finalArr[piv] = it
            piv++
        }

        File(cfile.parent+ "\\" +cfile.nameWithoutExtension+".grml").writeBytes(finalArr)
        control.compressComplete(File(cfile.parent+ "\\" +cfile.nameWithoutExtension+".grml"))
    }
    fun decompressStart(cfile:File){
        val path:String = cfile.path
    }
}