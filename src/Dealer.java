import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Dealer {
    private List<Player> players;
    
    private boolean gameStarted;
    
    public Dealer() {
        
    }
    
    public void addPlayer(Player player) {
        if (!gameStarted || player == null)
            if (players.contains(player))
                players.add(player);
        else ;
            //TODO
    }
    
    public void deal() {
        gameStarted = true;
        
        int countPlayers = players.size();
        int[] playerCardCount = new int[countPlayers];
        List<HashSet<Card>> hands = new ArrayList<HashSet<Card>>();
        List<Card> pack = new ArrayList<Card>();
        
        for (int i = 0; i < 52; i++)
            pack.add(new Card(i));
        
        Random rng = new Random();
        for (int i = 0; i < 52; i++) {
            Card nextCard = pack.remove(rng.nextInt(i));
            playerCardCount[i]++;
            hands.get(i).add(nextCard);
        }
        
        GameConfig config = new GameConfig(countPlayers, playerCardCount);
        
        for (Player player : players)
            player.initialize(config);
    }
}
