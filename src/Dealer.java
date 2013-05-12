import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Dealer {
    private final List<Player> players;
    private final ArrayList<HashSet<Card>> hands;

    private boolean gameStarted = false;

    public Dealer() {
        players = new ArrayList<Player>();
        hands = new ArrayList<HashSet<Card>>();
    }

    public void addPlayer(Player player) {
        if (!gameStarted || player == null)
            if (!players.contains(player))
                players.add(player);
            else
                ;
        // TODO
    }

    public void deal() {
        int countPlayers = players.size();
        if (countPlayers >= 3) {
            gameStarted = true;

            int[] playerCardCount = new int[countPlayers];
            List<Card> pack = new ArrayList<Card>();
            
            // Initialize empty hands for each player
            for (int i = 0; i < countPlayers; i++)
                hands.add(new HashSet<Card>());

            // Initialize an unshuffled deck
            for (int i = 1; i <= 52; i++) {
                pack.add(new Card(i));
            }

            // Pick random card from the deck, and give it to the next player
            Random rng = new Random();
            for (int i = 0; i < 52; i++) {
                int playerNum = i % countPlayers;
                Card nextCard = pack.remove(rng.nextInt(52 - i));
                hands.get(playerNum).add(nextCard);
                playerCardCount[playerNum]++;
            }

            
            GameConfig config = new GameConfig(countPlayers, playerCardCount);
            for (Player player : players)
                player.initialize(config);
        }

    }
}
