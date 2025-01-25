package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    TaskService taskManager;
    Task task1;
    Task task2;
    Task task3;

    @BeforeEach
    void setUp() {
        taskManager = new TaskService(new Scanner(System.in));
        task1 = new Task("Задача 1", "Описание 1", 10, Task.TaskStatus.NEW_TASK);
        task2 = new Task("Задача 2", "Описание 2", 15, Task.TaskStatus.IN_WORK);
        task3 = new Task("Задача 3", "Описание 3", 20, Task.TaskStatus.DONE);
        taskManager.getTasks().addAll(Arrays.asList(task1, task2, task3));
    }

    @Test
    void printTest() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        taskManager.print();

        System.setOut(originalOut);
        String expectedOutput = "Вот какие задачи есть:\nИмя | Статус | До какого\n"
                + task1.getName() + " | " + task1.getStatus() + " | " + task1.getTime() + "\n"
                + task2.getName() + " | " + task2.getStatus() + " | " + task2.getTime() + "\n"
                + task3.getName() + " | " + task3.getStatus() + " | " + task3.getTime();
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void editTest() {
        Scanner scannerMock = Mockito.mock(Scanner.class);
        when(scannerMock.nextLine())
                .thenReturn("Старая задача") // Имя задачи, которую редактируем
                .thenReturn("Новая задача") // Новое имя задачи
                .thenReturn("Новое описание задачи") // Новое описание задачи
                .thenReturn("in_work") // Новый статус задачи
                .thenReturn(""); // Очистка буфера после nextInt()

        when(scannerMock.nextInt()).thenReturn(15); // Новый срок выполнения задачи

        TaskService taskManager = new TaskService(scannerMock);
        Task taskToEdit = new Task("Старая задача", "Старое описание", 10, Task.TaskStatus.NEW_TASK);
        taskManager.getTasks().add(taskToEdit);

        taskManager.edit();

        Task editedTask = taskManager.getTasks().get(0);
        assertEquals("Новая задача", editedTask.getName());
        assertEquals("Новое описание задачи", editedTask.getDescription());
        assertEquals(15, editedTask.getTime());
        assertEquals(Task.TaskStatus.IN_WORK, editedTask.getStatus());
        System.out.println(taskToEdit);
        System.out.println(editedTask);
    }

    @Test
    void addTest() {
        String input = "Моя задача\nОписание задачи\n10\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        TaskService taskManager = new TaskService(new Scanner(System.in));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        taskManager.add();

        System.setIn(System.in);
        System.setOut(originalOut);

        List<Task> tasks = taskManager.getTasks();
        assertEquals(1, tasks.size());

        Task addedTask = tasks.get(0);
        assertEquals("Моя задача", addedTask.getName());
        assertEquals("Описание задачи", addedTask.getDescription());
        assertEquals(10, addedTask.getTime());
        assertEquals(Task.TaskStatus.NEW_TASK, addedTask.getStatus());
        System.out.println(tasks);
    }

    @Test
    void filterStatusTest() {
        String input = "in_work\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        taskManager = new TaskService(new Scanner(System.in));
        task1 = new Task("Задача 1", "Описание 1", 10, Task.TaskStatus.NEW_TASK);
        task2 = new Task("Задача 2", "Описание 2", 15, Task.TaskStatus.IN_WORK);
        task3 = new Task("Задача 3", "Описание 3", 20, Task.TaskStatus.DONE);
        taskManager.getTasks().addAll(Arrays.asList(task1, task2, task3));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        taskManager.filterStatus();

        System.setIn(System.in);
        System.setOut(originalOut);
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Сортировка по статусу задач, по какому статусу нужен фильтр?"));
        assertTrue(consoleOutput.contains("done - готовые задачи"));
        assertTrue(consoleOutput.contains("new_task - новые задачи"));
        assertTrue(consoleOutput.contains("in_work - в работе"));
        assertTrue(consoleOutput.contains("Задача 2 | IN_WORK")); // Проверяем, что задача с статусом IN_WORK выведена
    }

    @Test
    void filterTimeWorkTest() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        taskManager.filterTimeWork();

        System.setOut(originalOut);
        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Сортировка по времени, до какого числа нужно сделать задачу"));
        assertTrue(consoleOutput.indexOf("10 | Задача 1") < consoleOutput.indexOf("15 | Задача 2"));
        assertTrue(consoleOutput.indexOf("10 | Задача 1") < consoleOutput.indexOf("20 | Задача 3"));
    }

    @Test
    void testDelete() {
        String input = "Задача 2\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        taskManager = new TaskService(new Scanner(System.in));
        task1 = new Task("Задача 1", "Описание 1", 10, Task.TaskStatus.NEW_TASK);
        task2 = new Task("Задача 2", "Описание 2", 15, Task.TaskStatus.IN_WORK);
        task3 = new Task("Задача 3", "Описание 3", 20, Task.TaskStatus.DONE);
        taskManager.getTasks().addAll(Arrays.asList(task1, task2, task3));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        taskManager.delete();

        System.setIn(System.in);
        System.setOut(originalOut);
        String consoleOutput = outputStream.toString();

        List<Task> tasks = taskManager.getTasks();
        assertEquals(2, tasks.size());
        assertFalse(tasks.contains(task2));
        assertTrue(consoleOutput.contains("Какую задачу надо удалить?"));
        assertTrue(consoleOutput.contains("Успешно удалено"));
    }

}








