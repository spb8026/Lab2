public class Pair{
    private Clause first;
    private Clause second;

    public Pair(Clause first, Clause second) {
        this.first = first;
        this.second = second;
    }

    public Clause getFirst() {
        return first;
    }

    public Clause getSecond() {
        return second;
    }

    public void setFirst(Clause first) {
        this.first = first;
    }

    public void setSecond(Clause second) {
        this.second = second;
    }
}

