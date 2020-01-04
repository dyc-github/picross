package com.example.picross;

import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.picross.dataclasses.Grid;
import com.example.picross.dataclasses.Tile;
import com.example.picross.enums.TileState;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

/*    private LinearLayout colHintsPlaceHolder;
    private LinearLayout rowHintsPlaceHolder;*/
    private LinearLayout gridPlaceHolder;
    private LinearLayout rowHintsPlaceHolder;
    private LinearLayout colHintsPlaceHolder;
    private NumberPicker numberPicker;
    private Button newGameBut;
    private Button resetGridBut;
    private Button showAnsBut;
    private Button checkAnsBut;

    private Grid grid;
    private int numOfTiles;
    private static final int GRID_LENGTH = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout mainView = this.findViewById(R.id.mainView);
        rowHintsPlaceHolder = this.findViewById(R.id.rowHintsPlaceholder);
        colHintsPlaceHolder = this.findViewById(R.id.columnHintsPlaceholder);
        gridPlaceHolder = this.findViewById(R.id.gridPlaceholder);
        newGameBut = this.findViewById(R.id.new_button);
        resetGridBut = this.findViewById(R.id.reset_button);
        showAnsBut = this.findViewById(R.id.show_button);
        checkAnsBut = this.findViewById(R.id.check_button);


        createNewGrid();


        newGameBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            createNewGrid();
        }});

        resetGridBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grid.resetGrid();
            }});
        showAnsBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               grid.showAnswer();
            }});
        checkAnsBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(MainActivity.this, "Wrong answer (there may be multiple solutions)", Toast.LENGTH_LONG);
                if (grid.checkAnswer()){
                    toast.setText("Correct answer");
                }
                toast.show();
            }});







        }









/*        gridView = findViewById(R.id.gameAreaView);


        Tile testTile = new Tile(MainActivity.this, 100);
        Tile testTile2 = new Tile(MainActivity.this,100);
        Tile testTile3 = new Tile(MainActivity.this,100);
        Tile testTile4 = new Tile(MainActivity.this,100);
        Tile testTile5 = new Tile(MainActivity.this,100);
        Tile testTile6 = new Tile(MainActivity.this,100);

        gridView.addView(testTile);
        gridView.addView(testTile2);
        gridView.addView(testTile3);
        gridView.addView(testTile4);
        gridView.addView(testTile5);
        gridView.addView(testTile6);*/

    private void createNewGrid() {

        // prompt user for info

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Set grid dimensions to:");


        View numberPickerView = getLayoutInflater().inflate(R.layout.number_picker, null);
        numberPicker = numberPickerView.findViewById(R.id.number_picker);
        numberPicker.setMinValue(5);
        numberPicker.setMaxValue(15);

        builder.setView(numberPickerView);


        builder.setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                numOfTiles = numberPicker.getValue();

                //clear grid placeholder and create a new grid

                gridPlaceHolder.removeAllViews();
                colHintsPlaceHolder.removeAllViews();
                rowHintsPlaceHolder.removeAllViews();

                grid = new Grid(MainActivity.this, numOfTiles, GRID_LENGTH);

                gridPlaceHolder.addView(grid);

                defineHints();
            }
        });
        builder.show();


    }




    private void defineHints() {
        ArrayList<ArrayList<Integer>> colHints = grid.getColHint();
        ArrayList<ArrayList<Integer>> rowHints = grid.getRowHint();

        for (int a = 0; a < numOfTiles; a++) {
            ArrayList<Integer> colHint = colHints.get(a);
            ArrayList<Integer> rowHint = rowHints.get(a);

            String colHintString = "";
            String rowHintString = "";

            Tile colHintView = new Tile(MainActivity.this, grid.getTileLength(), TileState.HINTCOL);
            Tile rowHintView = new Tile(MainActivity.this, grid.getTileLength(), TileState.HINTROW);
            TextView colHintTextView = colHintView.findViewById(R.id.text);
            TextView rowHintTextView = rowHintView.findViewById(R.id.text);

            int hintTextSize;

            if (numOfTiles < 8){
                hintTextSize = 18;
            }
            else if(numOfTiles< 12){
                hintTextSize = 14;
            }
            else if(numOfTiles<15){
                hintTextSize = 10;
            }
            else{
                hintTextSize = 9;
            }
            colHintTextView.setTextSize(hintTextSize);
            rowHintTextView.setTextSize(hintTextSize);


            for (int b = 0; b < colHint.size(); b++) {
                if (colHintString.isEmpty()) {
                    colHintString = colHint.get(b).toString();

                } else {
                    colHintString = colHintString + '\n' + colHint.get(b);
                }

            }
            for (int b = 0; b < rowHint.size(); b++) {
                if (rowHintString.isEmpty()) {
                    rowHintString = rowHint.get(b).toString();
                } else {
                    rowHintString = rowHintString + ' ' + rowHint.get(b);
                }
            }

            colHintTextView.setText(colHintString);
            rowHintTextView.setText(rowHintString);

            colHintsPlaceHolder.addView(colHintView);
            rowHintsPlaceHolder.addView(rowHintView);
        }
    }

/*    private void Grid(int numTiles) {
        int tileLength = GRID_LENGTH / numTiles;
        userGrid = new Tile[numTiles][numTiles];
        answerGrid = new TileState[numTiles][numTiles];
        Random random = new Random();
        for (int y = 0; y < numTiles; y++) {
            for (int x = 0; x < numTiles; x++) {
                //construct user grid
                Tile tile = new Tile(MainActivity.this, tileLength);
//                Tile tile = new Tile(MainActivity.this);
                gridView.addView(tile);
                userGrid [y][x]= tile;


                //construct answer grid
                TileState temp = TileState.CROSSED;
                int i = random.nextInt(2);
                if (i == 0) {
                    temp = TileState.FILLED;
                }
                answerGrid[y][x] = temp;
            }
        }
    }*/


}

