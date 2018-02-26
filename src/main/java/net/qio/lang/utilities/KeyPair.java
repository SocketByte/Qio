package net.qio.lang.utilities;

import java.util.AbstractMap;
import java.util.Map;

public class KeyPair<K, V> extends AbstractMap.SimpleEntry<K, V> {

    private K key;
    private V value;

    public KeyPair(K key, V value) {
        super(key, value);
    }

    public Map.Entry<K, V> getEntry() {
        return this;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }
}
