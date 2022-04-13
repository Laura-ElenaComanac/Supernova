package tasks.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.model.TasksOperations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class TaskServiceTest {

    private Task task;
    private Date start,end;
    private String descr;
    private boolean activ;
    private int interval;

    private TasksService tasksService;
    private TasksService tasksServiceMock;
    private ArrayTaskList taskList;
    private ArrayTaskList taskListMock;
    private SimpleDateFormat sdf;
    private TasksOperations tasksOperations;
    private ObservableList<Task> tasks =  FXCollections.observableArrayList();


    @BeforeEach
    void setUp() {
        taskListMock = mock(ArrayTaskList.class);
        taskList = new ArrayTaskList();
        tasksService = new TasksService(taskList);
        tasksServiceMock = mock(TasksService.class,withSettings().useConstructor(taskListMock));
        sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
        tasksOperations = new TasksOperations(tasks);
    }

    @Test
    void addTaskValid() {

        //Arrange
        try {
            descr = "description";
            start =  sdf.parse("[2022-02-30 12:00:00.000]");
            end =  sdf.parse("[2022-02-30 12:00:00.000]");
            activ = true;
            interval = 2;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Act
        Mockito.doNothing().when(tasksServiceMock).addTask(descr,start,end,activ,interval);

        //Assert
        Mockito.when(tasksServiceMock.size()).thenReturn(1);
    }

    @Test
    void addTaskNonValid() {

        //Arrange
        try {
            descr = "";
            start =  sdf.parse("[2022-02-30 12:00:00.000]");
            end =  sdf.parse("[2022-02-30 12:00:00.000]");
            activ = true;
            interval = 2;
        } catch (ParseException e) {
            e.printStackTrace();
        }

       Mockito.doThrow(IllegalArgumentException.class).when(tasksServiceMock).addTask(descr,start,end,activ,interval);
        Mockito.when(tasksServiceMock.size()).thenReturn(0);
    }





    @ParameterizedTest
    @Tag("Valid")
    @ValueSource(strings = { "description1", "descr", "desc", "d" })
    @DisplayName("Add task valid description BVA Min")
    void addTaskValidDescription_BVA_Min(String description) {
        //Arrange
        try {
            descr = description;
            start =  sdf.parse("[2022-02-30 12:00:00.000]");
            end =  sdf.parse("[2022-02-30 12:00:00.000]");
            activ = true;
            interval = 2;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Act
        tasksService.addTask(descr,start,end,activ,interval);

        //Assert
        assertNotEquals("",descr);
    }

    @ParameterizedTest
    @Tag("Valid")
    @ValueSource(strings = { "Description length.", "Description length?", "Description length!" })
    @DisplayName("Add task valid description BVA Max")
    void addTaskValidDescription_BVA_Max(String description) {
        //Arrange
        try {
            descr = description;
            start =  sdf.parse("[2022-02-30 12:00:00.000]");
            end =  sdf.parse("[2022-02-30 12:00:00.000]");
            activ = true;
            interval = 2;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Act
        tasksService.addTask(descr,start,end,activ,interval);

        //Assert
        assertEquals(19,descr.length());
    }


    @Test
    @Tag("Valid")
    @DisplayName("Add task valid description ECP")
    void addTaskValidDescription_ECP() {
        //Arrange
        try {
            descr = "descr";
            start =  sdf.parse("[2022-02-30 12:00:00.000]");
            end =  sdf.parse("[2022-02-30 12:30:00.000]");
            activ = true;
            interval = 2;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Act
        tasksService.addTask(descr,start,end,activ,interval);

        //Assert
        assertTrue(descr.length() >=1 && descr.length() <=20);
    }

    @Test
    @DisplayName("Add task non valid description BVA Min")
    void addTaskNonValidDescription_BVA_Min() {
        //Arrange
        try {
            descr = "";
            start =  sdf.parse("[2022-02-30 12:00:00.000]");
            end =  sdf.parse("[2022-02-30 12:00:00.000]");
            activ = true;
            interval = 2;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> tasksService.addTask(descr,start,end,activ,interval));
    }

    @ParameterizedTest
    @ValueSource(strings = { "This description is t", "This description is e" +
            "This description is l"})
    @DisplayName("Add task non valid description BVA Max")
    void addTaskNonValidDescription_BVA_Max(String description) {
        //Arrange
        try {
            descr = description;
            start =  sdf.parse("[2022-02-30 12:00:00.000]");
            end =  sdf.parse("[2022-02-30 12:00:00.000]");
            activ = true;
            interval = 2;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> tasksService.addTask(descr,start,end,activ,interval));
    }


    @Test
    @DisplayName("Add task non valid description ECP")
    void addTaskNonValidDescription_ECP() {
        //Arrange
        try {
            descr = "This description is too long";
            start =  sdf.parse("[2022-02-30 12:00:00.000]");
            end =  sdf.parse("[2022-02-30 12:00:00.000]");
            activ = true;
            interval = 2;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> tasksService.addTask(descr,start,end,activ,interval));
    }

    @Test
    @Tag("Valid")
    @DisplayName("Add task valid year ECP")
    void addTaskValidYear_ECP() {
        //Arrange
        try {
            descr = "desc";
            start =  sdf.parse("[2022-05-30 12:00:00.000]");
            end =  sdf.parse("[2022-05-30 12:30:00.000]");
            activ = true;
            interval = 2;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Act
        tasksService.addTask(descr,start,end,activ,interval);

        //Assert
        String[] splittedTime = start.toString().split(" ");
        int year = Integer.parseInt(splittedTime[splittedTime.length-1]);
        assertTrue(year >= 2020 && year <=2022);
    }


    @Test
    @DisplayName("Add task non valid year ECP")
    void addTaskNonValidYear_ECP() {
        //Arrange
        try {
            descr = "desc";
            start =  sdf.parse("[2023-05-30 12:00:00.000]");
            end =  sdf.parse("[2022-05-30 12:30:00.000]");
            activ = true;
            interval = 2;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Act and Assert
        assertThrows(IllegalArgumentException.class, () ->
                tasksService.addTask(descr,start,end,activ,interval));
    }

    @Test
    void filterTasksValid() {
        //Arrange
        try {
            ArrayList<Task> tasks1 = new ArrayList<>();
            Task t1 = new Task("d",
                    sdf.parse("[2022-02-20 20:00:00.000]"),
                    sdf.parse("[2022-02-21 21:00:00.000]"),
                    2);
            t1.setActive(true);
            Task t2 = new Task("d",
                    sdf.parse("[2022-02-20 20:00:00.000]"),
                    sdf.parse("[2022-02-20 22:00:00.000]"),
                    2);
            t2.setActive(true);
            tasks1.add(t1);
            tasks1.add(t2);
            tasksOperations.tasks = tasks1;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Act
        Iterable<Task> resultedTasks = new ArrayTaskList();
        try {
            resultedTasks = tasksOperations.incoming(
                    sdf.parse("[2022-02-20 20:00:00.000]"),
                    sdf.parse("[2022-02-22 22:00:00.000]")
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Assert

        assertEquals(2,StreamSupport.stream(resultedTasks.spliterator(), false)
                .count());
    }

    @Test
    void filterTasksNonValid() {
        //Arrange
        ArrayList<Task> tasks1 = new ArrayList<>();
        tasksOperations.tasks = tasks1;

        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> tasksOperations.incoming(
                null,
                sdf.parse("[2022-02-22 22:00:00.000]")
        ));
    }

    @Test
    void filterTasksNonValid1() {
        //Arrange
        ArrayList<Task> tasks1 = new ArrayList<>();
        tasksOperations.tasks = tasks1;

        //Act and Assert
        assertThrows(IllegalArgumentException.class, () -> tasksOperations.incoming(
                sdf.parse("[2022-02-22 22:00:00.000]"),
                null
        ));
    }

    @Test
    void filterTasksValid1() {

        //Arrange
        ArrayList<Task> tasks1 = new ArrayList<>();
        tasksOperations.tasks = tasks1;

        //Act
        Iterable<Task> resultedTasks = new ArrayTaskList();
        try {
            resultedTasks = tasksOperations.incoming(
                    sdf.parse("[2022-02-20 20:00:00.000]"),
                    sdf.parse("[2022-02-22 22:00:00.000]")
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Act and Assert
        //assertIterableEquals();
        assertEquals(0,StreamSupport.stream(resultedTasks.spliterator(), false)
                .count());
    }

    @Test
    void filterTasksValid2() {
        //Arrange
        try {
            ArrayList<Task> tasks1 = new ArrayList<>();
            Task t1 = new Task("d",
                    sdf.parse("[2022-02-20 20:00:00.000]"),
                    sdf.parse("[2022-02-22 22:00:00.000]"),
                    2);
            t1.setActive(true);
            tasks1.add(t1);
            tasksOperations.tasks = tasks1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Act
        Iterable<Task> resultedTasks = new ArrayTaskList();
        try {
            resultedTasks = tasksOperations.incoming(
                    sdf.parse("[2022-02-20 20:00:00.000]"),
                    sdf.parse("[2022-02-22 22:00:00.000]")
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Assert

        assertEquals(1,StreamSupport.stream(resultedTasks.spliterator(), false)
                .count());
    }


}