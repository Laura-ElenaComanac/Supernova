package tasks.services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
class TasksServiceTest {

    private Task task;
    private Date start,end;
    private String descr;
    private boolean activ;
    private int interval;

    private TasksService tasksService;
    private ArrayTaskList taskList;
    private SimpleDateFormat sdf;


    @BeforeEach
    void setUp() {
        taskList = new ArrayTaskList();
        tasksService = new TasksService(taskList);
        sdf = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]");
    }

    @ParameterizedTest
    @Tag("Valid")
    @ValueSource(strings = { "description1", "descr", "desc" })
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

}