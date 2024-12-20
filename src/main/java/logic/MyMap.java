package logic;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface MyMap<K, V> {
    void clear();
    boolean containsKey(Object k);
    boolean containsValue(Object v);
    boolean equals(Object obj);
    boolean isEmpty();
    V get(Object k);
    V getOrDefault(Object k, V defaultValue);
    V put(K k, V v);
    Set<K> keySet();
    List<V> values();
    void putAll(Map<? extends K, ? extends V> map);
    V remove(Object k);
    int size();
}
