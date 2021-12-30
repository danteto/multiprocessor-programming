package com.dan.ds;

public interface Set<T> {
    boolean add(T value);
    boolean remove(T value);
    boolean contains(T value);
    int size();
}
