package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tasks {

    private String taskName; // имя задачи
    private String taskDescription; // описание задачи
    private int timeWorks; // срок выполнения
    private String taskStatus; // статус задачи

}
