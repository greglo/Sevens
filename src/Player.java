import java.util.Set;



public interface Player {
    public void initialize(GameConfig config);
    public void deal(Set<Card> hand);
    public void movePlayed(int playerIndex, Card card);
    public Card play();
    public String getName();
}
