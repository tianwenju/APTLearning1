package com.delta.aptlearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.delta.annotationmodule.Test;

@Test
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
