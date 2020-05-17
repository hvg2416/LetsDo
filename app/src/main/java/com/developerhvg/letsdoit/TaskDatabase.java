package com.developerhvg.letsdoit;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = TaskEntity.class,version = 2)
public abstract class TaskDatabase extends RoomDatabase
{
    public abstract TaskDao taskDao();
    private static volatile TaskDatabase taskDatabaseInstance;

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE TASK_TABLE ADD COLUMN task_protection TEXT");
        }
    };

    static TaskDatabase getTaskDatabaseInstance(final Context context)
    {
        if(taskDatabaseInstance == null)
        {
            synchronized (TaskDatabase.class)
            {
                if(taskDatabaseInstance == null)
                {
                    taskDatabaseInstance = Room.databaseBuilder(context,TaskDatabase.class,"TASK_DB")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return taskDatabaseInstance;
    }
}
