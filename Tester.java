import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Tester {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<KnowledgeBase> KBases = new ArrayList<>();
        // KBases.add(new KnowledgeBase("./constants/c01.cnf"));
        // KBases.add(new KnowledgeBase("./constants/c02.cnf"));
        // KBases.add(new KnowledgeBase("./constants/c03.cnf"));
        // KBases.add(new KnowledgeBase("./constants/c04.cnf"));
        // KBases.add(new KnowledgeBase("./constants/c05.cnf"));
        // KBases.add(new KnowledgeBase("./constants/c06.cnf"));
        // KBases.add(new KnowledgeBase("./constants/c07.cnf"));
        // KBases.add(new KnowledgeBase("./constants/c08.cnf"));
        // KBases.add(new KnowledgeBase("./constants/c09.cnf"));

        // KBases.add(new KnowledgeBase("./functions/f1.cnf"));
        // KBases.add(new KnowledgeBase("./functions/f2.cnf"));
        // KBases.add(new KnowledgeBase("./functions/f3.cnf"));
        // KBases.add(new KnowledgeBase("./functions/f4.cnf"));
        // KBases.add(new KnowledgeBase("./functions/f5.cnf"));
        // KBases.add(new KnowledgeBase("./functions/f6.cnf"));
        // KBases.add(new KnowledgeBase("./functions/f7.cnf"));

        KBases.add(new KnowledgeBase("./prop/p01.cnf"));
        KBases.add(new KnowledgeBase("./prop/p02.cnf"));
        KBases.add(new KnowledgeBase("./prop/p03.cnf"));
        KBases.add(new KnowledgeBase("./prop/p04.cnf"));
        KBases.add(new KnowledgeBase("./prop/p05.cnf"));
        KBases.add(new KnowledgeBase("./prop/p06.cnf"));
        KBases.add(new KnowledgeBase("./prop/p07.cnf"));
        KBases.add(new KnowledgeBase("./prop/p08.cnf"));
        KBases.add(new KnowledgeBase("./prop/p09.cnf"));
        KBases.add(new KnowledgeBase("./prop/p10.cnf"));
        KBases.add(new KnowledgeBase("./prop/p11.cnf"));
        KBases.add(new KnowledgeBase("./prop/p13.cnf"));
        
        for(KnowledgeBase KB: KBases)
        {
            KB.printKB();
            Resolver resolver = new Resolver(KB);
            System.out.println(resolver.resolve());
        }

    }

}
