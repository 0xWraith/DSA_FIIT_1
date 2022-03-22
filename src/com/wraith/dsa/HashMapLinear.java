package com.wraith.dsa;

public class HashMapLinear<K, V>
{
    private static class LinearNode<K, V>
    {
        private final K key;
        private V value;
        private int hash;

        public LinearNode(K key, V value, int hash)
        {
            this.key = key;
            this.value = value;
            this.hash = hash;
        }

        public K getKey() { return this.key; }
        public V getValue() { return this.value; }
        public int getHash() { return this.hash; }

        public void setHash(int hash) { this.hash = hash; }
        public void setValue(V value) { this.value = value; }
    }

    public int size;
    private int capacity;
    private float threshold;
    private final float loadFactor;
    private LinearNode<K, V>[] hashTable;

    public HashMapLinear() { this(16, 0.75F); }
    public HashMapLinear(int capacity, float loadFactor)
    {
        this.capacity = capacity;
        this.loadFactor = loadFactor;

        threshold = capacity * loadFactor;
        hashTable = new LinearNode[capacity];
    }
    public void insert(final K key, final V value)
    {
        int hash = hash(key);
        int idx = index(hash);

        LinearNode<K, V> node = hashTable[idx];

        if(node == null)
            hashTable[idx] = new LinearNode<>(key, value, hash);

        else if(compare(node, key, hash))
            node.setValue(value);

        else
        {
            boolean flag = false;

            for(int i = idx + 1; i < capacity; i++)
            {
                node = hashTable[i];

                if(node == null)
                {
                    flag = true;
                    hashTable[i] = new LinearNode<>(key, value, hash);
                    break;
                }
                else if(compare(node, key, hash))
                {
                    flag = true;
                    node.setValue(value);
                    break;
                }
            }

            if(!flag)
            {
                for(int i = 0; i < idx; i++)
                {
                    node = hashTable[i];

                    if(node == null)
                    {
                        flag = true;
                        hashTable[i] = new LinearNode<>(key, value, hash);
                        break;
                    }
                    else if(compare(node, key, hash))
                    {
                        node.setValue(value);
                        break;
                    }
                }
            }
            if(!flag)
                return;
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


        LinearNode<K, V> node = hashTable[idx];

        if(compare(node, key, hash))
            return node.getValue();

        for(int i = idx + 1; i < capacity; i++)
        {
            node = hashTable[i];

            if(node == null)
                continue;

            if(compare(node, key, hash))
                return node.getValue();

        }

        for(int i = 0; i < idx; i++)
        {
            node = hashTable[i];

            if(node == null)
                continue;

            if(compare(node, key, hash))
                return node.getValue();

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

        LinearNode<K, V> node = hashTable[idx];

        if(compare(node, key, hash))
        {
            hashTable[idx] = null;
            return true;
        }
        else
        {
            for(int i = idx + 1; i < capacity; i++)
            {
                node = hashTable[idx];

                if(compare(node, key, hash))
                {
                    hashTable[idx] = null;
                    return true;
                }
            }
            for(int i = 0; i < idx; i++)
            {
                node = hashTable[idx];

                if(compare(node, key, hash))
                {
                    hashTable[idx] = null;
                    return true;
                }
            }
        }
        return false;
    }
    private void resize()
    {
        LinearNode<K, V> node;
        LinearNode<K, V>[] newHashTable = hashTable;

        size = 0;
        capacity *= 2;
        threshold = capacity * loadFactor;

        hashTable = new LinearNode[capacity];

        for (LinearNode<K, V> kvChainingNode : newHashTable)
        {
            if(kvChainingNode == null)
                continue;

            resizeInsert(kvChainingNode);
        }
    }
    public void resizeInsert(LinearNode<K, V> nodeToInsert)
    {
        int hash = hash(nodeToInsert.getKey());
        int idx = index(hash);

        nodeToInsert.setHash(hash);

        LinearNode<K, V> node = hashTable[idx];

        if(node == null)
            hashTable[idx] = nodeToInsert;

        else
        {
            boolean flag = false;

            for(int i = idx + 1; i < capacity; i++)
            {
                node = hashTable[i];

                if(node == null)
                {
                    flag = true;
                    hashTable[i] = nodeToInsert;
                    break;
                }
            }

            if(!flag)
            {
                for(int i = 0; i < idx; i++)
                {
                    node = hashTable[i];

                    if(node == null)
                    {
                        flag = true;
                        hashTable[i] = nodeToInsert;
                        break;
                    }
                }
            }
        }

        size++;
    }
    private int hash(final K key)
    {
        if(key == null)
            return 0;

        int h = key.hashCode();

        return h ^ (h >>> 16);
    }
    private int index(int hash) { return (capacity - 1) & hash; }
    private boolean compare(LinearNode<K, V> node, K key, int hash) { return (node.getHash() == hash && (node.getKey() == key || key.equals(node.getKey()))); }
}
