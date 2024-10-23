import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class Resolver {
    private KnowledgeBase KB;

    public Resolver (KnowledgeBase KB)
    {
        this.KB = KB;
    }


    public boolean resolve()
    {
        ArrayList<Clause> clauses = KB.getClauses();
        Hashtable<String,Predicate> predicates = KB.getPredicates();
        Hashtable<String,ArrayList<Clause>> predClauseTable = new Hashtable<>();
        for (Predicate predicate: predicates.values())
        {
            predClauseTable.put(predicate.name.replaceAll("\\s+", ""), new ArrayList<Clause>());
        }
        for (Clause curClause: clauses)
        {
            ArrayList<Predicate> clasueLiterals = curClause.getLiterals();
            for (Predicate lits: clasueLiterals)
            {
                ArrayList<Clause> clauseList;
                if ((clauseList = predClauseTable.get(lits.name)) != null)
                {
                    clauseList.add(curClause);
                    predClauseTable.put(lits.name, clauseList);
                }
            }
        }
        Set<String> visited = new HashSet<>();
        while (true)
        {
            for (String curPred: predClauseTable.keySet())
            {
                ArrayList<Clause> curPredClauses = predClauseTable.get(curPred);
                int i = 0;
                int j = 1;
                while (i < curPredClauses.size()-1)
                {
                    while (j < curPredClauses.size())
                    {
                        Clause newClause = PCResolve(curPredClauses.get(i),curPredClauses.get(j));
                        if (newClause != null)
                        {
                            if (newClause.literals == null)
                            {
                                return false;
                            }
                            for (Predicate newClausePred: newClause.literals)
                            {
                                ArrayList<Clause> newClauseList;
                                if (((newClauseList = predClauseTable.get(newClausePred.name)) != null))
                                {
                                    newClauseList.add(newClause);
                                    predClauseTable.put(newClausePred.name, newClauseList);
                                    visited.remove(newClausePred.name);
                                }
                            }
                        }
                    j++;
                    }
                    i++;
                }
                visited.add(curPred);
                if (visited.size() == predicates.keySet().size())
                {
                    return true;
                }
            }
        }
    }


    public Clause PCResolve(Clause Ci, Clause Cj) 
    {
        ArrayList<Predicate> CiLits= Ci.getLiterals();
        ArrayList<Predicate> CjLits= Cj.getLiterals();      
        if (Ci.isUnit() && Cj.isUnit())
        {
            Predicate CiUnitPred = CiLits.get(0);
            Predicate CjUnitPred = CjLits.get(0);
            Boolean sameargs = true;
            ArrayList<Term> CiUnitArgs = CiUnitPred.getArguments();
            ArrayList<Term> CjUnitArgs = CjUnitPred.getArguments();
            int i = 0;
            int j = 0;
            while (i < CiUnitArgs.size())
            {
                while (j< CjUnitArgs.size())
                {
                    if (CiUnitArgs.get(i).toString() != CjUnitArgs.get(j).toString())
                    {
                        sameargs = false;
                    }
                    i++;
                    j++;
                }
            }
            if (sameargs)
            {
                if ((CiUnitPred.is_negated && !CjUnitPred.is_negated) || (!CiUnitPred.is_negated && CjUnitPred.is_negated))
                {
                    System.err.println(Ci.toString() + " and  " + Cj.toString() + " to []");
                    return new Clause(null);
                }
            }
        }
        else if (Ci.isUnit() && !Cj.isUnit())
        {
            // System.out.println(Ci.toString());
            // System.out.println(Cj.toString());
            Predicate CiUnitPred = CiLits.get(0);
            for (Predicate pred: CjLits)
            {
                if ((CiUnitPred.name.contentEquals(pred.name)) && ((CiUnitPred.is_negated && !pred.is_negated) || (!CiUnitPred.is_negated && pred.is_negated)))
                {
                    Boolean sameargs = true;
                    ArrayList<Term> CiUnitArgs = CiUnitPred.getArguments();
                    ArrayList<Term> predArgs = pred.getArguments();
                    int i = 0;
                    int j = 0;
                    while (i < CiUnitArgs.size())
                    {
                        while (j< predArgs.size())
                        {
                            if (CiUnitArgs.get(i).toString() != predArgs.get(j).toString())
                            {
                                sameargs = false;
                            }
                            i++;
                            j++;
                        }
                    }
                    if (sameargs)
                    {
                        System.err.println(Ci.toString() + " and  " + Cj.toString());
                        CjLits.remove(CjLits.indexOf(pred));
                        System.out.println(" to " + CjLits);
                        return new Clause(CjLits);
                    }
                } 
            }
        }
        else if (!Ci.isUnit() && Cj.isUnit())
        {
            Predicate CjUnitPred = CjLits.get(0);
            for (Predicate pred: CiLits)
            {
                if ((CjUnitPred.name.contentEquals(pred.name)) && ((CjUnitPred.is_negated && !pred.is_negated) || (!CjUnitPred.is_negated && pred.is_negated)))
                {
                    Boolean sameargs = true;
                    ArrayList<Term> CjUnitArgs = CjUnitPred.getArguments();
                    ArrayList<Term> predArgs = pred.getArguments();
                    int i = 0;
                    int j = 0;
                    while (i < CjUnitArgs.size())
                    {
                        while (j< predArgs.size())
                        {
                            if (CjUnitArgs.get(i).toString() != predArgs.get(j).toString())
                            {
                                sameargs = false;
                            }
                            i++;
                            j++;
                        }
                    }
                    if (sameargs)
                    {
                        System.err.println(Ci.toString() + " and  " + Cj.toString());
                        CiLits.remove(CiLits.indexOf(pred));
                        System.out.println(" to " + CiLits);
                        return new Clause(CiLits);
                    }
                } 
            }
        }
        return null;

    }
}
