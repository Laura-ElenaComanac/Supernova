package tasks.services;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.model.TasksOperations;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TasksServiceRepoIntegrationTest {


    private TasksService tasksService;
    private ArrayTaskList tasksRepo;
    private Task task;
    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {

        tasksRepo = new ArrayTaskList();
        tasksService = new TasksService(tasksRepo);
        sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
    }

    @Test
    void addTaskValid(){

        task = mock(Task.class);
        tasksRepo.add(task);
        assertEquals(1,tasksRepo.size());
        assertEquals(1,tasksService.size());

        try {
            tasksService.addTask("description",sdf.parse("[2022-02-30 12:00:00.000]"),
                    sdf.parse("[2022-02-30 12:00:00.000]"),true,2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(2, tasksRepo.size());
        assertEquals(2,tasksService.size());
    }

    @Test
    void addTaskNonValid(){

        task = mock(Task.class);
        tasksRepo.add(task);
        assertEquals(1,tasksRepo.size());
        assertEquals(1,tasksService.size());

        assertThrows(IllegalArgumentException.class,
                    () -> tasksService.addTask("",sdf.parse("[2022-02-30 12:00:00.000]"),
                    sdf.parse("[2022-02-30 12:00:00.000]"),true,2));
        assertEquals(1, tasksRepo.size());
        assertEquals(1,tasksService.size());
    }


}
