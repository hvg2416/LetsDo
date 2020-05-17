package com.developerhvg.letsdoit;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TASK_TABLE")
public class TaskEntity
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    int task_id;

    @NonNull
    @ColumnInfo(name = "task_title")
    private String task_title;

    @ColumnInfo(name = "task_desc")
    private String task_desc;

    @ColumnInfo(name = "task_protection")
    private String task_protection;

    public TaskEntity(@NonNull String task_title, String task_desc, String task_protection) {
        this.task_title = task_title;
        this.task_desc = task_desc;
        this.task_protection = task_protection;
    }

    @NonNull
    public String getTask_title() {
        return task_title;
    }

    public String getTask_desc() {
        return task_desc;
    }

    public int getTask_id() {
        return task_id;
    }

    public String getTask_protection() {
        return task_protection;
    }
}
