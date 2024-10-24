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
        // KBases.add(new KnowledgeBase("./functions/f2.cnf")); ////CHECK
        // KBases.add(new KnowledgeBase("./functions/f3.cnf"));
        // KBases.add(new KnowledgeBase("./functions/f4.cnf"));
        // KBases.add(new KnowledgeBase("./functions/f5.cnf"));
        // KBases.add(new KnowledgeBase("./functions/f6.cnf"));
        // KBases.add(new KnowledgeBase("./functions/f7.cnf"));

        // KBases.add(new KnowledgeBase("./prop/p01.cnf"));
        // KBases.add(new KnowledgeBase("./prop/p02.cnf"));
        // KBases.add(new KnowledgeBase("./prop/p03.cnf"));
        // KBases.add(new KnowledgeBase("./prop/p04.cnf"));
        // KBases.add(new KnowledgeBase("./prop/p05.cnf"));
        // KBases.add(new KnowledgeBase("./prop/p06.cnf"));
        // KBases.add(new KnowledgeBase("./prop/p07.cnf"));
        // KBases.add(new KnowledgeBase("./prop/p08.cnf"));
        // KBases.add(new KnowledgeBase("./prop/p09.cnf"));
        // KBases.add(new KnowledgeBase("./prop/p10.cnf"));
        // KBases.add(new KnowledgeBase("./prop/p11.cnf"));
        // KBases.add(new KnowledgeBase("./prop/p13.cnf"));
        
        // KBases.add(new KnowledgeBase("./universals/u01.cnf"));
        // KBases.add(new KnowledgeBase("./universals/u02.cnf"));
        // KBases.add(new KnowledgeBase("./universals/u03.cnf"));
        // KBases.add(new KnowledgeBase("./universals/u04.cnf"));
        // KBases.add(new KnowledgeBase("./universals/u05.cnf"));
        // KBases.add(new KnowledgeBase("./universals/u06.cnf"));

        // KBases.add(new KnowledgeBase("./universals+constants/uc01.cnf"));
        // KBases.add(new KnowledgeBase("./universals+constants/uc02.cnf"));
        // KBases.add(new KnowledgeBase("./universals+constants/uc03.cnf"));
        // KBases.add(new KnowledgeBase("./universals+constants/uc04.cnf"));
        // KBases.add(new KnowledgeBase("./universals+constants/uc05.cnf"));
        // KBases.add(new KnowledgeBase("./universals+constants/uc06.cnf"));
        // KBases.add(new KnowledgeBase("./universals+constants/uc07.cnf"));
        // KBases.add(new KnowledgeBase("./universals+constants/uc08.cnf"));
        KBases.add(new KnowledgeBase("./universals+constants/uc09.cnf"));



        for(KnowledgeBase KB: KBases)
        {
            KB.printKB();
            Resolver resolver = new Resolver(KB);
            boolean result = resolver.queResolve();
            System.out.println(KB.filename);
            if (result)
            {
                System.out.println("yes");
            }
            else
            {
                System.out.println("no");
            }
        }

    }

}
