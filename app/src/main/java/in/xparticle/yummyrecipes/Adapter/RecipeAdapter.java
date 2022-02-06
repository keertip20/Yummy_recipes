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

import in.xparticle.yummyrecipes.Model.RecipeModel;
import in.xparticle.yummyrecipes.R;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{
    ArrayList<RecipeModel>recipeModelArrayList;
    Context context;

    public RecipeAdapter(ArrayList<RecipeModel> recipeModelArrayList, Context context) {
        this.recipeModelArrayList = recipeModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item,parent,false);
        return new RecipeViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.name.setText(recipeModelArrayList.get(position).getRecipe_name());
        holder.instruction.setText(recipeModelArrayList.get(position).getRecipe_instruction());
        holder.videoURL.setText(recipeModelArrayList.get(position).getRecipe_video());

        Glide
                .with(context)
                .load(recipeModelArrayList.get(position).getRecipe_img())
//                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        return recipeModelArrayList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{

        TextView name,instruction,videoURL;
        ImageView img;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(itemView).findViewById(R.id.rtv_name);
            instruction=(itemView).findViewById(R.id.rtv_instructionsList);
            videoURL=(itemView).findViewById(R.id.rtv_videoList);
            img=(itemView).findViewById(R.id.riv_recipe);
        }
    }
}
