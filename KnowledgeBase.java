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
    public String filename;


    public KnowledgeBase(String filename) throws FileNotFoundException
    {
        this.filename = filename;
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
        /// Get Functions
        scanner.next();
        String[] funcStrings = scanner.nextLine().strip().split("\\s+");
        for (String funcs: funcStrings)
        {
            terms.put(funcs, new Function(funcs, new ArrayList<Term>()));
        }
        /// Get Clauses
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

    private Predicate parsePredicate(String predString) {
        boolean isNegated = false;

    
        if (predString.charAt(0) == '!') {
            isNegated = true;
            predString = predString.substring(1);
        }
        String predName = predString;
        ArrayList<Term> arguments = new ArrayList<>();
    
        if (predString.contains("(")) {
            predName = predString.substring(0, predString.indexOf("("));
            String argString = predString.substring(predString.indexOf('(') + 1, predString.lastIndexOf(')')).strip();
    
            // Split arguments
            int i = 0;
            while (i < argString.length()) {
                StringBuilder termString = new StringBuilder();
                while (i < argString.length() && argString.charAt(i) != ',' && argString.charAt(i) != ')') {
                    termString.append(argString.charAt(i));
                    i++;
                }
    
                String term = termString.toString().trim();
                if (term.contains("(")) {
                    // Parse function
                    term += ")";
                    i++;
                    arguments.add(parseFunction(term));
                } else if (terms.containsKey(term)) {
                    // If it's a variable or constant
                    arguments.add(terms.get(term));
                } else {
                    throw new IllegalArgumentException("Undefined term: " + term);
                }
    
                // Move past the comma
                i++;
            }
        }
    
        return new Predicate(predName, isNegated, arguments);
    }
    

    private Function parseFunction(String funcString) {
        String funcName = funcString.substring(0, funcString.indexOf('('));
        String argString = funcString.substring(funcString.indexOf('(') + 1, funcString.lastIndexOf(')'));
        ArrayList<Term> arguments = new ArrayList<>();
    
        // Split the arguments by comma, and parse each term
        int i = 0;
        while (i < argString.length()) {
            StringBuilder termString = new StringBuilder();
            while (i < argString.length() && argString.charAt(i) != ',' && argString.charAt(i) != ')') {
                termString.append(argString.charAt(i));
                i++;
            }
    
            // Check if the argument is a function or a simple term
            String term = termString.toString().trim();
            if (term.contains("(")) {
                // Recursively parse the function
                arguments.add(parseFunction(term));
            } else if (terms.containsKey(term)) {
                // If it's a variable or constant, use it from terms
                arguments.add(terms.get(term));
            } else {
                throw new IllegalArgumentException("Undefined term: " + term);
            }
    
            // Move past the comma
            i++;
        }
    
        return new Function(funcName, arguments);
    }
    

    public ArrayList<Clause> getClauses() {
        return clauses;
    }
    public Hashtable<String, Term> getTerms() {
        return terms;
    }
    public Hashtable<String, Predicate> getPredicates() {
        return predicates;
    }

    public void printKB() {
        //System.out.println(filename);
        for (Clause cl: clauses)
        {
            //System.out.println(cl.toString());
        }
        //System.out.println("??????????????????????????????????????????????????????????????????????????????????????");
    }

}
