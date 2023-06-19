package com.example.dynamicimageview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.util.Log;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    int numRows = 6;
    int numColumns = 7;
    int turn = 1;
    HashMap<String, ImageView> images;

    int [][] gameMatrix = new int [numRows][numColumns];
    int [] fallPosition = new int [numColumns];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i=0;i<numRows;i++)
        {
            for(int j=0;j<numColumns;j++)
            {
                gameMatrix[i][j] = 0;
            }
        }
        for(int i=0;i<numColumns;i++)
        {
            fallPosition[i] = numRows;
        }
        images = new HashMap<String, ImageView>();
        LinearLayout llmain = findViewById(R.id.lvmain);
        for (int row = 1; row <= numRows; row++){
            LinearLayout llrow = new LinearLayout(this);
            llrow.setOrientation(LinearLayout.HORIZONTAL);
            for (int col = 1; col <= numColumns; col++){
                ImageView iv = new ImageView(this);
                iv.setTag(row+","+col);
                images.put(row+","+col, iv);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,200);
                layoutParams.weight = 1;
                iv.setLayoutParams(layoutParams);
                iv.setImageResource(R.drawable.white);
                iv.setOnClickListener((v)->{
                    String s1;
                    s1 = v.getTag().toString();
                    String[] s2 = s1.split(",");
                    //int elementRow = Integer.parseInt(s2[0]);
                    int elementCol = Integer.parseInt(s2[1]);
                    int write_matrix_row = fallPosition[elementCol-1] - 1;
                    int write_matrix_col = elementCol - 1;
                    //Toast.makeText(MainActivity.this, "Selected column " + elementCol, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MainActivity.this, "Kliknuo si na sliku "+v.getTag().toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MainActivity.this, "Matrica: "+write_matrix_row+write_matrix_col, Toast.LENGTH_SHORT).show();
                    if(turn == 1) {
                        turn = -1;
                        gameMatrix[write_matrix_row][write_matrix_col] = 1;
                        images.get(fallPosition[elementCol-1] + "," + elementCol).setImageResource(R.drawable.red);
                        fallPosition[elementCol-1]--;
                        gameEnd(gameMatrix, turn);
                    }else if(turn == -1){
                        turn = 1;
                        gameMatrix[write_matrix_row][write_matrix_col] = -1;
                        images.get(fallPosition[elementCol-1] + "," + elementCol).setImageResource(R.drawable.blue);
                        fallPosition[elementCol-1]--;
                    }
                    if(gameEnd(gameMatrix,turn) == 1) {
                        if (turn == 1)
                            Toast.makeText(MainActivity.this, "Pobedio Plavi", Toast.LENGTH_SHORT).show();
                        else if((turn == -1))
                            Toast.makeText(MainActivity.this, "Pobedio Crveni", Toast.LENGTH_SHORT).show();
                        for(int i=0;i<numRows;i++)
                        {
                            for(int j=0;j<numColumns;j++)
                            {
                                gameMatrix[i][j] = 0;
                                int image_i = i + 1;
                                int image_j = j + 1;
                                images.get(image_i + "," + image_j).setImageResource(R.drawable.white);
                            }
                        }
                        for(int i=0;i<numColumns;i++)
                        {
                            fallPosition[i] = numRows;
                        }

                    }
                });
                llrow.addView(iv);
            }
            llmain.addView(llrow);
        }
    }
    protected int gameEnd(int [][] gameMatrix, int turn){
        int returnValue = 0;
        int value = turn * (-1);
        /********* 4 in a row *********/
        for(int i=0;i<numRows;i++)
        {
            for(int j=0;j<numColumns - 3;j++)
            {
                boolean statement = (gameMatrix[i][j] == value) && (gameMatrix[i][j+1] == value)
                                 && (gameMatrix[i][j+2] == value) && (gameMatrix[i][j+3] == value);
                if(statement){
                    returnValue = 1;

                }
            }
        }
        /********* 4 in a column *********/
        for(int i=0;i<numRows - 3;i++)
        {
            for(int j=0;j<numColumns;j++)
            {
                boolean statement = (gameMatrix[i][j] == value) && (gameMatrix[i+1][j] == value)
                        && (gameMatrix[i+2][j] == value) && (gameMatrix[i+3][j] == value);
                if(statement){
                    returnValue = 1;
                }
            }
        }
        /********* 4 in a direction up-left/down-right *********/
        for(int i=0;i<numRows - 3;i++)
        {
            for(int j=0;j<numColumns- 3;j++)
            {
                boolean statement = (gameMatrix[i][j] == value) && (gameMatrix[i+1][j+1] == value)
                        && (gameMatrix[i+2][j+2] == value) && (gameMatrix[i+3][j+3] == value);
                if(statement){
                    returnValue = 1;
                }
            }
        }
        /********* 4 in a direction down-left/up-right *********/
        for(int i=0;i<numRows - 3;i++)
        {
            for(int j=3;j<numColumns;j++)
            {
                boolean statement = (gameMatrix[i][j] == value) && (gameMatrix[i+1][j-1] == value)
                        && (gameMatrix[i+2][j-2] == value) && (gameMatrix[i+3][j-3] == value);
                if(statement){
                    returnValue = 1;
                }
            }
        }
        return returnValue;
    }
}