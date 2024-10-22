import java.util.ArrayList;

public class Function extends Term{
    String name;
    ArrayList<Term> arguments;

    public Function (String name, ArrayList<Term> arguments)
    {
        this.name = name;
        this.arguments = arguments;
    }

    public Function (String name)
    {
        this.name = name;
    }

    private Term evaluateFunction(){ return null;}
}
