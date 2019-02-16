package Model

import Model.Parts.*
import Controller
import tornadofx.getProperty
import java.io.*
import java.nio.ByteBuffer
import java.util.*
import kotlin.collections.RandomAccess

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
        val fileInfo:ByteArray = (cfile.nameWithoutExtension+"\n"+cfile.extension+"\n").toByteArray()
        val headBytearr:ByteArray = DataProcess().makeMaptoByte(codeMap)
        val offsetarr:ByteArray = (offset.toString()+"\n").toByteArray()
        val filesize:ByteArray = (content.size.toString()+"\n").toByteArray()
        val headlen:ByteArray = (headBytearr.size.toString()+"\n").toByteArray()
        val finallen:Int = bittest.toByteArray().size+ headBytearr.size+headlen.size
        var finalArr:ByteArray = ByteArray(finallen+offsetarr.size+filesize.size+fileInfo.size)
        var piv:Int = 0
        offsetarr.forEach {
            finalArr[piv] = it
            piv++
        }
        headlen.forEach{
            finalArr[piv]= it
            piv++
        }
        filesize.forEach {
            finalArr[piv] = it
            piv++
        }
        fileInfo.forEach {
            finalArr[piv] = it
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
        val numList:List<String> = cfile.useLines{
            it.take(5).toList()
        }
        val raf:RandomAccessFile = RandomAccessFile(cfile,"r")
        raf.seek((numList[0]+"\n"+numList[1]+"\n"+numList[2]+"\n"+numList[3]+"\n"+numList[4]+"\n").toByteArray().size.toLong())
        //val fis:FileInputStream = FileInputStream(cfile)
        //val bb:ByteBuffer = ByteBuffer.allocateDirect(numList[1].toInt())
        //fis.channel.read(bb,(numList[0]+numList[1]+numList[2]+numList[3]+"\n\n\n").toByteArray().size.toLong())
        val headArr:ByteArray = ByteArray(numList[1].toInt())
        //bb.get(headArr)
        raf.read(headArr)
        val codeMap:HashMap<Int,String> = DataProcess().makeBytetoMap(headArr)

        val firlen = (numList[0]+"\n"+numList[1]+"\n"+numList[2]+"\n"+numList[3]+"\n"+numList[4]+"\n").toByteArray().size+numList[1].toInt()

        var sBuild:StringBuilder=StringBuilder()
        var piv:Int = 0
        cfile.readBytes().forEach{
            if(piv>=firlen){
                sBuild.append(String.format("%8s",Integer.toBinaryString(it.toInt() and 0xFF  )).replace(' ','0'))
            }
            piv++
        }
        val final = DecompAlgo().decompress(sBuild.toString(),DataProcess().makeMaptoNode(codeMap),numList[0].toInt(),numList[2].toInt())

        var num:Int =0
        if(File(cfile.parent+ "\\"+numList[3]+"."+numList[4]).exists()){
            num++
        }
        while(File(cfile.parent+ "\\"+numList[3]+"(${num})"+"."+numList[4]).exists()){
            num++
        }
        if(num==0) {
            File(cfile.parent+ "\\" +numList[3]+"."+numList[4]).writeBytes(final)
            control.decompressComplete(File(cfile.parent + "\\" + numList[3]+"."+numList[4]))
        }
        else{
            File(cfile.parent+ "\\" + numList[3]+"(${num})"+"."+numList[4]).writeBytes(final)
            control.decompressComplete(File(cfile.parent + "\\" + numList[3]+"(${num})"+"."+numList[4]))
        }
    }
}