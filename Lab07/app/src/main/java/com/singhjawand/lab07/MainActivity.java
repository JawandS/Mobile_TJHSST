package com.singhjawand.lab07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void addFrag(View view) {
        LinearLayout layout = (LinearLayout)findViewById(R.id.parent);
        FragmentTransaction txn = getSupportFragmentManager().beginTransaction();
        Fragment[] fragments = {new FooFrag(), new FooFrag(), new FooFrag(), new FooFrag()};
        int i = 1; // This seems really fragile though
        for (Fragment f : fragments) {
            FrameLayout frame = new FrameLayout(this);
            frame.setId(i);
            layout.addView(frame);
            txn.add(i, f);
            i++;
        }
        txn.commit();
//        ft = getSupportFragmentManager().beginTransaction();
//        // Replace the contents of the container with the new fragment
//        ft.add(R.id.framework, new FooFrag());
//        // or ft.add(R.id.your_placeholder, new FooFragment());
//        // Complete the changes added above
//        ft.commit();
    }
}