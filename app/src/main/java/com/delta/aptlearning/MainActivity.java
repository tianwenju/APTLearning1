package com.delta.aptlearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.delta.annotationmodule.BindView;
import com.delta.annotationmodule.Test;
import com.delta.api.Butterknife;

@Test
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv )
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Butterknife.bind(this);
        textView.setText("我被初始化了");
    }
}
