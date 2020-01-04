package com.example.picross.dataclasses;

import android.content.Context;
import android.widget.GridLayout;

import com.example.picross.enums.TileState;

import java.util.ArrayList;
import java.util.Random;

public class Grid extends GridLayout {
    private TileState[][] answerGrid;
    private Tile[][] userGrid;

    private int numOfTiles;



    private ArrayList<ArrayList<Integer>> rowHint;
    private ArrayList<ArrayList<Integer>> colHint;

    private Context context;

    private int tileLength;


    public Grid (Context context, int numOfTiles, int gridLen){
        super(context);

        this.numOfTiles = numOfTiles;

        this.setColumnCount(numOfTiles);
        this.setRowCount(numOfTiles);

        this.context = context;

        tileLength = gridLen / numOfTiles;
        userGrid = new Tile[numOfTiles][numOfTiles];
        answerGrid = new TileState[numOfTiles][numOfTiles];
        Random random = new Random();

        for (int y = 0; y < numOfTiles; y++) {
            for (int x = 0; x < numOfTiles; x++) {


                //construct answer grid
                TileState temp = TileState.CROSSED;
                int i = random.nextInt(2);
                if (i == 0) {
                    temp = TileState.FILLED;
                }
                answerGrid[y][x] = temp;

                //construct user grid

                    Tile tile = new Tile(context, tileLength);
                    this.addView(tile);
                    //Tile tile = new Tile(MainActivity.this);
                    userGrid[y][x] = tile;

            }

        }
        defineHints(numOfTiles);
    }

    public ArrayList<ArrayList<Integer>> getRowHint() {
        return rowHint;
    }

    public ArrayList<ArrayList<Integer>> getColHint() {
        return colHint;
    }
    public int getTileLength() {
        return tileLength;
    }

    //finish this
    public boolean checkAnswer() {
        for (int y = 0; y < numOfTiles; y++) {
            for (int x = 0; x < numOfTiles; x++) {
                TileState userTileState = userGrid[y][x].getTileState();
                TileState answerTileState = answerGrid[y][x];
                if(answerTileState == TileState.FILLED && (userTileState==TileState.CROSSED|| userTileState==TileState.EMPTY)){
                    return false;
                }
                else if(answerTileState == TileState.CROSSED && userTileState ==TileState.FILLED){
                    return false;
                }
            }
        }
        return true;
    }

    public void showAnswer(){
        for (int y = 0; y < numOfTiles; y++) {
            for (int x = 0; x < numOfTiles; x++) {
                TileState answerTileState = answerGrid[y][x];
                Tile userTile = userGrid[y][x];
                if(answerTileState == TileState.FILLED){
                    userTile.toFilled();
                }
                else{
                    userTile.toCrossed();
                }
            }
        }
    }

    public void resetGrid(){
        for(Tile[]a:userGrid){
            for (Tile b:a){
                b.toEmpty();
            }
        }

    }



    private void defineHints(int numOfTiles){
        rowHint = new ArrayList<>(numOfTiles);
        colHint = new ArrayList<>(numOfTiles);
        for (int a = 0; a<numOfTiles; a++){
            rowHint.add(a, new ArrayList<Integer>());
            colHint.add(a, new ArrayList<Integer>());
            int horizTemp = 0;
            int vertTemp = 0;
            for (int b = 0; b<numOfTiles; b++){

                if(answerGrid[a][b] == TileState.FILLED){
                    horizTemp++;
                    if (b==numOfTiles - 1) {

                        rowHint.get(a).add(horizTemp);
                    }
                }

                else if (horizTemp != 0){
                    rowHint.get(a).add(horizTemp);
                    horizTemp = 0;
                }

                if(answerGrid[b][a] == TileState.FILLED){
                    vertTemp++;
                    if (b==numOfTiles - 1) {
                        colHint.get(a).add(vertTemp);
                    }
                }

                else if (vertTemp != 0){
                    colHint.get(a).add(vertTemp);
                    vertTemp = 0;
                }
            }
        }


        System.out.println(rowHint);
        System.out.println(colHint);
        printAnswer(answerGrid);

    }




    private void printAnswer(TileState[][] array){
        for(int y =0; y <array.length; y++){

            for(int x = 0; x < array[0].length; x++){
                if(array[y][x] == TileState.FILLED)
                    System.out.print("O");
                else
                    System.out.print("X");

            }
            System.out.println();
        }
    }


}
