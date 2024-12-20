package logic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TreeMapTest {
    TreeMap<Integer, Integer> tr = new TreeMap<>();

    @Test
    void clear() {
        tr.put(1,2);
        tr.put(2,2);
        tr.put(3,2);
        tr.put(4,2);
        tr.put(5,2);
        tr.clear();
        assertNull(tr.root);
    }

    @Test
    void containsKey() {
        tr.put(1,2);
        tr.put(2,2);
        tr.put(3,2);
        tr.put(4,2);
        tr.put(5,2);
        assertTrue(tr.containsKey(1));
        assertTrue(tr.containsKey(5));
    }

    @Test
    void containsValue() {
        tr.put(1,1);
        tr.put(2,2);
        tr.put(3,3);
        tr.put(4,4);
        tr.put(5,25);
        assertTrue(tr.containsValue(1));
        assertTrue(tr.containsValue(25));
    }

    @Test
    void isEmpty() {
        tr.clear();
        assertTrue(tr.isEmpty());
        tr = new TreeMap<Integer,Integer>();
        assertTrue(tr.isEmpty());

    }

    @Test
    void get() {
        tr.put(1,1);
        tr.put(2,2);
        assertEquals(tr.get(1), 1);
    }

    @Test
    void getOrDefault() {
        tr.put(1,1);
        tr.put(2,2);
        assertNull(tr.getOrDefault(4, null));
    }

    @Test
    void put() {
        assertDoesNotThrow(() -> tr.put(1,1));
        assertDoesNotThrow(() -> tr.put(2,1));
        assertDoesNotThrow(() -> tr.put(13,1));
    }

    @Test
    void keySet() {
        tr.put(1,1);
        tr.put(2,2);
        tr.put(3,3);
        tr.put(4,4);
        tr.put(5,5);
        assertEquals(tr.keySet(), new HashSet<>(Arrays.asList(1,2,3,4,5)));
    }

    @Test
    void values() {
        tr.put(1,1);
        tr.put(2,2);
        tr.put(3,3);
        tr.put(4,4);
        tr.put(5,5);
        assertEquals(tr.values(), Arrays.asList(1,2,3,4,5));
    }


    @Test
    void putAll() {
    }

    @Test
    void remove() {
            TreeMap<Integer, String> treeMap = new TreeMap<>();
            treeMap.put(5, "five");
            treeMap.put(3, "three");
            treeMap.put(7, "seven");
            treeMap.put(6, "six");

            // Remove a node with one child (key 7)
            String removed = treeMap.remove(7);
            assertEquals("seven", removed, "Expected to remove value 'seven'");

            // Verify the key is no longer in the map
            assertFalse(treeMap.containsKey(7), "Key 7 should no longer exist in the map");
            assertTrue(treeMap.containsKey(6), "Key 6 should still exist in the map");
            assertEquals(3, treeMap.size(), "Size should be 3 after removal");
    }

    @Test
    void size() {
        tr.put(1,1);
        tr.put(1,1);
        tr.put(3,1);
        tr.put(4,1);
        tr.put(5,1);
        tr.remove(1);
        assertEquals(tr.size, 3);
    }
}