class DataProcess {
    fun makeTable(s:ByteArray):Array<Pair<Long,Int>>{
        var datatable:MutableMap<Int,Long> = mutableMapOf<Int,Long>()
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
}