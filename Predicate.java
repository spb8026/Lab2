import java.util.ArrayList;

public class Predicate {
    String name;
    boolean is_negated;
    ArrayList<Term> arguments;

    public Predicate(String name, boolean is_negated, ArrayList<Term> arguments) {
        this.name = name;
        this.is_negated = is_negated;
        this.arguments = arguments;
    }

    public Predicate(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String str = "";
        if (is_negated) {
            str += "~";
        }
        str += this.name;
        str += "(";
        for (Term trm : arguments) {
            if (trm != null) {
                str += trm.toString() + ",";
            }
        }
        str += ")";
        return str;
    }

    public ArrayList<Term> getArguments() {
        return arguments;
    }

    public void applySubstitution(Subsitution theta) {
        for (int i = 0; i < arguments.size(); i++) {
            Term arg = arguments.get(i);
            Term substitutedArg = arg.applySubstitution(theta);
            arguments.set(i, substitutedArg);  // Update with substituted argument
        }
    }

    public Predicate deepCopy()
    {
        ArrayList<Term> argCopy = new ArrayList<>();
        argCopy.addAll(this.arguments);
        return new Predicate(this.name, this.is_negated,argCopy);
    }


}
