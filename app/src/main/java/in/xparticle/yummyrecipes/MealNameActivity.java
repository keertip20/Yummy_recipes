package in.xparticle.yummyrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import in.xparticle.yummyrecipes.Adapter.ListAdapter;
import in.xparticle.yummyrecipes.Interface.ListInterface;
import in.xparticle.yummyrecipes.Model.ListModel;
import in.xparticle.yummyrecipes.Model.MySingleton;

public class MealNameActivity extends AppCompatActivity implements ListInterface {

    RecyclerView listRecyclerView;
    ListAdapter listAdapter;
    ArrayList<ListModel>listModelArrayList;

    String listURL="https://www.themealdb.com/api/json/v1/1/filter.php?c=";


    private static final String TAG = "MealNameActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_name);

        listModelArrayList = new ArrayList<>();
        listRecyclerView = findViewById(R.id.RL_recycler);
        listAdapter = new ListAdapter(listModelArrayList, this, this);
        listRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        listRecyclerView.setAdapter(listAdapter);
        listRecyclerView.setHasFixedSize(true);

        String categoryName = getIntent().getStringExtra("categoryName");

        Log.e(TAG, "onCreate: " + categoryName);


//        String listURL = "www.themealdb.com/api/json/v1/1/filter.php?c=";
        String myURL = listURL + categoryName;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "onResponse: ten" );
                        try {
                            Log.e(TAG, "onResponse: cat"+response);

                            String list=response.getString("meals");
                            JSONArray jsonArray=new JSONArray(list);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject meals=jsonArray.getJSONObject(i);
                                String mealName=meals.getString("strMeal");
                                String mealImg=meals.getString("strMealThumb");
                                String mealId=meals.getString("idMeal");

                                ListModel listModel=new ListModel();
                                listModel.setList_name(mealName);
                                listModel.setList_img(mealImg);
                                listModel.setList_id(mealId);
                                listModelArrayList.add(listModel);
                            }
                                listAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();

                            Log.e(TAG, "onResponse: "+e.getMessage() );
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "onErrorResponse: error"+error.getMessage());
            }
        });

        MySingleton.getInstance(MealNameActivity.this).addToRequestQueue(jsonObjectRequest);

    }

            @Override
            public void listIntent(String codeId) {

                Intent intent=new Intent(MealNameActivity.this, RecipesActivity.class);
                intent.putExtra("listid",codeId);
                startActivity(intent);
            }
}