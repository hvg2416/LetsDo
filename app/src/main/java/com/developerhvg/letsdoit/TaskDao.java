package com.developerhvg.letsdoit;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao
{
    @Insert
    void insert(TaskEntity taskEntity);

    @Delete
    void delete(TaskEntity taskEntity);

    @Query("SELECT * FROM TASK_TABLE WHERE task_protection = 'UNSECURED'")
    LiveData<List<TaskEntity>> getTasks();

    @Query("SELECT * FROM TASK_TABLE WHERE task_protection = 'SECURED'")
    LiveData<List<TaskEntity>> getPrivateTasks();

    @Update
    void update(TaskEntity taskEntity);
}
