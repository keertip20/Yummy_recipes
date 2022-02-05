package in.xparticle.yummyrecipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import in.xparticle.yummyrecipes.Adapter.CategoriesAdapter;
import in.xparticle.yummyrecipes.Interface.IntentInterface;
import in.xparticle.yummyrecipes.Model.CategoriesModel;
import in.xparticle.yummyrecipes.Model.MySingleton;

public class CategoriesActivity extends AppCompatActivity implements IntentInterface {

    RecyclerView mRecyclerView;
    CategoriesAdapter mCategoriesAdapter;
    ArrayList<CategoriesModel>mList;
    ImageView img;
//    LinearLayout group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        mList=new ArrayList<>();
        mRecyclerView=findViewById(R.id.recycler);
        mCategoriesAdapter=new CategoriesAdapter(mList,this, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mCategoriesAdapter);
        mRecyclerView.setHasFixedSize(true);

        img=findViewById(R.id.iv_catImage);
//        group=findViewById(R.id.group);

        String url="https://www.themealdb.com/api/json/v1/1/categories.php";

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    Log.e("try", "onResponse: try"+response );
                    String info=response.getString("categories");
                    JSONArray ar=new JSONArray(info);
                    for (int i=0;i<ar.length();i++){
                        JSONObject categories= ar.getJSONObject(i);
                        String cateName=categories.getString("strCategory");
                        String cateImg=categories.getString("strCategoryThumb");

                        CategoriesModel categoriesModel=new CategoriesModel();
                        categoriesModel.setName(cateName);
                        categoriesModel.setImage(cateImg);
                        mList.add(categoriesModel);

                        Log.e("hehe", "onResponse: hehe"+mList );
                    }
                    mCategoriesAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "onErrorResponse: error"+ error.getMessage() );
            }

    });
        MySingleton.getInstance(CategoriesActivity.this).addToRequestQueue(jsonObjectRequest);

//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(CategoriesActivity.this,MealNameActivity.class);
//                intent.putExtra(mList);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void intent(String cateName) {
        Log.e("TAG", "intent: name "+cateName );
        Intent intent=new Intent(CategoriesActivity.this,MealNameActivity.class);
        intent.putExtra("categoryName",cateName);
        startActivity(intent);
    }

//    public void intent(String name){
//        Intent intent=new Intent(CategoriesActivity.this,MealNameActivity.class);
//        startActivity(intent);

//    }

}