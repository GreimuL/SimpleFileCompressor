package Model

import Model.Parts.Node
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream
import tornadofx.toProperty
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class DataProcess {
    var headStr:StringBuilder = StringBuilder()

    fun makeTable(s:ByteArray):Array<Pair<Long,Int>>{
        var datatable:HashMap<Int,Long> = HashMap<Int,Long>()
        var cnt:Int =0
        for(i in s){
            var tmp:Int = i.toInt()
            if(datatable[tmp] == null) {
                datatable.put(tmp,1)
                cnt++
            }
            else {
                datatable.put(tmp, datatable[tmp]!! + 1)
            }
        }
        var indextable:Array<Pair<Long,Int>> = Array<Pair<Long,Int>>(cnt){Pair(0,0)}
        var ind:Int =0
        for(i in datatable){
            indextable[ind] = Pair(i.value,i.key)
            ind++
        }
        return indextable
    }
    fun makeMaptoByte(map:HashMap<Int,String>):ByteArray{
        var byteArr =ByteArrayOutputStream()
        var mapConv  = ObjectOutputStream(byteArr)
        mapConv.writeObject(map)
        println(byteArr.toByteArray())
        return byteArr.toByteArray()
    }
    fun makeBytetoMap(ba:ByteArray):HashMap<Int,String>{
        println(ba)
        val ois = ObjectInputStream(ByteArrayInputStream(ba))
        return ois.readObject() as HashMap<Int,String>
    }
    fun makeMaptoNode(map:HashMap<Int,String>):Node{
        val headNode:Node = Node(null,null,null)
        var curNode:Node = headNode
        map.forEach {
            curNode = headNode
            var data:Int = it.component1()
            var piv = 0
            val len=it.component2().length
            it.component2().forEach {
                if(it=='0'){
                    if(curNode.left==null){
                        curNode.left = Node(null,null,null)
                    }
                    curNode = curNode.left!!
                }
                else{
                    if(curNode.right==null){
                        curNode.right = Node(null,null,null)
                    }
                    curNode = curNode.right!!
                }
                if(piv==len-1){
                    curNode.data = data
                }
                piv++
            }
        }
        return headNode
    }
}