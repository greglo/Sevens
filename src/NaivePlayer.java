import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NaivePlayer implements Player {
    private Set<Card> hand;
    private Map<Suit, Tower> towers = new HashMap<Suit, Tower>();

    public NaivePlayer() {
        for (Suit suit : Suit.values())
            towers.put(suit, new Tower());
    }

    @Override
    public void initialize(GameConfig config) {

    }

    @Override
    public void deal(Set<Card> hand) {
        this.hand = hand;
    }

    @Override
    public void movePlayed(int index, Card card) {
        if (card != null) {
            Suit suit = card.getSuit();
            towers.get(suit).play(card.getCard());
        }
    }

    @Override
    public Card play() {
        for (Card card : hand) {
            if (towers.get(card.getSuit()).canPlay(card.getCard()))
                return card;
        }
        return null;
    }

    @Override
    public String getName() {
        return "Owen";
    }

    private class Tower {
        int upperBound = 7;
        int lowerBound = 7;

        public void play(int card) {
            if (card == upperBound)
                upperBound += 1;

            if (card == lowerBound)
                lowerBound -= 1;
        }

        public boolean canPlay(int card) {
            return card == lowerBound || card == upperBound;
        }
    }
}
