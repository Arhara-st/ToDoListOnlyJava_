package org.example;
import lombok.Data;

import java.util.*;
@Data
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();
    private int timeWork = 0;
    Scanner scanner = new Scanner(System.in);

    // для заполненности списка (не нужен)
    public void add(Task task) {
        tasks.add(task);
    }

    // добавление задачи
    public void add() {
        System.out.println("Добавление новой задачи\nИмя задачи:");
        String name = scanner.nextLine();
        System.out.println("Описание задачи:");
        String description = scanner.nextLine();
        System.out.println("До какого числа нужно выполнить задачу:");
        int timeWork = getTimeWork();

        Task task = new Task(name, description, timeWork, Task.TaskStatus.NEW_TASK);
        tasks.add(task);
        System.out.println("Task add");
    }

    // просмотр всех задач
    public void print() {
        System.out.println("Вот какие задачи есть:");
        System.out.println("Имя | Статус | До какого");
        tasks.stream()
                .map(task -> task.getName() + " | "+ task.getStatus() + " | " + task.getTime())
                .forEach(System.out::println);
    }

    // изменение задачи
    public void edit(){
        boolean flag = false;
        System.out.println("Какую задачу нужно изменить?");
        String editTaskName = scanner.nextLine(); // имя задачи которую редачим
        System.out.println("Новое имя задачи:");
        String newTaskName = scanner.nextLine();
        System.out.println("Описание задачи:");
        String newTaskDescription = scanner.nextLine();
        System.out.println("Статус задачи: done / in_work / new_task");
        Task.TaskStatus status = null;
        while (!flag) {
            try{
                String newStatus = scanner.nextLine().toUpperCase().replace(" ", "_");
                status = Task.TaskStatus.valueOf(newStatus);
                flag = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: введен неверный статус, попробуйте еще раз");
            }
        }

        System.out.println("Срок задачи (до какого числа):");
        int newTimeWork = getTimeWork();

        Task.TaskStatus newStatus = status;
        tasks.stream()
                .filter(task -> task.getName().equals(editTaskName))
                .findFirst()
                .ifPresentOrElse(task -> {
                    task.setName(newTaskName);
                    task.setDescription(newTaskDescription);
                    task.setTime(newTimeWork);
                    task.setStatus(newStatus);
                    System.out.println("Задача успешно изменена");
                }, () -> System.out.println("Задача не найдена и ничего не изменилось"));

    }

    // удаление задачи
    public void delete(){
        System.out.println("Какую задачу надо удалить?");
        String deleteTaskName = scanner.nextLine();
        tasks.removeIf(task -> task.getName().equals(deleteTaskName));
        System.out.println("Успешно удалено");
    }

    // фильтр задач по статусу
    public void filterStatus() {
        System.out.println("""
        Сортировка по статусу задач, по какому статусу нужен фильтр?
        done - готовые задачи
        new_task - новые задачи
        in_work - в работе
        """);
        String filterStatus = scanner.nextLine().toUpperCase().replace(" ", "_");

        try {
            Task.TaskStatus status = Task.TaskStatus.valueOf(filterStatus);

            tasks.stream()
                    .filter(task -> task.getStatus().equals(status))
                    .map(task -> task.getName() + " | " + task.getStatus())
                    .forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: введен неверный статус");
        }
    }

    // сортировка по сроку выполнения
    public void filterTimeWork(){
        System.out.println("Сортировка по времени, до какого числа нужно сделать задачу");
        tasks.stream().sorted(Comparator.comparing(Task::getTime))
                .map(task -> task.getTime() + " | " + task.getName())
                .forEach(System.out::println);
    }

    // добавление срока задачи
    private int getTimeWork(){
        boolean flag = false;
        while (!flag) { //was !flag
            try {
                timeWork = scanner.nextInt();
                if (timeWork >= 1 && timeWork <= 31) {
                    flag = true;
                } else System.out.println("Ошибка: не бывает в месяце столько дней");
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введено не число. Пожалуйста, попробуйте снова.");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return timeWork;
    }

    // конструктор для тестов
    public TaskService(Scanner scanner) {
        this.scanner = scanner;
    }

    // конструктор по умолчанию для класса Main
    public TaskService() {
    }

}
