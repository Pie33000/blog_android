package com.example.pierrickrugery.blog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GithubFragment extends ListFragment implements AdapterView.OnItemClickListener {
    String titles[] = {"AI in bank", "GITHUB in bio medicine"};
    String contents[] = {"Description item 1 ...", "Description item 2 ..."};
    String dates[] = {"14/04/1994", "23/11/2019"};
    String language[] = {"C", "Python"};
    //int imgs[] = {R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp, R.drawable.ic_date_range_black_24dp};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.github_fragment, null);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GitHubAdapter adapter = new GitHubAdapter(getContext(), titles, contents, dates, language);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "Item" + position, Toast.LENGTH_SHORT).show();
    }

    class GitHubAdapter extends ArrayAdapter<String> {
        Context context;
        String myTitle[];
        String myContents[];
        int[] images;
        String[] myDates;
        String[] myLanguage;

        GitHubAdapter(Context c, String[] titles, String[] contents, String[]dates, String[] language) {
            super(c, R.layout.row2, R.id.title, titles);
            this.context = c;
            this.myTitle = titles;
            this.myContents = contents;
            this.myDates = dates;
            this.myLanguage = language;
            //this.images = imgs;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row2, parent, false);
            //ImageView images = row.findViewById(R.id.logo);
            TextView myTitle = row.findViewById(R.id.title);
            TextView myDate = row.findViewById(R.id.date);
            TextView myContent = row.findViewById(R.id.content);
            TextView myLanguage = row.findViewById(R.id.language);
            //images.setImageResource(imgs[position]);
            myTitle.setText(titles[position]);
            myDate.setText(dates[position]);
            myContent.setText(contents[position]);
            myLanguage.setText(language[position]);
            return super.getView(position, convertView, parent);
        }

    }
}
