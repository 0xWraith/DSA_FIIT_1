package com.wraith.dsa;

class AVLTree<V extends Comparable<V>>
{
    public static class AVLNode<V>
    {
        private V data;
        private int balance;

        private AVLNode<V> leftChild;
        private AVLNode<V> rightChild;

        public AVLNode(V data, AVLNode<V> leftChild, AVLNode<V> rightChild, int balance)
        {
            this.data = data;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.balance = balance;
        }

        /*getters*/
        public V getNodeData() { return this.data; }
        public int getNodeBalance() { return this.balance; }
        public AVLNode<V> getNodeLeftChild() { return this.leftChild; }
        public AVLNode<V> getNodeRightChild() { return this.rightChild; }
        /*setters*/
        public void setNodeData(V data) { this.data = data; }
        public void setNodeBalance(int balance) { this.balance = balance; }
        public void setNodeLeftChild(AVLNode<V> leftChild) { this.leftChild = leftChild; }
        public void setNodeRightChild(AVLNode<V> rightChild) { this.rightChild = rightChild; }
    }

    AVLNode<V> root;

    { root = null; }

    public AVLNode<V> insert(AVLNode<V> node, V data)
    {
        if (node == null)
            return (new AVLNode<>(data, null, null, 1));

        int result = data.compareTo(node.getNodeData());

        if (result < 0)
            node.setNodeLeftChild(insert(node.getNodeLeftChild(), data));

        else if (result > 0)
            node.setNodeRightChild(insert(node.getNodeRightChild(), data));

        else
            return node;

        AVLNode<V> returnResult;
        node.setNodeBalance(Math.max(getHeight(node.getNodeLeftChild()), getHeight(node.getNodeRightChild())) + 1);

        returnResult = AVLBalanceNodeInsert(node, data);
        return returnResult == null ? node : returnResult;
    }
    public AVLNode<V> search(V data)
    {
        V value;
        int result;
        AVLNode<V> tempNode = root;

        while(tempNode != null)
        {
            value = tempNode.getNodeData();
            result = data.compareTo(value);

            if(result == 0)
                return tempNode;

            else if(result < 0)
                tempNode = tempNode.getNodeLeftChild();

            else
                tempNode = tempNode.getNodeRightChild();
        }
        return null;
    }
    public AVLNode<V> delete(AVLNode<V> node, V data)
    {
        if (node == null)
            return null;

        int result = data.compareTo(node.getNodeData());

        if (result < 0)
            node.setNodeLeftChild(delete(node.getNodeLeftChild(), data));

        else if (result > 0)
            node.setNodeRightChild(delete(node.getNodeRightChild(), data));

        else
        {
            AVLNode<V> temp;

            if (node.getNodeLeftChild() == null || node.getNodeRightChild() == null)
            {
                if (node.getNodeLeftChild() == null)
                    temp = node.getNodeRightChild();
                else
                    temp = node.getNodeLeftChild();

                node = temp;
            }
            else
            {
                temp = findMinimalNode(node.getNodeRightChild());

                node.setNodeData(temp.getNodeData());
                node.setNodeRightChild(delete(node.getNodeRightChild(), temp.getNodeData()));
            }
        }

        if (node == null)
            return null;

        AVLNode<V> returnResult;
        node.setNodeBalance(Math.max(getHeight(node.getNodeLeftChild()), getHeight(node.getNodeRightChild())) + 1);

        returnResult = AVLBalanceNodeDelete(node);
        return returnResult == null ? node : returnResult;
    }
    private AVLNode<V> rightRotate(AVLNode<V> node)
    {
        AVLNode<V> nodeLeftChild = node.getNodeLeftChild();

        node.setNodeLeftChild(nodeLeftChild.getNodeRightChild());
        nodeLeftChild.setNodeRightChild(node);
        node.setNodeBalance(Math.max(getHeight(node.getNodeLeftChild()), getHeight(node.getNodeRightChild())) + 1);
        nodeLeftChild.setNodeBalance(Math.max(getHeight(nodeLeftChild.getNodeLeftChild()), getHeight(nodeLeftChild.getNodeRightChild())) + 1);

        return nodeLeftChild;
    }
    private AVLNode<V> leftRotate(AVLNode<V> node)
    {
        AVLNode<V> nodeRightChild = node.getNodeRightChild();

        node.setNodeRightChild(nodeRightChild.getNodeLeftChild());
        nodeRightChild.setNodeLeftChild(node);
        node.setNodeBalance(Math.max(getHeight(node.getNodeLeftChild()), getHeight(node.getNodeRightChild())) + 1);
        nodeRightChild.setNodeBalance(Math.max(getHeight(nodeRightChild.getNodeLeftChild()), getHeight(nodeRightChild.getNodeRightChild())) + 1);

        return nodeRightChild;
    }
    private AVLNode<V> AVLBalanceNodeInsert(AVLNode<V> node, V data)
    {
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1 && data != node.getNodeLeftChild().getNodeData())
        {
            if (data.compareTo(node.getNodeLeftChild().getNodeData()) > 0)
                node.setNodeLeftChild(leftRotate(node.getNodeLeftChild()));

            return rightRotate(node);
        }
        else if (balanceFactor < -1 && data != node.getNodeRightChild().getNodeData())
        {
            if (data.compareTo(node.getNodeRightChild().getNodeData()) < 0)
                node.setNodeRightChild(rightRotate(node.getNodeRightChild()));

            return leftRotate(node);
        }
        return null;
    }
    private AVLNode<V> AVLBalanceNodeDelete(AVLNode<V> node)
    {
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1)
        {
            if (getBalanceFactor(node.getNodeLeftChild()) < 0)
                node.setNodeLeftChild(leftRotate(node.getNodeLeftChild()));

            return rightRotate(node);
        }
        else if (balanceFactor < -1)
        {
            if (getBalanceFactor(node.getNodeRightChild()) > 0)
                node.setNodeRightChild(rightRotate(node.getNodeRightChild()));

            return leftRotate(node);
        }
        return null;
    }
    private AVLNode<V> findMinimalNode(AVLNode<V> node)
    {
        if(node == null)
            return null;

        AVLNode<V> temp = node;

        while (temp.getNodeLeftChild() != null)
            temp = temp.getNodeLeftChild();

        return temp;
    }
    private int getHeight(AVLNode<V> node) { return (node == null ? 0 : node.getNodeBalance()); }
    private int getBalanceFactor(AVLNode<V> node) { return node == null ? 0 : getHeight(node.getNodeLeftChild()) - getHeight(node.getNodeRightChild()); }
}

