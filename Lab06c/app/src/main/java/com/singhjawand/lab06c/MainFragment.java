package com.singhjawand.lab06c;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.w3c.dom.Text;

import java.util.Random;

public class MainFragment extends Fragment {
    ViewPager2 mViewPager2;
    int position;

    public static Fragment newInstance(ViewPager2 mViewPager2, int position) {
        MainFragment fragment = new MainFragment();
        fragment.mViewPager2 = mViewPager2;
        fragment.position = position;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //add to tab layout
        TabLayout tabLayout = getActivity().findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, mViewPager2,
                (tab, position) -> tab.setText("TAB " + (position))
        ).attach();
        // access the button
        Button mButton = view.findViewById(R.id.pressme);
        mButton.setText("Press " + position);
        // access the text and replace with quote
        TypedArray quotes = getResources().obtainTypedArray(R.array.quotes);
        String quote = quotes.getString((new Random().nextInt(quotes.length())));
        System.out.println("YOOOO " + quote);

        TextView quote_holder = (TextView) view.findViewById(R.id.quote_holder);
        quote_holder.setText(quote);

    }

}
