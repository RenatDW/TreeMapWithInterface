package logic;

public interface MyEntry<K, V> {
        boolean equals(Object obj);
        K getKey();
        V getValue();
        V setValue(V v);
        int hashCode();
}
