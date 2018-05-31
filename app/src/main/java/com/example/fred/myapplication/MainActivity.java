package com.example.fred.myapplication;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MediaPlayer MainSong;

    private Button mTrueButton;
    private Button mFalseButton;

    private TextView mQuestionTextView;
    private TextView mTextTimer;

    private int mCurrentIndex = 0;

    private Handler customHandler = new Handler();

    private long startTime;

    private Question[] mQuestions = new Question[]{
            //true or false
            new Question(R.string.question_text, true),
            new Question(R.string.question_text2, false),
            new Question(R.string.question_text3, true),
            new Question(R.string.question_text4, true),
            new Question(R.string.question_text5, false),

            //multiple choice
            new Question(R.string.question_text6, false),
            new Question(R.string.question_text7, false),
            new Question(R.string.question_text8, false),
    };

    private Question mCurrentQuestion = mQuestions[mCurrentIndex]; // initial question
    private Runnable updateTimeThread = new Runnable() {

        public void run() {
            long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            int secs = (int) (timeInMilliseconds / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (timeInMilliseconds % 1000);

            mTextTimer.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
            customHandler.removeCallbacks(updateTimeThread);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.true_or_false);
        MainSong = MediaPlayer.create(MainActivity.this,R.raw.game_theme3);
        MainSong.start();

        //Buttons
        mFalseButton = (Button) findViewById(R.id.btnFalse);
        mTrueButton = (Button) findViewById(R.id.btnTrue);



        customHandler.postDelayed(updateTimeThread,0);


        mQuestionTextView = (TextView) findViewById(R.id.tvQuestion);
        mTextTimer = (TextView) findViewById(R.id.tvTimer);


        //Show inital question
        mCurrentIndex =( mCurrentIndex+1)% mQuestions.length;
        setCurrentQuestion(mCurrentIndex);
        updateQuestion();

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // button response
            public void onClick(View v) {
                next();
                displayMessage(isCorrectAnswer(mCurrentQuestion, true));
            }
        });


        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
                displayMessage(isCorrectAnswer(mCurrentQuestion, false));
            }
        });


    }
    private  void next(){
        mCurrentIndex =( mCurrentIndex+1)% mQuestions.length;
        setCurrentQuestion(mCurrentIndex);
        updateQuestion();
        if (mCurrentIndex==5){
            setContentView(R.layout.multiple_choice);
        }
    }

    private void displayMessage(boolean isCorrect) {
        if (isCorrect) {
            Toast.makeText(MainActivity.this,
                    R.string.correct_toast,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,
                    R.string.incorrect_toast,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isCorrectAnswer(Question question, boolean pressedTrue) {
        return question.isAnswerTrue() == pressedTrue;
    }

    private void setCurrentQuestion(int qIndex) {
        mCurrentQuestion = mQuestions[qIndex];
    }

    private void updateQuestion() {
        mQuestionTextView.setText(mCurrentQuestion.getTextQuestionId());
    }




}
