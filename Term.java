public abstract class Term {
    private String name;

    public abstract String toString();   
    
    public Term applySubstitution(Subsitution theta) {
        if (this instanceof Variable && theta.contains(this)) {
            return theta.getVal(this);  // Return the term the variable is mapped to
        }
        return this;  // If no substitution found, return the original term
    }

    public Term deepCopy()
    {
        if (this instanceof Variable)
        {
            return new Variable(name);
        }
        else if (this instanceof Constant)
        {
            return new Constant(name);
        }
        else if (this instanceof Function)
        {
            return new Function(name);
        }
        else
        {
            return null;
        }
    }


}
