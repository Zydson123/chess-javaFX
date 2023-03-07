package com.example.chessjavafx;

import com.rits.cloning.Cloner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ChessJavaFX extends Application {
    private boolean didTell=false;
    private boolean turn=true;
    private boolean isPieceSelected=false;
    private boolean isMoveSelected=false;
    private boolean playable = true;
    private String winner;
    private Pane root = new Pane();
    private int y = 8;
    private int x = 8;
    private Piece board[][] = new Piece[8][8];
    private GridPane tiles[][] = new GridPane[8][8];
    private ArrayList<String> tilesToColor = new ArrayList<String>();
    private ArrayList<String> legalMoves = new ArrayList<String>();
    int XofPiece=0, YofPiece=0;
    Piece piece = board[0][0];
    boolean isKingInCheck = false;

    public void updateBoard(){
        root.getChildren().clear();
        for(int i=0; i<8; i++){
            for(int j=0; j<8;j++){
                GridPane tile = new GridPane(j,i);
                tile.setTranslateX(j * 75);
                tile.setTranslateY(i * 75);
                root.getChildren().add(tile);
                tiles[i][j] = tile;
                give_image(tiles,board,i,j);
                color_tiles(tiles,i,j);
            }
        }
    }
    public void color_tile(GridPane tiles[][],int i,int j){
        tiles[i][j].setBackground((new Background(new BackgroundFill(Color.RED, new CornerRadii(0), Insets.EMPTY))));
    }
    public void calculate_legal_moves(Piece piece, Piece[][] board){
        if(piece.getColor()==turn){
            if(piece.getType()=='P' && piece.getColor()==true){
                //takes left
                if(piece.getPosY()-1 >= 0){
                    if(board[0][0].check_legal(piece,board,piece.getPosY()-1,piece.getPosX()-1, turn)){
                        int x = piece.getPosY()-1;
                        int y = piece.getPosX()-1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //takes right
                if(piece.getPosY()+1 < 8){
                    if(board[0][0].check_legal(piece,board, piece.getPosY()+1,piece.getPosX()-1, turn)){
                        int x = piece.getPosY()+1;
                        int y = piece.getPosX()-1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //goes forward 2 squares
                if(!piece.isDidMove()){
                    int x = piece.getPosY();
                    int y = piece.getPosX()-1;
                    if(board[0][0].check_legal(piece, board, x,y, turn)) {
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                        y = piece.getPosX()-2;
                        if(board[0][0].check_legal(piece, board, x,y, turn)){
                            tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                        }
                    }
                }
                //goes forward 1 square
                else{
                    int x = piece.getPosY();
                    int y = piece.getPosX()-1;
                    if(board[0][0].check_legal(piece, board, x,y, turn)) {
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
            }
            else if(piece.getType()=='P' && piece.getColor()==false){
                //if takes left
                if(piece.getPosY()-1 > 0 && piece.getPosX()+1 < 8){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()-1,piece.getPosX()+1, turn)){
                        int x = piece.getPosY()-1;
                        int y = piece.getPosX()+1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //takes right
                if(piece.getPosY()+1 < 8 && piece.getPosX()+1 < 8){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()+1,piece.getPosX()+1, turn)){
                        int x = piece.getPosY()+1;
                        int y = piece.getPosX()+1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                if(!piece.isDidMove()){
                    int x = piece.getPosY();
                    int y = piece.getPosX()+1;
                    if(board[0][0].check_legal(piece, board, x,y, turn)) {
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                        y = piece.getPosX()+2;
                        if(board[0][0].check_legal(piece, board, x,y, turn)){
                            tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                        }
                    }
                }
                else{
                    int x = piece.getPosY();
                    int y = piece.getPosX()+1;
                    if(board[0][0].check_legal(piece, board, x,y, turn)) {
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
            }
            if(piece.getType()=='N'){
                //right up
                if(piece.getPosY()+1 < 8 && piece.getPosX()-2 >= 0){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()+1,piece.getPosX()-2, turn)){
                        int x = piece.getPosY()+1;
                        int y = piece.getPosX()-2;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //left up
                if(piece.getPosY()-1 >= 0 && piece.getPosX()-2 >= 0){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()-1,piece.getPosX()-2, turn)){
                        int x = piece.getPosY()-1;
                        int y = piece.getPosX()-2;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //right sideways up
                if(piece.getPosY()+2 < 8 && piece.getPosX()-1 >= 0){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()+2,piece.getPosX()-1, turn)){
                        int x = piece.getPosY()+2;
                        int y = piece.getPosX()-1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //left sideways up
                if(piece.getPosY()-2 >= 0 && piece.getPosX()-1 >= 0){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()-2,piece.getPosX()-1, turn)){
                        int x = piece.getPosY()-2;
                        int y = piece.getPosX()-1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //right down
                if(piece.getPosY()+1 < 8 && piece.getPosX()+2 < 8){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()+1,piece.getPosX()+2, turn)){
                        int x = piece.getPosY()+1;
                        int y = piece.getPosX()+2;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //left down
                if(piece.getPosY()-1 >= 0 && piece.getPosX()+2 < 8){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()-1,piece.getPosX()+2, turn)){
                        int x = piece.getPosY()-1;
                        int y = piece.getPosX()+2;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //right sideways down
                if(piece.getPosY()+2 < 8 && piece.getPosX()+1 < 8){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()+2,piece.getPosX()+1, turn)){
                        int x = piece.getPosY()+2;
                        int y = piece.getPosX()+1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
                //left sideways down
                if(piece.getPosY()-2 >= 0 && piece.getPosX()+1 < 8){
                    if(board[0][0].check_legal(piece, board, piece.getPosY()-2,piece.getPosX()+1, turn)){
                        int x = piece.getPosY()-2;
                        int y = piece.getPosX()+1;
                        tilesToColor.add(Integer.toString(y) + Integer.toString(x));
                    }
                }
            }
            if(piece.getType()=='B'){
                int i = 1;
                int j = 1;
                while(j!=7 && i!=7){
                    int kot = piece.getPosY()-j;
                    int pies = piece.getPosX()-i;
                    if(pies >= 0 && kot >= 0){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i = 1;
                j = 1;
                while(j!=7 && i!=7){
                    int kot = piece.getPosY()+j;
                    int pies = piece.getPosX()-i;
                    if((pies >= 0) && kot < 8){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i = 1;
                j = 1;
                while(j!=7 && i!=7){
                    int kot = piece.getPosY()+j;
                    int pies = piece.getPosX()+i;
                    if(pies < 8 && kot < 8){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i = 1;
                j = 1;
                while(j!=7 && i!=7){
                    //X
                    int kot = piece.getPosY()-j;
                    //Y
                    int pies = piece.getPosX()+i;
                    if((pies < 8) && (kot >= 0)){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
            }
            if(piece.getType()=='R'){
                int i,j;
                i=1;
                //right
                while(i!=8){
                    int pies = piece.getPosY()+i;
                    if(pies < 8){
                        if(!board[piece.getPosX()][pies].isUsed() && board[0][0].check_legal(piece, board, pies, piece.getPosX(), turn)){
                            tilesToColor.add(Integer.toString(piece.getPosX()) + Integer.toString(pies));
                        }
                    }
                    i++;
                }
                i=1;
                //left
                while(i!=8){
                    int pies = piece.getPosY()-i;
                    if(pies >= 0){
                        if(!board[piece.getPosX()][pies].isUsed() && board[0][0].check_legal(piece, board, pies, piece.getPosX(), turn)){
                            tilesToColor.add(Integer.toString(piece.getPosX()) + Integer.toString(pies));
                        }
                    }
                    i++;
                }
                j=1;
                //up
                while(j!=8){
                    int kot = piece.getPosX()-j;
                    if(kot >= 0){
                        if(!board[kot][piece.getPosY()].isUsed() && board[0][0].check_legal(piece, board, piece.getPosY(), kot, turn)){
                            tilesToColor.add(Integer.toString(kot) + Integer.toString(piece.getPosY()));
                        }
                    }
                    j++;
                }
                j=1;
                //down
                while(j!=8){
                    int kot = piece.getPosX()+j;
                    if(kot < 8){
                        if(!board[kot][piece.getPosY()].isUsed() && board[0][0].check_legal(piece, board, piece.getPosY(), kot, turn)){
                            tilesToColor.add(Integer.toString(kot) + Integer.toString(piece.getPosY()));
                        }
                    }
                    j++;
                }
            }
            if(piece.getType()=='Q'){
                int i = 1;
                int j = 1;
                while(j!=7 && i!=7){
                    int kot = piece.getPosY()-j;
                    int pies = piece.getPosX()-i;
                    if(pies >= 0 && kot >= 0){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i = 1;
                j = 1;
                while(j!=7 && i!=7){
                    int kot = piece.getPosY()+j;
                    int pies = piece.getPosX()-i;
                    if((pies >= 0) && kot < 8){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i = 1;
                j = 1;
                while(j!=7 && i!=7){
                    int kot = piece.getPosY()+j;
                    int pies = piece.getPosX()+i;
                    if(pies < 8 && kot < 8){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i = 1;
                j = 1;
                while(j!=7 && i!=7){
                    //X
                    int kot = piece.getPosY()-j;
                    //Y
                    int pies = piece.getPosX()+i;
                    if((pies < 8) && (kot >= 0)){
                        if(!board[pies][kot].isUsed() && board[0][0].check_legal(piece, board,kot,pies, turn))
                        {
                            tilesToColor.add(Integer.toString(pies) + Integer.toString(kot));
                        }
                    }
                    j++;
                    i++;
                }
                i=1;
                //right
                while(i!=8){
                    int pies = piece.getPosY()+i;
                    if(pies < 8){
                        if(!board[piece.getPosX()][pies].isUsed() && board[0][0].check_legal(piece, board, pies, piece.getPosX(), turn)){
                            tilesToColor.add(Integer.toString(piece.getPosX()) + Integer.toString(pies));
                        }
                    }
                    i++;
                }
                i=1;
                //left
                while(i!=8){
                    int pies = piece.getPosY()-i;
                    if(pies >= 0){
                        if(!board[piece.getPosX()][pies].isUsed() && board[0][0].check_legal(piece, board, pies, piece.getPosX(), turn)){
                            tilesToColor.add(Integer.toString(piece.getPosX()) + Integer.toString(pies));
                        }
                    }
                    i++;
                }
                j=1;
                //up
                while(j!=8){
                    int kot = piece.getPosX()-j;
                    if(kot >= 0){
                        if(!board[kot][piece.getPosY()].isUsed() && board[0][0].check_legal(piece, board, piece.getPosY(), kot, turn)){
                            tilesToColor.add(Integer.toString(kot) + Integer.toString(piece.getPosY()));
                        }
                    }
                    j++;
                }
                j=1;
                //down
                while(j!=8){
                    int kot = piece.getPosX()+j;
                    if(kot < 8){
                        if(!board[kot][piece.getPosY()].isUsed() && board[0][0].check_legal(piece, board, piece.getPosY(), kot, turn)){
                            tilesToColor.add(Integer.toString(kot) + Integer.toString(piece.getPosY()));
                        }
                    }
                    j++;
                }
            }
            if(piece.getType()=='K'){
                int pies = piece.getPosY()+1;
                int kot = piece.getPosX()+1;
                if(pies < 8 && kot < 8){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()+1;
                kot = piece.getPosX()-1;
                if(pies < 8 && kot >= 0){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()-1;
                kot = piece.getPosX()-1;
                if(pies >= 0 && kot >= 0){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()-1;
                kot = piece.getPosX()+1;
                if(pies >= 0 && kot < 8){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()-1;
                kot = piece.getPosX();
                if(pies >= 0){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()+1;
                kot = piece.getPosX();
                if(pies < 8){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()+2;
                kot = piece.getPosX();
                if(pies < 8){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY()-2;
                kot = piece.getPosX();
                if(pies >= 0){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY();
                kot = piece.getPosX()-1;
                if(kot >= 0){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
                pies = piece.getPosY();
                kot = piece.getPosX()+1;
                if(kot < 8){
                    if(board[0][0].check_legal(piece,board,pies,kot, turn)){
                        tilesToColor.add(Integer.toString(kot) + Integer.toString(pies));
                    }
                }
            }
        }
    }
    public void color_legal_moves(){
        for (int k=0; k < tilesToColor.size(); k++){
            char y = tilesToColor.get(k).charAt(0);
            char x = tilesToColor.get(k).charAt(1);
            String y2 = String.valueOf(y);
            String x2 = String.valueOf(x);
            int y3 = Integer.parseInt(y2);
            int x3 = Integer.parseInt(x2);
            //Piece.legalMoves = tilesToColor;
            color_tile(tiles,y3,x3);
        }
        tilesToColor.clear();
    }
    public static void color_tiles(GridPane tiles[][],int i,int j){
        int kot = j+1;
        int pies = i+1;
        if(!((kot%2!=0 && pies%2!=0) || (kot%2==0 && pies%2==0))){
            tiles[i][j].setBackground((new Background(new BackgroundFill(Color.GREEN, new CornerRadii(0), Insets.EMPTY))));
        }
        else{
            tiles[i][j].setBackground((new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY))));
        }
    }
    public static void fill_board(Piece board[][]){
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                board[i][j] = new Piece('l',false,i,j,true);
            }
        }
    }
    public static void add_pieces(Piece board[][]){
        //white pieces
        board[7][7] = new Piece('R',true,7,7,false);
        board[7][0] = new Piece('R',true,7,0,false);
        board[7][3] = new Piece('Q',true,7,3,false);
        board[7][4] = new Piece('K',true,7,4,false);
        board[7][1] = new Piece('N',true,7,1,false);
        board[7][6] = new Piece('N',true,7,6,false);
        board[7][2] = new Piece('B',true,7,2,false);
        board[7][5] = new Piece('B',true,7,5,false);
        //black pieces
        board[0][0] = new Piece('R',false,0,0,false);
        board[0][7] = new Piece('R',false,0,7,false);
        board[0][3] = new Piece('Q',false,0,3,false);
        board[0][4] = new Piece('K',false,0,4,false);
        board[0][1] = new Piece('N',false,0,1,false);
        board[0][6] = new Piece('N',false,0,6,false);
        board[0][2] = new Piece('B',false,0,2,false);
        board[0][5] = new Piece('B',false,0,5,false);

        for(int i = 0; i< board.length; i++){
            //black pawns
            board[1][i] = new Piece('P',false,1,i,false);
            //white pawns
            board[6][i] = new Piece('P',true,6,i,false);
        }

    }
    public void give_image(GridPane tiles2[][], Piece board[][], int i, int j) {
        File file = new File("pieces\\WPawn.png");
        Image image = new Image(file.getAbsolutePath());
        ImageView WPawn = new ImageView(image);
        file = new File("pieces\\BPawn.png");
        image = new Image(file.getAbsolutePath());
        ImageView BPawn = new ImageView(image);
        file = new File("pieces\\WRook.png");
        image = new Image(file.getAbsolutePath());
        ImageView WRook = new ImageView(image);
        file = new File("pieces\\BRook.png");
        image = new Image(file.getAbsolutePath());
        ImageView BRook = new ImageView(image);
        file = new File("pieces\\WBishop.png");
        image = new Image(file.getAbsolutePath());
        ImageView WBishop = new ImageView(image);
        file = new File("pieces\\BBishop.png");
        image = new Image(file.getAbsolutePath());
        ImageView BBishop = new ImageView(image);
        file = new File("pieces\\WKnight.png");
        image = new Image(file.getAbsolutePath());
        ImageView WKnight = new ImageView(image);
        file = new File("pieces\\BKnight.png");
        image = new Image(file.getAbsolutePath());
        ImageView BKnight = new ImageView(image);
        file = new File("pieces\\WQueen.png");
        image = new Image(file.getAbsolutePath());
        ImageView WQueen = new ImageView(image);
        file = new File("pieces\\BQueen.png");
        image = new Image(file.getAbsolutePath());
        ImageView BQueen = new ImageView(image);
        file = new File("pieces\\BKing.png");
        image = new Image(file.getAbsolutePath());
        ImageView BKing = new ImageView(image);
        file = new File("pieces\\WKing.png");
        image = new Image(file.getAbsolutePath());
        ImageView WKing = new ImageView(image);
        if(board[i][j].getType()=='P' && board[i][j].getColor()){
            tiles2[i][j].getChildren().add(WPawn);
        }
        else if(board[i][j].getType()=='P' && !board[i][j].getColor()){
            tiles2[i][j].getChildren().add(BPawn);
        }
        else if(board[i][j].getType()=='R' && board[i][j].getColor()){
            tiles2[i][j].getChildren().add(WRook);
        }
        else if(board[i][j].getType()=='R' && !board[i][j].getColor()){
            tiles2[i][j].getChildren().add(BRook);
        }
        else if(board[i][j].getType()=='Q' && board[i][j].getColor()){
            tiles2[i][j].getChildren().add(WQueen);
        }
        else if(board[i][j].getType()=='Q' && !board[i][j].getColor()){
            tiles2[i][j].getChildren().add(BQueen);
        }
        else if(board[i][j].getType()=='B' && board[i][j].getColor()){
            tiles2[i][j].getChildren().add(WBishop);
        }
        else if(board[i][j].getType()=='B' && !board[i][j].getColor()){
            tiles2[i][j].getChildren().add(BBishop);
        }
        else if(board[i][j].getType()=='N' && board[i][j].getColor()){
            tiles2[i][j].getChildren().add(WKnight);
        }
        else if(board[i][j].getType()=='N' && !board[i][j].getColor()){
            tiles2[i][j].getChildren().add(BKnight);
        }
        else if(board[i][j].getType()=='K' && board[i][j].getColor()){
            tiles2[i][j].getChildren().add(WKing);
        }
        else if(board[i][j].getType()=='K' && !board[i][j].getColor()){
            tiles2[i][j].getChildren().add(BKing);
        }
    }
    private Parent createContext() {
        root.setPrefSize(600, 600);
        updateBoard();
        return root;
    }
    @Override
    public void start(Stage stage){
        fill_board(board);
        add_pieces(board);
        stage.setScene(new Scene(createContext()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    private class GridPane extends StackPane {
        private int x;
        private int y;
        private Text text = new Text();
        public GridPane(int x, int y) {
            super();
            this.x = x;
            this.y = y;
            Rectangle border = new Rectangle(75, 75);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(10));

            getChildren().addAll(border, text);

            setOnMouseClicked(e -> {
                if(e.getButton()== MouseButton.PRIMARY && playable){
                    if(playable && !isPieceSelected && !isMoveSelected && board[this.getY()][this.getX()].getType()!='l' && board[this.getY()][this.getX()].getColor()==turn){
                        if(legalMoves.size()!=0){
                            legalMoves.clear();
                        }
                        for(int i=0; i < board.length; i++){
                            for(int j=0; j < board.length; j++){
                                Piece piece2 = board[i][j];
                                calculate_legal_moves(piece2, board);
                            }
                        }
                        Cloner cloner = new Cloner();
                        legalMoves = cloner.deepClone(tilesToColor);
                        tilesToColor.clear();
                        XofPiece = this.getX();
                        YofPiece = this.getY();
                        piece = board[YofPiece][XofPiece];
                        isPieceSelected=true;
                        calculate_legal_moves(piece,board);
                        color_legal_moves();
                        if(turn){
                            isKingInCheck = piece.isKingAttacked(board[0][0].findWhiteKing(board),board);
                            if(isKingInCheck && legalMoves.size()==0){
                                System.out.println("The game has ended! It's checkmate, black wins!");
                                playable = false;
                            }
                            else if(!isKingInCheck && legalMoves.size()==0){
                                System.out.println("The game has ended! It's stalemate, it's a draw!");
                                playable = false;
                            }
                            else if(isKingInCheck){
                                System.out.println("The white king is in check! Get him out of danger");
                            }
                        }
                        else{
                            isKingInCheck = piece.isKingAttacked(board[0][0].findBlackKing(board),board);
                            if(isKingInCheck && legalMoves.size()==0){
                                System.out.println("The game has ended! It's checkmate, white wins!");
                                playable = false;
                            }
                            else if(!isKingInCheck && legalMoves.size()==0){
                                System.out.println("The game has ended! It's stalemate, it's a draw!");
                                playable = false;
                            }
                            else if(isKingInCheck){
                                System.out.println("The black king is in check! Get him out of danger");
                            }
                        }
                    }
                    else if(playable && isPieceSelected && !isMoveSelected ){
                        int XofTarget = this.getX();
                        int YofTarget = this.getY();
                        if(board[0][0].check_legal(piece, board, XofTarget, YofTarget, turn)){
                            piece.move_piece(piece, XofTarget,YofTarget,turn,board, turn);
                            if(turn) turn = false;
                            else turn = true;
                        }
                        piece = null;
                        isPieceSelected = false;
                        isMoveSelected = false;
                        updateBoard();
                    }
                }
                else if(e.getButton() == MouseButton.SECONDARY){
                    isPieceSelected = false;
                    isMoveSelected = false;
                    for(int i=0; i<8; i++){
                        for(int j=0; j<8;j++){
                            give_image(tiles,board,i,j);
                            color_tiles(tiles,i,j);
                        }
                    }
                }
                else if(!playable && !didTell){
                    System.out.println("The game has ended! " + winner + " won");
                    didTell=true;
                }
            });
        }
        private void draw(String what){
            text.setText(what);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
