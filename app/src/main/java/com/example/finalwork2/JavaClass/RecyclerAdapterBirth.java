package com.example.finalwork2.JavaClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalwork2.R;
import com.example.finalwork2.Utils.RoundImageView;

import java.util.ArrayList;

public class RecyclerAdapterBirth extends RecyclerView.Adapter<RecyclerAdapterBirth.ViewHolder>
{
    private ArrayList<Birth> births;
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


    public RecyclerAdapterBirth(ArrayList<Birth> births)
    {
        this.births=births;
    }

    @NonNull
    @Override
    public RecyclerAdapterBirth.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_birth, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterBirth.ViewHolder holder, int position)
    {
        holder.tv_title.setText(births.get(position).getTitle());
        holder.tv_time.setText(births.get(position).getBirthday());
        holder.riv_img.setImageResource(births.get(position).getImage_id());
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
        return births.size();
    }
    public ArrayList<Birth> getBirths(){
        return this.births;
    }

//    public void addItem(int position,String title,boolean finish,String registerdate){
//        getSchedules().add(position,new Schedule(title,finish,registerdate,nowDateTime.getNowDate(), ContentUtils.DEFAULT_CATEGORY,ContentUtils.DEFAULT_REMIND_TIME,ContentUtils.DEFAULT_PRIORITY,ContentUtils.DEFAULT_START_TIME,ContentUtils.DEFAULT_END_TIME,ContentUtils.DEFAULT_TIPS));
//        notifyItemInserted(position);
//    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_title,tv_time;
        RoundImageView riv_img;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.riv_img=itemView.findViewById(R.id.birth_iv_img);
            this.tv_title=itemView.findViewById(R.id.birth_tv_title);
            this.tv_time=itemView.findViewById(R.id.brith_tv_birthday);
        }

    }
}
