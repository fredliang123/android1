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

public class MainActivity extends AppCompatActivity {

    MediaPlayer MainSong;

    private Button mTrueButton;
    private Button mFalseButton;
    private TextView mQuestionTextView;
    private TextView score;
    private int mScore;
    private int mCurrentIndex = 0;
    private Chronometer mTimer;

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

    private QuestionTF mCurrentQuestion = mQuestionsTF[mCurrentIndex]; // initial question


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
        mTimer =  findViewById(R.id.tvTimer);
        //sets timer at zero on click
        mTimer.setBase(SystemClock.elapsedRealtime());
        mTimer.start();

        score = (TextView) findViewById(R.id.tvScore);

        //Show inital question
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

    private void next() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionsTF.length;
        setCurrentQuestion(mCurrentIndex);
        updateQuestion();
        if (mCurrentIndex >= 10) {
            MainSong.stop();
            Intent i = new Intent(MainActivity.this.getBaseContext(), MultipleChoice.class);
            i.putExtra("time", (int)  mTimer.getBase());
            i.putExtra("score", mScore);
            startActivityForResult(i,0);
        }
    }

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

    private boolean isCorrectAnswer(QuestionTF question, boolean pressedTrue) {
        return question.isAnswerTrue() == pressedTrue;
    }

    private void setCurrentQuestion(int qIndex) {
        mCurrentQuestion = mQuestionsTF[qIndex];
    }

    private void updateQuestion() {
        mQuestionTextView.setText(mCurrentQuestion.getTextQuestionId());
    }


}
