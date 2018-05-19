package com.rporopat.relativlayoutjava;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;
import java.util.ArrayList;
import java.util.Collections;

public class Questions extends AppCompatActivity {
    //Logcat TAG - Filter
    public static final String TAG = "MyActivity";

    DonutProgress donutProgress;
    int variable = 0;
    TextView ques, help;
    ImageView picture;
    Button OptA, OptB, OptC, OptD;
    ImageButton play_button;
    String get;

    //Objects of different classes
    tanker Tanker;
    cruiser Cruiser;
    cargo Cargo;
    dredger Dredger;
    warship Warship;

    public int visibility = 0, c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, i, j = 0, k = 0, l = 0;
    public int end = 0;
    String global = null, Ques, Opta, Optb, Optc, Optd;
    ArrayList<Integer> list = new ArrayList<Integer>();
    Toast toast;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Receiving the intent send by the Navigation activity
        Intent intent = getIntent();
        //Converting that intent message to string using the getStringExtra() method
        get = intent.getStringExtra(Navigation_Activity.Message);
        toast = new Toast(this);
        //Attribute of the circular progress bar
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        donutProgress.setMax(100);
        donutProgress.setFinishedStrokeColor(Color.parseColor("#82D5F8"));
        donutProgress.setTextColor(Color.parseColor("#1674A2"));
        donutProgress.setKeepScreenOn(true);
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        //To play background sound
        if (sp.getInt("Sound", 0) == 0) {
            mediaPlayer = MediaPlayer.create(this, R.raw.abc);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
        //Now the linking of all the database files with the Question activity
        /*TANKER*/
        Tanker= new tanker(this);
        Tanker.createDatabase();
        Tanker.openDatabase();
        Tanker.getWritableDatabase();

        /*CRUISER*/
        Cruiser = new cruiser(this);
        Cruiser.createDatabase();
        Cruiser.openDatabase();
        Cruiser.getWritableDatabase();

        /*CARGO CARRIER*/
        Cargo = new cargo(this);
        Cargo.createDatabase();
        Cargo.openDatabase();
        Cargo.getWritableDatabase();

        /*DREDGER*/
        Dredger = new dredger(this);
        Dredger.createDatabase();
        Dredger.openDatabase();
        Dredger.getWritableDatabase();

        /*WARSHIP*/
        Warship = new warship(this);
        Warship.createDatabase();
        Warship.openDatabase();
        Warship.getWritableDatabase();

        //Till here we are linking the database file
        OptA = (Button) findViewById(R.id.OptionA);
        OptB = (Button) findViewById(R.id.OptionB);
        OptC = (Button) findViewById(R.id.OptionC);
        OptD = (Button) findViewById(R.id.OptionD);
        ques = (TextView) findViewById(R.id.Questions);

        //Picture on top of the question
        picture = (ImageView) findViewById(R.id.image);
        picture.setImageResource(R.drawable.wave);

        //Play button to start the game
        play_button = (ImageButton) findViewById(R.id.play_button);
        //Use for display .gif animation
        Glide.with(this)
                .load(R.raw.ship_play)
                .asGif()
                .crossFade()
                .into(play_button);
    }
    //When this method is executed then there will be new question came and also same method for play button
    public void onClick(View v) {
        final SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        k++;
        if (visibility == 0) {
            //Showing the buttons which were previously invisible
            OptA.setVisibility(View.VISIBLE);
            OptB.setVisibility(View.VISIBLE);
            OptC.setVisibility(View.VISIBLE);
            OptD.setVisibility(View.VISIBLE);
            picture.setVisibility(View.VISIBLE);
            play_button.setVisibility(View.GONE);
            donutProgress.setVisibility(View.VISIBLE);
            visibility = 1;
        }
        if (global != null) {
            if (global.equals("A")) {
                if (v.getId() == R.id.OptionA) {
                    //Here we use the SNACKBAR because if we use the toast then they will be stacked an user cannot identify which questions answer is it showing
                    Snackbar.make(v, "Correct\tAnswer:  ☺", Snackbar.LENGTH_SHORT).show();
                    l++;
                } else {
                    Snackbar.make(v, "Incorrect\tAnswer: " + Opta + "", Snackbar.LENGTH_SHORT).show();
                }
            } else if (global.equals("B")) {
                if (v.getId() == R.id.OptionB) {
                    Snackbar.make(v, "Correct\tAnswer:  ☺", Snackbar.LENGTH_SHORT).show();
                    l++;
                } else {
                    Snackbar.make(v, "Incorrect\tAnswer: " + Optb + "", Snackbar.LENGTH_SHORT).show();
                }
            } else if (global.equals("C")) {
                if (v.getId() == R.id.OptionC) {

                    Snackbar.make(v, "Correct\tAnswer:  ☺", Snackbar.LENGTH_SHORT).show();
                    l++;
                } else {
                    Snackbar.make(v, "Incorrect\tAnswer: " + Optc + "", Snackbar.LENGTH_SHORT).show();
                }
            } else if (global.equals("D")) {
                if (v.getId() == R.id.OptionD) {
                    Snackbar.make(v, "Correct\tAnswer:  ☺", Snackbar.LENGTH_SHORT).show();
                    l++;
                } else {
                    Snackbar.make(v, "Incorrect\tAnswer: " + Optd + "", Snackbar.LENGTH_SHORT).show();
                }
            }
            //Checking values like length of quiz, timer and on off variable
            if (j == 25 && end == 0 && variable == 0|| j == 1 && end == 1 && variable == 0 || j > 1 && end == 1 && variable == 0) {
                visibility = 1;
                Intent intent = new Intent(Questions.this, Result.class);
                intent.putExtra("correct", l);
                intent.putExtra("attemp", k-1);
                startActivity(intent);
                finish();
            }
        }
        if (get.equals("c1")) {
            if (c1 == 0) {
                final CountDownTimer myCountDown = new CountDownTimer(50000, 1000) {
                    int i = 100;

                    @Override
                    public void onTick(long millisUntilFinished) {
                        i = i - 2;
                        donutProgress.setProgress(i);
                    }
                    public void onFinish() {
                        toast.cancel();
                        donutProgress.setProgress(0);
                        end = 1;
                        //Checking values on start and finish of timer
                        if(end == 1 && l == 0 && variable == 0 || end == 1 && l > 0 && variable == 0){
                        visibility = 1;
                        Intent intent = new Intent(Questions.this, Result.class);
                        intent.putExtra("correct", l);
                        intent.putExtra("attemp",k);
                        startActivity(intent);
                        finish();}
                    }
               }.start();

                for (i = 1; i < 26; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                c1=1;
            }
            Ques = Tanker.readQuestion(list.get(j));
            Opta = Tanker.readOptionA(list.get(j));
            Optb = Tanker.readOptionB(list.get(j));
            Optc = Tanker.readOptionC(list.get(j));
            Optd = Tanker.readOptionD(list.get(j));
            global = Tanker.readAnswer(list.get(j++));
        } else if (get.equals("c2")) {
            if (c2 == 0) {
                final CountDownTimer myCountDown = new CountDownTimer(50000, 1000) {
                    int i = 100;

                    @Override
                    public void onTick(long millisUntilFinished) {
                        i = i - 2;
                        donutProgress.setProgress(i);
                    }
                    public void onFinish() {
                        toast.cancel();
                        donutProgress.setProgress(0);
                        end = 1;
                        //Checking values on start and finish of timer
                        if(end == 1 && l == 0 && variable == 0 || end == 1 && l > 0 && variable == 0){
                            visibility = 1;
                            Intent intent = new Intent(Questions.this, Result.class);
                            intent.putExtra("correct", l);
                            intent.putExtra("attemp",k);
                            startActivity(intent);
                            finish();}
                    }
                }.start();
                for (i = 1; i < 26; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                c2=1;
            }
            Ques = Cruiser.readQuestion(list.get(j));
            Opta = Cruiser.readOptionA(list.get(j));
            Optb = Cruiser.readOptionB(list.get(j));
            Optc = Cruiser.readOptionC(list.get(j));
            Optd = Cruiser.readOptionD(list.get(j));
            global = Cruiser.readAnswer(list.get(j++));
        } else if (get.equals("c3")) {
            if (c3 == 0) {
                final CountDownTimer myCountDown = new CountDownTimer(50000, 1000) {
                    int i = 100;

                    @Override
                    public void onTick(long millisUntilFinished) {
                        i = i - 2;
                        donutProgress.setProgress(i);
                    }
                    public void onFinish() {
                        toast.cancel();
                        donutProgress.setProgress(0);
                        end = 1;
                        //Checking values on start and finish of timer
                        if(end == 1 && l == 0 && variable == 0 || end == 1 && l > 0 && variable == 0){
                            visibility = 1;
                            Intent intent = new Intent(Questions.this, Result.class);
                            intent.putExtra("correct", l);
                            intent.putExtra("attemp",k);
                            startActivity(intent);
                            finish();}
                    }
                }.start();
                for (i = 1; i < 26; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                c3=1;
            }
            Ques = Cargo.readQuestion(list.get(j));
            Opta = Cargo.readOptionA(list.get(j));
            Optb = Cargo.readOptionB(list.get(j));
            Optc = Cargo.readOptionC(list.get(j));
            Optd = Cargo.readOptionD(list.get(j));
            global = Cargo.readAnswer(list.get(j++));
        } else if (get.equals("c4")) {
            if (c4 == 0) {
                final CountDownTimer myCountDown = new CountDownTimer(50000, 1000) {
                    int i = 100;

                    @Override
                    public void onTick(long millisUntilFinished) {
                        i = i - 2;
                        donutProgress.setProgress(i);
                    }
                    public void onFinish() {
                        toast.cancel();
                        donutProgress.setProgress(0);
                        end = 1;
                        //Checking values on start and finish of timer
                        if(end == 1 && l == 0 && variable == 0 || end == 1 && l > 0 && variable == 0){
                            visibility = 1;
                            Intent intent = new Intent(Questions.this, Result.class);
                            intent.putExtra("correct", l);
                            intent.putExtra("attemp",k);
                            startActivity(intent);
                            finish();}
                    }
                }.start();
                for (i = 1; i < 26; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                c4=1;
            }
            Ques = Dredger.readQuestion(list.get(j));
            Opta = Dredger.readOptionA(list.get(j));
            Optb = Dredger.readOptionB(list.get(j));
            Optc = Dredger.readOptionC(list.get(j));
            Optd = Dredger.readOptionD(list.get(j));
            global = Dredger.readAnswer(list.get(j++));
        } else if (get.equals("c5")) {
            if (c5 == 0) {
                final CountDownTimer myCountDown = new CountDownTimer(50000, 1000) {
                    int i = 100;

                    @Override
                    public void onTick(long millisUntilFinished) {
                        i = i - 2;
                        donutProgress.setProgress(i);
                    }
                    public void onFinish() {
                        toast.cancel();
                        donutProgress.setProgress(0);
                        end = 1;
                        //Checking values on start and finish of timer
                        if(end == 1 && l == 0 && variable == 0 || end == 1 && l > 0 && variable == 0){
                            visibility = 1;
                            Intent intent = new Intent(Questions.this, Result.class);
                            intent.putExtra("correct", l);
                            intent.putExtra("attemp",k);
                            startActivity(intent);
                            finish();}
                    }
                }.start();
                for (i = 1; i < 26; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                c5=1;
            }
            Ques = Warship.readQuestion(list.get(j));
            Opta = Warship.readOptionA(list.get(j));
            Optb = Warship.readOptionB(list.get(j));
            Optc = Warship.readOptionC(list.get(j));
            Optd = Warship.readOptionD(list.get(j));
            global = Warship.readAnswer(list.get(j++));
        }
        ques.setText("" + Ques);
        OptA.setText(Opta);
        OptB.setText(Optb);
        OptC.setText(Optc);
        OptD.setText(Optd);
    }

    @Override
    protected void onPause() {
        super.onPause();
        variable =1;
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        variable =1;
        SharedPreferences sp = getSharedPreferences("Score", Context.MODE_PRIVATE);
        if (sp.getInt("Sound", 0) == 0)
            mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        variable = 1;
        finish();
    }
}
