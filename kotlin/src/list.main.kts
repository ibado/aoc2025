fun <T> List<T>.pairs(): List<Pair<T, T>> = buildList {
    for (i in this@pairs.indices) {
        for (j in (i+1)..<this@pairs.size) {
            add(this@pairs[i] to this@pairs[j])
        }
    }
}