package com.example.fred.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mStartButton;
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private TextView mChoiceATextView;
    private TextView mChoiceBTextView;
    private TextView mChoiceCTextView;
    private TextView mChoiceDTextView;
    private int mCurrentIndex = 0;

    private Handler customHandler = new Handler();


    private Question[] mQuestions = new Question[]{
            new Question(R.string.question_text, true),
            new Question(R.string.question_text2, false),
            new Question(R.string.question_text3, true),
            new Question(R.string.question_text4, true),
            new Question(R.string.question_text5, false),
    };

    private Question mCurrentQuestion = mQuestions[mCurrentIndex]; // initial question


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        //Buttons
        mStartButton = (Button) findViewById(R.id.btnStart);
        mFalseButton = (Button) findViewById(R.id.btnFalse);
        mTrueButton = (Button) findViewById(R.id.btnTrue);
        mNextButton = (Button) findViewById(R.id.btnNext);

        mQuestionTextView = (TextView) findViewById(R.id.tvQuestion);

        /*mChoiceATextView = (TextView) findViewById(R.id.btnA);
        mChoiceBTextView = (TextView) findViewById(R.id.btnB);
        mChoiceCTextView = (TextView) findViewById(R.id.btnC);
        mChoiceDTextView = (TextView) findViewById(R.id.btnD);*/

        //Show inital question
        mCurrentIndex =( mCurrentIndex+1)% mQuestions.length;
        setCurrentQuestion(mCurrentIndex);
        updateQuestion();

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.true_or_false);
            }
        });
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // button response
            public void onClick(View v) {
                displayMessage(isCorrectAnswer(mCurrentQuestion, true));
            }
        });


        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayMessage(isCorrectAnswer(mCurrentQuestion, false));
            }
        });

       mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex =( mCurrentIndex+1)% mQuestions.length;
                setCurrentQuestion(mCurrentIndex);
                updateQuestion();
            }
        });



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

   /* mPostScoreButton.setOnClickListener(new View.OnClickListener) {
    @Override
    public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, ScoreActivity.class)
        startActivity(i);
    }
});*/
    /*private long startTime = SystemClock.uptimeMillis();
    customHandler.postDelayed(updateTimerThread,0);
    private Runnable updateTimeThread = new Runnable() {

        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            int secs = (int) (timeInMilliseconds / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds (int) (timeInMilliseconds % 1000);

            mTextTimer.setTimer("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
            customHandler.removeCallbacks(updateTimerThread);
        }
    };*/


}
