import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Hashtable;
import java.util.Scanner; // Import the Scanner class to read text files

public class KnowledgeBase {
    private Hashtable<String,Term>  terms;

    private Hashtable<String,Predicate> predicates;
    private ArrayList<Clause> clauses;


    public KnowledgeBase(String filename) throws FileNotFoundException
    {
        Scanner scanner = new Scanner(new File(filename));

        Hashtable<String,Term>  terms = new Hashtable<>();

    
        Hashtable<String,Predicate> predicates = new Hashtable<>();
        ArrayList<Clause> clauses = new ArrayList<>();
    

        /// Get Predicates
        scanner.next();
        String[] predStrings = scanner.nextLine().strip().split("\\s+");
        for( String pred: predStrings)
        {
            predicates.put(pred, new Predicate(pred));
        }
        // Get Vars
        scanner.next();
        String[] varStrings = scanner.nextLine().strip().split("\\s+");
        for (String vars: varStrings)
        {
            terms.put(vars,new Variable(vars));
        }
        // Get Constants
        scanner.next();
        String[] constStrings = scanner.nextLine().strip().split("\\s+");
        for (String consts: constStrings)
        {
            terms.put(consts, new Constant(consts));
        }
        scanner.next();
        String[] funcStrings = scanner.nextLine().split("\\s+");
        for (String funcs: funcStrings)
        {
            terms.put(funcs, new Function(filename, null));
        }
        scanner.nextLine();
        String line;
        this.terms = terms;
        while (scanner.hasNextLine())
        {
            line = scanner.nextLine();
            clauses.add(parseClause(line));
        }
        this.clauses = clauses;
        this.predicates = predicates;
        scanner.close();
    }

    private Clause parseClause(String line)
    {
        ArrayList<Predicate> clausePredicates = new ArrayList<>();
        String[] predicateStrings = line.split("\\s+");
        for (String pred: predicateStrings)
        {
            clausePredicates.add(parsePredicate(pred));
        }
        return new Clause(clausePredicates);

    }

    private Predicate parsePredicate(String predString)
    {
        Boolean isNegated;
        if (predString.charAt(0) == '!')
        {
            isNegated = true;
            predString = predString.substring(1);
        }
        else
        {
            isNegated = false;
        }
        String predName = predString.substring(0,predString.indexOf("("));
        predString = predString.substring(predString.indexOf('(')+1);
        ArrayList<Term> arguments = new ArrayList<>();
        int i = 0;
        while (i < predString.length()-1)
        {
            String termString = "";
            while (predString.charAt(i) != ',' && predString.charAt(i) != ')')
            {
                termString += predString.charAt(i);
                i ++;
            }
            arguments.add(terms.get(termString));
            i ++;
        }
        return new Predicate(predName, isNegated, arguments);
    }


}
