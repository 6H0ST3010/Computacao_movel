package cache

class Cache<K : Any, V: Any>{
    val store = mutableMapOf<K,V>()

    fun put(key: K, value: V) {
        store[key] = value
    }

    fun get(key: K): V? {
        return when (store.containsKey(key)){
            true -> store[key]
            false -> null
        }
    }

    fun evict(key: K) {
        store.remove(key)
    }

    fun size(): Int {
        return store.size
    }

    fun getOrPut(key: K, default: () -> V): V {
        return store.getOrPut(key, default)
    }

    fun transform(key: K, action: (V) -> V): Boolean {
        val currentValue = store[key]
        if (currentValue != null) {
            val transformed = action(currentValue)
            store[key] = transformed
            return true
        }
        else {
            return false
        }
    }

    fun snapshot(): Map<K, V>{
        return store.toMap()
    }

}

fun main(){
    println("--- Word frequency cache ---")
    val wordCache = Cache<String, Int>()

    wordCache.put("kotlin", 1)
    wordCache.put("scala", 1)
    wordCache.put("haskell", 1)

    println("Size: ${wordCache.size()}")

    println("Frequency of 'kotlin' ${wordCache.get("kotlin")}")
    println("getOrPut 'kotlin': ${wordCache.getOrPut("kotlin") {1}}")
    println("getOrPut 'java': ${wordCache.getOrPut("java") {0}}")
    println("Size after getOrPut: ${wordCache.size()}")
    println("Transform 'kotlin': ${wordCache.transform("kotlin") {it + 1}}")
    println("Transform 'cobol': ${wordCache.transform("cobol") {it + 1}}")
    println("Snapshot: ${wordCache.snapshot()}")

    println("--- Id registry cache ---")
    val idCache = Cache<Int, String>()

    idCache.put(1, "Alice")
    idCache.put(2, "Bob")

    println("Id 1 -> ${idCache.get(1)}")
    println("Id 2 -> ${idCache.get(2)}")
    idCache.evict(1)
    println("After evict id 1, size: ${idCache.size()}")
    println("Id 1 after evict -> ${idCache.get(1)}")
}