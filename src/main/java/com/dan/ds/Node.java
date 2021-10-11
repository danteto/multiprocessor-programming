package com.dan.ds;

public class Node<T> {
    T item;
    int key;
    Node<T> next;

    public Node(T item) {
        this.item = item;
    }
}
