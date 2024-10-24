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
    
    public ArrayList<Term> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        String str = name;
        str += "(";
        for (Term trm: arguments)
        {
            str += trm.toString();
        }
        str += ")";
        return str;

    }
    
    
}
