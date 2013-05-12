import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Dealer {
    private final List<Player> players;
    private final ArrayList<HashSet<Card>> hands;

    private boolean gameStarted = false;
    private int playerCount = 0;
    private int nextPlayerIndex = 0;

    public Dealer() {
        players = new ArrayList<Player>();
        hands = new ArrayList<HashSet<Card>>();
    }

    public void addPlayer(Player player) {
        if (!gameStarted || player == null)
            if (!players.contains(player)) {
                players.add(player);
                playerCount++;
            }
            else
                ;
        // TODO
    }

    public void deal() {
        if (playerCount >= 3) {
            gameStarted = true;

            int[] playerCardCount = new int[playerCount];

            // Initialize empty hands for each player
            hands.clear();
            for (int i = 0; i < playerCount; i++)
                hands.add(new HashSet<Card>());

            // Initialize an unshuffled deck
            List<Card> pack = new ArrayList<Card>();
            for (int i = 1; i <= 52; i++) {
                pack.add(new Card(i));
            }

            // Pick random card from the deck, and give it to the next player
            Random rng = new Random();
            for (int i = 0; i < 52; i++) {
                int playerNum = i % playerCount;
                Card nextCard = pack.remove(rng.nextInt(52 - i));
                hands.get(playerNum).add(nextCard);
                playerCardCount[playerNum]++;
            }

            GameConfig config = new GameConfig(playerCount, playerCardCount);
            Player player;
            for (int i = 0; i < playerCount; i++) {
                player = players.get(i);
                player.initialize(config);
                player.deal((Set<Card>) hands.get(i).clone());
            }
        }
    }

    public void playStep() {
        if (gameStarted) {
            Player player = players.get(nextPlayerIndex);
            Card nextCard = player.play();

            // TODO check that card is actually valid

            if (nextCard != null)
                System.out.println(player.getName() + " plays " + nextCard.toString());
            else
                System.out.println(player.getName() + " cannot play");

            for (Player p : players)
                p.movePlayed(nextPlayerIndex, nextCard);
            
            nextPlayerIndex = (nextPlayerIndex + 1) % playerCount;
        }
    }

    public void playAll() {
        // TODO actually do this
        for (int i = 0; i < 100; i++)
            playStep();
    }
}
