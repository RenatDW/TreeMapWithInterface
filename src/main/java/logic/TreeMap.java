package logic;

import java.util.*;

public class TreeMap<K extends Comparable<K>, V extends Comparable<V>> implements MyMap<K, V> {
    public TreeNode<K, V> root;
    public int size = 0;

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public class TreeNode<K, V> {
        K key;
        V value;
        TreeNode<K, V> leftSide;
        TreeNode<K, V> rightSide;
        TreeNode<K, V> parent;
        boolean color; // true for red, false for black

        public TreeNode(K key, V value, boolean color, TreeNode<K, V> parent) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.parent = parent;
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(Object k) {
        return getNode((K) k) != null;
    }

    @Override
    public V get(Object k) {
        TreeNode<K, V> node = getNode((K) k);
        return node == null ? null : node.value;
    }

    @Override
    public V getOrDefault(Object k, V defaultValue) {
        return null;
    }

    private TreeNode<K, V> getNode(K key) {
        TreeNode<K, V> current = root;
        while (current != null) {
            int compare = key.compareTo(current.key);
            if (compare < 0) {
                current = current.leftSide;
            } else if (compare > 0) {
                current = current.rightSide;
            } else {
                return current;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (root == null) {
            root = new TreeNode<>(key, value, BLACK, null);
            size++;
            return null;
        }

        TreeNode<K, V> current = root;
        TreeNode<K, V> parent = null;
        int compare = 0;

        while (current != null) {
            parent = current;
            compare = key.compareTo(current.key);
            if (compare < 0) {
                current = current.leftSide;
            } else if (compare > 0) {
                current = current.rightSide;
            } else {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
        }

        TreeNode<K, V> newNode = new TreeNode<>(key, value, RED, parent);
        if (compare < 0) {
            parent.leftSide = newNode;
        } else {
            parent.rightSide = newNode;
        }

        size++;
        fixAfterInsertion(newNode);
        return null;
    }

    private void fixAfterInsertion(TreeNode<K, V> node) {
        while (node != null && node != root && node.parent.color == RED) {
            if (node.parent == node.parent.parent.leftSide) {
                TreeNode<K, V> uncle = node.parent.parent.rightSide;
                if (uncle != null && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.rightSide) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateRight(node.parent.parent);
                }
            } else {
                TreeNode<K, V> uncle = node.parent.parent.leftSide;
                if (uncle != null && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.leftSide) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rotateLeft(node.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    private void rotateLeft(TreeNode<K, V> node) {
        TreeNode<K, V> rightChild = node.rightSide;
        node.rightSide = rightChild.leftSide;

        if (rightChild.leftSide != null) {
            rightChild.leftSide.parent = node;
        }
        rightChild.parent = node.parent;

        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.leftSide) {
            node.parent.leftSide = rightChild;
        } else {
            node.parent.rightSide = rightChild;
        }

        rightChild.leftSide = node;
        node.parent = rightChild;
    }

    private void rotateRight(TreeNode<K, V> node) {
        TreeNode<K, V> leftChild = node.leftSide;
        node.leftSide = leftChild.rightSide;

        if (leftChild.rightSide != null) {
            leftChild.rightSide.parent = node;
        }
        leftChild.parent = node.parent;

        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.rightSide) {
            node.parent.rightSide = leftChild;
        } else {
            node.parent.leftSide = leftChild;
        }

        leftChild.rightSide = node;
        node.parent = leftChild;
    }

    @Override
    public V remove(Object key) {
        TreeNode<K, V> node = getNode((K) key);
        if (node == null) return null;

        V oldValue = node.value;
        deleteNode(node);
        size--;
        return oldValue;
    }

    private void deleteNode(TreeNode<K, V> node) {
        if (node.leftSide != null && node.rightSide != null) {
            TreeNode<K, V> successor = findMin(node.rightSide);
            node.key = successor.key;
            node.value = successor.value;
            node = successor;
        }

        TreeNode<K, V> replacement = (node.leftSide != null) ? node.leftSide : node.rightSide;

        if (replacement != null) {
            replacement.parent = node.parent;
            if (node.parent == null) {
                root = replacement;
            } else if (node == node.parent.leftSide) {
                node.parent.leftSide = replacement;
            } else {
                node.parent.rightSide = replacement;
            }

            node.leftSide = node.rightSide = node.parent = null;

            if (node.color == BLACK) {
                fixAfterDeletion(replacement);
            }
        } else if (node.parent == null) {
            root = null;
        } else {
            if (node.color == BLACK) {
                fixAfterDeletion(node);
            }

            if (node.parent != null) {
                if (node == node.parent.leftSide) {
                    node.parent.leftSide = null;
                } else {
                    node.parent.rightSide = null;
                }
                node.parent = null;
            }
        }
    }

    private void fixAfterDeletion(TreeNode<K, V> node) {
        while (node != root && colorOf(node) == BLACK) {
            if (node == leftOf(parentOf(node))) {
                TreeNode<K, V> sibling = rightOf(parentOf(node));

                if (colorOf(sibling) == RED) {
                    setColor(sibling, BLACK);
                    setColor(parentOf(node), RED);
                    rotateLeft(parentOf(node));
                    sibling = rightOf(parentOf(node));
                }

                if (colorOf(leftOf(sibling)) == BLACK && colorOf(rightOf(sibling)) == BLACK) {
                    setColor(sibling, RED);
                    node = parentOf(node);
                } else {
                    if (colorOf(rightOf(sibling)) == BLACK) {
                        setColor(leftOf(sibling), BLACK);
                        setColor(sibling, RED);
                        rotateRight(sibling);
                        sibling = rightOf(parentOf(node));
                    }

                    setColor(sibling, colorOf(parentOf(node)));
                    setColor(parentOf(node), BLACK);
                    setColor(rightOf(sibling), BLACK);
                    rotateLeft(parentOf(node));
                    node = root;
                }
            } else {
                TreeNode<K, V> sibling = leftOf(parentOf(node));

                if (colorOf(sibling) == RED) {
                    setColor(sibling, BLACK);
                    setColor(parentOf(node), RED);
                    rotateRight(parentOf(node));
                    sibling = leftOf(parentOf(node));
                }

                if (colorOf(rightOf(sibling)) == BLACK && colorOf(leftOf(sibling)) == BLACK) {
                    setColor(sibling, RED);
                    node = parentOf(node);
                } else {
                    if (colorOf(leftOf(sibling)) == BLACK) {
                        setColor(rightOf(sibling), BLACK);
                        setColor(sibling, RED);
                        rotateLeft(sibling);
                        sibling = leftOf(parentOf(node));
                    }

                    setColor(sibling, colorOf(parentOf(node)));
                    setColor(parentOf(node), BLACK);
                    setColor(leftOf(sibling), BLACK);
                    rotateRight(parentOf(node));
                    node = root;
                }
            }
        }

        setColor(node, BLACK);
    }

    private TreeNode<K, V> findMin(TreeNode<K, V> node) {
        while (node.leftSide != null) {
            node = node.leftSide;
        }
        return node;
    }

    private <K, V> boolean colorOf(TreeNode<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private <K, V> TreeNode<K, V> parentOf(TreeNode<K, V> node) {
        return node == null ? null : node.parent;
    }

    private <K, V> TreeNode<K, V> leftOf(TreeNode<K, V> node) {
        return node == null ? null : node.leftSide;
    }

    private <K, V> TreeNode<K, V> rightOf(TreeNode<K, V> node) {
        return node == null ? null : node.rightSide;
    }

    private <K, V> void setColor(TreeNode<K, V> node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsValue(Object value) {
        for (V v : values()) {
            if (Objects.equals(v, value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        inOrderTraversalKeys(root, keys);
        return keys;
    }

    private void inOrderTraversalKeys(TreeNode<K, V> node, Set<K> keys) {
        if (node == null) return;
        inOrderTraversalKeys(node.leftSide, keys);
        keys.add(node.key);
        inOrderTraversalKeys(node.rightSide, keys);
    }

    @Override
    public List<V> values() {
        List<V> values = new ArrayList<>();
        inOrderTraversalValues(root, values);
        return values;
    }

    private void inOrderTraversalValues(TreeNode<K, V> node, List<V> values) {
        if (node == null) return;
        inOrderTraversalValues(node.leftSide, values);
        values.add(node.value);
        inOrderTraversalValues(node.rightSide, values);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }
}