package com.wraith.dsa;

public class RedBlackTree<V extends Comparable<V>>
{
    public static class RBTNode<V>
    {
        private V data;
        private int color;

        private RBTNode<V> parent;
        private RBTNode<V> leftChild;
        private RBTNode<V> rightChild;

        public RBTNode(V data, RBTNode<V> leftChild, RBTNode<V> rightChild, int color)
        {
            this.parent = null;

            this.data = data;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.color = color;
        }
        /*getters*/
        public V getNodeData() { return this.data; }
        public int getNodeColor() { return this.color; }
        public RBTNode<V> getNodeParent() { return this.parent; }
        public RBTNode<V> getNodeLeftChild() { return this.leftChild; }
        public RBTNode<V> getNodeRightChild() { return this.rightChild; }
        /*setters*/
        public void setNodeData(V data) { this.data = data; }
        public void setNodeColor(int color) { this.color = color; }
        public void setNodeParent(RBTNode<V> parent) { this.parent = parent; }
        public void setNodeLeftChild(RBTNode<V> leftChild) { this.leftChild = leftChild; }
        public void setNodeRightChild(RBTNode<V> rightChild) { this.rightChild = rightChild; }
    }

    private RBTNode<V> root;
    private final RBTNode<V> nil;

    {
        this.root = null;
        nil = new RBTNode<>(null, null, null, 0);
    }

