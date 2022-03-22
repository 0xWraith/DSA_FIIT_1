package com.wraith.dsa;

import java.util.Arrays;

public class HashMapChaining<K, V>
{
    private static class ChainingNode<K, V>
    {
        private final K key;
        private V value;
        private int hash;
        private ChainingNode<K, V> next;

        public ChainingNode(K key, V value, int hash)
        {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = null;
        }

        public K getKey() { return this.key; }
        public V getValue() { return this.value; }
        public int getHash() { return this.hash; }
        public ChainingNode<K, V> getNext() { return this.next; }

        public void setHash(int hash) { this.hash = hash; }
        public void setValue(V value) { this.value = value; }
        public void setNext(ChainingNode<K, V> next) { this.next = next; }
    }

    private int size;
    private int capacity;
    private float threshold;
    private final float loadFactor;
    private ChainingNode<K, V>[] hashTable;

    public HashMapChaining() { this(16, 0.75F); }
    public HashMapChaining(int capacity, float loadFactor)
    {
        this.capacity = capacity;
        this.loadFactor = loadFactor;

        threshold = capacity * loadFactor;
        hashTable = new ChainingNode[capacity];
    }
    public void insert(K key, V value)
    {
        int hash = hash(key);
        int idx = index(hash);

        ChainingNode<K, V> node = hashTable[idx];

        if(node == null)
            hashTable[idx] = new ChainingNode<>(key, value, hash);

        else if(compare(node, key, hash))
            node.setValue(value);

        else
        {
            while (true)
            {
                if(compare(node, key, hash))
                {
                    node.setValue(value);
                    break;
                }
                if(node.getNext() == null)
                {
                    node.setNext(new ChainingNode<>(key, value, hash));
                    break;
                }

                node = node.getNext();
            }
        }

        if(++size > threshold)
            resize();
    }
    public V search(K key)
    {
        if(key == null)
            return null;

        int hash = hash(key);
        int idx = index(hash);

        if(idx >= capacity || hashTable[idx] == null)
            return null;


        ChainingNode<K, V> node = hashTable[idx];

        if(compare(node, key, hash))
            return node.getValue();

        node = node.getNext();

        while (node != null)
        {
            if(compare(node, key, hash))
                return node.getValue();

            node = node.getNext();
        }

        return null;
    }
    public boolean delete(K key)
    {
        if(key == null)
            return false;

        int hash = hash(key);
        int idx = index(hash);

        if(idx >= capacity || hashTable[idx] == null)
            return false;

        ChainingNode<K, V> node = hashTable[idx];

        if(compare(node, key, hash))
        {
            hashTable[idx] = node.getNext();
            return true;
        }
        else
        {
            if(node.getNext() == null)
                return false;

            while (node != null)
            {
                if(compare(node.getNext(), key, hash))
                    node.setNext(node.getNext().getNext());

                node = node.getNext();
            }
        }

        return true;
    }
    private void resize()
    {
        ChainingNode<K, V> node, temp;
        ChainingNode<K, V>[] newHashTable = hashTable;

        size = 0;
        capacity *= 2;
        threshold = capacity * loadFactor;

        hashTable = new ChainingNode[capacity];

        for (ChainingNode<K, V> kvChainingNode : newHashTable)
        {
            node = kvChainingNode;

            if (kvChainingNode == null)
                continue;

            while (node != null)
            {
                resizeInsert(node);

                temp = node.getNext();
                node.setNext(null);
                node = temp;
            }
        }
    }
    private void resizeInsert(ChainingNode<K, V> nodeToInsert)
    {
        int hash = hash(nodeToInsert.getKey());
        int idx = index(hash);

        ChainingNode<K, V> node = hashTable[idx];

        nodeToInsert.setHash(hash);

        if(node == null)
            hashTable[idx] = nodeToInsert;

        else
        {
            while (true)
            {
                if(node.getNext() == null)
                {
                    node.setNext(nodeToInsert);
                    break;
                }
                node = node.getNext();
            }
        }
        size++;
    }
    private int hash(K key)
    {
        if(key == null)
            return 0;

        int h = key.hashCode();

        return h ^ (h >>> 16);
    }
    private int index(int hash) { return (capacity - 1) & hash; }
    private boolean compare(ChainingNode<K, V> node, K key, int hash) { return (node.getHash() == hash && (node.getKey() == key || key.equals(node.getKey()))); }
}
