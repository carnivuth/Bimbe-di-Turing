package it.unibo.ai.didattica.competition.tablut.AI;

import java.io.IOException;
import java.net.UnknownHostException;

import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.State.Turn;

public class MyTablutClient extends TablutClient {

    // Qua ci sono i corstruttori di TablutClient
    // gli argomenti sono:
    // - il colore del giocatore (WHITE o BLACK)
    // - il nome del giocatore
    // - il timeout
    // - l'indirizzo IP del server

    public MyTablutClient(String player, String name, int timeout, String ipAddress)
            throws UnknownHostException, IOException {
        super(player, name, timeout, ipAddress);
    }

    public MyTablutClient(String player, String name, int timeout) throws UnknownHostException, IOException {
        super(player, name, timeout);
    }

    public MyTablutClient(String player, String name) throws UnknownHostException, IOException {
        super(player, name);
    }

    public MyTablutClient(String player, String name, String ipAddress) throws UnknownHostException, IOException {
        super(player, name, ipAddress);
    }

    // Qui ci sono i metodi astratti di TablutClient
    @Override
    public void run() {
        MinMax minmax = new MinMax(2, this.getPlayer());
        try {
            this.declareName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.getPlayer() == (State.Turn.WHITE)) {
            try {
                clientRoutine(minmax, Turn.WHITE);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                clientRoutine(minmax, Turn.BLACK);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void clientRoutine(MinMax minmax, Turn myTurn) throws ClassNotFoundException, IOException {
        Action action;
        boolean ended=false;
        while (!ended) {
            this.read();
            if (this.getCurrentState().getTurn().equals(myTurn)) {

                action = minmax.minmaxDecision( new BimbeState(this.getCurrentState()));
                this.write(action);
            } else {
                System.out.println("Waiting for the opponent move...");
            }
            //check for end of the game
            if(this.getCurrentState().getTurn().equals(Turn.WHITEWIN)||this.getCurrentState().getTurn().equals(Turn.BLACKWIN)||this.getCurrentState().getTurn().equals(Turn.DRAW)){
                ended=true;
            }

        }

    }

    // il main
    public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
        String role;
        if (args.length == 0) {
           // System.out.println("You must specify which player you are (WHITE or BLACK)!");
           // System.exit(-1);
           role="WHITE";
        }else{
         role = args[0];

        }
        if (role.equals("WHITE")) {
            TablutClient client = new MyTablutClient("WHITE", "Bimbe", 60, "localhost");
            client.run();
        } else if (role.equals("BLACK")) {
            TablutClient client = new MyTablutClient("NERO", "Bimbe", 60, "localhost");
            client.run();
        } else {
            System.out.println("Allowed roles are only WHITE and BLACK!");
            System.exit(-1);
        }
    }
}