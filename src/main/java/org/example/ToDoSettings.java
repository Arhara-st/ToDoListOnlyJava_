package org.example;
import lombok.Data;
import java.util.*;

@Data
public class ToDoSettings {

    private final List<Tasks> task = new ArrayList<>();
    private int timeWork = 0;

    // для заполненности списка (не нужен)
    public void add(Tasks task) {
        this.task.add(task);
    }

    // добавление задачи
    public void addTask() {
        boolean flag = false;
        System.out.println("Добавление новой задачи\nИмя задачи:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Описание задачи:");
        String description = scanner.nextLine();
        System.out.println("До какого числа нужно выполнить задачу:");
        while (!flag) {
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

        Tasks tasks = new Tasks(name, description, timeWork, "new task");
        this.task.add(tasks);
        System.out.println("Task add");
    }

    // просмотр всех задач
    public void printTasks() {
        System.out.println("Вот какие задачи есть:");
        System.out.println("Имя | Статус | До какого");
        task.stream()
                .map(tasks -> tasks.getTaskName() + " | "+ tasks.getTaskStatus() + " | " + tasks.getTimeWorks())
                .forEach(System.out::println);
    }

    // изменение задачи
    public void editTask(){
        boolean flag = false;
        System.out.println("Какую задачу нужно изменить?");
        Scanner scanner = new Scanner(System.in);
        String editTaskName = scanner.nextLine(); // имя задачи которую редачим
        System.out.println("Новое имя задачи:");
        String newTaskName = scanner.nextLine();
        System.out.println("Описание задачи:");
        String newTaskDescription = scanner.nextLine();
        System.out.println("Статус задачи: done / in work / new task");
        String newStatus = scanner.nextLine();
        System.out.println("Срок задачи (до какого числа):");
        while (!flag) {
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

        int finalNewTaskTimeWork = timeWork;
        task.stream()
                .filter(task -> task.getTaskName().equals(editTaskName))
                .findFirst()
                .ifPresent(task -> {
                    task.setTaskName(newTaskName);
                    task.setTaskDescription(newTaskDescription);
                    task.setTimeWorks(finalNewTaskTimeWork);
                    task.setTaskStatus(newStatus);
                });
        System.out.println("Задача успешно изменена");
    }

    // удаление задачи
    public void deleteTask(){
        System.out.println("Какую задачу надо удалить?");
        Scanner scanner = new Scanner(System.in);
        String deleteTaskName = scanner.nextLine();
        task.removeIf(task -> task.getTaskName().equals(deleteTaskName));
        System.out.println("Успешно удалено");
    }

    // фильтр задач по статусу
    public void filterStatus(){
        System.out.println("""
        Сортировка по статусу задач, по какому статусу нужен фильтр?
        done - готовые задачи
        new task - новые задачи
        in work - в работе
        """);
        Scanner scanner = new Scanner(System.in);
        String filterStatus = scanner.nextLine().toLowerCase();

        task.stream()
                .filter(task -> task.getTaskStatus().equals(filterStatus))
                .map(tasks -> tasks.getTaskName() + " | " + tasks.getTaskStatus())
                .forEach(System.out::println);

    }

    // сортировка по сроку выполнения
    public void filterTimeWork(){
        System.out.println("Сортировка по времени, до какого числа нужно сделать задачу");
        task.stream().sorted(Comparator.comparing(Tasks::getTimeWorks))
                .map(tasks -> tasks.getTimeWorks() + " | " + tasks.getTaskName())
                .forEach(System.out::println);
    }


//    public List<Tasks> getTasks() {
//        return this.task;
//    }
}
