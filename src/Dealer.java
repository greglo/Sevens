import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Dealer {
    private final List<Player> players;
    private final Map<Player, HashSet<Card>> hands;
    private final Map<Suit, Tower> towers;

    private boolean gameStarted = false;
    private int playerCount = 0;
    private int nextPlayerIndex = 0;

    public Dealer() {
        players = new ArrayList<Player>();
        hands = new HashMap<Player, HashSet<Card>>();
        towers = new HashMap<Suit, Tower>();
        for (Suit suit : Suit.values())
            towers.put(suit, new Tower());
    }

    public void addPlayer(Player player) {
        if (!gameStarted && player != null)
            if (!players.contains(player)) {
                players.add(player);
                hands.put(player, new HashSet<Card>());
                playerCount++;
            }
    }

    public void deal() {
        if (playerCount >= 3) {
            gameStarted = true;

            int[] playerCardCount = new int[playerCount];

            // Initialize an unshuffled deck
            List<Card> pack = new ArrayList<Card>();
            for (int i = 1; i <= 52; i++) {
                pack.add(new Card(i));
            }

            // Pick random card from the deck, and give it to the next player
            Random rng = new Random();
            for (int i = 0; i < 52; i++) {
                int playerNum = i % playerCount;
                Player player = players.get(playerNum);

                Card nextCard = pack.remove(rng.nextInt(52 - i));
                hands.get(player).add(nextCard);
                playerCardCount[playerNum]++;
            }

            // Send out the GameConfig and Hands to players
            GameConfig config = new GameConfig(playerCount, playerCardCount);
            for (Player player : players) {
                player.initialize(config);
                player.deal((Set<Card>) hands.get(player).clone());
            }
        }
    }

    private Player playStep() throws SomeoneNoobException {
        if (!gameStarted || gameFinished())
            throw new GregNoobException();

        Player player = players.get(nextPlayerIndex);
        Card nextCard = player.play();

        if (nextCard != null) {
            Tower tower = towers.get(nextCard.getSuit());

            if (hands.get(player).contains(nextCard) && tower.canPlay(nextCard.getCardIndex()))
                tower.play(nextCard.getCardIndex());
            else
                throw new SomeoneNoobException(player.getName());

            for (Player p : players)
                p.movePlayed(nextPlayerIndex, nextCard);

        } else if (hasValidMove(player))
            throw new SomeoneNoobException(player.getName());
            

        nextPlayerIndex = (nextPlayerIndex + 1) % playerCount;
        return player;
    }

    public Player playAll() throws SomeoneNoobException {
        Player currentPlayer = null;
        while (!gameFinished()) {
            currentPlayer = playStep();
            if (hands.get(currentPlayer).isEmpty())
                break;
        }
        return currentPlayer;
    }

    public boolean gameFinished() {
        return (towers.get(Suit.DIAMOND).isClosed() && towers.get(Suit.CLUB).isClosed() && towers.get(Suit.HEART).isClosed() && towers
                .get(Suit.SPADE).isClosed());
    }
    
    private boolean hasValidMove(Player player) {
        for (Card card : hands.get(player)) {
            Tower tower = towers.get(card.getSuit());
            if (tower.canPlay(card.getCardIndex()))
                return true;
        }
        return false;
    }

    private class Tower {
        int upperBound = 7;
        int lowerBound = 7;

        public void play(int card) throws GregNoobException {
            if (!canPlay(card))
                throw new GregNoobException();

            if (card == upperBound)
                upperBound += 1;

            if (card == lowerBound)
                lowerBound -= 1;
        }

        public boolean canPlay(int cardIndex) {
            return !isClosed() && (cardIndex == lowerBound || cardIndex == upperBound);
        }

        public boolean isClosed() {
            return lowerBound <= 0 && upperBound >= 14;
        }
    }
}
