package com.a50647;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.a50647.widget.RoundTextView;

public class MainActivity extends AppCompatActivity {
    boolean selected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RoundTextView roundTextView = findViewById(R.id.tv);
        roundTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roundTextView.setRoundTextViewSelect(selected);
                selected = !selected;
            }
        });
    }
}
