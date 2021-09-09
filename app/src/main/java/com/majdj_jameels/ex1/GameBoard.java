package com.majdj_jameels.ex1;

import android.graphics.Color;
import android.widget.TextView;

import java.util.Random;

public class GameBoard {

      int[][] board = new int[4][4];
      boolean done=false;
      boolean moved=false;
      int countMoves=0;

//    a function that orders the game board from 1-15
    public void reorder() {
        done=false;
        int counter = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = counter;
                counter++;
            }
        }
        board[3][3] = 0;
    }
//    a function that generates a random board
    public void randomBoard() {
//        randomly swap the boxes random number(60-120) times, this way we'll get a random board and 100% solvable
        done=false;
        countMoves=0;
        Random random = new Random();
        int n = (random.nextInt(5));
//        #0 coordinates
        int whiteX=3;
        int whiteY=3;

        for (int rand = 0; rand < n; rand++) {
//            current square
            int oldX=whiteX;
            int oldY=whiteY;
            int x;
            if(whiteY==3) {
                if (whiteX==3){
//                    can go up or left
                    x=random.nextInt(1);
                    if(x==0)
                        whiteY--;
                    else
                        whiteX--;
                }
                else if (whiteX==0){
//                    can go down or left
                    x=random.nextInt(1);
                    if(x==0)
                        whiteY--;
                    else
                        whiteX++;

                }
                else{
                     x=random.nextInt(2);
//                     can go up , down , left
                    if(x==0)
                        whiteY--;
                    else if(x==1)
                        whiteX++;
                    else
                        whiteX--;
                }
            }

            else if(whiteY==0){
                if (whiteX==0){
//                    can go down right
                    x=random.nextInt(1);
                    if(x==0)
                        whiteY++;
                    else
                        whiteX++;
                }
                else if (whiteX==3){
//                    can go up , right
                    x=random.nextInt(1);
                    if(x==0)
                        whiteY++;
                    else
                        whiteX--;
                }
                else{
                     x=random.nextInt(2);
                    if(x==0)
                        whiteX--;
                    else if(x==1)
                        whiteX++;
                    else
                        whiteY++;
                }
            }

            else if(whiteX==3&&(whiteY==1||whiteY==2)){
//                can go up,right,left
                x=random.nextInt(2);
                if(x==0)
                    whiteX--;
                else if(x==1)
                    whiteY++;
                else
                    whiteY--;
            }

            else if(whiteX==0&&(whiteY==1||whiteY==2)){
//                can go down ,right,left
                x=random.nextInt(2);
                if(x==0)
                    whiteY++;
                else if(x==1)
                    whiteX++;
                else
                    whiteY--;
            }

            else{
//                can go 4 directions
                x=random.nextInt(3);
                if(x==0)
                    whiteY++;
                else if(x==1)
                    whiteX--;
                else if(x==2)
                    whiteY--;
                else
                    whiteX++;
            }

//           now swap
            swap(whiteX,whiteY,oldX,oldY);

        }



    }
// a function that swaps 2 numbers
    public void swap(int x1,int y1,int x2,int y2){
        int temp=board[x1][y1];
        board[x1][y1]=board[x2][y2];
        board[x2][y2]=temp;
    }
// a function that swap 2 square (visually )
    public void renderMove(int x1,int y1,int x2,int y2,TextView t1,TextView [] views){
        swap(x1,y1,x2,y2);
        int white=0;
//        find the white piece
        for(int i=0;i<16;i++){
            if(views[i].getText()=="") {
                white = i;
                break;
            }
    }
        views[white].setText(t1.getText());
        views[white].setBackgroundColor(Color.parseColor("#785447"));
        views[white].setTextColor(Color.parseColor("#FFEB3B"));

        t1.setText("");
        t1.setBackgroundColor(Color.WHITE);
        moved=true;

    }
//    a function that checks if the numbers are ordered and the game is finished
    public boolean isDone(){
        int counter=1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
//                reached last square
                if(i==3&&j==3)
                    counter=0;
                if(board[i][j] != counter)
                    return false;
                counter++;
            }
        }
        return true;
    }
//  render the squares according to the board array we have here
    public void renderBoard(TextView[] views){
        reorder();
        randomBoard();
        for(int b=0;b<16;b++){
            if(board[b/4][b%4]==0) {
                views[b].setText("");
                views[b].setBackgroundColor(Color.WHITE);

            }
            else{
                views[b].setText("" + board[b / 4][b % 4]);
                views[b].setBackgroundColor(Color.parseColor("#785447"));
                views[b].setTextColor(Color.parseColor("#FFEB3B"));

            }
        }


    }
