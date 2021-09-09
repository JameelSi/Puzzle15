package com.majdj_jameels.ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
     TextView movesView;
     TextView timeView;
     Button newGameBtn;
//   pieces of the board
     TextView one;
     TextView two;
     TextView three;
     TextView four;
     TextView five;
     TextView six;
     TextView seven;
     TextView eight;
     TextView nine;
     TextView ten;
     TextView eleven;
     TextView twelve;
     TextView thirteen;
     TextView fourteen;
     TextView fifteen;
     TextView white;
     GameBoard gameBoard;
     TextView[] views;
//    timer vars
     int minutes,seconds,i;
     boolean isActive=true;
     private Thread timerThread;
     boolean finished = false;
     boolean play_music;
     MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        movesView=findViewById(R.id.moves);
        timeView=findViewById(R.id.time);
        newGameBtn=findViewById(R.id.newGame);
        newGameBtn.setOnClickListener(this);
        gameBoard=new GameBoard();
        //Get the numbers
        views=new TextView[16];
        one=findViewById(R.id.num1);
        views[0]=one;
        two=findViewById(R.id.num2);
        views[1]=two;
        three=findViewById(R.id.num3);
        views[2]=three;
        four=findViewById(R.id.num4);
        views[3]=four;
        five=findViewById(R.id.num5);
        views[4]=five;
        six=findViewById(R.id.num6);
        views[5]=six;
        seven=findViewById(R.id.num7);
        views[6]=seven;
        eight=findViewById(R.id.num8);
        views[7]=eight;
        nine=findViewById(R.id.num9);
        views[8]=nine;
        ten=findViewById(R.id.num10);
        views[9]=ten;
        eleven=findViewById(R.id.num11);
        views[10]=eleven;
        twelve=findViewById(R.id.num12);
        views[11]=twelve;
        thirteen=findViewById(R.id.num13);
        views[12]=thirteen;
        fourteen=findViewById(R.id.num14);
        views[13]=fourteen;
        fifteen=findViewById(R.id.num15);
        views[14]=fifteen;
        white=findViewById(R.id.num0);
        views[15]=white;
//      get the data
        SharedPreferences sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        play_music=sp.getBoolean("user",false);
        mp=MediaPlayer.create(GameActivity.this,R.raw.song);


        if (play_music==true) {
            mp.start();
            mp.setLooping(true);
        }
        else
            stop();

        for(int i=0;i<16;i++)
            views[i].setOnClickListener(this);

        gameBoard.renderBoard(views);
    }
// a function that unlink the click ,stop the timer, and show winning message
    public void showMsg(){
        timerThread=null;
        Toast.makeText(GameActivity.this, "Game Over - Puzzle Solved!", Toast.LENGTH_LONG).show();
        for(int i=0;i<16;i++)
            views[i].setClickable(false);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.num1:
                if(gameBoard.move(one,views,movesView))
                    showMsg();
                break;
            case R.id.num2:
                if(gameBoard.move(two,views,movesView))
                    showMsg();
                break;
            case R.id.num3:
                if(gameBoard.move(three,views,movesView))
                    showMsg();
                break;
            case R.id.num4:
                if(gameBoard.move(four,views,movesView))
                    showMsg();
                break;
            case R.id.num5:
                if(gameBoard.move(five,views,movesView))
                    showMsg();
                break;
            case R.id.num6:
                if(gameBoard.move(six,views,movesView))
                    showMsg();
                break;
            case R.id.num7:
                if(gameBoard.move(seven,views,movesView))
                    showMsg();
                break;
            case R.id.num8:
                if(gameBoard.move(eight,views,movesView))
                    showMsg();
                break;
            case R.id.num9:
                if(gameBoard.move(nine,views,movesView))
                    showMsg();
                break;
            case R.id.num10:
                if(gameBoard.move(ten,views,movesView))
                    showMsg();
                break;
            case R.id.num11:
                if(gameBoard.move(eleven,views,movesView))
                    showMsg();
                break;
            case R.id.num12:
                if(gameBoard.move(twelve,views,movesView))
                    showMsg();
                break;
            case R.id.num13:
                if(gameBoard.move(thirteen,views,movesView))
                    showMsg();
                break;
            case R.id.num14:
                if(gameBoard.move(fourteen,views,movesView))
                    showMsg();
                break;
            case R.id.num15:
                if(gameBoard.move(fifteen,views,movesView))
                    showMsg();
                break;
            case R.id.num0:
                if(gameBoard.move(white,views,movesView))
                    showMsg();
                break;
            case R.id.newGame:
//             start new game , reset moves,timer , enables clicks
                for(int i=0;i<16;i++)
                    views[i].setClickable(true);
                gameBoard.renderBoard(views);
                i=0;
                movesView.setText("Moves: 0000");
                timeView.setText("Time: 00:00");
//              start new timer
                finished=false;
                startTimer();
                break;
        }
    }
//    create new thread and start timer
    private void startTimer() {
        if(timerThread==null){
            timerThread = new Thread(new Runnable() {

                @Override
                public void run() {

                    while (!finished && isActive) {
                        finished = gameBoard.done;
                        i++;
                        minutes = i / 60;
                        seconds = i % 60;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeView.setText(String.format("Time: %02d:%02d", minutes, seconds));
                            }
                        });
                        SystemClock.sleep(1000);


                    }
                }
            });
            timerThread.start();
        }
    }

    public void stop() {
        if(play_music == true)
            mp.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
//      pause the timer
        if (timerThread != null)
            timerThread = null;

    }

    @Override
    protected void onStop() {
        super.onStop();
        mp.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//       play music , start timer
        if (play_music == true) {
            mp.start();
            mp.setLooping(true);
        }
        if (timerThread == null) {
            isActive = true;
            startTimer();
        }

    }

}