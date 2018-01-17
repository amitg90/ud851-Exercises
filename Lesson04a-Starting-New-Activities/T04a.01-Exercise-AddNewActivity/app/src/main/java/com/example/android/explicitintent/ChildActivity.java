package com.example.android.explicitintent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ChildActivity extends AppCompatActivity {

    TextView childTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        childTextView = (TextView) findViewById(R.id.tv_display);

    }
}