//    moves counter
    public void movesCounter(TextView moves){
        countMoves++;
        int digits=0;
        int temp=countMoves;
        String text="Moves: ";
        while(temp!=0){
            temp = temp/10;
            digits++;
        }
        if(digits==1)
            moves.setText(text+"000"+countMoves);
        else if(digits==2)
            moves.setText(text+"00"+countMoves);
        else if(digits==3)
            moves.setText(text+"0"+countMoves);
        else if(digits==4)
            moves.setText(text+countMoves);
    }

//  make a move
    public boolean move(TextView t1, TextView[] views,TextView moves){
        if(!done && t1.getText().toString()!="") {

            int x = 0;
            int y = 0;
            int num=Integer.parseInt(t1.getText().toString());
//        find the coordinates of the number
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (board[i][j] == num) {
                        x = i;
                        y = j;
                    }
                }
            }

            if (y == 3) {
                if (x == 3) {
//                    can go up or left
//                up
                    if (board[x - 1][y] == 0)
                        renderMove(x, y, x - 1, y, t1, views);

//                left
                    else if (board[x][y - 1] == 0)
                        renderMove(x, y, x, y - 1, t1, views);

                } else if (x == 0) {
//                    can go down or left
//                down
                    if (board[x + 1][y] == 0)
                        renderMove(x, y, x + 1, y, t1, views);

//                left
                    else if (board[x][y - 1] == 0)
                        renderMove(x, y, x, y - 1, t1, views);

                } else {
//                up
                    if (board[x - 1][3] == 0)
                        renderMove(x, y, x - 1, y, t1, views);

//                left
                    else if (board[x][y - 1] == 0)
                        renderMove(x, y, x, y - 1, t1, views);
                        //    down
                    else if (board[x + 1][3] == 0)
                        renderMove(x, y, x + 1, y, t1, views);


                }
            } else if (y == 0) {
                if (x == 0) {
//                down
                    if (board[x + 1][y] == 0)
                        renderMove(x, y, x + 1, y, t1, views);

//                right
                    else if (board[x][y + 1] == 0)
                        renderMove(x, y, x, y + 1, t1, views);

                } else if (x == 3) {
//                up
                    if (board[x - 1][y] == 0)
                        renderMove(x, y, x - 1, y, t1, views);

//                right
                    else if (board[x][y + 1] == 0)
                        renderMove(x, y, x, y + 1, t1, views);
                } else {
//                down
                    if (board[x + 1][y] == 0)
                        renderMove(x, y, x + 1, y, t1, views);

//                up
                    else if (board[x - 1][y] == 0)
                        renderMove(x, y, x - 1, y, t1, views);

//                right
                    else if (board[x][y + 1] == 0)
                        renderMove(x, y, x, y + 1, t1, views);
                }
            } else if (x == 3 && (y == 1 || y == 2)) {
//                up
                if (board[x - 1][y] == 0)
                    renderMove(x, y, x - 1, y, t1, views);

//                left
                else if (board[x][y - 1] == 0)
                    renderMove(x, y, x, y - 1, t1, views);

//                right
                else if (board[x][y + 1] == 0)
                    renderMove(x, y, x, y + 1, t1, views);
            } else if (x == 0 && (y == 1 || y == 2)) {
//                down
                if (board[x + 1][y] == 0)
                    renderMove(x, y, x + 1, y, t1, views);

//                left
                else if (board[x][y - 1] == 0)
                    renderMove(x, y, x, y - 1, t1, views);

//                right
                else if (board[x][y + 1] == 0)
                    renderMove(x, y, x, y + 1, t1, views);
            } else {
//                up
                if (board[x - 1][y] == 0)
                    renderMove(x, y, x - 1, y, t1, views);

//                left
                else if (board[x][y - 1] == 0)
                    renderMove(x, y, x, y - 1, t1, views);

//                right
                else if (board[x][y + 1] == 0)
                    renderMove(x, y, x, y + 1, t1, views);

//                down
                else if (board[x + 1][y] == 0)
                    renderMove(x, y, x + 1, y, t1, views);
            }

            if(moved) {
                movesCounter(moves);
                done = isDone();
                moved=false;
                if(done)
                    return true;
            }
        }
        return false;
    }


}
