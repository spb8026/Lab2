import java.util.*;


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
            //System.out.println(predicate.name);
            predClauseTable.put(predicate.name, new ArrayList<Clause>());
        }
        for (Clause curClause: clauses)
        {
            ArrayList<Predicate> clasueLiterals = curClause.getLiterals();
            for (Predicate lits: clasueLiterals)
            {
                ArrayList<Clause> clauseList;
                //System.out.println(lits.name);
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
                            if (newClause.literals.isEmpty())
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

    public Boolean queResolve()
    {
        ArrayList<Clause> clauses = KB.getClauses();
        Queue<Pair> clausePairs = new LinkedList<>();
        Set<Clause> newClausesSet = new HashSet<>();
        int i = 0;
        while (i < clauses.size()-1)
        {
            int j = i + 1;
            while (j < clauses.size()) {
                Clause c1 = clauses.get(i);
                Clause c2 = clauses.get(j);
                if (checkIfHavePreds(c1, c2))
                {
                    clausePairs.add(new Pair(c1, c2));
                }
                j++;
            }
            i++;
        }
        while (true)
        {
            while (!clausePairs.isEmpty())
            {
                Pair cuPair = clausePairs.poll();
                ArrayList<Clause> newClauses = PCResolve(cuPair.getFirst(), cuPair.getSecond());
                for (Clause newClause: newClauses)
                {
                    if (newClause != null)
                    {
                        if (newClause.literals == null || newClause.literals.isEmpty())
                        {
                            return false;
                        }
                        newClausesSet.addAll(newClauses);
                    }
                }
            }
            Boolean propSubset = true;
            for (Clause nClause : newClausesSet) {
                // System.out.println(nClause.toString());
                if (!clauses.contains(nClause)) { // Corrected: remove semicolon here
                    propSubset = false; // Set false only when the clause is not found
                    break; // Once we know it's not a subset, no need to check further
                }
            }
            if (propSubset)
            {
                return true;
            }
            clauses.addAll(newClausesSet);
            for (Clause newClause: newClausesSet)
                for (Clause otherClauses: clauses)
                {
                    if (checkIfHavePreds(otherClauses, newClause))
                    {
                        clausePairs.add(new Pair(otherClauses, newClause));
                    }
                }
            }
    }

    public Boolean checkIfHavePreds(Clause c1, Clause c2)
    {
        ArrayList<Predicate> c1Preds = c1.getLiterals();
        ArrayList<Predicate> c2Preds = c2.getLiterals();
        for (Predicate c1pred: c1Preds)
        {
            for (Predicate c2pred: c2Preds)
            {
                if (c1pred.name.toString().equals(c2pred.name.toString()))
                {
                    return true;
                }
            }
        }
        return false;
    }


    public ArrayList<Clause> PCResolve(Clause Ci, Clause Cj) 
    {
        ArrayList<Predicate> CiLits= Ci.getLiterals();
        ArrayList<Predicate> CjLits= Cj.getLiterals();    
        ArrayList<Clause> newClasues = new ArrayList<>();
        if (Ci.isUnit() && Cj.isUnit())
        {
            Predicate CiUnitPred = CiLits.get(0);
            Predicate CjUnitPred = CjLits.get(0);
            Boolean sameargs = true;
            ArrayList<Term> CiUnitArgs = CiUnitPred.getArguments();
            ArrayList<Term> CjUnitArgs = CjUnitPred.getArguments();
            int i = 0;
            while (i < CiUnitArgs.size())
            {
                    if (!CiUnitArgs.get(i).toString().equals(CjUnitArgs.get(i).toString()))
                    {
                        sameargs = false;
                    }
                    i++;
            }
            if (sameargs)
            {
                if ((CiUnitPred.is_negated && !CjUnitPred.is_negated) || (!CiUnitPred.is_negated && CjUnitPred.is_negated))
                {
                    // System.out.println(Ci.toString() + " and  " + Cj.toString() + " to []");
                        newClasues.add(new Clause(null));
                }
            }
        }
        else if (Ci.isUnit() && !Cj.isUnit())
        {
            Predicate CiUnitPred = CiLits.get(0);
            for (Predicate pred: CjLits)
            {
                if ((CiUnitPred.name.contentEquals(pred.name)) && ((CiUnitPred.is_negated && !pred.is_negated) || (!CiUnitPred.is_negated && pred.is_negated)))
                {
                    Boolean sameargs = true;
                    ArrayList<Term> CiUnitArgs = CiUnitPred.getArguments();
                    ArrayList<Term> predArgs = pred.getArguments();
                    int i = 0;
                    while (i < CiUnitArgs.size())
                    {
                            if (!CiUnitArgs.get(i).toString().equals(predArgs.get(i).toString()))
                            {
                                sameargs = false;
                            }
                            i++;
                    }
                    if (sameargs)
                    {
                        // System.out.println(Ci.toString() + " and  " + Cj.toString());
                        ArrayList<Predicate> newCj = new ArrayList<>();
                        newCj.addAll(CjLits);
                        newCj.remove(CjLits.indexOf(pred));
                        // System.out.println(" to " + CjLits);
                        newClasues.add(new Clause(newCj));
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
                    while (i < CjUnitArgs.size())
                    {
                            if (!(CjUnitArgs.get(i).toString().equals(predArgs.get(i).toString())))
                            {
                                sameargs = false;
                            }
                            i++;
                    }
                    if (sameargs)
                    {
                        // System.out.println(Ci.toString() + " and  " + Cj.toString());
                        ArrayList<Predicate> newCi = new ArrayList<>();
                        newCi.addAll(CiLits);
                        newCi.remove(CiLits.indexOf(pred));
                        // System.out.println(" to " + CiLits);
                        newClasues.add(new Clause(newCi));
                    }
                } 
            }
        }
        else
        {
            for (Predicate CiPred: CiLits)
            {
                for (Predicate CjPred: CjLits)
                {
                    if (CiPred.name.toString().contains(CjPred.name.toString()))
                    {
                        if ((CiPred.is_negated && !CjPred.is_negated) || (!CiPred.is_negated && CjPred.is_negated))
                        {
                            Boolean sameargs = true;
                            ArrayList<Term> CiArgs = CiPred.getArguments();
                            ArrayList<Term> CjArgs = CjPred.getArguments();
                            int i = 0;
                            while (i < CiArgs.size())
                            {
    
                                    if (!CiArgs.get(i).toString().equals(CjArgs.get(i).toString()))
                                    {
                                        sameargs = false;
                                    }
                                    i++;
    
                            }
                            if (sameargs)
                            {
                                // System.out.println(Ci.toString() + " and  " + Cj.toString());
                                ArrayList<Predicate> newCi = new ArrayList<>();
                                newCi.addAll(CiLits);
                                newCi.remove(CiPred);
                                ArrayList<Predicate> newCj = new ArrayList<>();
                                newCj.addAll(CjLits);
                                newCj.remove(CjPred);
                                newCi.addAll(newCj);
                                newClasues.add(new Clause(newCi));
                            }
                        }
                    }
                }
            }
        }
        return newClasues;
    }
}
