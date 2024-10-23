public class Constant extends Term{
    private String name;

    public Constant(String name)
    {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
