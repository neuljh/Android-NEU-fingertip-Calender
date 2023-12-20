package com.example.finalwork2.JavaClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalwork2.R;

import java.util.ArrayList;
import java.util.Random;

public class RecyclerAdapterTimeline extends RecyclerView.Adapter<RecyclerAdapterTimeline.ViewHolder>
{
    private ArrayList<Schedule> schedules;
    private OnItemClickListener monItemClickListener;
    private OnItemLongClickListener monItemLongClickListener;
    private Random random=new Random();
//    private NowDateTime nowDateTime=new NowDateTime();

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
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


    public RecyclerAdapterTimeline(ArrayList<Schedule> schedules)
    {
        this.schedules=schedules;
    }

    @NonNull
    @Override
    public RecyclerAdapterTimeline.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline_schedule, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterTimeline.ViewHolder holder, int position)
    {

        holder.tv_date.setText(schedules.get(position).getRegisterdate());
        holder.tv_title.setText(schedules.get(position).getTitle());
        //holder.tv_dot.setBackgroundColor();
//        int choice=random.nextInt(3);
//        switch (choice){
//            case 0:
//                holder.tv_dot.setBackgroundColor(R.drawable.timeline);
//                break;
//            case 1:
//                holder.tv_dot.setBackgroundColor(R.drawable.timeline1);
//                break;
//            case 2:
//                holder.tv_dot.setBackgroundColor(R.drawable.timeline2);
//                break;
//            case 3:
//                holder.tv_dot.setBackgroundColor(R.drawable.timelline_dot_normal);
//                break;
//        }

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

    //    public void addItem(int position,String title,boolean finish,String registerdate){
//        getSchedules().add(position,new Schedule(title,finish,registerdate,nowDateTime.getNowDate(), ContentUtils.DEFAULT_CATEGORY,ContentUtils.DEFAULT_REMIND_TIME,ContentUtils.DEFAULT_PRIORITY,ContentUtils.DEFAULT_START_TIME,ContentUtils.DEFAULT_END_TIME,ContentUtils.DEFAULT_TIPS));
//        notifyItemInserted(position);
//    }
    //Schedule(String title, boolean finish, String registerdate, String scheduledate, String category, String remindtime, String priority, String starttime, String endtime, String tips)
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_title,tv_date,tv_dot;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.tv_date=itemView.findViewById(R.id.tv_timeline_date);
            this.tv_title=itemView.findViewById(R.id.tv_timeline_title);
            //this.tv_dot=itemView.findViewById(R.id.tvDot);
        }

    }
}
