package tasks.services;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.model.TasksOperations;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

public class TasksServiceRepoIntegrationTest {


    private TasksService tasksService;
    private ArrayTaskList taskList;
    private Task task;
    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        task = mock(Task.class);
        taskList = new ArrayTaskList();
        tasksService = new TasksService(taskList);
        sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
    }

    @Test
    void addTaskValid(){




    }


}
