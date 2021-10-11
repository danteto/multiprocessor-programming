package com.dan.ds;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CoarseList<T> implements Set<T> {
    private Node<T> head;
    private Lock lock = new ReentrantLock();

    public CoarseList() {
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);
    }

    @Override
    public boolean add(T item) {
        Node pred, curr;
        int key = item.hashCode();
        lock.lock();

        try {
            pred = head;
            curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }

            if(key == curr.key) {
                return false;
            } else {
                Node node = new Node(item);
                node.next = curr;
                pred.next = node;

                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean remove(T item) {
        Node pred, curr;
        int key = item.hashCode();
        lock.lock();
        try {
            pred = head;
            curr = pred.next;
            while (curr.key < key) {
                pred = curr;
                curr = curr.next;
            }
            if (key == curr.key) {
                pred.next = curr.next;
                return true;
            } else  {
                return  false;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean contains(T item) {
        Node curr;
        int key = item.hashCode();
        lock.lock();
        try {
            curr = head;
            while (curr.next != null && curr.key != key) {
                curr = curr.next;
            }
            if (key == curr.key) {
                return true;
            }
        } finally {
            lock.unlock();
        }

        return false;
    }
}
