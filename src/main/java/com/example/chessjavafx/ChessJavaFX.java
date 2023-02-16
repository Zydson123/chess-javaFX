package com.example.chessjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ChessJavaFX extends Application {
    private boolean isPieceSelected=false;
    private boolean isMoveSelected=false;
    private Pane root = new Pane();
    private int y = 8;
    private int x = 8;
    private Piece board[][] = new Piece[y][x];
    private Button buttons[][] = new Button[y][x];

    public static void color_tiles(Button tiles[][], int i, int j){
        int kot = j+1;
        int pies = i+1;
        if(!((kot%2!=0 && pies%2!=0) || (kot%2==0 && pies%2==0))){
            tiles[i][j].setBackground((new Background(new BackgroundFill(Color.GREEN, new CornerRadii(0), Insets.EMPTY))));
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
    public static void print_board(Button buttons[][], Piece board[][]){
        for (int i = 0; i < buttons.length; i++)
        {
            for (int j=0; j < buttons.length; j++){
                buttons[i][j].draw(board[i][j].print_piece());
            }
        }
    }
    private Parent createContext() {
        root.setPrefSize(600, 600);
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                Button btn = new Button(j,i);
                btn.setTranslateX(j * 75);
                btn.setTranslateY(i * 75);
                root.getChildren().add(btn);
                buttons[i][j] = btn;
                color_tiles(buttons,i,j);
            }
        }
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
        fill_board(board);
        add_pieces(board);
        stage.setScene(new Scene(createContext()));
        print_board(buttons,board);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    private class Button extends StackPane {
        private int x;
        private int y;
        private Text text = new Text();
        public Button(int x, int y) {
            super();
            this.x = x;
            this.y = y;
            Rectangle border = new Rectangle(75, 75);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            text.setFont(Font.font(10));

            getChildren().addAll(border, text);

            setOnMouseClicked(e -> {
                if(e.getButton()== MouseButton.PRIMARY){
                    if(isPieceSelected){

                    }
                    else if(!isPieceSelected && !isMoveSelected){
                        Piece selectedPiece = board[this.getY()][this.getX()];
                        this.setBackground((new Background(new BackgroundFill(Color.RED, new CornerRadii(0), Insets.EMPTY))));
                        System.out.println("X: " + this.getX());
                        System.out.println("Y: " + this.getY());
                        System.out.println(selectedPiece);
                        System.out.println("X of piece: " + selectedPiece.getPosY());
                        System.out.println("Y of piece: " + selectedPiece.getPosX());
                        isPieceSelected=true;
                    }
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
