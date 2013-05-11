import java.util.Set;



public interface Player {
    public void initialize(GameConfig config);
    public void deal(Set<Card> hand);
    public void movePlayed(int index, Card card);
    public Card play();
}
