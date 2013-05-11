public class Card {
    private final Suit suit;
    private final int card;
    
    public Card(int index) {
        this.suit = Suit.values()[index / 4];
        this.card = index % 13;
    }

    public Card(Suit suit, int card) {
        if (0 <= card && card < 14) {
            this.suit = suit;
            this.card = card;
        } else
            throw new IllegalArgumentException();
    }
    
    public Suit getSuit() {
        return suit;
    }
    
    public int getCard() {
        return card;
    }
    
   @Override
   public String toString() {
       return cardToString(card)
               + " of "
               + suit.toString().substring(0, 1).toUpperCase()
               + suit.toString().substring(1).toLowerCase()
               + "s";
   }
   
   private String cardToString(int card) {
       switch (card) {
       case 0:  return "Ace";
       case 11: return "Jack";
       case 12: return "Queen";
       case 13: return "King";
       default: return Integer.toString(card);
       }
   }
}
