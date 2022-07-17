package com.example.planner.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.planner.model.DailyTask;
import com.example.planner.model.Motivation;
import com.example.planner.model.Task;

import java.util.List;

@Dao
public interface PlannerDao {

    @Query("SELECT * FROM DailyTask ORDER BY startTime ASC")
    LiveData<List<DailyTask>> getAllDailyTasks();

    @Query("SELECT * FROM task ORDER BY CASE WHEN dueTime='' THEN '9999' ELSE dueTime END DESC, priorityLevel DESC")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM Motivation")
    LiveData<List<Motivation>> getAllMotivations();

    @Insert
    void insert(Task... tasks);

    @Insert
    void insert(DailyTask... dailyTasks);

    @Insert
    void insert(Motivation... motivations);

    @Delete
    void delete(Task task);

    @Delete
    void delete(DailyTask dailyTask);

    @Delete
    void delete(Motivation motivation);

    @Update
    void update(Task task);

    @Update
    void update(DailyTask dailyTask);

    @Update
    void update(Motivation motivation);

    @Query("SELECT * FROM task WHERE id =:id")
    Task getTaskById(int id);

    @Query("SELECT * FROM DailyTask WHERE id =:id")
    DailyTask getDailyTaskViewById(int id);
}