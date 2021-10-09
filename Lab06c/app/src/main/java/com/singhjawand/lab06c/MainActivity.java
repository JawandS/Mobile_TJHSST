package com.singhjawand.lab06c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    ViewPager2 mViewPager2;
    RecyclerView.Adapter mMyFragmentStateAdapter;
    int NUM_ITEMS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instance of ViewPager2
        mViewPager2 = findViewById(R.id.container);
        //adapter for ViewPager2
        mMyFragmentStateAdapter = new MyFragmentStateAdapter(this);
        //set adapter
        mViewPager2.setAdapter(mMyFragmentStateAdapter);
    }

    private class MyFragmentStateAdapter extends FragmentStateAdapter {
        public MyFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return MainFragment.newInstance(mViewPager2, position);
        }

        @Override
        public int getItemCount() {
            return NUM_ITEMS;
        }
    }

}