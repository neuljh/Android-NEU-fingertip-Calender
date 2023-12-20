package com.example.finalwork2.JavaClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalwork2.R;
import com.example.finalwork2.Utils.ContentUtils;

import java.util.ArrayList;

public class RecyclerAdapterSchedule extends RecyclerView.Adapter<RecyclerAdapterSchedule.ViewHolder>
{
    private ArrayList<Schedule> schedules;
    private OnItemClickListener monItemClickListener;
    private OnItemLongClickListener monItemLongClickListener;
    private NowDateTime nowDateTime=new NowDateTime();

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.monItemClickListener= onItemClickListener;

    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.monItemLongClickListener= onItemLongClickListener;
    }

    public void updatedata(ArrayList<Schedule> schedules){
        this.schedules=schedules;
        notifyDataSetChanged();
    }


    public RecyclerAdapterSchedule(ArrayList<Schedule> schedules)
    {
        this.schedules=schedules;
    }

    @NonNull
    @Override
    public RecyclerAdapterSchedule.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterSchedule.ViewHolder holder, int position)
    {
        holder.tv_title.setText(schedules.get(position).getTitle());
        holder.tv_time.setText(schedules.get(position).getRegisterdate());
        holder.cb_sign.setChecked(schedules.get(position).isFinish());
        if(monItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=holder.getLayoutPosition();
                    monItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
        if(monItemLongClickListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    monItemLongClickListener.onItemLongClick(holder.itemView,position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }


    }

    @Override
    public int getItemCount()
    {
        return schedules.size();
    }
    public ArrayList<Schedule> getSchedules(){
        return this.schedules;
    }

    public void addItem(int position,String title,boolean finish,String registerdate){
        getSchedules().add(position,new Schedule(title,finish,registerdate,nowDateTime.getNowDate(), ContentUtils.DEFAULT_CATEGORY,ContentUtils.DEFAULT_REMIND_TIME,ContentUtils.DEFAULT_PRIORITY,ContentUtils.DEFAULT_START_TIME,ContentUtils.DEFAULT_END_TIME,ContentUtils.DEFAULT_TIPS));
        notifyItemInserted(position);
    }
    //Schedule(String title, boolean finish, String registerdate, String scheduledate, String category, String remindtime, String priority, String starttime, String endtime, String tips)
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_title,tv_time;
        CheckBox cb_sign;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.tv_title=itemView.findViewById(R.id.schedulelist_tv_title);
            this.tv_time=itemView.findViewById(R.id.schedulelist_tv_time);
            this.cb_sign=itemView.findViewById(R.id.schedule_cb);
        }

    }
}