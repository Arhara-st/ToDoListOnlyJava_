package org.example;
import java.util.Scanner;

/*
Main - основной файл с запуском всей программы
ToDoSettings - файл со всей реализацией тудушки
Tasks - описание задач = поля
Status - in work/ new task / done

    !JUNIT's сделать
 */

public class Main {
    static private final TaskService todo = new TaskService();

    public static void main(String[] args) {
        init(args);
        process(args);
    }

    // запуск консоли
    public static void process(String[] args) {
        boolean flag = true;
        String helloMessage = """
                add - добавление элементов
                list - вывести список задач
                edit - редактировать задачу
                delete - удалить задачу
                filter - отфильтровать задачу по статусу
                sort - отсортировать задачи
                exit - выход из системы
                """;
        System.out.println(helloMessage);
        while (flag) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine().toLowerCase().trim();

            switch (command) {
                case "add":
                    todo.add();
                    break;

                case "exit":
                    flag = false;
                    break;

                case "list":
                    todo.print();
                    break;

                case "edit":
                    todo.edit();
                    break;

                case "delete":
                    todo.delete();
                    break;

                case "filter":
                    todo.filterStatus();
                    break;

                case "sort":
                    todo.filterTimeWork();
                    break;
                default:
                    System.out.println("Попробуйте еще раз:\n" + helloMessage);
            }
        }


    }

    // инициализация задач
    public static void init(String[] args) {
        Task task = new Task("Сходить в магазин","Купить хлеба", 14, Task.TaskStatus.NEW_TASK);
        Task task2 = new Task("Сходить погулять","пройти много км пешком", 14, Task.TaskStatus.NEW_TASK);
        Task task3 = new Task("в доту катнуть","+40 ммр", 15, Task.TaskStatus.NEW_TASK);

        todo.add(task);
        todo.add(task2);
        todo.add(task3);
    }
}
