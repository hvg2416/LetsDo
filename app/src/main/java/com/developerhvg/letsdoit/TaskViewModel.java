package com.developerhvg.letsdoit;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel
{
    private TaskDao taskDao;
    private TaskDatabase taskDatabase;
    private LiveData<List<TaskEntity>> liveData;
    private LiveData<List<TaskEntity>> liveProtectedData;

    public TaskViewModel(@NonNull Application application)
    {
        super(application);
        taskDatabase = TaskDatabase.getTaskDatabaseInstance(application);
        taskDao = taskDatabase.taskDao();
        liveData = taskDao.getTasks();
        liveProtectedData = taskDao.getPrivateTasks();
    }

    public void insert(TaskEntity taskEntity)
    {
        new InsertAsyncTask(taskDao).execute(taskEntity);
    }

    public void delete(TaskEntity taskEntity)
    {
        new DeleteAsyncTask(taskDao).execute(taskEntity);
    }

    public LiveData<List<TaskEntity>> getLiveData()
    {
        return liveData;
    }

    public LiveData<List<TaskEntity>> getLiveProtectedData()
    {
        return liveProtectedData;
    }

    public void update(TaskEntity taskEntity)
    {
        new UpdateAsyncTask(taskDao).execute(taskEntity);
    }

    private class InsertAsyncTask extends AsyncTask<TaskEntity,Void,Void>
    {
        TaskDao taskDao;

        public InsertAsyncTask(TaskDao taskDao)
        {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntity... taskEntities)
        {
            taskDao.insert(taskEntities[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<TaskEntity,Void,Void>
    {
        TaskDao taskDao;

        public DeleteAsyncTask(TaskDao taskDao)
        {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntity... taskEntities)
        {
            taskDao.delete(taskEntities[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<TaskEntity,Void,Void>
    {
        private TaskDao taskDao;

        public UpdateAsyncTask(TaskDao taskDao)
        {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntity... taskEntities)
        {
            taskDao.update(taskEntities[0]);
            return null;
        }
    }
}
