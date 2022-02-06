package in.xparticle.yummyrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import in.xparticle.yummyrecipes.Adapter.RecipeAdapter;
import in.xparticle.yummyrecipes.Model.MySingleton;
import in.xparticle.yummyrecipes.Model.RecipeModel;

public class RecipesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;
    ArrayList<RecipeModel>recipeModelArrayList;

    private static final String TAG = "RecipesActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        String listid=getIntent().getStringExtra("listid");
        Log.e(TAG, "onCreate: "+listid );

        recipeModelArrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.recipe_recycler);
        recipeAdapter=new RecipeAdapter(recipeModelArrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setHasFixedSize(true);

        String url="https://www.themealdb.com/api/json/v1/1/lookup.php?i=";
        String myURL=url+listid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            Log.e(TAG, "onResponse: try"+response );
                            String rec=response.getString("meals");
                            JSONArray jsonArray=new JSONArray(rec);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject meal=jsonArray.getJSONObject(i);
                                String mealName=meal.getString("strMeal");
                                String mealImg=meal.getString("strMealThumb");
                                String mealIns=meal.getString("strInstructions");
                                String mealUrl=meal.getString("strYoutube");

                                Log.e(TAG, "onResponse: name"+mealName);

                                RecipeModel recipeModel=new RecipeModel();
                                recipeModel.setRecipe_name(mealName);
                                recipeModel.setRecipe_img(mealImg);
                                recipeModel.setRecipe_instruction(mealIns);
                                recipeModel.setRecipe_video(mealUrl);

                                recipeModelArrayList.add(recipeModel);
                                Log.e(TAG, "onResponse:RR "+recipeModel );
                            }
                                recipeAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "onResponse: catch"+e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: error"+error.getMessage() );

            }
        });
        MySingleton.getInstance(RecipesActivity.this).addToRequestQueue(jsonObjectRequest);


    }
}