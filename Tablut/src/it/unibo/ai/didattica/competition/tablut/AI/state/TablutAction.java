package ai.state;
import aima.core.agent.Action;

public class TablutAction  implements Action{

    public Pawn pawn;
    public int[] coordinates;

    public TablutAction(Pawn pawn,int[] coordinates){
        this.pawn=pawn;
        this.coordinates=coordinates;
        
    }

    @Override
    public boolean isNoOp() {
        return false;
    }
    @Override
    public String toString(){
        return Integer.toString(this.pawn.x)+":"+Integer.toString(this.pawn.y)+"->"+Integer.toString(this.coordinates[ 0])+":"+Integer.toString(this.coordinates[1])+"\n";  
    }
    
}
