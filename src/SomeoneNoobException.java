
public class SomeoneNoobException extends Exception{
    private final String name;
    
    public SomeoneNoobException(String name) {
        this.name = name;
    }
    
    @Override
    public String getMessage() {
        return name + " is a massive noob who needs to learn to program!";
    }
}
