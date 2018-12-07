package org.mab.playground.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mab.playground.R;


/**
 * Created by Mirza Ahmed Baig on 01/12/18.
 * Avantari Technologies
 * mirza@avantari.org
 */
public class SearchFragment extends Fragment {

    ImageView imageView;
    LinearLayout layout;

    private void onPostExecute() {
        final View view_read = getLayoutInflater().inflate(R.layout.read_book_button, null);
        TextView name = view_read.findViewById(R.id.bookName);
        name.setText("kushdfkjgskdf");
        layout.addView(view_read);
    }

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, null);

        imageView = view.findViewById(R.id.search_image);
        layout = view.findViewById(R.id.linear_search);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPostExecute();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
