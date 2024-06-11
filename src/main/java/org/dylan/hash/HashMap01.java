package org.dylan.hash;


import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Dylan
 * @version 2024/6/11
 */
public class HashMap01<K, V> implements Map<K, V> {

    private final Object[] table = new Object[8];

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {

        int idx = key.hashCode() & (table.length - 1);
        return (V) table[idx];
    }

    @Override
    public V put(K key, V value) {
        int idx = key.hashCode() & (table.length - 1);
        table[idx] = value;
        return value;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return Collections.emptySet();
    }

    @Override
    public Collection<V> values() {
        return Collections.emptyList();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Collections.emptySet();
    }
}
