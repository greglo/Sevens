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
            towers.get(suit).play(card.getCardIndex());
        }
    }

    @Override
    public Card play() {
        for (Card card : hand) {
            if (towers.get(card.getSuit()).canPlay(card.getCardIndex())) {
                hand.remove(card);
                return card;
            }
            else
                System.out.println("Cannot play " + card.toString());
        }
        return null;
    }

    @Override
    public String getName() {
        return "Owen";
    }

    private class Tower {
        private int upperBound = 7;
        private int lowerBound = 7;

        public void play(int cardIndex) {
            if (cardIndex == upperBound)
                upperBound += 1;

            if (cardIndex == lowerBound)
                lowerBound -= 1;
        }

        public boolean canPlay(int cardIndex) {
            return cardIndex == lowerBound || cardIndex == upperBound;
        }
    }
}
