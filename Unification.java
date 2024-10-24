import java.util.ArrayList;

public class Unification {

    private KnowledgeBase KB;

    public Unification(KnowledgeBase KB)
    {
        this.KB = KB;
    }

    public Subsitution unify(Term x, Term y, Subsitution theta)
    {
        if (theta == null)
        {
            return null;
        }
        else if (x.equals(y))
        {
            return theta;
        }
        else if (x instanceof Variable)
        {
            Variable xVar = (Variable) x;
            return unifyVar(xVar,y,theta);
        }
        else if (y instanceof Variable)
        {
            Variable yVar = (Variable) y;
            return unifyVar(yVar, x, theta);
        }
        else if (x instanceof Function && y instanceof Function)
        {
            Function funcX = (Function) x;
            Function funcY = (Function) y;
            return unify(funcX.getArguments(),funcY.getArguments(), theta);
        }
        return null;

    }

    public Subsitution unify(ArrayList<Term> x, ArrayList<Term> y, Subsitution theta)
    {
        if (x.size() != y.size())
        {
            return null;
        }
        if (x.isEmpty() && y.isEmpty())
        {
            return theta;
        }
        ArrayList<Term> xNew = new ArrayList<>();
        xNew.addAll(x);
        xNew.removeFirst();
        ArrayList<Term> yNew = new ArrayList<>();
        yNew.addAll(y);
        yNew.removeFirst();
        return unify(xNew,yNew, unify(x.getFirst(), y.getFirst(), theta));
    }


    private Subsitution unifyVar(Variable var, Term x, Subsitution theta) {
        if (theta.contains(var))
        {
            return unify(theta.getVal(var), x, theta);
        }
        else if (theta.contains(x))
        {
            return unify(var, theta.getVal(x), theta);
        }
        else if (occurCheck(var,x))
        {
            return null;
        }
        else
        {
            theta.addSub(var, x);
            return theta;
        }
    }

    private static boolean occurCheck(Variable var, Term x) {
        if (x instanceof Variable) {
            return var.equals(x); // Occur check fails if var equals x
        } else if (x instanceof Function) {
            Function func = (Function) x;
            for (Term arg : func.getArguments()) {
                if (occurCheck(var, arg)) {
                    return true; // Occur check fails if var appears in any argument
                }
            }
        }
        return false;
    }

}
