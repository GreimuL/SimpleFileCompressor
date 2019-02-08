class DecompAlgo {
    fun decompress(path:String,node:Node,offset:Int,arrlen:Int):ByteArray{
        var curNode:Node = node
        var sBuild:StringBuilder = StringBuilder()
        var byteArr:ByteArray = ByteArray(arrlen)
        var ind:Int = 0
        for(i in 0..path.length-1-offset) {
            if (path[i] == '0') {
                curNode = curNode.left!!
                if (curNode.data != null) {
                    byteArr[ind] = curNode.data!!.toByte()
                    ind++
                    curNode = node
                }
            } else {
                curNode = curNode.right!!
                if (curNode.data != null) {
                    byteArr[ind] = curNode.data!!.toByte()
                    ind++
                    curNode = node
                }
            }
        }
        return byteArr
    }
    fun readStr(node:Node){

    }
}