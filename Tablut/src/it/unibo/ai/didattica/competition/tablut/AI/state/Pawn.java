package ai.state;

// stato composto da matrice della scacchiera con stato degli scacchi
public class Pawn {
    
    public int x;
    public int y;
    public int type;

    public Pawn(int x,int y,int type){
        this.type=type;
        this.x=x;
        this.y=y;
    }
}
