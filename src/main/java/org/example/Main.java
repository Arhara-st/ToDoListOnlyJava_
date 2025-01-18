package org.example;
import java.util.Scanner;

/*
Main - основной файл с запуском всей программы
ToDoSettings - файл со всей реализацией тудушки
Tasks - описание задач = поля
Status - in work/ new task / done

/Что-то можно вынести из методов (какие-то переменные) в сам класс и под приват их поставить
/JUNIT's сделать
 */

public class Main {
    public static void main(String[] args) {
        boolean flag = true;
        ToDoSettings todo = new ToDoSettings();

        Tasks tasks = new Tasks("Сходить в магазин","Купить хлеба", 14, "new task");
        Tasks tasks2 = new Tasks("Сходить погулять","пройти много км пешком", 14, "new task");
        Tasks tasks3 = new Tasks("в доту катнуть","+40 ммр", 15, "new task");

        todo.add(tasks);
        todo.add(tasks2);
        todo.add(tasks3);
        System.out.println("""
                add - добавление элементов\
                list - вывести список задач
                edit - редактировать задачу
                delete - удалить задачу
                filter - отфильтровать задачу по статусу
                sort - отсортировать задачи
                exit - выход из системы
                """);
        while (flag) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine().toLowerCase().trim();

            switch (command) {
                case "add":
                    todo.addTask();
                    break;

                case "exit":
                    flag = false;
                    break;

                case "list":
                    todo.printTasks();
                    break;

                case "edit":
                    todo.editTask();
                    break;

                case "delete":
                    todo.deleteTask();
                    break;

                case "filter":
                    todo.filterStatus();
                    break;

                case "sort":
                    todo.filterTimeWork();
                    break;
                default:
                    System.out.println("Попробуйте еще раз");
            }
        }


    }
}
