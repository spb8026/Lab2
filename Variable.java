public class Variable extends Term {
    String name;
    Term curRelation;
    public Variable(String name)
    {
        this.name = name;
    }

    public void setCurRelation(Term relation)
    {
        this.curRelation = relation;
    }

}
