package tasks.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class TasksServiceREIntegrationTest {

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

        try {
            task = new Task("new task",Task.getDateFormat().parse("2022-02-12 10:10"),
                    Task.getDateFormat().parse("2022-04-12 10:10"),2);
            task.setActive(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

        try {
            task = new Task("new task",Task.getDateFormat().parse("2022-02-12 10:10"),
                    Task.getDateFormat().parse("2022-04-12 10:10"),2);
            task.setActive(true);

        } catch (ParseException e) {
            e.printStackTrace();
        }

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
