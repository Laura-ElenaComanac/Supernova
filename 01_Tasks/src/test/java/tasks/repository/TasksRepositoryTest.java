package tasks.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TasksRepositoryTest {

    private ArrayTaskList taskRepo;
    private Task task;

    @BeforeEach
    void setUp() {
        taskRepo = mock(ArrayTaskList.class);
        try {
            task = new Task("new task", Task.getDateFormat().parse("2022-02-12 10:10"),
                    Task.getDateFormat().parse("2022-04-12 10:10"), 2);
            task.setActive(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addTaskValid() {
        Mockito.when(taskRepo.add(task)).thenReturn(task);
        Mockito.when(taskRepo.size()).thenReturn(1);
    }

    @Test
    void addTaskNonValid() {
        task = null;
        Mockito.doThrow(NullPointerException.class).when(taskRepo).add(task);
        Mockito.when(taskRepo.size()).thenReturn(0);
    }


}