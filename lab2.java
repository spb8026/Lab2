import java.io.FileNotFoundException;
import java.util.ArrayList;

public class lab2 {

    public static void main(String[] args) throws FileNotFoundException {
        KnowledgeBase KB = new KnowledgeBase(args[0]);
        Resolver resolver = new Resolver(KB);
        Boolean ans = resolver.queResolve();
        if(ans)
        {
            System.out.println("yes");
        }
        else
        {
            System.out.println("no");
        }
    }

}
