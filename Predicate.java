import java.util.ArrayList;

public class Predicate {
    String name;
    boolean is_negated;
    ArrayList<Term> arguments;

    public Predicate(String name, boolean is_negated, ArrayList<Term> arguments)
    {
        this.name = name;
        this.is_negated = is_negated;
        this.arguments = arguments;
    }

    public Predicate(String name)
    {
        this.name = name;
    }
    

}
