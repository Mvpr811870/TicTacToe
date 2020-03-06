package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import static com.example.tictactoe.Board.B;

public class MainActivity extends AppCompatActivity {

    int i,j;
    boolean computer_plays = true;

    Button[][] button;
    HashMap<Button, Pair<Integer,Integer>> M = new HashMap<>();
    TextView result;
    TextView player_window;


    boolean halt = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Board.initiallize();

        button = new Button[3][3];

        button[0][0] = findViewById(R.id.b00);
        button[0][1] = findViewById(R.id.b01);
        button[0][2] = findViewById(R.id.b02);
        button[1][0] = findViewById(R.id.b10);
        button[1][1] = findViewById(R.id.b11);
        button[1][2] = findViewById(R.id.b12);
        button[2][0] = findViewById(R.id.b20);
        button[2][1] = findViewById(R.id.b21);
        button[2][2] = findViewById(R.id.b22);

        result = findViewById(R.id.result);
        player_window = findViewById(R.id.player);

        for( i=0;i<3;++i){
            for(j=0;j<3;++j){
                M.put(button[i][j],new Pair<>(i,j));
                button[i][j].setOnClickListener(new View.OnClickListener() {
                    int x=i,y=j;

                    @Override
                    public void onClick(View view) {
                        final boolean didWork = MainActivity.this.onEntry(M.get(button[x][y]).first,M.get(button[x][y]).second);
                        final Handler handler = new Handler();
                        if(didWork&&computer_plays){
                            if(Board.move=='O'){
                                Pair<Integer,Integer> p = Board.computer();
                                MainActivity.this.onEntry(M.get(button[p.first][p.second]).first,M.get(button[p.first][p.second]).second);
                            }
                        }

//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if(didWork){
//                                    if(Board.move=='O'){
//                                        Pair<Integer,Integer> p = Board.computer();
//                                        MainActivity.this.onEntry(M.get(button[p.first][p.second]).first,M.get(button[p.first][p.second]).second);
//                                    }
//                                }
//                                handler.postDelayed(this,100000);
//                            }
//                        });


                    }
                });
            }
        }

    }

    public boolean onEntry(int x,int y){
        if(halt)
            return false;

        Board.RESULT result = Board.set(x,y);
        if(result!=null){
            button[x][y].setText(String.valueOf(B[x][y]));
            if(result== Board.RESULT.X)
                xwon();
            else if(result== Board.RESULT.O)
                owon();
            else if(result== Board.RESULT.DRAW)
                draw();
            else
                return true;
            return false;
        }
        return false;
    }

    private void owon() {
        result.setText("Congrats O");
        halt=true;
    }

    private void xwon() {
        result.setText("Congrats X");
        halt=true;
    }

    private void draw() {
        result.setText("Congrats O");
        result.setText("OOPS! Its a DRAW");
        halt=true;
    }

    public void playAgain2(View view){
        Board.initiallize();
        for(int i=0;i<3;++i){
            for(int j=0;j<3;++j){
                button[i][j].setText(String.valueOf(B[i][j]));
            }
        }

        result.setText("");
        player_window.setText("2P");
        halt=false;
        computer_plays=false;
    }

    public void playAgain1(View view){
        Board.initiallize();
        for(int i=0;i<3;++i){
            for(int j=0;j<3;++j){
                button[i][j].setText(String.valueOf(B[i][j]));
            }
        }

        result.setText("");
        player_window.setText("1P");
        halt=false;
        computer_plays=true;
    }
}
