package com.developerhvg.letsdoit;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>
{
    private List<TaskEntity> taskEntityList;
    private Context context;

    public CustomAdapter(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.each_item_layout,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, final int position)
    {
        holder.task_title_text_view.setText(taskEntityList.get(position).getTask_title());
        holder.task_desc_text_view.setText(taskEntityList.get(position).getTask_desc());
        final String task_protection = taskEntityList.get(position).getTask_protection();
        holder.delete_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemRemoved(position);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(task_protection.equals("UNSECURED"))
                        {
                            MainActivity.delete(taskEntityList.get(position));
                        }
                        else
                        {
                            PrivateTaskActivity.delete(taskEntityList.get(position));
                        }
                    }
                },400);
            }
        });
        holder.edit_image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task_tile,task_description;
                int task_id;
                task_id = taskEntityList.get(position).getTask_id();
                task_tile = taskEntityList.get(position).getTask_title();
                task_description = taskEntityList.get(position).getTask_desc();
                Intent intent = new Intent(context,EditTaskActivity.class);
                intent.putExtra("task_id",task_id);
                intent.putExtra("task_title",task_tile);
                intent.putExtra("task_desc",task_description);
                intent.putExtra("task_protection",task_protection);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        if(taskEntityList != null)
        {
            return taskEntityList.size();
        }
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder
    {
        TextView task_title_text_view,task_desc_text_view;
        ImageView delete_image_view,edit_image_view;
        public CustomViewHolder(@NonNull View itemView)
        {
            super(itemView);
            delete_image_view = itemView.findViewById(R.id.delete_image_view);
            edit_image_view = itemView.findViewById(R.id.edit_image_view);
            task_title_text_view = itemView.findViewById(R.id.task_title_text_view);
            task_desc_text_view = itemView.findViewById(R.id.task_desc_text_view);
        }
    }

    public void setData(List<TaskEntity> taskEntityList)
    {
        this.taskEntityList = taskEntityList;
        notifyDataSetChanged();
    }
}
