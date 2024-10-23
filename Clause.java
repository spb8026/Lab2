import java.util.ArrayList;

public class Clause {
    ArrayList<Predicate> literals;

    public Clause(ArrayList<Predicate> literals)
    {
        this.literals = literals;
    }

    public ArrayList<Predicate> getLiterals() {
        return literals;
    }

    public boolean isUnit()
    {
        return literals.size() == 1;
    }

    public String toString()
    {
        String str = "";
        for (Predicate pred: this.literals)
        {
            str += pred.toString();
        }
        return str;
    }


}
