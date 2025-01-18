package org.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoSettingsTest {

    @Test
    void addTaskTest() {
        // Задаём ввод с консоли (эмулируем ввод пользователя)
        String simulatedInput = "Task 1\nDescription 1\n15\n";
        InputStream originalIn = System.in; // Сохраняем оригинальный поток System.in
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes())); // Устанавливаем наш поток

        try {
            ToDoSettings todo = new ToDoSettings();
            todo.addTask(); // Вызываем метод, который читает из System.in    !!!!addTask was

            // Проверяем, что задача добавлена
            List<Tasks> tasks = todo.getTask();
            assertEquals(1, tasks.size());
            assertEquals("Task 1", tasks.get(0).getTaskName());
            assertEquals("Description 1", tasks.get(0).getTaskDescription());
            assertEquals(15, tasks.get(0).getTimeWorks());
            assertEquals("new task", tasks.get(0).getTaskStatus());
        } finally {
            // Восстанавливаем оригинальный поток System.in
            System.setIn(originalIn);
        }
    }


    @Test
    void addTaskTestInvalid() {
        // Эмулируем ввод: сначала неправильное значение (abc), затем правильное (15)
        String simulatedInput = "Task 1\nDescription 1\nabc\n15\n";
        InputStream originalIn = System.in; // Сохраняем оригинальный поток System.in
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes())); // Устанавливаем наш поток

        try {
            ToDoSettings taskManager = new ToDoSettings();
            taskManager.addTask(); // Вызываем метод, который читает из System.in

            // Проверяем, что задача добавлена
            List<Tasks> tasks = taskManager.getTask();
            assertEquals(1, tasks.size());
            assertEquals("Task 1", tasks.get(0).getTaskName());
            assertEquals("Description 1", tasks.get(0).getTaskDescription());
            assertEquals(15, tasks.get(0).getTimeWorks());
            assertEquals("new task", tasks.get(0).getTaskStatus());
        } finally {
            // Восстанавливаем оригинальный поток System.in
            System.setIn(originalIn);
        }
    }


}
