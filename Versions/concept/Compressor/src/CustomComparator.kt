class CustomComparator {
    companion object:Comparator<Pair<Long,Node>> {
        override fun compare(o1: Pair<Long, Node>, o2: Pair<Long, Node>): Int=when {
            o1.first-o2.first>0->1
            else->-1
        }
    }
} 
