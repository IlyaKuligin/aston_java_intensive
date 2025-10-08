package org.example.homework1;

/**
 * Реализация собственной HashMap в рамках домашнего задания №1. <br>
 * Реализованы основные методы: get, put, remove. <br>
 *
 *
 * <p><b>Особенности реализации:</b>
 * <ul>
 *   <li>Коллекция не поддерживает null-ключи (выбрасывает IllegalArgumentException)</li>
 *   <li>Реализован только один конструктор с дефолтными параметрами:</li>
 *       - Начальная емкость: 16 ячеек <br>
 *       - Коэффициент загрузки: 0.75
 *   <li>При достижении предела происходит удвоение размера таблицы</li>
 *   <li>Для вычисления индекса используем модуль хэш-кода и остаток деления его
 *   на количество ячеек в массиве</li>
 * </ul>
 *
 * @author Кулигин Илья
 * @version 1.0
 */

public class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private static final int DEFAULT_SIZE = 0;

    private Node<K, V>[] table;
    private int size;
    private double loadFactor;

    private static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public MyHashMap() {
        this.table = new Node[DEFAULT_CAPACITY];
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.size = DEFAULT_SIZE;
    }

    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (size >= table.length * loadFactor) {
            resize();
        }
        int index = getIndex(key, table.length);
        Node<K, V> head = table[index];

        Node<K, V> current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }

        Node<K, V> newNode = new Node<>(key, value, head);
        table[index] = newNode;
        size++;

        return null;
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        int index = getIndex(key, table.length);
        Node<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int index = getIndex(key, table.length);
        Node<K, V> current = table[index];
        Node<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    private int getIndex(K key, int capacity) {
        int hashCode = key.hashCode();
        int positiveHashCode = Math.abs(hashCode);
        return positiveHashCode % capacity;
    }

    private void resize() {
        int newCapacity = table.length * 2;
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];

        for (Node<K, V> head : table) {
            Node<K, V> current = head;
            while (current != null) {
                Node<K, V> next = current.next;
                int newIndex = getIndex(current.key, newCapacity);
                current.next = newTable[newIndex];
                newTable[newIndex] = current;
                current = next;
            }
        }
        table = newTable;
    }
}
