package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoSettingsTest {

    ToDoSettings todo = new ToDoSettings();
    Tasks task1;

    @BeforeEach
    void setUp() {
        task1 = new Tasks("Task 1", "description", 14, "new task");
        todo.add(task1);
    }

    @Test
    void printTaskTest() {
        // Перехватываем вывод
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Вызываем метод
        todo.printTasks();

        // Возвращаем стандартный вывод
        System.setOut(originalOut);
        String expectedOutput = "Вот какие задачи есть:\nИмя | Статус | До какого\n"
                + task1.getTaskName() + " | " + task1.getTaskStatus() + " | " + task1.getTimeWorks();
        assertEquals(expectedOutput, outputStream.toString().trim());

    }

    @Test
    void editTaskTest() {

       // assertEquals(, todo.editTask());
    }


}
