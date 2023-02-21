package com.example.chessjavafx;

import java.util.ArrayList;

public class Piece {
    private char type;
    private boolean color;
    private int posX;
    private int posY;

    private int id;
    private boolean isFiller;
    private boolean didMove;
    public Piece lastTaken;
    private boolean isUsed;
    private ArrayList<String> legalMoves = new ArrayList<String>();
    private ArrayList<Piece> blockades = new ArrayList<Piece>();
    private ArrayList<Piece> blockades2 = new ArrayList<Piece>();
    private ArrayList<Piece> blockades3 = new ArrayList<Piece>();
    private ArrayList<Piece> blockades4 = new ArrayList<Piece>();
    private ArrayList<Piece> blockades5 = new ArrayList<Piece>();
    private ArrayList<Piece> blockades6 = new ArrayList<Piece>();
    private ArrayList<Piece> blockades7 = new ArrayList<Piece>();
    private ArrayList<Piece> blockades8 = new ArrayList<Piece>();
    ArrayList<Piece> seenLeftUpPieces = new ArrayList<Piece>();
    ArrayList<Piece> seenRightUpPieces = new ArrayList<Piece>();
    ArrayList<Piece> seenRightDownPieces = new ArrayList<Piece>();
    ArrayList<Piece> seenLeftDownPieces = new ArrayList<Piece>();
    ArrayList<Character> LegalPieces = new ArrayList<Character>();
    public Piece(char type, boolean color, int posX, int posY, boolean isFiller) {
        this.id++;
        this.type = type;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.isFiller = isFiller;
        this.didMove=false;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
    public void move_piece(Piece piece, int posXofMove,int posYofMove, boolean whoseTurn, Piece board[][]){
        if(LegalPieces.size()==0){
            LegalPieces.add('Q');
            LegalPieces.add('B');
            LegalPieces.add('N');
            LegalPieces.add('R');
        }
        if(piece.getColor()==whoseTurn) {
            if (board[posXofMove][posYofMove].check_legal(piece,board,posXofMove,posYofMove)) {
                board[piece.getPosX()][piece.getPosY()]= new Piece('l',false,piece.getPosX(),piece.getPosY(),true);
                if(piece.getType()=='P' && piece.getColor()==true && posYofMove==0){
                    char pieceType = 'Q';
                    if (LegalPieces.contains(pieceType)) {
                        piece.setDidMove(true);
                        piece.setPosX(posYofMove);
                        piece.setPosY(posXofMove);
                        board[piece.getPosX()][piece.getPosY()] = new Piece(pieceType, true, piece.getPosX(), piece.getPosY(), false);
                    }
                }
                else if(piece.getType()=='P' && piece.getColor()==false && posYofMove==7){
                    char pieceType = 'Q';
                    if (LegalPieces.contains(pieceType)) {
                        piece.setDidMove(true);
                        piece.setPosX(posYofMove);
                        piece.setPosY(posXofMove);
                        board[piece.getPosX()][piece.getPosY()] = new Piece(pieceType, false, piece.getPosX(), piece.getPosY(), false);
                    }
                }
                //short castle white
                else if(
                        posXofMove == 6
                                && posYofMove == 7
                                && piece.getColor()==true
                                && piece.isDidMove()==false
                                && board[7][7].getType()=='R'
                                && board[7][7].isDidMove()==false
                                && board[7][6].getType()=='l'
                                && board[7][5].getType()=='l'
                                && piece.getPosX()==posYofMove
                                && posXofMove-piece.getPosY()==2
                ){
                    Piece rook = board[7][7];
                    rook.setPosY(7);
                    rook.setPosX(5);
                    piece.setPosY(posXofMove);
                    piece.setPosX(posYofMove);
                    board[7][7]= new Piece('l',false,7,7,false); //filler in rook place
                    board[7][4]= new Piece('l',false,7,4,false); //filler in king place
                    board[7][6] = new Piece('K',true,7,6,false); //king in right castling place
                    board[7][5] = new Piece('R',true,7,5,false); //rook in right castling place
                }
                //long castle white
                else if(
                        posXofMove == 2
                                && posYofMove == 7
                                && piece.getColor()==true
                                && piece.isDidMove()==false
                                && board[7][0].getType()=='R'
                                && board[7][0].isDidMove()==false
                                && board[7][1].getType()=='l'
                                && board[7][2].getType()=='l'
                                && board[7][3].getType()=='l'
                                && piece.getPosX()==posYofMove
                                && piece.getPosY()-posXofMove==2
                ){
                    Piece rook = board[7][0];
                    rook.setPosY(7);
                    rook.setPosX(3);
                    piece.setPosY(posXofMove);
                    piece.setPosX(posYofMove);
                    board[7][0]= new Piece('l',false,7,0,false); //filler in rook place
                    board[7][4]= new Piece('l',false,7,4,false); //filler in king place
                    board[7][2] = new Piece('K',true,7,2,false); //king in right castling place
                    board[7][3] = new Piece('R',true,7,3,false); //rook in right castling place
                }
                //short castle black
                if(
                        posXofMove == 6
                                && posYofMove == 0
                                && piece.getColor()==false
                                && piece.isDidMove()==false
                                && board[0][7].getType()=='R'
                                && board[0][7].isDidMove()==false
                                && board[0][6].getType()=='l'
                                && board[0][5].getType()=='l'
                                && piece.getPosX()==posYofMove
                                && posXofMove-piece.getPosY()==2
                )
                {
                    Piece rook = board[0][7];
                    rook.setPosY(0);
                    rook.setPosX(5);
                    piece.setPosY(posXofMove);
                    piece.setPosX(posYofMove);
                    board[0][7] = new Piece('l',false,0,7,false); //filler in rook place
                    board[0][4] = new Piece('l',false,0,4,false); //filler in king place
                    board[0][6] = new Piece('K',false,0,6,false); //king in right castling place
                    board[0][5] = new Piece('R',false,0,5,false); //rook in right castling place
                }
                //long castle black
                if(
                        posXofMove == 2
                                && posYofMove == 0
                                && piece.getColor()==false
                                && piece.isDidMove()==false
                                && board[0][0].getType()=='R'
                                && board[7][0].isDidMove()==false
                                && board[0][1].getType()=='l'
                                && board[0][2].getType()=='l'
                                && board[0][3].getType()=='l'
                                && piece.getPosX()==posYofMove
                                && piece.getPosY()-posXofMove==2
                )
                {
                    Piece rook = board[0][0];
                    rook.setPosY(0);
                    rook.setPosX(3);
                    piece.setPosY(posXofMove);
                    piece.setPosX(posYofMove);
                    board[0][0]= new Piece('l',false,0,0,false); //filler in rook place
                    board[0][4]= new Piece('l',false,0,4,false); //filler in king place
                    board[0][2] = new Piece('K',false,0,2,false); //king in right castling place
                    board[0][3] = new Piece('R',false,0,3,false); //rook in right castling place
                }
                else{
                    piece.setDidMove(true);
                    piece.setPosX(posYofMove);
                    piece.setPosY(posXofMove);
                    board[piece.getPosX()][piece.getPosY()]=piece;
                }
            }
            else{
                System.out.println("You cant go to:" + posXofMove + "," + posYofMove + " with: " + piece.getType());

            }
        }
        else{
            System.out.println("Its not your turn!");
        }
    }
    public boolean check_legal(Piece piece, Piece board[][],int posXofMove,int posYofMove){
        boolean isLegal=false;
        piece.setUsed(true);
        this.blockades.clear();
        this.blockades2.clear();
        this.blockades3.clear();
        this.blockades4.clear();
        this.blockades5.clear();
        this.blockades6.clear();
        this.blockades7.clear();
        this.blockades8.clear();

        System.out.println("Pos X of piece: " + piece.getPosY());
        System.out.println("Pos Y of piece: " + piece.getPosX());
        System.out.println("pos X of move: " + posXofMove);
        System.out.println("pos Y of move: " + posYofMove);
        System.out.println("Type of target square: " + board[posYofMove][posXofMove]);

        if(piece.getType()=='P' && piece.getColor()==true){
            //go forward by two
            if(piece.getPosX()==6 && 6-posYofMove==2 && piece.getPosY()==posXofMove && board[posYofMove][posXofMove].getType()=='l'){
                isLegal = true;
            }
            //go forward by one
            else if(piece.getPosX()-posYofMove==1 && piece.getPosY()==posXofMove && board[posYofMove][posXofMove].getType()=='l'){
                isLegal = true;
            }
            //takes right
            else if(piece.getPosX()-posYofMove==1 && board[posYofMove][posXofMove].getType()!='l' && piece.getPosY()-posXofMove==-1 && posXofMove!=piece.getPosY() && board[posYofMove][posXofMove].getColor()!=piece.getColor()){
                isLegal = true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
            }
            //takes left
            else if(piece.getPosX()-posYofMove==1 && board[posYofMove][posXofMove].getType()!='l' && piece.getPosY()-posXofMove==1 && posXofMove!=piece.getPosY() && board[posYofMove][posXofMove].getColor()!=piece.getColor()){
                isLegal = true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
            }
            else{
                isLegal=false;
            }
        }
        else if(piece.getType()=='P' && piece.getColor()==false){
            //go forward by two
            if(piece.getPosX()==1 && piece.getPosX()+posYofMove==4 && piece.getPosY()==posXofMove && board[posYofMove][posXofMove].getType()=='l'){
                isLegal = true;
            }
            //go forward by one
            else if(posYofMove-piece.getPosX()==1 && piece.getPosY()==posXofMove && board[posYofMove][posXofMove].getType()=='l'){
                isLegal = true;
            }
            //takes
            else if(posYofMove-piece.getPosX()==1 && board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor()){
                isLegal = true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
            }
            else{
                isLegal=false;
            }
        }
        else if(piece.getType()=='N'){
            //if its filler and goes forward and is on the right side of the board
            if(board[posYofMove][posXofMove].getType()=='l' && piece.getPosX()-posYofMove==2 && piece.getPosY()-posXofMove==1){
                isLegal=true;
            }
            //if its filler and goes right
            else if(board[posYofMove][posXofMove].getType()=='l' && posXofMove-piece.getPosY()==2 && piece.getPosX()-posYofMove==1){
                isLegal=true;
            }
            //if its filler and goes left
            else if(board[posYofMove][posXofMove].getType()=='l' && posXofMove-piece.getPosY()==-2 && piece.getPosX()-posYofMove==1){
                isLegal=true;
            }
            //if its filler and goes right back
            else if(board[posYofMove][posXofMove].getType()=='l' && posXofMove-piece.getPosY()==2 && piece.getPosX()-posYofMove==-1){
                isLegal=true;
            }
            //if takes and goes right back
            else if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor() && posXofMove-piece.getPosY()==2 && piece.getPosX()-posYofMove==-1){
                isLegal=true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);
            }
            //if its filler and goes left back
            else if(board[posYofMove][posXofMove].getType()=='l' && posXofMove-piece.getPosY()==-2 && piece.getPosX()-posYofMove==-1){
                isLegal=true;
            }
            //if takes and goes left back
            else if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor() && posXofMove-piece.getPosY()==-2 && piece.getPosX()-posYofMove==-1){
                isLegal=true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
            }
            //if takes and goes left
            else if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor() && posXofMove-piece.getPosY()==-2 && piece.getPosX()-posYofMove==1){
                isLegal=true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
            }
            //if takes and goes right
            else if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor() && posXofMove-piece.getPosY()==2 && piece.getPosX()-posYofMove==1){
                isLegal=true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
            }
            //if its filler and goes forward and is on the left side of the board
            else if(board[posYofMove][posXofMove].getType()=='l' && piece.getPosX()-posYofMove==2 && posXofMove-piece.getPosY()==1){
                isLegal=true;
            }
            //if its filler and goes backwards and knight is on left side of the board
            else if(board[posYofMove][posXofMove].getType()=='l' && piece.getPosY()-posXofMove==1 && posYofMove-piece.getPosX()==2){
                isLegal=true;
            }
            //if its filler and goes backwards and knight is on left side of the board
            else if(board[posYofMove][posXofMove].getType()=='l' && posXofMove-piece.getPosY()==1 && posYofMove-piece.getPosX()==2){
                isLegal=true;
            }
            //if takes and forward and knight is on right side of board
            else if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor() && piece.getPosX()-posYofMove==2 && piece.getPosY()-posXofMove==1){
                isLegal=true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);
            }
            //if takes and forward and knight is on left side of board
            else if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor() && piece.getPosX()-posYofMove==2 && posXofMove-piece.getPosY()==1){
                isLegal=true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);
            }
            //if takes and backwards and knight is on right side of the board
            else if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor() && piece.getPosY()-posXofMove==1 && piece.getPosX()-posYofMove==2){
                isLegal=true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);
            }
            //if takes and backwards and knight is on left side of the board
            else if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor() && piece.getPosY()-posXofMove==1 && posYofMove-piece.getPosX()==2){
                isLegal=true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);
            }
        }
        else if(piece.getType()=='R'){
            int i = 1;
            while(i!=8){
                int pies = piece.getPosY()+i;
                if(pies < 8){
                    if(!board[piece.getPosX()][pies].isUsed() && board[piece.getPosX()][pies].getType()!='l'){
                        this.blockades5.add(board[piece.getPosX()][pies]);
                    }
                }
                i++;
            }
            int j=1;
            while(j!=8){
                int kot = piece.getPosX()-j;
                if(kot >= 0){
                    if(!board[kot][piece.getPosY()].isUsed() && board[kot][piece.getPosY()].getType()!='l'){
                        this.blockades6.add(board[kot][piece.getPosY()]);
                    }
                }
                j++;
            }
            j=1;
            while(j!=8){
                int kot = piece.getPosX()+j;
                if(kot < 8){
                    if(!board[kot][piece.getPosY()].isUsed() && board[kot][piece.getPosY()].getType()!='l'){
                        this.blockades7.add(board[kot][piece.getPosY()]);
                    }
                }
                j++;
            }
            i = 1;
            while(i!=8){
                int pies = piece.getPosY()-i;
                if(pies >= 0){
                    if(!board[piece.getPosX()][pies].isUsed() && board[piece.getPosX()][pies].getType()!='l'){
                        this.blockades8.add(board[piece.getPosX()][pies]);
                    }
                }
                i++;
            }
            i=0;
            j=0;
            Piece XblockadeFriendlyRight = board[0][0];
            Piece YblockadeFriendlyUp = board[0][0];
            Piece YblockadeFriendlyDown = board[0][0];
            Piece XblockadeFriendlyLeft = board[0][0];
            if(this.blockades5.size()==0){

            }
            else if(this.blockades5.size()==1){
                XblockadeFriendlyRight = this.blockades5.get(0);
            }
            else{
                XblockadeFriendlyRight = this.blockades5.get(0);
            }
            if(this.blockades6.size()==0){

            }
            else if(this.blockades6.size()==1){
                YblockadeFriendlyUp = this.blockades6.get(0);
            }
            else{
                YblockadeFriendlyUp = this.blockades6.get(0);
            }
            if(this.blockades7.size()==0){

            }
            else if(this.blockades7.size()==1){
                YblockadeFriendlyDown = this.blockades7.get(0);
            }
            else{
                YblockadeFriendlyDown = this.blockades7.get(0);
            }
            if(this.blockades8.size()==0){

            }
            else if(this.blockades8.size()==1){
                XblockadeFriendlyLeft = this.blockades8.get(0);
            }
            else{
                XblockadeFriendlyLeft = this.blockades8.get(0);
            }
            /*System.out.println("X of Y right blockade: " + XblockadeFriendlyRight.getPosY());
            System.out.println("Y of Y right blockade: " + XblockadeFriendlyRight.getPosX());
            System.out.println("X Right blockade: " + XblockadeFriendlyRight);
            System.out.println("X of Y left blockade: " + XblockadeFriendlyLeft.getPosY());
            System.out.println("Y of Y left blockade: " + XblockadeFriendlyLeft.getPosX());
            System.out.println("X Left blockade: " + XblockadeFriendlyLeft);
            System.out.println("Right blockades: " + this.blockades5);
            System.out.println("Left blockades: " + this.blockades8);*/
            //if goes up and there is blockade
            if(
                    posXofMove == piece.getPosY()
                            && posYofMove != piece.getPosX()
                            && piece.getPosX() - posYofMove > 0
                            && posYofMove - YblockadeFriendlyUp.getPosX() < 0
                            && YblockadeFriendlyUp.getPosX() < piece.getPosX()
                            && this.blockades6.size()!=0
            )
            {
                isLegal=false;
            }
            //if goes down but there is blockade
            else if(
                    posXofMove == piece.getPosY()
                            && posYofMove != piece.getPosX()
                            && piece.getPosX() - posYofMove < 0
                            && posYofMove - YblockadeFriendlyDown.getPosX() > 0
                            && YblockadeFriendlyDown.getPosX() > piece.getPosX()
                            && this.blockades7.size()!=0
            )
            {
                isLegal=false;
            }
            //if goes right and there is blockade
            else if(
                    posXofMove != piece.getPosY()
                            && posYofMove == piece.getPosX()
                            && posXofMove - piece.getPosY() > 0
                            && posXofMove - XblockadeFriendlyRight.getPosY() > 0
                            && piece.getPosY() - XblockadeFriendlyRight.getPosY() < 0
                            && this.blockades5.size()!=0
            )
            {
                isLegal=false;
            }
            //if goes left and there is blockade
            else if(
                    posXofMove != piece.getPosY()
                            && posYofMove == piece.getPosX()
                            && posXofMove - piece.getPosY() < 0
                            && posYofMove - XblockadeFriendlyLeft.getPosX() == 0
                            && XblockadeFriendlyLeft.getPosX() == piece.getPosX()
                            && posXofMove - XblockadeFriendlyLeft.getPosY() < 0
                            && this.blockades8.size()!=0
            )
            {
                isLegal=false;
            }
            //if goes sideways and is filler and no blockade
            else if(board[posYofMove][posXofMove].getType()=='l' && posXofMove != piece.getPosY() && posYofMove == piece.getPosX()){
                isLegal=true;
            }
            //if goes up/down and is filler and no blockade
            else if(board[posYofMove][posXofMove].getType()=='l' && posXofMove == piece.getPosY() && posYofMove != piece.getPosX()){
                isLegal=true;
            }
            //if goes sideways and takes and no blockade
            else if(board[posYofMove][posXofMove].getType()!='l'
                    && board[posYofMove][posXofMove].getColor()!=piece.getColor()
                    && posXofMove != piece.getPosY() && posYofMove == piece.getPosX())
            {
                isLegal=true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
            }
            //if goes up/down and takes and no blockade
            else if(
                    board[posYofMove][posXofMove].getType()!='l'
                            && board[posYofMove][posXofMove].getColor()!=piece.getColor()
                            && posXofMove == piece.getPosY()
                            && posYofMove != piece.getPosX())
            {
                isLegal=true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
            }
        }
        else if(piece.getType()=='B'){
            int i,j;
            Piece RightUpBlockade = null;
            Piece LeftUpBlockade = null;
            Piece RightDownBlockade = null;
            Piece LeftDownBlockade = null;
            if(this.seenLeftUpPieces.size()!=0){
                this.seenLeftUpPieces.clear();
            }
            if(this.seenRightDownPieces.size()!=0){
                this.seenRightDownPieces.clear();
            }
            if(this.seenRightUpPieces.size()!=0){
                this.seenRightUpPieces.clear();
            }
            if(this.seenLeftDownPieces.size()!=0){
                this.seenLeftDownPieces.clear();
            }
            i = 1;
            j = 1;
            while(j!=7 && i!=7){
                //X
                int kot = piece.getPosY()+j;
                //Y
                int pies = piece.getPosX()-i;
                if((pies >= 0) && kot < 8){
                    if(!board[pies][kot].isUsed() && (board[pies][kot].getColor()!=board[piece.getPosX()][piece.getPosY()].getColor() || board[pies][kot].getType()=='l'))
                    {
                        this.seenRightUpPieces.add(board[pies][kot]);
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
                int pies = piece.getPosX()-i;
                if(pies >= 0 && kot >= 0){
                    if(!board[pies][kot].isUsed() && (board[pies][kot].getColor()!=board[piece.getPosX()][piece.getPosY()].getColor() || board[pies][kot].getType()=='l'))
                    {
                        this.seenLeftUpPieces.add(board[pies][kot]);
                    }
                }
                j++;
                i++;
            }

            i = 1;
            j = 1;
            while(j!=7 && i!=7){
                //X
                int kot = piece.getPosY()+j;
                //Y
                int pies = piece.getPosX()+i;
                //Again, I have no idea why these are inverted when you put them in. Goofy ahh language
                if(pies < 8 && kot < 8){
                    if(!board[pies][kot].isUsed() && (board[pies][kot].getColor()!=board[piece.getPosX()][piece.getPosY()].getColor() || board[pies][kot].getType()=='l'))
                    {
                        this.seenRightDownPieces.add(board[pies][kot]);
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
                    if(!board[pies][kot].isUsed() && (board[pies][kot].getColor()!=board[piece.getPosX()][piece.getPosY()].getColor() || board[pies][kot].getType()=='l'))
                    {
                        this.seenLeftDownPieces.add(board[pies][kot]);
                    }
                }
                j++;
                i++;
            }
            i = 1;
            j = 1;
            while(j!=7 && i!=7){
                //X
                int kot = piece.getPosY()+j;
                //Y
                int pies = piece.getPosX()-i;
                if((pies >= 0) && (kot < 8)){
                    if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l')
                    {
                        this.blockades.add(board[pies][kot]);
                    }
                }
                j++;
                i++;

            }
            if(this.blockades.size()==0){

            }
            else{
                RightUpBlockade = this.blockades.get(0);
            }
            i = 1;
            j = 1;
            while(j!=7 && i!=7){
                //X
                int kot = piece.getPosY()-j;
                //Y
                int pies = piece.getPosX()-i;
                if(!(pies<0) && !(kot<0)){
                    if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l')
                    {
                        this.blockades2.add(board[pies][kot]);
                    }
                }
                j++;
                i++;
            }
            if(this.blockades2.size()==0){

            }
            else{
                LeftUpBlockade = this.blockades2.get(0);
            }
            i = 1;
            j = 1;
            while(j!=7 && i!=7){
                //X
                int kot = piece.getPosY()+j;
                //Y
                int pies = piece.getPosX()+i;
                //Again, I have no idea why these are inverted when you put them in. Goofy ahh language
                if(!(pies>=8) && !(kot>=8)){
                    if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l')
                    {
                        this.blockades3.add(board[pies][kot]);
                    }
                }
                j++;
                i++;

            }
            if(this.blockades3.size()==0){

            }
            else{
                RightDownBlockade = this.blockades3.get(0);
            }
            i = 1;
            j = 1;
            while(j!=7 && i!=7){
                //X
                int kot = piece.getPosY()-j;
                //Y
                int pies = piece.getPosX()+i;
                if(!(pies>=8) && !(kot<0)){
                    if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l')
                    {
                        this.blockades4.add(board[pies][kot]);
                    }
                }
                j++;
                i++;

            }
            if(this.blockades4.size()==0){

            }
            else{
                LeftDownBlockade = this.blockades4.get(0);
            }
            //if goes right up BUT there is blockade
            if(
                    this.blockades.size()!=0
                            && RightUpBlockade.getPosY()-posXofMove < 0
                            && RightUpBlockade.getPosX()-posYofMove > 0
                            && piece.getPosY()-posXofMove < 0
                            && piece.getPosX()-posYofMove > 0
            ){
                isLegal = false;
            }
            //if goes left up BUT there is blockade
            else if(
                    this.blockades2.size()!=0
                            && LeftUpBlockade.getPosY()-posXofMove > 0
                            && LeftUpBlockade.getPosX()-posYofMove > 0
                            && LeftUpBlockade.getPosY()-piece.getPosY()<0
                            && LeftUpBlockade.getPosX()-piece.getPosX()<0
                            && piece.getPosY()-posXofMove > 0
                            && piece.getPosX()-posYofMove > 0
            ){
                isLegal = false;
            }
            //if goes right down BUT there is blockade
            else if(
                    this.blockades3.size()!=0
                            && RightDownBlockade.getPosY()-posXofMove<0
                            && RightDownBlockade.getPosX()-posYofMove<0
                            && piece.getPosY()-posXofMove < 0
                            && piece.getPosX()-posYofMove < 0
            ){
                isLegal = false;
            }
            //if goes left down BUT there is blockade
            else if(
                    this.blockades4.size()!=0
                            && piece.getPosY()-posXofMove > 0
                            && piece.getPosX()-posYofMove < 0
                            && LeftDownBlockade.getPosY()-posXofMove > 0
                            && LeftDownBlockade.getPosX()-posYofMove < 0
            ){
                isLegal = false;
            }
            //if goes right up and no blockade bishop
            else if(
                    board[posYofMove][posXofMove].getType()=='l'
                            && piece.getPosY()-posXofMove < 0
                            && piece.getPosX()-posYofMove > 0
                            && this.seenRightUpPieces.contains(board[posYofMove][posXofMove])
            ){
                System.out.println("right up legal");
                isLegal = true;
            }
            //if goes left up and no blockade
            else if(
                    board[posYofMove][posXofMove].getType()=='l'
                            && (piece.getPosY()-posXofMove > 0)
                            && (piece.getPosX()-posYofMove > 0)
                            && this.seenLeftUpPieces.contains(board[posYofMove][posXofMove])
            ){
                System.out.println("left up legal");
                isLegal = true;
            }
            //if goes left down and no blockade
            else if(
                    board[posYofMove][posXofMove].getType()=='l'
                            && piece.getPosY()-posXofMove > 0
                            && piece.getPosX()-posYofMove < 0
                            && this.seenLeftDownPieces.contains(board[posYofMove][posXofMove])
            ){
                System.out.println("left down legal");
                isLegal = true;
            }
            //if goes right down and no blockade bishop
            else if(
                    board[posYofMove][posXofMove].getType()=='l'
                            && piece.getPosY()-posXofMove < 0
                            && piece.getPosX()-posYofMove < 0
                            && this.seenRightDownPieces.contains(board[posYofMove][posXofMove])
            ){
                System.out.println("right down legal");
                isLegal = true;
            }
            //if goes right up and no blockade and takes bishop
            else if(
                    board[posYofMove][posXofMove].getType()!='l'
                            && board[posYofMove][posXofMove].getColor()!=piece.getColor()
                            && (piece.getPosY()-posXofMove < 0)
                            && (piece.getPosX()-posYofMove > 0)
                            && this.seenRightUpPieces.contains(board[posYofMove][posXofMove])

            ){
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                isLegal = true;
            }
            //if goes left up and no blockade and takes
            else if(
                    board[posYofMove][posXofMove].getType()!='l'
                            && board[posYofMove][posXofMove].getColor()!=piece.getColor()
                            && (piece.getPosY()-posXofMove > 0 || piece.getPosY()-posXofMove == piece.getPosY())
                            && (piece.getPosX()-posYofMove > 0 || piece.getPosX()-posYofMove == piece.getPosX())
                            && this.seenLeftUpPieces.contains(board[posYofMove][posXofMove])
            ){
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                isLegal = true;
            }
            //if goes left down and no blockade and takes
            else if(
                    board[posYofMove][posXofMove].getType()!='l'
                            && board[posYofMove][posXofMove].getColor()!=piece.getColor()
                            && piece.getPosY()-posXofMove > 0
                            && piece.getPosX()-posYofMove < 0
                            && this.seenLeftDownPieces.contains(board[posYofMove][posXofMove])
            ){
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                isLegal = true;
            }
            //if goes right down and no blockade and takes
            else if(
                    board[posYofMove][posXofMove].getType()!='l'
                            && board[posYofMove][posXofMove].getColor()!=piece.getColor()
                            && piece.getPosY()-posXofMove < 0
                            && piece.getPosX()-posYofMove < 0
                            && !(posXofMove-piece.getPosY()==1 && posYofMove-piece.getPosX()==2)
                            && this.seenRightDownPieces.contains(board[posYofMove][posXofMove])
            ){
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                isLegal = true;
            }
        }
        else if(piece.getType()=='Q'){
            int i = 1;
            int j=0;

            while(i!=8){
                int pies = piece.getPosY()+i;
                if(pies < 8){
                    if(!board[piece.getPosX()][pies].isUsed() && board[piece.getPosX()][pies].getType()!='l'){
                        this.blockades5.add(board[piece.getPosX()][pies]);
                    }
                }
                i++;
            }
            j=1;
            while(j!=8){
                int kot = piece.getPosX()-j;
                if(kot >= 0){
                    if(!board[kot][piece.getPosY()].isUsed() && board[kot][piece.getPosY()].getType()!='l'){
                        this.blockades6.add(board[kot][piece.getPosY()]);
                    }
                }
                j++;
            }
            j=1;
            while(j!=8){
                int kot = piece.getPosX()+j;
                if(kot < 8){
                    if(!board[kot][piece.getPosY()].isUsed() && board[kot][piece.getPosY()].getType()!='l'){
                        this.blockades7.add(board[kot][piece.getPosY()]);
                    }
                }
                j++;
            }
            i = 1;
            while(i!=8){
                int pies = piece.getPosY()-i;
                if(pies >= 0){
                    if(!board[piece.getPosX()][pies].isUsed() && board[piece.getPosX()][pies].getType()!='l'){
                        this.blockades8.add(board[piece.getPosX()][pies]);
                    }
                }
                i++;
            }
            i=0;
            j=0;
            Piece XblockadeFriendlyRight = board[0][0];
            Piece YblockadeFriendlyUp = board[0][0];
            Piece YblockadeFriendlyDown = board[0][0];
            Piece XblockadeFriendlyLeft = board[0][0];
            if(this.blockades5.size()==0){

            }
            else if(this.blockades5.size()==1){
                XblockadeFriendlyRight = this.blockades5.get(0);
            }
            else{
                XblockadeFriendlyRight = this.blockades5.get(0);
            }
            if(this.blockades6.size()==0){

            }
            else if(this.blockades6.size()==1){
                YblockadeFriendlyUp = this.blockades6.get(0);
            }
            else{
                YblockadeFriendlyUp = this.blockades6.get(0);
            }
            if(this.blockades7.size()==0){

            }
            else if(this.blockades7.size()==1){
                YblockadeFriendlyDown = this.blockades7.get(0);
            }
            else{
                YblockadeFriendlyDown = this.blockades7.get(0);
            }
            if(this.blockades8.size()==0){

            }
            else if(this.blockades8.size()==1){
                XblockadeFriendlyLeft = this.blockades8.get(0);
            }
            else{
                XblockadeFriendlyLeft = this.blockades8.get(0);
            }
            Piece RightUpBlockade = null;
            Piece LeftUpBlockade = null;
            Piece RightDownBlockade = null;
            Piece LeftDownBlockade = null;
            if(this.seenLeftUpPieces.size()!=0){
                this.seenLeftUpPieces.clear();
            }
            if(this.seenRightDownPieces.size()!=0){
                this.seenRightDownPieces.clear();
            }
            if(this.seenRightUpPieces.size()!=0){
                this.seenRightUpPieces.clear();
            }
            if(this.seenLeftDownPieces.size()!=0){
                this.seenLeftDownPieces.clear();
            }
            i = 1;
            j = 1;
            while(j!=7 && i!=7){
                //X
                int kot = piece.getPosY()+j;
                //Y
                int pies = piece.getPosX()-i;
                if((pies >= 0) && kot < 8){
                    if(!board[pies][kot].isUsed() && (board[pies][kot].getColor()!=board[piece.getPosX()][piece.getPosY()].getColor() || board[pies][kot].getType()=='l'))
                    {
                        this.seenRightUpPieces.add(board[pies][kot]);
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
                int pies = piece.getPosX()-i;
                if(pies >= 0 && kot >= 0){
                    if(!board[pies][kot].isUsed() && (board[pies][kot].getColor()!=board[piece.getPosX()][piece.getPosY()].getColor() || board[pies][kot].getType()=='l'))
                    {
                        this.seenLeftUpPieces.add(board[pies][kot]);
                    }
                }
                j++;
                i++;
            }

            i = 1;
            j = 1;
            while(j!=7 && i!=7){
                //X
                int kot = piece.getPosY()+j;
                //Y
                int pies = piece.getPosX()+i;
                //Again, I have no idea why these are inverted when you put them in. Goofy ahh language
                if(pies < 8 && kot < 8){
                    if(!board[pies][kot].isUsed() && (board[pies][kot].getColor()!=board[piece.getPosX()][piece.getPosY()].getColor() || board[pies][kot].getType()=='l'))
                    {
                        this.seenRightDownPieces.add(board[pies][kot]);
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
                    if(!board[pies][kot].isUsed() && (board[pies][kot].getColor()!=board[piece.getPosX()][piece.getPosY()].getColor() || board[pies][kot].getType()=='l'))
                    {
                        this.seenLeftDownPieces.add(board[pies][kot]);
                    }
                }
                j++;
                i++;
            }
            i = 1;
            j = 1;
            while(j!=7 && i!=7){
                //X
                int kot = piece.getPosY()+j;
                //Y
                int pies = piece.getPosX()-i;
                if(pies >= 0 && kot < 8){
                    if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l')
                    {
                        this.blockades.add(board[pies][kot]);
                    }
                }
                j++;
                i++;

            }
            if(this.blockades.size()==0){

            }
            else{
                RightUpBlockade = this.blockades.get(0);
            }
            i = 1;
            j = 1;
            while(j!=7 && i!=7){
                //X
                int kot = piece.getPosY()-j;
                //Y
                int pies = piece.getPosX()-i;
                if(!(pies<0) && !(kot<0)){
                    if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l')
                    {
                        this.blockades2.add(board[pies][kot]);
                    }
                }
                j++;
                i++;
            }
            if(this.blockades2.size()==0){

            }
            else{
                LeftUpBlockade = this.blockades2.get(0);
            }
            i = 1;
            j = 1;
            while(j!=7 && i!=7){
                //X
                int kot = piece.getPosY()+j;
                //Y
                int pies = piece.getPosX()+i;
                //Again, I have no idea why these are inverted when you put them in. Goofy ahh language
                if(!(pies>=8) && !(kot>=8)){
                    if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l')
                    {
                        this.blockades3.add(board[pies][kot]);
                    }
                }
                j++;
                i++;

            }
            if(this.blockades3.size()==0){

            }
            else{
                RightDownBlockade = this.blockades3.get(0);
            }
            i = 1;
            j = 1;
            while(j!=7 && i!=7){
                //X
                int kot = piece.getPosY()-j;
                //Y
                int pies = piece.getPosX()+i;
                if(!(pies>=8) && !(kot<0)){
                    if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l')
                    {
                        this.blockades4.add(board[pies][kot]);
                    }
                }
                j++;
                i++;

            }
            if(this.blockades4.size()==0){

            }
            else{
                LeftDownBlockade = this.blockades4.get(0);
            }
            //if goes right up BUT there is blockade
            if(
                    this.blockades.size()!=0
                            && RightUpBlockade.getPosY()-posXofMove < 0
                            && RightUpBlockade.getPosX()-posYofMove > 0
                            && piece.getPosY()-posXofMove < 0
                            && piece.getPosX()-posYofMove > 0
            ){
                isLegal = false;
            }
            //if goes left up BUT there is blockade
            else if(
                    this.blockades2.size()!=0
                            && LeftUpBlockade.getPosY()-posXofMove > 0
                            && LeftUpBlockade.getPosX()-posYofMove > 0
                            && LeftUpBlockade.getPosY()-piece.getPosY()<0
                            && LeftUpBlockade.getPosX()-piece.getPosX()<0
                            && piece.getPosY()-posXofMove > 0
                            && piece.getPosX()-posYofMove > 0
            ){
                isLegal = false;
            }
            //if goes right down BUT there is blockade
            else if(
                    this.blockades3.size()!=0
                            && RightDownBlockade.getPosY()-posXofMove<0
                            && RightDownBlockade.getPosX()-posYofMove<0
                            && piece.getPosY()-posXofMove < 0
                            && piece.getPosX()-posYofMove < 0
            ){
                isLegal = false;
            }
            //if goes left down BUT there is blockade
            else if(
                    this.blockades4.size()!=0
                            && piece.getPosY()-posXofMove > 0
                            && piece.getPosX()-posYofMove < 0
                            && LeftDownBlockade.getPosY()-posXofMove > 0
                            && LeftDownBlockade.getPosX()-posYofMove < 0
            ){
                isLegal = false;
            }
            //if goes right up and no blockade bishop
            else if(
                    board[posYofMove][posXofMove].getType()=='l'
                            && piece.getPosY()-posXofMove < 0
                            && piece.getPosX()-posYofMove > 0
                            && this.seenRightUpPieces.contains(board[posYofMove][posXofMove])
            ){
                isLegal = true;
            }
            //if goes left up and no blockade
            else if(
                    board[posYofMove][posXofMove].getType()=='l'
                            && (piece.getPosY()-posXofMove > 0)
                            && (piece.getPosX()-posYofMove > 0)
                            && this.seenLeftUpPieces.contains(board[posYofMove][posXofMove])
            ){
                isLegal = true;
            }
            //if goes left down and no blockade
            else if(
                    board[posYofMove][posXofMove].getType()=='l'
                            && piece.getPosY()-posXofMove > 0
                            && piece.getPosX()-posYofMove < 0
                            && this.seenLeftDownPieces.contains(board[posYofMove][posXofMove])
            ){
                isLegal = true;
            }
            //if goes right down and no blockade bishop
            else if(
                    board[posYofMove][posXofMove].getType()=='l'
                            && piece.getPosY()-posXofMove < 0
                            && piece.getPosX()-posYofMove < 0
                            && this.seenRightDownPieces.contains(board[posYofMove][posXofMove])
            ){
                isLegal = true;
            }
            //if goes right up and no blockade and takes bishop
            else if(
                    board[posYofMove][posXofMove].getType()!='l'
                            && board[posYofMove][posXofMove].getColor()!=piece.getColor()
                            && (piece.getPosY()-posXofMove < 0)
                            && (piece.getPosX()-posYofMove > 0)
                            && this.seenRightUpPieces.contains(board[posYofMove][posXofMove])

            ){
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                isLegal = true;
            }
            //if goes left up and no blockade and takes
            else if(
                    board[posYofMove][posXofMove].getType()!='l'
                            && board[posYofMove][posXofMove].getColor()!=piece.getColor()
                            && (piece.getPosY()-posXofMove > 0 || piece.getPosY()-posXofMove == piece.getPosY())
                            && (piece.getPosX()-posYofMove > 0 || piece.getPosX()-posYofMove == piece.getPosX())
                            && this.seenLeftUpPieces.contains(board[posYofMove][posXofMove])
            ){
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                isLegal = true;
            }
            //if goes left down and no blockade and takes
            else if(
                    board[posYofMove][posXofMove].getType()!='l'
                            && board[posYofMove][posXofMove].getColor()!=piece.getColor()
                            && piece.getPosY()-posXofMove > 0
                            && piece.getPosX()-posYofMove < 0
                            && this.seenLeftDownPieces.contains(board[posYofMove][posXofMove])
            ){
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                isLegal = true;
            }
            //if goes right down and no blockade and takes
            else if(
                    board[posYofMove][posXofMove].getType()!='l'
                            && board[posYofMove][posXofMove].getColor()!=piece.getColor()
                            && piece.getPosY()-posXofMove < 0
                            && piece.getPosX()-posYofMove < 0
                            && !(posXofMove-piece.getPosY()==1 && posYofMove-piece.getPosX()==2)
                            && this.seenRightDownPieces.contains(board[posYofMove][posXofMove])
            ){
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                isLegal = true;
            }
            if(
                    posXofMove == piece.getPosY()
                            && posYofMove != piece.getPosX()
                            && piece.getPosX() - posYofMove > 0
                            && posYofMove - YblockadeFriendlyUp.getPosX() < 0
                            && YblockadeFriendlyUp.getPosX() < piece.getPosX()
                            && this.blockades6.size()!=0
            )
            {
                isLegal=false;
            }
            //if goes down but there is blockade
            else if(
                    posXofMove == piece.getPosY()
                            && posYofMove != piece.getPosX()
                            && piece.getPosX() - posYofMove < 0
                            && posYofMove - YblockadeFriendlyDown.getPosX() > 0
                            && YblockadeFriendlyDown.getPosX() > piece.getPosX()
                            && this.blockades7.size()!=0
            )
            {
                isLegal=false;
            }
            //if goes right and there is blockade
            else if(
                    posXofMove != piece.getPosY()
                            && posYofMove == piece.getPosX()
                            && posXofMove - piece.getPosY() > 0
                            && posXofMove - XblockadeFriendlyRight.getPosY() > 0
                            && piece.getPosY() - XblockadeFriendlyRight.getPosY() < 0
                            && this.blockades5.size()!=0
            )
            {
                isLegal=false;
            }
            //if goes left and there is blockade
            else if(
                    posXofMove != piece.getPosY()
                            && posYofMove == piece.getPosX()
                            && posXofMove - piece.getPosY() < 0
                            && posYofMove - XblockadeFriendlyLeft.getPosX() == 0
                            && XblockadeFriendlyLeft.getPosX() == piece.getPosX()
                            && posXofMove - XblockadeFriendlyLeft.getPosY() < 0
                            && this.blockades8.size()!=0
            )
            {
                isLegal=false;
            }
            //if goes sideways and is filler and no blockade
            else if(board[posYofMove][posXofMove].getType()=='l' && posXofMove != piece.getPosY() && posYofMove == piece.getPosX()){
                isLegal=true;
            }
            //if goes up/down and is filler and no blockade
            else if(board[posYofMove][posXofMove].getType()=='l' && posXofMove == piece.getPosY() && posYofMove != piece.getPosX()){
                isLegal=true;
            }
            //if goes sideways and takes and no blockade
            else if(board[posYofMove][posXofMove].getType()!='l'
                    && board[posYofMove][posXofMove].getColor()!=piece.getColor()
                    && posXofMove != piece.getPosY() && posYofMove == piece.getPosX())
            {
                isLegal=true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
            }
            //if goes up/down and takes and no blockade
            else if(
                    board[posYofMove][posXofMove].getType()!='l'
                            && board[posYofMove][posXofMove].getColor()!=piece.getColor()
                            && posXofMove == piece.getPosY()
                            && posYofMove != piece.getPosX())
            {
                isLegal=true;
                board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
            }
        }
        else if(piece.getType()=='K'){
            if(board[posYofMove][posXofMove].getColor()==piece.getColor() && board[posYofMove][posXofMove].getType()!='l'){
                isLegal=false;
            }
            else{
                //if goes up
                if (posXofMove-piece.getPosY() == 0 && piece.getPosX()-posYofMove == 1)
                {
                    if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor()){
                        isLegal = true;
                        board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                    }
                    else{
                        isLegal = true;
                    }
                }
                //if goes left up
                if (piece.getPosY()-posXofMove==1 && piece.getPosX()-posYofMove == 1)
                {
                    if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor()){
                        isLegal = true;
                        board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                    }
                    else{
                        isLegal = true;
                    }
                }
                //if goes right up
                if (piece.getPosY()-posXofMove==-1 && piece.getPosX()-posYofMove == 1)
                {
                    if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor()){
                        isLegal = true;
                        board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                    }
                    else{
                        isLegal = true;
                    }
                }
                //if goes right
                if (piece.getPosY()-posXofMove==-1)
                {
                    if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor()){
                        isLegal = true;
                        board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                    }
                    else{
                        isLegal = true;
                    }
                }
                //if goes left down
                if (piece.getPosY()-posXofMove == 1 && piece.getPosX()-posYofMove == -1)
                {
                    if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor()){
                        isLegal = true;
                        board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                    }
                    else{
                        isLegal = true;
                    }
                }
                //if goes left
                if (piece.getPosY()-posXofMove == 1)
                {
                    if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor()){
                        isLegal = true;
                        board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                    }
                    else{
                        isLegal = true;
                    }
                }
                //if goes right down
                if (piece.getPosY()-posXofMove == -1 && piece.getPosX()-posYofMove == -1)
                {
                    if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor()){
                        isLegal = true;
                        board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                    }
                    else{
                        isLegal = true;
                    }
                }
                //if goes down
                if (piece.getPosY()-posXofMove == 0 && piece.getPosX()-posYofMove == -1)
                {
                    if(board[posYofMove][posXofMove].getType()!='l' && board[posYofMove][posXofMove].getColor()!=piece.getColor()){
                        isLegal = true;
                        board[0][0].setLastTaken(board[posYofMove][posXofMove]);;
                    }
                    else{
                        isLegal = true;
                    }
                }
                //short castle, white
                if(
                        posXofMove == 6
                                && posYofMove == 7
                                && piece.getColor()==true
                                && piece.isDidMove()==false
                                && board[7][7].getType()=='R'
                                && board[7][7].isDidMove()==false
                                && board[7][6].getType()=='l'
                                && board[7][5].getType()=='l'
                                && piece.getPosX()==posYofMove
                                && posXofMove-piece.getPosY()==2
                )
                {
                    isLegal = true;
                }
                //long castle, white
                if(
                        posXofMove == 2
                                && posYofMove == 7
                                && piece.getColor()==true
                                && piece.isDidMove()==false
                                && board[7][0].getType()=='R'
                                && board[7][0].isDidMove()==false
                                && board[7][1].getType()=='l'
                                && board[7][2].getType()=='l'
                                && board[7][3].getType()=='l'
                                && piece.getPosX()==posYofMove
                                && piece.getPosY()-posXofMove==2
                )
                {
                    isLegal = true;
                }
                //short castle, black
                if(
                        posXofMove == 6
                                && posYofMove == 0
                                && piece.getColor()==false
                                && piece.isDidMove()==false
                                && board[0][7].getType()=='R'
                                && board[0][7].isDidMove()==false
                                && board[0][6].getType()=='l'
                                && board[0][5].getType()=='l'
                                && piece.getPosX()==posYofMove
                                && posXofMove-piece.getPosY()==2
                )
                {
                    isLegal = true;
                }
                //long castle, black
                if(
                        posXofMove == 2
                                && posYofMove == 0
                                && piece.getColor()==false
                                && piece.isDidMove()==false
                                && board[0][0].getType()=='R'
                                && board[7][0].isDidMove()==false
                                && board[0][1].getType()=='l'
                                && board[0][2].getType()=='l'
                                && board[0][3].getType()=='l'
                                && piece.getPosX()==posYofMove
                                && piece.getPosY()-posXofMove==2
                )
                {
                    isLegal = true;
                }
            }
            }
        piece.setUsed(false);
        return isLegal;
    }
    public boolean getColor() {
        return color;
    }
    public void setColor(boolean color) {
        this.color = color;
    }
    public int getPosX() {
        return posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public int getPosY() {
        return posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public boolean isFiller() {
        return isFiller;
    }
    public void setFiller(boolean filler) {
        isFiller = filler;
    }
    public boolean isDidMove() {
        return didMove;
    }
    public void setDidMove(boolean didMove) {
        this.didMove = didMove;
    }
    public Piece getLastTaken() {
        return lastTaken;
    }
    public void setLastTaken(Piece lastTaken) {
        this.lastTaken = lastTaken;
    }
    public boolean isUsed() {
        return isUsed;
    }
    public void setUsed(boolean used) {
        isUsed = used;
    }
    public boolean isKingAttacked(Piece piece, Piece board[][]){
        ArrayList<Piece> seenRightPieces = new ArrayList<Piece>();
        ArrayList<Piece> seenLeftPieces = new ArrayList<Piece>();
        ArrayList<Piece> seenUpPieces = new ArrayList<Piece>();
        ArrayList<Piece> seenDownPieces = new ArrayList<Piece>();
        this.blockades.clear();
        this.blockades2.clear();
        this.blockades3.clear();
        this.blockades4.clear();
        this.blockades5.clear();
        this.blockades6.clear();
        this.blockades7.clear();
        this.blockades8.clear();
        boolean papuga = false;
        int i = 1;
        int j=0;

        while(i!=8){
            int pies = piece.getPosY()+i;
            if(pies < 8){
                if(!board[piece.getPosX()][pies].isUsed() && board[piece.getPosX()][pies].getType()!='l'){
                    this.blockades5.add(board[piece.getPosX()][pies]);
                }
            }
            i++;
        }
        j=1;
        while(j!=8){
            int kot = piece.getPosX()-j;
            if(kot >= 0){
                if(!board[kot][piece.getPosY()].isUsed() && board[kot][piece.getPosY()].getType()!='l'){
                    this.blockades6.add(board[kot][piece.getPosY()]);
                }
            }
            j++;
        }
        j=1;
        while(j!=8){
            int kot = piece.getPosX()+j;
            if(kot < 8){
                if(!board[kot][piece.getPosY()].isUsed() && board[kot][piece.getPosY()].getType()!='l'){
                    this.blockades7.add(board[kot][piece.getPosY()]);
                }
            }
            j++;
        }
        i = 1;
        while(i!=8){
            int pies = piece.getPosY()-i;
            if(pies >= 0){
                if(!board[piece.getPosX()][pies].isUsed() && board[piece.getPosX()][pies].getType()!='l'){
                    this.blockades8.add(board[piece.getPosX()][pies]);
                }
            }
            i++;
        }
        Piece XblockadeFriendlyRight = board[0][0];
        Piece YblockadeFriendlyUp = board[0][0];
        Piece YblockadeFriendlyDown = board[0][0];
        Piece XblockadeFriendlyLeft = board[0][0];
        if(this.blockades5.size()==0){

        }
        else if(this.blockades5.size()==1){
            XblockadeFriendlyRight = this.blockades5.get(0);
        }
        else{
            XblockadeFriendlyRight = this.blockades5.get(0);
        }
        if(this.blockades6.size()==0){

        }
        else if(this.blockades6.size()==1){
            YblockadeFriendlyUp = this.blockades6.get(0);
        }
        else{
            YblockadeFriendlyUp = this.blockades6.get(0);
        }
        if(this.blockades7.size()==0){

        }
        else if(this.blockades7.size()==1){
            YblockadeFriendlyDown = this.blockades7.get(0);
        }
        else{
            YblockadeFriendlyDown = this.blockades7.get(0);
        }
        if(this.blockades8.size()==0){

        }
        else if(this.blockades8.size()==1){
            XblockadeFriendlyLeft = this.blockades8.get(0);
        }
        else{
            XblockadeFriendlyLeft = this.blockades8.get(0);
        }
        Piece RightUpBlockade = null;
        Piece LeftUpBlockade = null;
        Piece RightDownBlockade = null;
        Piece LeftDownBlockade = null;
        i = 1;
        j = 1;
        while(j!=7 && i!=7){
            //X
            int kot = piece.getPosY()+j;
            //Y
            int pies = piece.getPosX()-i;
            if(pies >= 0 && kot < 8){
                if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l')
                {
                    this.blockades.add(board[pies][kot]);
                }
            }
            j++;
            i++;

        }
        if(this.blockades.size()==0){

        }
        else{
            RightUpBlockade = this.blockades.get(0);
        }
        i = 1;
        j = 1;
        while(j!=7 && i!=7){
            //X
            int kot = piece.getPosY()-j;
            //Y
            int pies = piece.getPosX()-i;
            if(!(pies<0) && !(kot<0)){
                if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l')
                {
                    this.blockades2.add(board[pies][kot]);
                }
            }
            j++;
            i++;
        }
        if(this.blockades2.size()==0){

        }
        else{
            LeftUpBlockade = this.blockades2.get(0);
        }
        i = 1;
        j = 1;
        while(j!=7 && i!=7){
            int kot = piece.getPosY()+j;
            int pies = piece.getPosX()+i;
            if(!(pies>=8) && !(kot>=8)){
                if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l')
                {
                    this.blockades3.add(board[pies][kot]);
                }
            }
            j++;
            i++;

        }
        if(this.blockades3.size()==0){

        }
        else{
            RightDownBlockade = this.blockades3.get(0);
        }
        i = 1;
        j = 1;
        while(j!=7 && i!=7){
            //X
            int kot = piece.getPosY()-j;
            //Y
            int pies = piece.getPosX()+i;
            if(!(pies>=8) && !(kot<0)){
                if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l')
                {
                    this.blockades4.add(board[pies][kot]);
                }
            }
            j++;
            i++;

        }
        if(this.blockades4.size()==0){

        }
        else{
            LeftDownBlockade = this.blockades4.get(0);
        }
        if(this.seenLeftUpPieces.size()!=0){
            this.seenLeftUpPieces.clear();
        }
        if(this.seenRightDownPieces.size()!=0){
            this.seenRightDownPieces.clear();
        }
        if(this.seenRightUpPieces.size()!=0){
            this.seenRightUpPieces.clear();
        }
        if(this.seenLeftDownPieces.size()!=0){
            this.seenLeftDownPieces.clear();
        }
        i = 1;
        j = 1;
        while(j!=7 && i!=7){
            //X
            int kot = piece.getPosY()+j;
            //Y
            int pies = piece.getPosX()-i;
            if((pies >= 0) && kot < 8){
                if(!board[pies][kot].isUsed())
                {
                    this.seenRightUpPieces.add(board[pies][kot]);
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
            int pies = piece.getPosX()-i;
            if(pies >= 0 && kot >= 0){
                if(!board[pies][kot].isUsed())
                {
                    this.seenLeftUpPieces.add(board[pies][kot]);
                }
            }
            j++;
            i++;
        }

        i = 1;
        j = 1;
        while(j!=7 && i!=7){
            //X
            int kot = piece.getPosY()+j;
            //Y
            int pies = piece.getPosX()+i;
            if(pies < 8 && kot < 8){
                if(!board[pies][kot].isUsed())
                {
                    this.seenRightDownPieces.add(board[pies][kot]);
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
                if(!board[pies][kot].isUsed())
                {
                    this.seenLeftDownPieces.add(board[pies][kot]);
                }
            }
            j++;
            i++;
        }
        i=1;
        j=1;
        while(i!=8){
            int pies = piece.getPosY()+i;
            if(pies < 8){
                if(!board[piece.getPosX()][pies].isUsed()){
                    seenRightPieces.add(board[piece.getPosX()][pies]);
                }
            }
            i++;
        }
        j=1;
        while(j!=8){
            int kot = piece.getPosX()-j;
            if(kot >= 0){
                if(!board[kot][piece.getPosY()].isUsed()){
                    seenUpPieces.add(board[kot][piece.getPosY()]);
                }
            }
            j++;
        }
        j=1;
        while(j!=8){
            int kot = piece.getPosX()+j;
            if(kot < 8){
                if(!board[kot][piece.getPosY()].isUsed()){
                    seenDownPieces.add(board[kot][piece.getPosY()]);
                }
            }
            j++;
        }
        i = 1;
        while(i!=8){
            int pies = piece.getPosY()-i;
            if(pies >= 0){
                if(!board[piece.getPosX()][pies].isUsed()){
                    seenLeftPieces.add(board[piece.getPosX()][pies]);
                }
            }
            i++;
        }
        ArrayList<Piece> RightUpAttackers = new ArrayList<Piece>();
        ArrayList<Piece> LeftUpAttackers = new ArrayList<Piece>();
        ArrayList<Piece> RightDownAttackers = new ArrayList<Piece>();
        ArrayList<Piece> LeftDownAttackers = new ArrayList<Piece>();
        i = 1;
        j = 1;
        while(j!=7 && i!=7){
            //X
            int kot = piece.getPosY()+j;
            //Y
            int pies = piece.getPosX()-i;
            if(pies >= 0 && kot < 8){
                if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l' && board[pies][kot].getColor()!=piece.getColor())
                {
                    RightUpAttackers.add(board[pies][kot]);
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
            int pies = piece.getPosX()-i;
            if(pies >= 0 && kot >= 0){
                if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l' && board[pies][kot].getColor()!=piece.getColor())
                {
                    LeftUpAttackers.add(board[pies][kot]);
                }
            }
            j++;
            i++;
        }

        i = 1;
        j = 1;
        while(j!=7 && i!=7){
            //X
            int kot = piece.getPosY()+j;
            //Y
            int pies = piece.getPosX()+i;
            if(pies < 8 && kot < 8){
                if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l' && board[pies][kot].getColor()!=piece.getColor())
                {
                    RightDownAttackers.add(board[pies][kot]);
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
                if(!board[pies][kot].isUsed() && board[pies][kot].getType()!='l' && board[pies][kot].getColor()!=piece.getColor())
                {
                    LeftDownAttackers.add(board[pies][kot]);
                }
            }
            j++;
            i++;
        }

        Piece rightUpAttacker = null;
        Piece leftUpAttacker = null;
        Piece rightDownAttacker = null;
        Piece leftDownAttacker = null;
        if(RightUpAttackers.size()!=0){
            rightUpAttacker=RightUpAttackers.get(0);
        }
        if(LeftUpAttackers.size()!=0){
            leftUpAttacker=LeftUpAttackers.get(0);
        }
        if(RightDownAttackers.size()!=0){
            rightDownAttacker=RightDownAttackers.get(0);
        }
        if(LeftDownAttackers.size()!=0){
            leftDownAttacker=LeftDownAttackers.get(0);
        }
        ArrayList<Piece> RightAttackers = new ArrayList<Piece>();
        ArrayList<Piece> LeftAttackers = new ArrayList<Piece>();
        ArrayList<Piece> UpAttackers = new ArrayList<Piece>();
        ArrayList<Piece> DownAttackers = new ArrayList<Piece>();
        i=1;
        while(i!=8){
            int pies = piece.getPosY()+i;
            if(pies < 8){
                if(!board[piece.getPosX()][pies].isUsed() && board[piece.getPosX()][pies].getColor()!=piece.getColor() && board[piece.getPosX()][pies].getType()!='l'){
                    RightAttackers.add(board[piece.getPosX()][pies]);
                }
            }
            i++;
        }
        i=1;
        while(i!=8){
            int pies = piece.getPosY()-i;
            if(pies >= 0){
                if(!board[piece.getPosX()][pies].isUsed() && board[piece.getPosX()][pies].getColor()!=piece.getColor() && board[piece.getPosX()][pies].getType()!='l'){
                    LeftAttackers.add(board[piece.getPosX()][pies]);
                }
            }
            i++;
        }
        j=1;
        while(j!=8){
            int kot = piece.getPosX()+j;
            if(kot < 8){
                if(!board[kot][piece.getPosY()].isUsed() && board[kot][piece.getPosY()].getType()!='l' && board[kot][piece.getPosY()].getColor()!=piece.getColor()){
                    DownAttackers.add(board[kot][piece.getPosY()]);
                }
            }
            j++;
        }
        i=1;
        while(i!=8){
            int kot = piece.getPosX()-i;
            if(kot >= 0){
                if(!board[kot][piece.getPosY()].isUsed() && board[kot][piece.getPosY()].getType()!='l' && board[kot][piece.getPosY()].getColor()!=piece.getColor()){
                    UpAttackers.add(board[kot][piece.getPosY()]);
                }
            }
            i++;
        }
        Piece rightAttacker = null;
        Piece leftAttacker = null;
        Piece upAttacker = null;
        Piece downAttacker = null;
        if(RightAttackers.size()!=0){
            rightAttacker=RightAttackers.get(0);
        }
        if(LeftAttackers.size()!=0){
            leftAttacker=LeftAttackers.get(0);
        }
        if(UpAttackers.size()!=0){
            upAttacker=UpAttackers.get(0);
        }
        if(DownAttackers.size()!=0) {
            downAttacker = DownAttackers.get(0);
        }
        //blockades = right up
        //blockades2 = left up
        //blockades3 = right down
        //blockades4 = left down
        //blockades5 = right
        //blockades6 = up
        //blockades7 = down
        //blockades8 = left
        if(rightUpAttacker!=null){
            if(RightUpBlockade==rightUpAttacker && this.seenRightUpPieces.contains(rightUpAttacker) && (rightUpAttacker.getType()=='B' || rightUpAttacker.getType()=='Q') && rightUpAttacker.getColor()!=piece.getColor()){
                papuga = true;
            }
        }
        if(leftUpAttacker!=null){
            if(LeftUpBlockade==leftUpAttacker && this.seenLeftUpPieces.contains(leftUpAttacker) && (leftUpAttacker.getType()=='B' || leftUpAttacker.getType()=='Q') && leftUpAttacker.getColor()!=piece.getColor()){
                papuga = true;
            }
        }
        if(rightDownAttacker!=null){
            if(RightDownBlockade==rightDownAttacker && this.seenRightDownPieces.contains(rightDownAttacker) && (rightDownAttacker.getType()=='B' || rightDownAttacker.getType()=='Q') && rightDownAttacker.getColor()!=piece.getColor()){
                papuga = true;
            }
        }
        if(leftDownAttacker!=null){
            if(LeftDownBlockade==leftDownAttacker && this.seenLeftDownPieces.contains(leftDownAttacker) && (leftDownAttacker.getType()=='B' || leftDownAttacker.getType()=='Q') && leftDownAttacker.getColor()!=piece.getColor()){
                papuga = true;
            }
        }
        if(rightAttacker!=null){
            if(XblockadeFriendlyRight==rightAttacker && seenRightPieces.contains(rightAttacker) && (rightAttacker.getType()=='R' || rightAttacker.getType()=='Q') && rightAttacker.getColor()!=piece.getColor()){
                papuga = true;
            }
        }
        if(leftAttacker!=null){
            if(XblockadeFriendlyLeft==leftAttacker && seenLeftPieces.contains(leftAttacker) && (leftAttacker.getType()=='R' || leftAttacker.getType()=='Q') && leftAttacker.getColor()!=piece.getColor()){
                papuga = true;
            }
        }
        if(upAttacker!=null){
            if(YblockadeFriendlyUp==upAttacker && seenUpPieces.contains(upAttacker) && (upAttacker.getType()=='R' || upAttacker.getType()=='Q') && upAttacker.getColor()!=piece.getColor()){
                papuga = true;
            }
        }
        if(downAttacker!=null){
            if(YblockadeFriendlyDown==downAttacker && seenDownPieces.contains(downAttacker) && (downAttacker.getType()=='R' || downAttacker.getType()=='Q') && downAttacker.getColor()!=piece.getColor()){
                papuga = true;
            }
        }
        return papuga;
    }

    public Piece findWhiteKing(Piece board[][]){
        Piece piece = new Piece('l',false,0,0,true);
        for(int i=0; i<8; i++){
            for(int j=0; j<8;j++){
                if(board[j][i].getType()=='K' && board[j][i].getColor()==true){
                    piece = board[j][i];
                    break;
                }
            }
        }
        return piece;
    }

    public Piece findBlackKing(Piece board[][]){
        Piece piece = new Piece('l',false,0,0,true);
        for(int i=0; i<8; i++){
            for(int j=0; j<8;j++){
                if(board[j][i].getType()=='K' && board[j][i].getColor()==false){
                    piece = board[j][i];
                    break;
                }
            }
        }
        return piece;
    }

    public String print_piece(){
        String pies;
        if(getColor()==true && getType()!='l'){
            pies = "w" + getType();
        }
        else if(getColor()==false && getType()!='l'){
            pies = "b" + getType();
        }
        else{
            pies = "f" + getType();
        }
        return pies;
    }

    @Override
    public String toString() {
        String pies;
        if(getColor()==true && getType()!='l'){
            pies = "w" + getType();
        }
        else if(getColor()==false && getType()!='l'){
            pies = "b" + getType();
        }
        else{
            pies = "f" + getType();
        }
        return pies;
    }

}