    public void insert(V data)
    {
        RBTNode<V> node = new RBTNode<>(data, nil, nil, 1);

        if(root == null)
        {
            root = node;
            root.setNodeColor(0);

            return;
        }

        RBTNode<V> tempRoot = root;
        RBTNode<V> parentNode;

        while (true)
        {
            parentNode = tempRoot;

            if (data.compareTo(tempRoot.getNodeData()) < 0)
                tempRoot = tempRoot.getNodeLeftChild();
            else if(data.compareTo(tempRoot.getNodeData()) > 0)
                tempRoot = tempRoot.getNodeRightChild();
            else
                return;

            if(tempRoot == nil)
            {
                node.setNodeParent(parentNode);

                if (node.getNodeData().compareTo(parentNode.getNodeData()) < 0)
                    parentNode.setNodeLeftChild(node);
                else
                    parentNode.setNodeRightChild(node);

                break;
            }
        }

        if (node.getNodeParent().getNodeParent() == null)
            return;

        fixInsert(node);
    }
    public RBTNode<V> search(V data)
    {
        if(root == null)
            return null;

        V value;
        int result;
        RBTNode<V> tempNode = root;

        while(tempNode != nil)
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
    public void delete(V data)
    {
        RBTNode<V> node = search(data);

        if(node == null)
            return;

        RBTNode<V> tempNodeX;
        RBTNode<V> tempNodeY = node;

        int color = tempNodeY.getNodeColor();

        if (node.getNodeLeftChild() == nil)
        {
            tempNodeX = node.getNodeRightChild();
            replace(node, node.getNodeRightChild());
        }
        else if (node.getNodeRightChild() == nil)
        {
            tempNodeX = node.getNodeLeftChild();
            replace(node, node.getNodeLeftChild());
        }
        else
        {
            tempNodeY = findMinimalNode(node.getNodeRightChild());
            color = tempNodeY.getNodeColor();
            tempNodeX = tempNodeY.getNodeRightChild();

            if (tempNodeY.getNodeParent() == node)
                tempNodeX.setNodeParent(tempNodeY);

            else
            {
                replace(tempNodeY, tempNodeY.getNodeRightChild());
                tempNodeY.setNodeRightChild(node.getNodeRightChild());
                tempNodeY.getNodeRightChild().setNodeParent(tempNodeY);
            }

            replace(node, tempNodeY);
            tempNodeY.setNodeLeftChild(node.getNodeLeftChild());
            tempNodeY.getNodeLeftChild().setNodeParent(tempNodeY);
            tempNodeY.setNodeColor(node.getNodeColor());
        }

        if (color == 0)
            fixDelete(tempNodeX);
    }
    private void replace(RBTNode<V> from, RBTNode<V> to)
    {
        if (from.getNodeParent() == null)
            root = to;

        else if (from == from.getNodeParent().getNodeLeftChild())
            from.getNodeParent().setNodeLeftChild(to);

        else
            from.getNodeParent().setNodeRightChild(to);

        to.setNodeParent(from.getNodeParent());
    }
    private void fixInsert(RBTNode<V> node)
    {
        RBTNode<V> nodeParent = node.getNodeParent();
        RBTNode<V> nodeGrandParent;

        while (nodeParent.getNodeColor() == 1)
        {
            nodeParent = node.getNodeParent();
            nodeGrandParent = nodeParent.getNodeParent();

            if (nodeParent == nodeGrandParent.getNodeRightChild())
            {
                if (nodeGrandParent.getNodeLeftChild().getNodeColor() == 1)
                {
                    nodeGrandParent.setNodeColor(1);
                    nodeGrandParent.getNodeLeftChild().setNodeColor(0);
                    nodeGrandParent.getNodeRightChild().setNodeColor(0);

                    node = nodeGrandParent;
                }
                else
                {
                    if (node == nodeParent.getNodeLeftChild())
                    {
                        node = nodeParent;
                        rightRotate(node);
                    }
                    nodeParent.setNodeColor(0);
                    nodeGrandParent.setNodeColor(1);
                    leftRotate(nodeGrandParent);
                }
            }
            else
            {
                if (nodeGrandParent.getNodeRightChild().getNodeColor() == 1)
                {
                    nodeGrandParent.setNodeColor(1);
                    nodeGrandParent.getNodeLeftChild().setNodeColor(0);
                    nodeGrandParent.getNodeRightChild().setNodeColor(0);

                    node = nodeGrandParent;
                }
                else
                {
                    if (node == nodeParent.getNodeRightChild())
                    {
                        node = nodeParent;
                        leftRotate(node);
                    }
                    nodeParent.setNodeColor(0);
                    nodeGrandParent.setNodeColor(1);
                    rightRotate(nodeGrandParent);
                }
            }
            if (node == root)
                break;
        }
        root.setNodeColor(0);
    }
    private void fixDelete(RBTNode<V> node)
    {
        RBTNode<V> nodeSib;

        while(node != root && node.getNodeColor() == 0)
        {
            if(node == node.getNodeParent().getNodeLeftChild())
            {
                nodeSib = node.getNodeParent().getNodeRightChild();

                if(nodeSib.getNodeColor() == 1)
                {
                    nodeSib.setNodeColor(0);
                    node.getNodeParent().setNodeColor(1);
                    leftRotate(node.getNodeParent());
                    nodeSib = node.getNodeParent().getNodeRightChild();
                }

                if(nodeSib.getNodeRightChild().getNodeColor() == 0
                        && nodeSib.getNodeLeftChild().getNodeColor() == 0)
                {
                    nodeSib.setNodeColor(1);
                    node = node.getNodeParent();
                }
                else
                {
                    if (nodeSib.getNodeRightChild().getNodeColor() == 0)
                    {
                        nodeSib.getNodeLeftChild().setNodeColor(0);
                        nodeSib.setNodeColor(1);
                        rightRotate(nodeSib);
                        nodeSib = node.getNodeParent().getNodeRightChild();
                    }
                    nodeSib.setNodeColor(node.getNodeParent().getNodeColor());
                    node.getNodeParent().setNodeColor(0);
                    nodeSib.getNodeRightChild().setNodeColor(0);
                    leftRotate(node.getNodeParent());
                    node = root;
                }
            }
            else
            {
                nodeSib = node.getNodeParent().getNodeLeftChild();

                if(nodeSib.getNodeColor() == 1)
                {
                    nodeSib.setNodeColor(0);
                    node.getNodeParent().setNodeColor(1);
                    rightRotate(node.getNodeParent());
                    nodeSib = node.getNodeParent().getNodeLeftChild();
                }

                if(nodeSib.getNodeLeftChild().getNodeColor() == 0
                        && nodeSib.getNodeRightChild().getNodeColor() == 0)
                {
                    nodeSib.setNodeColor(1);
                    node = node.getNodeParent();
                }
                else
                {
                    if (nodeSib.getNodeLeftChild().getNodeColor() == 0)
                    {
                        nodeSib.getNodeRightChild().setNodeColor(0);
                        nodeSib.setNodeColor(1);
                        leftRotate(nodeSib);
                        nodeSib = node.getNodeParent().getNodeLeftChild();
                    }
                    nodeSib.setNodeColor(node.getNodeParent().getNodeColor());
                    nodeSib.getNodeLeftChild().setNodeColor(0);
                    node.getNodeParent().setNodeColor(0);
                    rightRotate(node.getNodeParent());
                    node = root;
                }
            }
            node.setNodeColor(0);
        }
    }
    private void leftRotate(RBTNode<V> node)
    {

        RBTNode<V> nodeParent     = node.getNodeParent();
        RBTNode<V> nodeRightChild = node.getNodeRightChild();

        node.setNodeRightChild(nodeRightChild.getNodeLeftChild());

        if (nodeRightChild.getNodeLeftChild() != nil)
            nodeRightChild.getNodeLeftChild().setNodeParent(node);

        nodeRightChild.setNodeParent(nodeParent);

        if (nodeParent == null)
            root = nodeRightChild;

        else if (node == nodeParent.getNodeLeftChild())
            nodeParent.setNodeLeftChild(nodeRightChild);

        else
            nodeParent.setNodeRightChild(nodeRightChild);

        nodeRightChild.setNodeLeftChild(node);
        node.setNodeParent(nodeRightChild);
    }
    private void rightRotate(RBTNode<V> node)
    {

        RBTNode<V> nodeParent     = node.getNodeParent();
        RBTNode<V> nodeLeftChild = node.getNodeLeftChild();

        node.setNodeLeftChild(nodeLeftChild.getNodeRightChild());

        if(nodeLeftChild.getNodeRightChild() != nil)
            nodeLeftChild.getNodeRightChild().setNodeParent(node);

        nodeLeftChild.setNodeParent(nodeParent);

        if(nodeParent == null)
            root = nodeLeftChild;

        else if(node == nodeParent.getNodeRightChild())
            nodeParent.setNodeRightChild(nodeLeftChild);

        else
            nodeParent.setNodeLeftChild(nodeLeftChild);

        nodeLeftChild.setNodeRightChild(node);
        node.setNodeParent(nodeLeftChild);

    }
    private RBTNode<V> findMinimalNode(RBTNode<V> node)
    {
        if(node == null)
            return null;

        while (node.getNodeLeftChild() != nil)
            node = node.getNodeLeftChild();

        return node;
    }
}
