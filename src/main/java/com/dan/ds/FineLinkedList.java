package com.dan.ds;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FineLinkedList<T> implements Set<T> {
    private final AtomicInteger size;
    private final Node<T> head;

    public FineLinkedList() {
        this.size = new AtomicInteger(0);
        this.head = new Node<>(Integer.MIN_VALUE, null);
        this.head.next = new Node<>(Integer.MAX_VALUE, null);
    }

    private class Node<V> {
        private int key;
        private V value;
        private Node<V> next;

        private Lock lock;

        public Node(int key, V value) {
            this.key = key;
            this.value = value;
            this.lock = new ReentrantLock();
        }

        public Node(V value) {
            this(value.hashCode(), value);
        }

        public void lock() {
            lock.lock();
        }

        public void unlock() {
            lock.unlock();
        }
    }

    @Override
    public boolean add(T value) {
        Node<T> node = new Node<>(value);
        Node<T> prevNode = find(node.key); // find node after which to insert
        Node<T> currNode = prevNode.next;
        boolean added = addNode(prevNode, node); // add node, only if key is unique
        if (added)
            size.incrementAndGet();

        unlockPair(prevNode, currNode);

        return added;
    }

    private boolean addNode(Node<T> prevNode, Node<T> node) {
        Node<T> currNode = prevNode.next;
        if (currNode.key == node.key) // check if already exists
            return false;

        node.next = currNode; // insert new node inbetween
        prevNode.next = node; //

        return true;
    }

    private Node<T> find(int key) {
        Node<T> currNode = head;
        lockPair(currNode); // lock first node pair.
        while (currNode.next.key < key)
            currNode = nextNode(currNode); // traverse in hand-over-hand fashion.

        return currNode;
    }

    private Node<T> nextNode(Node<T> node) {
        node.unlock();
        node = node.next;
        node.next.lock();

        return node;
    }

    private void lockPair(Node<T> node) {
        node.lock();
        node.next.lock();
    }

    private void unlockPair(Node<T> prevNode, Node<T> currNode) {
        prevNode.unlock();
        currNode.unlock();
    }

    public boolean isEmpty() {
        if (head.next == null && head.value == null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean remove(T value) {
        int key = value.hashCode();
        Node<T> prevNode = find(key);
        Node<T> currNode = prevNode.next;
        boolean removded = removeNode(prevNode, key);
        if (removded)
            size.decrementAndGet();

        unlockPair(prevNode, currNode);

        return removded;
    }

    private boolean removeNode(Node<T> prevNode, int key) {
        Node<T> currNode = prevNode.next;
        if (currNode.key != key) // check if does not exist
            return false;

        prevNode.next = currNode.next; // Detaching the node

        return true;
    }

    public void print() {
        Node<T> curr = head;
        while (curr != null) {
            System.out.print(curr.value + ", ");
        }
    }

    @Override
    public boolean contains(T value) {
        int key = value.hashCode();
        Node<T> prevNode = find(key);
        Node<T> currNode = prevNode.next;
        boolean contains = currNode.key == key;
        unlockPair(prevNode, currNode);

        return contains;
    }

    @Override
    public int size() {
        return size.get();
    }

}
