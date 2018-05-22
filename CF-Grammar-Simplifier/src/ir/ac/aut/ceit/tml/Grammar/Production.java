package ir.ac.aut.ceit.tml.grammar;


public class Production {
    private char leftSide;
    private String rightSide;

    public Production(char leftSide, String rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    public char getLeftSide() {
        return leftSide;
    }

    public String getRightSide() {
        return rightSide;
    }

    @Override
    public String toString() {
        return leftSide + "->" + rightSide;
    }

    @Override
    public boolean equals(Object obj) {
        Production production = (Production) obj;
        return (production.getLeftSide() == this.leftSide && production.getRightSide().equals(this.rightSide));
    }

}
