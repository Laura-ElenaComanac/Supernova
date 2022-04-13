package tasks.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.model.TasksOperations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class TasksService {

    private ArrayTaskList tasks;

    public TasksService(ArrayTaskList tasks){
        this.tasks = tasks;
    }


    public ObservableList<Task> getObservableList(){
        return FXCollections.observableArrayList(tasks.getAll());
    }
    public String getIntervalInHours(Task task){
        int seconds = task.getRepeatInterval();
        int minutes = seconds / DateService.SECONDS_IN_MINUTE;
        int hours = minutes / DateService.MINUTES_IN_HOUR;
        minutes = minutes % DateService.MINUTES_IN_HOUR;
        return formTimeUnit(hours) + ":" + formTimeUnit(minutes);//hh:MM
    }
    public String formTimeUnit(int timeUnit){
        StringBuilder sb = new StringBuilder();
        final int ten = 10;
        if (timeUnit < ten)
            sb.append("0");
        if (timeUnit == 0)
            sb.append("0");
        else {
            sb.append(timeUnit);
        }
        return sb.toString();
    }

    public int parseFromStringToSeconds(String stringTime){//hh:MM
        String[] units = stringTime.split(":");
        int hours = Integer.parseInt(units[0]);
        int minutes = Integer.parseInt(units[1]);
        int result = (hours * DateService.MINUTES_IN_HOUR + minutes) * DateService.SECONDS_IN_MINUTE;
        return result;
    }

    public Iterable<Task> filterTasks(Date start, Date end){
        TasksOperations tasksOps = new TasksOperations(getObservableList());
        Iterable<Task> filtered = tasksOps.incoming(start,end);
        //Iterable<Task> filtered = tasks.incoming(start, end);

        return filtered;
    }

    public void addTask(String description, Date startTime, Date endTime, boolean active,
                        int repeatInterval) {

        if(description.equals("") || description.length()<1 || description.length()>20)
            throw new IllegalArgumentException("Invalid description");
        String[] splittedTime = startTime.toString().split(" ");
        int year = Integer.parseInt(splittedTime[splittedTime.length-1]);
        if(year < 2020 || year > 2022 )
            throw new IllegalArgumentException("Invalid start year");
        Task task = new Task(description, startTime,endTime, repeatInterval);
        task.setActive(active);
        tasks.add(task);
    }

    public int size(){
        return tasks.size();
    }
}
