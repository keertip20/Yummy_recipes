package in.xparticle.yummyrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MealNameActivity extends AppCompatActivity {

    private static final String TAG = "MealNameActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_name);

        String categoryName = getIntent().getStringExtra("categoryName");

        Log.e(TAG, "onCreate: "+categoryName );
    }
}