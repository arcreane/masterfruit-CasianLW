package com.example.wordscrambler;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GuessNumber extends AppCompatActivity {

    enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    private Difficulty difficulty;
    private View gameLayout;
    private EditText guessInput;
    private TextView statusTextView;
    private ProgressBar attemptProgressBar;
    private ListView attemptListView;
    private int targetNumber;
    private int attemptCount;
    private ArrayAdapter<Integer> attemptListAdapter;
    private Button tryAgainButton;
    private Button goToGamesButton;
    private Button submitGuessButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_number);

        final RadioGroup difficultyGroup = findViewById(R.id.difficultyGroup);
        Button startGameButton = findViewById(R.id.startGameButton);
        gameLayout = findViewById(R.id.gameLayout);

        guessInput = findViewById(R.id.guessInput);
        statusTextView = findViewById(R.id.statusTextView);
        attemptProgressBar = findViewById(R.id.attemptProgressBar);
        attemptListView = findViewById(R.id.attemptListView);

        // The game layout should be invisible at the start.
        gameLayout.setVisibility(View.GONE);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = difficultyGroup.getCheckedRadioButtonId();

                if (selectedId == R.id.easyRadioButton) {
                    difficulty = Difficulty.EASY;
                } else if (selectedId == R.id.mediumRadioButton) {
                    difficulty = Difficulty.MEDIUM;
                } else {
                    difficulty = Difficulty.HARD;
                }

                // Hide the difficulty selection and start button.
                difficultyGroup.setVisibility(View.GONE);
                startGameButton.setVisibility(View.GONE);

                // Show the game layout.
                gameLayout.setVisibility(View.VISIBLE);

                // Initialize the game with the chosen difficulty.
                resetGame();
            }
        });

        // Button submitGuessButton = findViewById(R.id.submitGuessButton);
        submitGuessButton = findViewById(R.id.submitGuessButton);
        submitGuessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitGuess();
            }
        });

        tryAgainButton = findViewById(R.id.tryAgainButton);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                gameLayout.setVisibility(View.VISIBLE);
                tryAgainButton.setVisibility(View.GONE);
                goToGamesButton.setVisibility(View.GONE);
            }
        });

        goToGamesButton = findViewById(R.id.goToGamesButton);
        goToGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuessNumber.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



    private void resetGame() {

        switch (difficulty) {
            case EASY:
                targetNumber = new Random().nextInt(10) + 1;
                break;
            case MEDIUM:
                targetNumber = new Random().nextInt(50) + 1;
                break;
            case HARD:
                targetNumber = new Random().nextInt(100) + 1;
                break;
        }
        attemptCount = 0;
        attemptProgressBar.setProgress(attemptCount);
        attemptListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        attemptListView.setAdapter(attemptListAdapter);
        statusTextView.setText("Guess a number");
        submitGuessButton.setVisibility(View.VISIBLE);
    }

    private void submitGuess() {
        String guessString = guessInput.getText().toString();

        // Ignore empty guesses.
        if (guessString.isEmpty()) {
            return;
        }

        int guess = Integer.parseInt(guessString);
        attemptCount++;
        attemptProgressBar.setProgress(attemptCount);
        attemptListAdapter.add(guess);

        if (guess == targetNumber) {
            statusTextView.setText("Congratulations! You guessed the number.");
            tryAgainButton.setVisibility(View.VISIBLE);
            goToGamesButton.setVisibility(View.VISIBLE);
            submitGuessButton.setVisibility(View.GONE);

        } else if (guess < targetNumber) {
            statusTextView.setText("Too low. Try again.");
        } else {
            statusTextView.setText("Too high. Try again.");
        }

        if (attemptCount >= 10) {
            statusTextView.setText("Game over! You didn't guess the number.");

            tryAgainButton.setVisibility(View.VISIBLE);
            goToGamesButton.setVisibility(View.VISIBLE);
            submitGuessButton.setVisibility(View.GONE);
        }
    }
}
