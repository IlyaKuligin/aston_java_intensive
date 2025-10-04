package org.example.homework1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class MyHashMapTest {
    private MyHashMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new MyHashMap<>();
    }

    @Test
    void testPutAndGet() { // Проверка добавления и получения
        map.put("apple", 10);
        map.put("banana", 20);
        assertEquals(10, map.get("apple"));
        assertEquals(20, map.get("banana"));
    }

    @Test
    void testGetNonExistentKey() { // Получение несуществующего ключа
        assertNull(map.get("nonexistent"));
    }

    @Test
    void testUpdateValue() { // Проверка обновлений значений
        map.put("apple", 10);
        map.put("apple", 25);
        assertEquals(25, map.get("apple"));
    }

    @Test
    void testRemove() { // Проверка метода удаления
        map.put("apple", 10);
        map.put("banana", 20);
        map.remove("apple");
        assertNull(map.get("apple"));
        assertEquals(20, map.get("banana"));
    }

    @Test
    void testRemoveNonExistent() {
        // Проверяем удаление несуществующего ключа (не должно падать)
        assertDoesNotThrow(() -> map.remove("nonexistent"));
    }

    @Test
    void testCollisions() { // Тестирование обработки коллизий
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        assertEquals(1, map.get("a"));
        assertEquals(2, map.get("b"));
        assertEquals(3, map.get("c"));
        map.remove("b");
        assertNull(map.get("b"));
        assertEquals(1, map.get("a"));
        assertEquals(3, map.get("c"));
    }

    @Test
    void testMultipleTypes() { // Тест на разные типы данных
        MyHashMap<Integer, String> intMap = new MyHashMap<>();
        intMap.put(1, "one");
        intMap.put(2, "two");
        assertEquals("one", intMap.get(1));
        assertEquals("two", intMap.get(2));
    }
}