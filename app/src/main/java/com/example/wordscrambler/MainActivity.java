package com.example.wordscrambler;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button guessNumberButton;
    Button wordScramblerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guessNumberButton = findViewById(R.id.guessNumberButton);
        wordScramblerButton = findViewById(R.id.wordScramblerButton);

        guessNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GuessNumber.class);
                startActivity(intent);
            }
        });

        wordScramblerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WordScrambler.class);
                startActivity(intent);
            }
        });
    }
}
