package com.example.superfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Adapters.IngredientAdapter;
import Other.Recipe;

public class RecipeScreen extends AppCompatActivity {
    private Recipe recipe;

    private HttpURLConnection connection = null;
    private String data;
    private String prodName;
    private String diet;

    private ListView lvl;
    private IngredientAdapter adapter;
    private ArrayList<String> ingredients;

    private TextView recipeName;
    private TextView calories;
    private TextView proteins;
    private TextView fats;
    private TextView carbs;

    private ImageView buttonBack;
    private ImageView recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_screen);

        lvl=findViewById(R.id.lvl);
        ingredients = new ArrayList<>();
        adapter = new IngredientAdapter(RecipeScreen.this, ingredients);

        recipeImage = findViewById(R.id.recipeImage);
        recipeName = findViewById(R.id.nameFood);
        calories = findViewById(R.id.kcal);
        proteins = findViewById(R.id.protein);
        fats = findViewById(R.id.fat);
        carbs = findViewById(R.id.carbs);

        Bundle arg = getIntent().getExtras();
        if(arg!=null){
            recipe= (Recipe) arg.getSerializable("recipe");
            Picasso.with(getApplicationContext()).load(recipe.getImageFoot()).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground).into(recipeImage);
            recipeName.setText(recipe.getLabel());
            calories.setText(recipe.getCalories() + " kcal");
            proteins.setText(recipe.getProtein());
            fats.setText(recipe.getFats());
            carbs.setText(recipe.getCarbs());
            prodName=arg.getString("prodName");
            diet = arg.getString("diet");
        }

        buttonBack = findViewById(R.id.back_button);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RecipeScreen.this, RecipesList.class));
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    data = GetDownloadData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Parsing(data);
                            lvl.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }


    private String GetDownloadData() {
        StringBuilder result = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL("https://api.edamam.com/search?q=" + prodName + "&app_id=4da5a427&app_key=6dd6f99730da1737e964379d886e607d&diet=" + diet).openConnection();
            // установка метода запроса
            connection.setRequestMethod("GET");
            // поключение
            connection.connect();
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                // считывание данных
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line; // строка для чтения
                // цикл чтения данных
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                    result.append("\n");
                }
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        // отключение
        if (connection != null) {
            connection.disconnect();
        }
        return result.toString();
    }
    private void Parsing(String jsonData) {
        try {
            // парсинг json
            Object object = new JSONParser().parse(jsonData);
            org.json.simple.JSONObject jsonObject = (JSONObject) object;
            // получение списка рецептов
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) jsonObject.get("hits");

            for (Object obj : jsonArray) {
                org.json.simple.JSONObject item = (org.json.simple.JSONObject) obj;
                org.json.simple.JSONObject recipeObject = (org.json.simple.JSONObject) item.get("recipe");
                String label = recipeObject.get("label").toString();
                if (label.equals(recipe.getLabel())) {
                    org.json.simple.JSONArray ingredientsObject = (org.json.simple.JSONArray) recipeObject.get("ingredientLines");
                    for (Object ing : ingredientsObject) {
                        ingredients.add(ing.toString());
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}