import java.util.HashMap;

public class Subsitution {

    private HashMap<Term, Term> subMap;

    public Subsitution() {
        this.subMap = new HashMap<>();
    }

    public boolean contains(Term var)
    {
        return subMap.containsKey(var);
    }

    public Term getVal(Term var)
    {
        return subMap.get(var);
    }

    public void addSub(Term var, Term val)
    {
        subMap.put(var, val);
    }

}
