package com.example.dasas.wody;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;


public class CircularProgressBarSample extends Activity{

    private static final String TAG = CircularProgressBarSample.class.getSimpleName();

    protected boolean mAnimationHasEnded = false;

    private Switch mAutoAnimateSwitch;

    private View view;

    /**
     * The Switch button.
     */
    private Button mColorSwitchButton;

    private HoloCircularProgressBar mHoloCircularProgressBar;

    private Button mOne;

    private ObjectAnimator mProgressBarAnimator;

    private Button mZero;

    private TextView tv;
    private TextView tv2;

    private Chronometer chrono;

    private String[] exercises = {"Run 800m", "20 Pushups", " 10 Jumps", "20 Ring Rows"};
    private int countExercises = 0;
    private boolean backgroundWhite = false;
            /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        if (getIntent() != null) {
            final Bundle extras = getIntent().getExtras();
            if (extras != null) {
                final int theme = extras.getInt("theme");
                if (theme != 0) {
                    setTheme(theme);
                }
            }
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mHoloCircularProgressBar = (HoloCircularProgressBar) findViewById(
                R.id.holoCircularProgressBar);

        view = findViewById(R.id.myView);
        //switchColor();
        Random r = new Random();
        int color = Color.rgb(128, 128, 128);
        mHoloCircularProgressBar.setProgressBackgroundColor(color);
        mHoloCircularProgressBar.setMarkerProgress2(0.40f);
        mHoloCircularProgressBar.setWheelSize(15);
        chrono = (Chronometer) findViewById(R.id.chronometer);
        chrono.start();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



    }

    @Override
    protected void onStart() {
        super.onStart();

        animate(mHoloCircularProgressBar, new AnimatorListener() {

            @Override
            public void onAnimationCancel(final Animator animation) {
                animation.end();
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                if (!mAnimationHasEnded) {
                    animate(mHoloCircularProgressBar, this);
                } else {
                    mAnimationHasEnded = false;
                }
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }

            @Override
            public void onAnimationStart(final Animator animation) {
            }
        });

        tv = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setTextColor(Color.parseColor("#D8D8D8"));


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(300);
                //int number = r.nextInt(4);
                if (countExercises<exercises.length-1){
                    System.out.print(Integer.toString(countExercises));
                    tv.setText(exercises[countExercises]);
                    tv2.setText(exercises[countExercises+1]);
                    float progress = mHoloCircularProgressBar.getProgress();
                    mHoloCircularProgressBar.setMarkerProgress(progress);
                    int color = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
                    mHoloCircularProgressBar.setProgressColor(color);
                    countExercises = countExercises+1;
                }
                else{
                    countExercises=0;
                    tv2.setText(exercises[1]);


                }

            }
        });

        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (backgroundWhite==false){
                    getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                    tv.setTextColor(Color.parseColor("BLACK"));
                    tv2.setTextColor(Color.parseColor("#D8D8D8"));

                    chrono.setTextColor(Color.parseColor("BLACK"));
                    backgroundWhite=true;
                }
                else{
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                    tv.setTextColor(Color.parseColor("WHITE"));
                    chrono.setTextColor(Color.parseColor("WHITE"));
                    backgroundWhite=false;

                }
                return true;
            }
        });

    }

    /**
    /**
     * generates random colors for the ProgressBar
     */
    protected void switchColor() {
        Random r = new Random();
        int randomColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        mHoloCircularProgressBar.setProgressColor(randomColor);

        randomColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        mHoloCircularProgressBar.setProgressBackgroundColor(randomColor);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.circular_progress_bar_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //switch (item.getItemId()) {
            //case R.id.menu_switch_theme:
              //  switchTheme();
                //break;

          //  default:
            //    Log.w(TAG, "couldn't map a click action for " + item);
              //  break;
        //}

        return super.onOptionsItemSelected(item);
    }

    /**
     * Switch theme.
     */
    public void switchTheme() {

        final Intent intent = getIntent();
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final int theme = extras.getInt("theme", -1);
            if (theme == R.style.AppThemeLight) {
                getIntent().removeExtra("theme");
            } else {
                intent.putExtra("theme", R.style.AppThemeLight);
            }
        } else {
            intent.putExtra("theme", R.style.AppThemeLight);
        }
        finish();
        startActivity(intent);
    }

    /**
     * Animate.
     *
     * @param progressBar the progress bar
     * @param listener    the listener
     */
    private void animate(final HoloCircularProgressBar progressBar,
            final AnimatorListener listener) {
        //final float progress = (float) (Math.random() * 2);
        final float progress = (float) 1;
        int duration = 60000;
        animate(progressBar, listener, progress, duration);
    }

    private void animate(final HoloCircularProgressBar progressBar, final AnimatorListener listener,
            final float progress, final int duration) {

        mProgressBarAnimator = ObjectAnimator.ofFloat(progressBar, "progress", progress);
        mProgressBarAnimator.setDuration(duration);

        mProgressBarAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationCancel(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                progressBar.setProgress(progress);
                mProgressBarAnimator.start();
                chrono.stop();
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.start();


            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }

            @Override
            public void onAnimationStart(final Animator animation) {
            }
        });
        if (listener != null) {
            mProgressBarAnimator.addListener(listener);
        }
        mProgressBarAnimator.reverse();
        mProgressBarAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                progressBar.setProgress((Float) animation.getAnimatedValue());
            }
        });
        progressBar.setMarkerProgress(progress);
        mProgressBarAnimator.start();
    }

}
