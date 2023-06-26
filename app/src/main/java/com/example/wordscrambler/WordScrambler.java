package com.example.wordscrambler;
import com.example.wordscrambler.WordGenerator;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class WordScrambler extends AppCompatActivity {

    enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    private Difficulty difficulty;
    private String currentWord;
    private String scrambledWord;
    private TextView scrambledWordTextView;
    private EditText guessInput;
    private ProgressBar progressBar;
    private TextView statusTextView;
    private RadioGroup difficultyGroup;
    private Button startGameButton;
    private View gameLayout;
    private CountDownTimer timer;
    private long timeLeft;
    private TextView scoreTextView;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_scrambler);

        // Retrieve Views
        scrambledWordTextView = findViewById(R.id.scrambledWordTextView);
        guessInput = findViewById(R.id.guessEditText);
        progressBar = findViewById(R.id.timerProgressBar);
        difficultyGroup = findViewById(R.id.difficultyGroup);
        startGameButton = findViewById(R.id.startGameButton);
        gameLayout = findViewById(R.id.gameLayout);
        scoreTextView = findViewById(R.id.scoreTextView);
        statusTextView = findViewById(R.id.statusTextView);

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
                findViewById(R.id.buttonGroup).setVisibility(View.VISIBLE);




                // Reset score
                score = 0;
                scoreTextView.setText("Score: " + score);

                // Reset Game
                resetGame();
            }
        });

        // Attach listener to the guess button
        Button guessButton = findViewById(R.id.submitGuessButton);
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userGuess = guessInput.getText().toString().trim();
                if (userGuess.equalsIgnoreCase(currentWord)) {
                    statusTextView.setText("Correct!");

                    // Update score based on remaining time
                    score += timeLeft / 1000;  // Add remaining seconds to the score
                    scoreTextView.setText("Score: " + score);

                    timer.cancel();  // stop the timer
                    resetGame();
                } else {
                    statusTextView.setText("Incorrect. Try again.");
                }
            }
        });

        Button changeDifficultyButton = findViewById(R.id.changeDifficultyButton);
        changeDifficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });


        Button goToGamesButton = findViewById(R.id.goToGamesButton);
        goToGamesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordScrambler.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void resetGame() {
        // Pick a new word based on difficulty level
//        switch (difficulty) {
//            case EASY:
//                currentWord = "easy";  // Replace with your own logic
//                break;
//            case MEDIUM:
//                currentWord = "medium";  // Replace with your own logic
//                break;
//            case HARD:
//                currentWord = "hard";  // Replace with your own logic
//                break;
//        }
        // Pick a new word based on difficulty level
        currentWord = WordGenerator.generateWord(WordGenerator.Difficulty.valueOf(difficulty.toString()));


        // Scramble the word
        scrambleWord(currentWord);

        // Reset guess input
        guessInput.setText("");

        // Start the timer
        startTimer();

        // Update Views
        scrambledWordTextView.setText(scrambledWord);
        statusTextView.setText("Guess the word!");
    }

    private void scrambleWord(String word) {
        List<String> letters = Arrays.asList(word.split(""));
        Collections.shuffle(letters);
        scrambledWord = "";
        for (String letter : letters) {
            scrambledWord += letter;
        }
    }


    private void startTimer() {
        int timerLength;

        switch (difficulty) {
            case EASY:
                timerLength = 60000;  // 60 seconds for easy difficulty
                break;
            case MEDIUM:
                timerLength = 45000;  // 45 seconds for medium difficulty
                break;
            case HARD:
                timerLength = 30000;  // 30 seconds for hard difficulty
                break;
            default:
                throw new IllegalStateException("Unexpected difficulty level: " + difficulty);
        }

        progressBar.setMax(timerLength / 1000);  // Set max value of ProgressBar

        timer = new CountDownTimer(timerLength, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                progressBar.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                statusTextView.setText("Time's up! Try again by changing difficluty.");
                findViewById(R.id.submitGuessButton).setVisibility(View.GONE);
                //resetGame();
            }
        }.start();
    }
}
