package in.xparticle.yummyrecipes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import in.xparticle.yummyrecipes.Interface.ListInterface;
import in.xparticle.yummyrecipes.Model.ListModel;
import in.xparticle.yummyrecipes.R;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder>{
    ArrayList<ListModel>listModelArrayList;
    Context context;
    ListInterface listInterface;

    public ListAdapter(ArrayList<ListModel> listModelArrayList, Context context, ListInterface listInterface) {
        this.listModelArrayList = listModelArrayList;
        this.context = context;
        this.listInterface = listInterface;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_list, parent,false);
        return new ListAdapter.ListViewHolder(rowItem);

    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.listName.setText(listModelArrayList.get(position).getList_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listInterface.listIntent(listModelArrayList.get(position).getList_id());
            }
        });

        Glide
                .with(context)
                .load(listModelArrayList.get(position).getList_img())
//                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.listImg);

    }

    @Override
    public int getItemCount() {
        return listModelArrayList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{

        TextView listName;
        ImageView listImg;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            listName=(itemView).findViewById(R.id.tv_list);
            listImg=(itemView).findViewById(R.id.iv_list);


        }
    }
}
