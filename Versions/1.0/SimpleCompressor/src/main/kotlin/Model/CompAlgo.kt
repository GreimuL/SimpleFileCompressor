package Model
import Model.Parts.*
import java.util.*

class CompAlgo {
    fun makeCode(indextable:Array<Pair<Long,Int>>):Node{
        lateinit var headNode:Node
        var pq:PriorityQueue<Pair<Long,Node>> = PriorityQueue(CustomComparator)
        indextable.forEach{pq.add(Pair(it.first,Node(null,null,it.second)))}
        while(!pq.isEmpty()){
            if(pq.size==1){
                headNode = pq.peek().second
                break
            }
            val left:Pair<Long,Node> = pq.peek()
            pq.remove()
            val right:Pair<Long,Node> = pq.peek()
            pq.remove()
            val tmpNode:Node = Node(left.second,right.second,null)
            pq.add(Pair(left.first+right.first,tmpNode))
        }
        return headNode
    }

    fun setCode(node:Node,s:String,codeMap:HashMap<Int,String>){
        if(node.left != null){
            setCode(node.left!!,s+'0',codeMap)
        }
        if(node.right!=null){
            setCode(node.right!!,s+'1',codeMap)
        }
        if(node.data!=null) {
            codeMap[node.data!!] = s
        }
    }
    fun getCodeMap(node:Node):HashMap<Int,String>{
        var codeMap:HashMap<Int,String> = HashMap<Int,String>()
        setCode(node,"",codeMap)
        return codeMap
    }
}