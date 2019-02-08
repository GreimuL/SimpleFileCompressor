class DataProcess {
    fun makeTable(s:String):Array<Pair<Long,Char>>{
        var datatable:MutableMap<Char,Long> = mutableMapOf<Char,Long>()
        var cnt:Int =0
        for(i in s){
            if(datatable[i] == null) {
                datatable.put(i,1)
                cnt++
            }
            else {
                datatable.put(i, datatable[i]!! + 1)
            }
        }
        var indextable:Array<Pair<Long,Char>> = Array<Pair<Long,Char>>(cnt){Pair(0,'0')}
        var ind:Int =0
        for(i in datatable){
            indextable[ind] = Pair(i.value,i.key)
            ind++
        }
        return indextable
    }
}