import java.io.File
import java.nio.charset.Charset
import java.util.*
import kotlin.experimental.and

fun main(args:Array<String>){
    val algo:CompAlgo = CompAlgo()
    val process:DataProcess  = DataProcess()

    val fileName:String = "src/CompAlgo.kt"
    val content = File(fileName).readBytes().toString(Charset.defaultCharset())

    val headNode:Node = algo.makeCode(process.makeTable(content))

    val codeMap:MutableMap<Char,String> =algo.getCodeMap(headNode)
    var str:String = ""
    println("makeCode Complete")
    var sbuild:StringBuilder = StringBuilder()
    content.forEach{sbuild.append(codeMap[it])}
    println("string Complete")
    str = sbuild.toString()
    var bittest: BitSet = BitSet(str.length)
    var ind = 0
    //println(str.toString())

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
    //bittest.toByteArray().forEach{println(it)}
    File("src/test.grml").writeBytes(bittest.toByteArray())
    //codeMap.forEach{println(it)}
    val loadName:String = "src/test.grml"
    val content2 = File(loadName).readBytes()
    //content2.forEach{println(it)}
    var bittest2:BitSet = BitSet(str.length)
    var str2:String =""
    var sBuild2:StringBuilder=StringBuilder()
    content2.forEach{sBuild2.append(String.format("%8s",Integer.toBinaryString(it.toInt() and 0xFF  )).replace(' ','0'))}
    /*content2.forEach{sBuild2.append(String.format("%8s",Integer.toBinaryString({
            num:Int->num shl (8-Integer.toBinaryString(it.toInt()).length)
    }(it.toInt()))).replace(' ','0'))}*/
    println(sBuild2.toString().length)
    println(str.length)
    val dec:DecompAlgo = DecompAlgo()
    File("src/testtext.txt").writeBytes(dec.decompress(sBuild2.toString(),headNode,offset).toByteArray())

}
