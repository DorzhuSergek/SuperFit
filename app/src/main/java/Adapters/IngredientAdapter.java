package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.superfit.R;

import java.util.ArrayList;

public class IngredientAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> data;

    public IngredientAdapter(Context context, ArrayList<String>data) {
        this.context=context;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredients_item,null);
        TextView name = view.findViewById(R.id.ingredients);
        name.setText(data.get(position));
        return view;
    }
}
