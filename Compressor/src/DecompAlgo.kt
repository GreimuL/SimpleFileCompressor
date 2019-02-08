class DecompAlgo {
    fun decompress(path:String,node:Node,offset:Int):String{
        var curNode:Node = node
        var sBuild:StringBuilder = StringBuilder()
        for(i in 0..path.length-1-offset) {
            if (path[i] == '0') {
                curNode = curNode.left!!
                if (curNode.data != null) {
                    sBuild.append(curNode.data!!)
                    curNode = node
                }
            } else {
                curNode = curNode.right!!
                if (curNode.data != null) {
                    sBuild.append(curNode.data!!)
                    curNode = node
                }
            }
        }
        return sBuild.toString()
    }
    fun readStr(node:Node){

    }
}