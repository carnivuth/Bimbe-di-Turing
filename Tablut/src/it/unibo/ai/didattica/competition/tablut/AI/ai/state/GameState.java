package ai.state;

public class GameState {

    public int color;
    //state
    public int[][] board= {{FREE,FREE,FREE,BLACK,BLACK,BLACK,FREE,FREE,FREE},
                            {FREE,FREE,FREE,FREE,BLACK,FREE,FREE,FREE,FREE},
                            {FREE,FREE,FREE,FREE,WHITE,FREE,FREE,FREE,FREE},
                            {BLACK,FREE,FREE,FREE,WHITE,FREE,FREE,FREE,BLACK},
                            {BLACK,BLACK,WHITE,WHITE,KING,WHITE,WHITE,BLACK,BLACK},
                            {BLACK,FREE,FREE,FREE,WHITE,FREE,FREE,FREE,BLACK},
                            {FREE,FREE,FREE,FREE,WHITE,FREE,FREE,FREE,FREE},
                            {FREE,FREE,FREE,FREE,BLACK,FREE,FREE,FREE,FREE},
                            {FREE,FREE,FREE,BLACK,BLACK,BLACK,FREE,FREE,FREE}};
   
    // costant values
    public static final int CAMP=1;
    public static final int KING=2;
    public static final int WHITE=3;
    public static final int BLACK=4;
    public static final int FREE=5;

    // important coordinates
    public static final int [] throne ={4,4};

    public static final int [][] citadels ={{0,3},{0,4},{0,4},{1,4},
                                            {8,3},{8,4},{8,5},{7,4},
                                            {3,8},{4,8},{5,8},{4,7},
                                            {3,0},{4,0},{5,0},{4,1}};
    
    public static final int [][] safeCitadels ={{0,3},{0,4},{0,4},
                                            {8,3},{8,4},{8,5},
                                            {3,8},{4,8},{5,8},
                                            {3,0},{4,0},{5,0}};
        
    public static final int [][] escapes ={{0,1},{0,2},{0,6},{0,7},
                                            {8,1},{8,2},{8,6},{8,7},
                                            {1,0},{2,0},{6,0},{7,0},
                                            {1,8},{2,8},{6,8},{7,0}};


    public Pawn[] whitePawns;
    public Pawn[] blackPawns;
    public Pawn king=new Pawn(4, 4, WHITE);
    public GameState(int color){
        this.color=color;
        whitePawns=new Pawn[8];

        whitePawns[0]=new Pawn(4,2,WHITE);
        whitePawns[1]=new Pawn(4,3,WHITE);
        whitePawns[2]=new Pawn(4,5,WHITE);
        whitePawns[3]=new Pawn(4,6,WHITE);
        whitePawns[4]=new Pawn(2,4,WHITE);
        whitePawns[5]=new Pawn(3,4,WHITE);
        whitePawns[6]=new Pawn(5,4,WHITE);
        whitePawns[7]=new Pawn(6,4,WHITE);
       
        blackPawns=new Pawn[16];
       
        blackPawns[0]=new Pawn(0, 3, BLACK);
        blackPawns[1]=new Pawn(0, 4, BLACK);
        blackPawns[2]=new Pawn(0, 5, BLACK);
        blackPawns[3]=new Pawn(1, 4, BLACK);

        blackPawns[4]=new Pawn(8, 3, BLACK);
        blackPawns[5]=new Pawn(8, 4, BLACK);
        blackPawns[6]=new Pawn(8, 5, BLACK);
        blackPawns[7]=new Pawn(7, 4, BLACK);

        blackPawns[8]=new Pawn(3, 0, BLACK);
        blackPawns[9]=new Pawn(4, 0, BLACK);
        blackPawns[10]=new Pawn(5, 0, BLACK);
        blackPawns[11]=new Pawn(4, 1, BLACK);

        blackPawns[12]=new Pawn(3, 8, BLACK);
        blackPawns[13]=new Pawn(4, 8, BLACK);
        blackPawns[14]=new Pawn(5, 8, BLACK);
        blackPawns[15]=new Pawn(4, 7, BLACK);


    }

    public GameState(int color,Pawn[] whitePawns,Pawn[] blackPawns,int [][] board){
        this.color=color;
        this.blackPawns=blackPawns;
        this.whitePawns=whitePawns;
        this.board=board;
    }

    @Override
    public String toString(){
      
        String result="";
        for (int i=0;i<board.length;i++){
            for (int j=0;j<board.length;j++){
                result+=board[i][j];
            }  
            result+="\n";
        }
        return result;
    }
    
    public void setState(int i, int j, int value){
        board[i][j]=value;
    }

    public int getState(int i, int j){
        return board[i][j];
    }

    public static boolean isIn(Pawn pawn, int[][] elements){
        for(int i=0;i<elements.length;i++){
            if(pawn.x==elements[i][0] &&pawn.y==elements[i][1])return true;
        }
        return false;

    }
}
