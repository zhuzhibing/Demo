package net.luculent.automatioin.laks.platform.util;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Author: zhuzb
 * @Description:
 * @Date Create In 13:45 2018/1/30
 * @Modified By:
 */

public class LaksMap<K, V> extends HashMap<K, V> implements Serializable {

    private static final long serialVersionUID = 1L;

//
//    public LaksMap(int initialCapacity, float loadFactor) {
//        if (initialCapacity < 0)
//            throw new IllegalArgumentException("Illegal initial capacity: " +
//                    initialCapacity);
//        if (initialCapacity > MAXIMUM_CAPACITY)
//            initialCapacity = MAXIMUM_CAPACITY;
//        if (loadFactor <= 0 || Float.isNaN(loadFactor))
//            throw new IllegalArgumentException("Illegal load factor: " +
//                    loadFactor);
//        this.loadFactor = loadFactor;
//        this.threshold = tableSizeFor(initialCapacity);
//    }
//
//    public LaksMap(int initialCapacity) {
//        this(initialCapacity, DEFAULT_LOAD_FACTOR);
//    }
//
//    public LaksMap() {
//        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
//    }
//
//
//    public LaksMap(Map<? extends K, ? extends V> m) {
//
//        this.loadFactor = DEFAULT_LOAD_FACTOR;
//        putMapEntries(m, false);
//    }


}

