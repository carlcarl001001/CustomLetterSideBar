package com.example.carl.customlettersidebar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tvLetter = findViewById(R.id.tvLetter);
        LetterSideBar lsbLetter = findViewById(R.id.lsbLetter);
        lsbLetter.setListener(new LetterSideBar.LetterTouchListener() {
            @Override
            public void touch(CharSequence letter) {
                tvLetter.setVisibility(View.VISIBLE);
                tvLetter.setText(letter);

            }

            @Override
            public void touchUp() {
                tvLetter.setVisibility(View.INVISIBLE);
            }
        });
    }
}
