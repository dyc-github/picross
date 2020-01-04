package com.example.picross.dataclasses;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.picross.R;
import com.example.picross.enums.TileState;
import com.example.picross.utilities.Util;


public class Tile extends RelativeLayout {

    private TileState tileState;
    private ConstraintLayout tile;
    private ImageView fill;
    private ImageView cross;





//    public Tile(Context context){

    public Tile(Context context, int tileLength){
        super(context);
        tileState = TileState.EMPTY;
        inflate(context, R.layout.tile, this);
        tile = findViewById(R.id.tile);
        fill = findViewById(R.id.fill);
        cross = findViewById(R.id.cross);
        adjustTileSize(context, tileLength);


        if(!(tileState == TileState.HINTCOL || tileState==TileState.HINTROW)) {
            tile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rotateTileState();
                }

            });
        }

    }

    public Tile(Context context, int tileLength, TileState tileState){
        super(context);
        this.tileState = tileState;
        inflate(context, R.layout.tile, this);
        tile = findViewById(R.id.tile);
        if(tileState==TileState.HINTCOL){
            tile.setLayoutParams(new RelativeLayout.LayoutParams((int)Util.pxFromDp(context, tileLength), (int)Util.pxFromDp(context, 90)));
        }
        if(tileState==TileState.HINTROW){
            tile.setLayoutParams(new RelativeLayout.LayoutParams( (int)Util.pxFromDp(context, 90), (int)Util.pxFromDp(context, tileLength)));
        }

        if(tileState == TileState.HINTCOL ||tileState==TileState.HINTROW){
            findViewById(R.id.text).setVisibility(VISIBLE);
        }


    }

    private void adjustTileSize(Context context, int tileLength){
        int tileLengthDp  = (int)Util.pxFromDp(context, tileLength);
        ViewGroup.LayoutParams tileParams = tile.getLayoutParams();
        tileParams.height= tileLengthDp;
        tileParams.width= tileLengthDp;
        tile.setLayoutParams(tileParams);
    }


    // Finish coding rotateTileState method
    private void rotateTileState(){

        switch (tileState){
            case EMPTY:
                toFilled();
                break;
            case FILLED:
                toCrossed();
                break;
            case CROSSED:
                toEmpty();
                break;
        }
    }

    public TileState getTileState() {
        return tileState;
    }

    public void toEmpty(){
        fill.setVisibility(INVISIBLE);
        cross.setVisibility(INVISIBLE);
        tileState = TileState.EMPTY;
    }public void toFilled(){
        fill.setVisibility(VISIBLE);
        cross.setVisibility(INVISIBLE);
        tileState = TileState.FILLED;
    }public void toCrossed(){
        fill.setVisibility(INVISIBLE);
        cross.setVisibility(VISIBLE);
        tileState = TileState.CROSSED;
    }









}
