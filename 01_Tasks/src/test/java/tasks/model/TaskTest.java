package tasks.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        try {
            task=new Task("new task",Task.getDateFormat().parse("2022-02-12 10:10"),
                    Task.getDateFormat().parse("2022-04-12 10:10"),2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testTaskCreationValid() throws ParseException {
        assertEquals(task.getDescription(),"new task");
        System.out.println(task.getFormattedDateStart());
        System.out.println(task.getDateFormat().format(Task.getDateFormat().parse("2022-02-12 10:10")));
        assertTrue(task.getFormattedDateStart().equals(task.getDateFormat().format(Task.getDateFormat().parse("2022-02-12 10:10"))));
        assertEquals(task.getRepeatInterval(),2);
        assertTrue(task.getFormattedDateEnd().equals(task.getDateFormat().format(Task.getDateFormat().parse("2022-04-12 10:10"))));
    }

    @Test
    void testTaskCreationNonvalid() throws ParseException {

        assertThrows(IllegalArgumentException.class, () -> new Task("new task",Task.getDateFormat().parse("2022-02-12 10:10"),
                Task.getDateFormat().parse("2022-04-12 10:10"),-2));

    }

}