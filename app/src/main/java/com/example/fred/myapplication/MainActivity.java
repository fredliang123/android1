package com.example.fred.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The true or false section of the game.
 * Players are given questions, they either
 * answer true or false and gain a point when they are correct.
 *
 * @author Fred Liang
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    MediaPlayer MainSong;

    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private TextView score;
    private int mScore;
    private int mCurrentIndex = 0;
    //chronometer used as the timer
    private Chronometer mTimer;

    /**
     * Creates the object for the true or false questions. Lists the questions in an array and their correct answer
     */
    private QuestionTF[] mQuestionsTF = new QuestionTF[]{
            //true or false
            new QuestionTF(R.string.question_text, true),
            new QuestionTF(R.string.question_text2, false),
            new QuestionTF(R.string.question_text3, false),
            new QuestionTF(R.string.question_text4, true),
            new QuestionTF(R.string.question_text5, false),
            new QuestionTF(R.string.question_text6, false),
            new QuestionTF(R.string.question_text7, true),
            new QuestionTF(R.string.question_text8, false),
            new QuestionTF(R.string.question_text9, false),
            new QuestionTF(R.string.question_text10, true),
            new QuestionTF(R.string.blank, false),
    };
    // The current question
    private QuestionTF mCurrentQuestion = mQuestionsTF[mCurrentIndex];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.true_or_false);
        MainSong = MediaPlayer.create(MainActivity.this, R.raw.game_theme);
        MainSong.start();

        //Buttons
        mFalseButton = (Button) findViewById(R.id.btnFalse);
        mTrueButton = (Button) findViewById(R.id.btnTrue);

        mQuestionTextView = (TextView) findViewById(R.id.tvQuestion);

        mTimer = findViewById(R.id.tvTimer);
        //sets timer at zero on click
        mTimer.setBase(SystemClock.elapsedRealtime());
        mTimer.start();

        score = (TextView) findViewById(R.id.tvScore);

        //Show initial question
        setCurrentQuestion(mCurrentIndex);
        score.setText("Score: " + mScore);
        updateQuestion();

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // button response
            public void onClick(View v) {
                displayMessage(isCorrectAnswer(mCurrentQuestion, true));
                next();
            }
        });


        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMessage(isCorrectAnswer(mCurrentQuestion, false));
                next();
            }
        });

    }

    /**
     * Updates the index of the true or false question array. The class is changed, the game moves to multiple choice questions when the index is 10 or more.
     */
    private void next() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionsTF.length;
        setCurrentQuestion(mCurrentIndex);
        updateQuestion();
        if (mCurrentIndex >= 10) {
            MainSong.stop();
            Intent i = new Intent(MainActivity.this.getBaseContext(), MultipleChoice.class);
            //sends the player's time and score to the MultipleChoice class
            // gets the value of the chronometer and turn it into an int
            i.putExtra("time", (int) mTimer.getBase());
            i.putExtra("score", mScore);
            startActivityForResult(i, 0);
        }
    }

    /**
     * Creates a toast telling the player if their answer is correct or not. Increases their score by one for a correct answer
     *
     * @param isCorrect - the player's answer that either matched the actual answer or not
     */
    private void displayMessage(boolean isCorrect) {
        if (isCorrect) {
            mScore++;
            score.setText("Score: " + mScore);
            Toast.makeText(MainActivity.this,
                    R.string.correct_toast,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,
                    R.string.incorrect_toast,
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Checks if the correct answer to the question and the player's answer are the same
     *
     * @param question    -the answer to the question
     * @param pressedTrue -the player's answer
     * @return - true or false depending if the answers match or not
     */
    private boolean isCorrectAnswer(QuestionTF question, boolean pressedTrue) {
        return question.isAnswerTrue() == pressedTrue;
    }

    /**
     * @param qIndex
     */
    private void setCurrentQuestion(int qIndex) {
        mCurrentQuestion = mQuestionsTF[qIndex];
    }

    /**
     * updates the true or false question on the layout
     */
    private void updateQuestion() {
        mQuestionTextView.setText(mCurrentQuestion.getTextQuestionId());
    }


}
