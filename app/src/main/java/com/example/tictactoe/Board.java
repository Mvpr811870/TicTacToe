package com.example.tictactoe;


import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.StrictMath.abs;

public class Board {
    static char[][] B = new char[3][3];
    static char move = 'X',last_move=(char)0;

    private static RESULT column(int c){
        if(B[0][c]==B[1][c]&&B[1][c]==B[2][c]){
            if(B[0][c]=='X')
                return RESULT.X;
            else if(B[0][c]=='O')
                return RESULT.O;
            else
                return RESULT.NR;
        }
        else
            if(B[0][c]==' '||B[1][c]==' '||B[2][c]==' ')
                return RESULT.NR;
            else
                return RESULT.DRAW;
    }

    private static RESULT row(int r){
        if(B[r][0]==B[r][1]&&B[r][1]==B[r][2]){
            if(B[r][0]=='X')
                return RESULT.X;
            else if(B[r][0]=='O')
                return RESULT.O;
            else
                return RESULT.NR;
        }
        else
            if(B[r][0]==' '||B[r][1]==' '||B[r][2]==' ')
                return RESULT.NR;
            else
                return RESULT.DRAW;
    }

    private static RESULT diag(int d){
        switch(d){
            case 0:
                if(B[0][0]==B[1][1]&&B[1][1]==B[2][2]) {
                    if (B[1][1] == 'X')
                        return RESULT.X;
                    else if (B[1][1] == 'O')
                        return RESULT.O;
                    else
                        return RESULT.NR;
                }
                else
                    if(B[0][0]==' '||B[1][1]==' '||B[2][2]==' ')
                        return RESULT.NR;
                    else
                        return RESULT.DRAW;
            case 1:
                if(B[0][2]==B[1][1]&&B[1][1]==B[2][0]) {
                    if (B[1][1] == 'X')
                        return RESULT.X;
                    else if (B[1][1] == 'O')
                        return RESULT.O;
                    else
                        return RESULT.NR;
                }
                else
                    if(B[0][2]==' '||B[1][1]==' '||B[2][0]==' ')
                        return RESULT.NR;
                    else
                        return RESULT.DRAW;
        }
        return RESULT.NR;
    }


    public static RESULT winner(){
        RESULT rs[]=new RESULT[8];
        RESULT C,R,D=RESULT.NR;
        boolean draw = true;
        for(int i=0;i<3;++i){
            C=column(i);
            R=row(i);
            if(i!=2)
                D=diag(i);

            if(C==RESULT.X||R==RESULT.X||D==RESULT.X)
                return RESULT.X;

            if(C==RESULT.O||R==RESULT.O||D==RESULT.O)
                return RESULT.O;

            draw = draw && R==RESULT.DRAW;
        }

        if(draw)
            return RESULT.DRAW;
        else
            return RESULT.NR;
    }

    public static RESULT set(int i,int j){
        if(B[i][j]!=' ')
            return null;
        else{
            last_move=move;

            B[i][j]=move;
            if(move=='O')   move='X';
            else    move='O';
            return winner();
        }
    }

    public static void initiallize(){
        for(int i=0;i<3;++i){
            for(int j=0;j<3;++j){
                B[i][j]=' ';
            }
        }
        move='X';
    }

    public static Pair<Integer,Integer> computer(){
        int count=0;
        ArrayList<Integer> compulsory = new ArrayList<>();
        Map<Integer,Pair<Integer,Integer> > T = new HashMap<>();
        for(int i=0;i<3;++i){
            for(int j=0;j<3;++j){
                if(B[i][j]==' '){
                    T.put(count++,new Pair<>(i,j));

                    B[i][j]='O';
                    RESULT temp = winner();
                    B[i][j]=' ';
                    if(temp==RESULT.O)
                        return new Pair<>(i,j);

                    B[i][j]='X';
                    temp = winner();
                    B[i][j]=' ';
                    if(temp==RESULT.X)
                        return new Pair<>(i,j);
                }
            }
        }

        int R = abs(new Random().nextInt());
        int r = R%count;
        return T.get(r);

    }

    enum RESULT{
        X,
        O,
        DRAW,
        NR
    }

}
