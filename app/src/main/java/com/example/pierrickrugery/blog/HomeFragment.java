package com.example.pierrickrugery.blog;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class HomeFragment extends ListFragment implements AdapterView.OnItemClickListener {

    String titles[] = {"AI in bank", "AI in bio medicine", "Apple la meilleure marque", "Test", "Test", "Test", "Test"};
    String description[] = {"Description item 1 ...", "Description item 2 ...", "Description item 3 ...", "Description item 3 ...", "Description item 3 ...", "Description item 3 ...", "Description item 3 ..."};
    int imgs[] = {R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp};

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyAdapter adapter = new MyAdapter(getContext(), titles, description, imgs);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "Item" + position, Toast.LENGTH_SHORT).show();
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String myTitle[];
        String myDescription[];
        int[] images;

        MyAdapter(Context c, String[] titles, String[] description, int[] imgs) {
            super(c, R.layout.row, R.id.title, titles);
            this.context = c;
            this.myTitle = titles;
            this.myDescription = description;
            this.images = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.logo);
            TextView myTitle = row.findViewById(R.id.title);
            TextView date = row.findViewById(R.id.date);
            images.setImageResource(imgs[position]);
            myTitle.setText(titles[position]);
            date.setText(description[position]);
            return super.getView(position, convertView, parent);
        }

    }
}
