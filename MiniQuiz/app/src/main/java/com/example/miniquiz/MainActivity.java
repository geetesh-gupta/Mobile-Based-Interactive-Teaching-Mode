package com.example.miniquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.miniquiz.modal.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button b1,b2,b3,b4;
    TextView t1_question,timerTxt;
    int total = 0;
    int correct = 0;
    int wrong = 0;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button)findViewById(R.id.button1);
        b2 = (Button)findViewById(R.id.button2);
        b3 = (Button)findViewById(R.id.button3);
        b4 = (Button)findViewById(R.id.button4);

        t1_question = (TextView)findViewById(R.id.questionTxt);
        timerTxt = (TextView)findViewById(R.id.timerTxt);
        updateQuestion();
        DatabaseReference timer = FirebaseDatabase.getInstance().getReference().child("time");
        timer.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int rev_timer = Integer.parseInt(dataSnapshot.getValue().toString());
                reverseTimer(rev_timer*60,timerTxt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onBackPressed()
    {

    }
    private void updateQuestion() {
        total++;
        final long[] total_count = new long[1];
        DatabaseReference total_count_db = FirebaseDatabase.getInstance().getReference().child("Questions");
        total_count_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                total_count[0] = dataSnapshot.getChildrenCount();
                Log.d("TAG","Totoal count = "+total_count[0]);
                if(total > total_count[0]){
                    //open result activity
                    Intent myIntent  = new Intent(MainActivity.this,ResultActivity.class);
                    myIntent.putExtra("total",String.valueOf(total-1));
                    myIntent.putExtra("correct",String.valueOf(correct));
                    myIntent.putExtra("incorrect",String.valueOf(wrong));
                    startActivity(myIntent);

                }else
                {
                    ref = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(total));
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final Question ques = dataSnapshot.getValue(Question.class);
                            t1_question.setText(ques.getQuestion());
                            b1.setText(ques.getOption1());
                            b2.setText(ques.getOption2());
                            b3.setText(ques.getOption3());
                            b4.setText(ques.getOption4());

                            b1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(b1.getText().toString().equals(ques.getAnswer()))
                                    {   //b1.setBackgroundColor(Color.GREEN);
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                //b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion();
                                            }
                                        },1500);
                                    }else
                                    {
                                        //answer is wrong so we need to find correct answer and make it green
                                        wrong ++;
                                        //b1.setBackgroundColor(Color.RED);
                                        /*if(b2.getText().toString().equals(ques.getAnswer()))
                                        {
                                            b2.setBackgroundColor(Color.GREEN);
                                        }else if(b3.getText().toString().equals(ques.getAnswer()))
                                        {
                                            b3.setBackgroundColor(Color.GREEN);
                                        }else {
                                            b4.setBackgroundColor(Color.GREEN);
                                        }*/
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                /*b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));*/
                                                updateQuestion();
                                            }
                                        },1500);

                                    }
                                }
                            });
                            b2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(b2.getText().toString().equals(ques.getAnswer()))
                                    {   //b2.setBackgroundColor(Color.GREEN);
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                //b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion();
                                            }
                                        },1500);
                                    }else
                                    {
                                        //answer is wrong so we need to find correct answer and make it green
                                        wrong ++;
                                        /*b2.setBackgroundColor(Color.RED);
                                        if(b1.getText().toString().equals(ques.getAnswer()))
                                        {
                                            b1.setBackgroundColor(Color.GREEN);
                                        }else if(b3.getText().toString().equals(ques.getAnswer()))
                                        {
                                            b3.setBackgroundColor(Color.GREEN);
                                        }else {
                                            b4.setBackgroundColor(Color.GREEN);
                                        }*/
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                               /* b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));*/
                                                updateQuestion();
                                            }
                                        },1500);

                                    }
                                }
                            });
                            b3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(b3.getText().toString().equals(ques.getAnswer()))
                                    {   //b3.setBackgroundColor(Color.GREEN);
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                //b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion();
                                            }
                                        },1500);
                                    }else
                                    {
                                        //answer is wrong so we need to find correct answer and make it green
                                        wrong ++;
                                        /*b3.setBackgroundColor(Color.RED);
                                        if(b2.getText().toString().equals(ques.getAnswer()))
                                        {
                                            b2.setBackgroundColor(Color.GREEN);
                                        }else if(b1.getText().toString().equals(ques.getAnswer()))
                                        {
                                            b1.setBackgroundColor(Color.GREEN);
                                        }else {
                                            b4.setBackgroundColor(Color.GREEN);
                                        }*/
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                /*b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));*/
                                                updateQuestion();
                                            }
                                        },1500);

                                    }
                                }
                            });
                            b4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(b4.getText().toString().equals(ques.getAnswer()))
                                    {   //b4.setBackgroundColor(Color.GREEN);
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                correct++;
                                                //b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                updateQuestion();
                                            }
                                        },1500);
                                    }else
                                    {
                                        //answer is wrong so we need to find correct answer and make it green
                                        wrong ++;
                                        /*b4.setBackgroundColor(Color.RED);
                                        if(b2.getText().toString().equals(ques.getAnswer()))
                                        {
                                            b2.setBackgroundColor(Color.GREEN);
                                        }else if(b3.getText().toString().equals(ques.getAnswer()))
                                        {
                                            b3.setBackgroundColor(Color.GREEN);
                                        }else {
                                            b1.setBackgroundColor(Color.GREEN);
                                        }*/
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                /*b1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                                b4.setBackgroundColor(Color.parseColor("#03A9F4"));*/
                                                updateQuestion();
                                            }
                                        },1500);

                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void reverseTimer(int Seconds,final TextView tv){
        new CountDownTimer(Seconds *  1000 +1000 , 1000)
        {
            public void onTick(long milliUtilFinished)
            {
                int seconds = (int) (milliUtilFinished / 1000);
                int minutes = seconds / 60;
                 seconds = seconds%60;
                 tv.setText(String.format("%02d",minutes)+
                         ":"+String.format("%02d",seconds));
            }
            public void onFinish(){
                tv.setText("Finished");
                Intent myIntent  = new Intent(MainActivity.this,ResultActivity.class);
                myIntent.putExtra("total",String.valueOf(total));
                myIntent.putExtra("correct",String.valueOf(correct));
                myIntent.putExtra("incorrect",String.valueOf(wrong));
                startActivity(myIntent);
            }
        }.start();
    }
}