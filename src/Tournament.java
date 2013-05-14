import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tournament {

    /**
     * @param args
     */
    public static void main(String[] args) {
        final int TOTAL_GAMES = 300000;
        final Map<Player, Integer> scores = new HashMap<Player, Integer>();

        List<Player> players = new ArrayList<Player>();

        players.add(new NaivePlayer("Naive1"));
        players.add(new NaivePlayer("Naive2"));
        players.add(new NaivePlayer("Naive3"));
        players.add(new NaivePlayer("Naive4"));

        int playerCount = players.size();
        int rotateAmount = 52 % playerCount;

        for (int i = 0; i < TOTAL_GAMES; i++) {
            try {
                Game game = new Game();
                java.util.Collections.shuffle(players); 
                game.addPlayers(players);
                game.deal();
                
                Player winner = game.play();
                if (scores.containsKey(winner))
                    scores.put(winner, scores.get(winner) + 1);
                else
                    scores.put(winner, 1);
                
                Collections.rotate(players, rotateAmount);
            } catch (SomeoneNoobException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        
        for (Player player : players)
            System.out.println(String.format("%1$-10s: ", player.getName()) + scores.get(player));
    }

}
