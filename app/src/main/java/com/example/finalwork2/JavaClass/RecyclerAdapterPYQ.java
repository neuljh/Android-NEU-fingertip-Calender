package com.example.finalwork2.JavaClass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalwork2.R;
import com.example.finalwork2.Utils.ContentUtils;
import com.example.finalwork2.Utils.MyLeanCloudApp;
import com.example.finalwork2.Utils.RoundImageView;

import java.util.ArrayList;

public class RecyclerAdapterPYQ extends RecyclerView.Adapter<RecyclerAdapterPYQ.ViewHolder>
{
    private ArrayList<PYQ> pyqs;
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


    public RecyclerAdapterPYQ(ArrayList<PYQ> pyqs)
    {
        this.pyqs=pyqs;
    }

    @NonNull
    @Override
    public RecyclerAdapterPYQ.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerAdapterPYQ.ViewHolder holder, int position)
    {
        holder.riv_user.setImageResource(pyqs.get(position).getImage_id());
        holder.tv_date.setText(pyqs.get(position).getDate());
        holder.tv_time.setText(pyqs.get(position).getTime());
        holder.tv_name.setText(pyqs.get(position).getName());
        holder.tv_content.setText(pyqs.get(position).getContent());
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
        return pyqs.size();
    }
    public ArrayList<PYQ> getpyqs(){
        return this.pyqs;
    }

//    public PYQ(int image_id, String date, String time, String name, String content) {
//        this.image_id = image_id;
//        this.date = date;
//        this.time = time;
//        this.name = name;
//        this.content = content;
//    }
    public void addItem(int position,String content,String name){
        pyqs.add(position,new PYQ(R.mipmap.neu,nowDateTime.getNowDate(),ContentUtils.get_nowtime(),name,content));
        MyLeanCloudApp.add(R.mipmap.neu,nowDateTime.getNowDate(),ContentUtils.get_nowtime(),name,content);
        notifyItemInserted(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_date,tv_time,tv_content,tv_name;
        RoundImageView riv_user;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.tv_date=itemView.findViewById(R.id.tv_pyq__date);
            this.tv_time=itemView.findViewById(R.id.tv_pyq_time);
            this.tv_content=itemView.findViewById(R.id.tv_pyq__content);
            this.tv_name=itemView.findViewById(R.id.tv_pyq_name);
            this.riv_user=itemView.findViewById(R.id.iv_user);
        }

    }
}

