import java.util.ArrayList;
import java.util.Objects;

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

     @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Clause other = (Clause) obj;
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString()); 
    }


}
