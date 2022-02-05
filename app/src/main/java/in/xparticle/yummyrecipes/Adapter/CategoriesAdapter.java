package in.xparticle.yummyrecipes.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import in.xparticle.yummyrecipes.Interface.IntentInterface;
import in.xparticle.yummyrecipes.Model.CategoriesModel;
import in.xparticle.yummyrecipes.Model.ListModel;
import in.xparticle.yummyrecipes.R;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
        ArrayList<CategoriesModel> mList;
        Context context;
        IntentInterface intentInterface;


    public CategoriesAdapter(ArrayList<CategoriesModel> mList, Context context, IntentInterface intentInterface) {
        this.mList = mList;
        this.context = context;
        this.intentInterface = intentInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list, parent,false);
        return new MyViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(mList.get(position).getName());

//        IntentInterface.intent(mList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentInterface.intent(mList.get(position).getName());
            }
        });

        Glide
                .with(context)
                .load(mList.get(position).getImage())
//                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.imgCate);

        Log.e("five", "onBindViewHolder: five"+mList );
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,description;
        ImageView imgCate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.tv_cateName);
            imgCate= itemView.findViewById(R.id.iv_catImage);
        }
    }
}
