package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Task {

    private String name; // имя задачи
    private String description; // описание задачи
    private int time; // срок выполнения
    private TaskStatus status; // статус задачи

    protected enum TaskStatus {
        NEW_TASK,
        IN_WORK,
        DONE,
    }
}


