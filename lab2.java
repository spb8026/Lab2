import java.io.FileNotFoundException;
import java.util.ArrayList;

public class lab2 {

    public static void main(String[] args) throws FileNotFoundException {
        // KnowledgeBase KB = new KnowledgeBase("./functions/f2.cnf");
        KnowledgeBase KB = new KnowledgeBase("./constants/c01.cnf");
        Resolver resolver = new Resolver(KB);
        resolver.resolve();
    }

}
