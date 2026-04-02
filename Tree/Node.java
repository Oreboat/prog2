package Tree;

public class Node {
    public void print(int n) {}

    public void print(int n, boolean p) {
        print(n);
    }

    public boolean isBoolean() { return false; }
    public boolean isNumber() { return false; }
    public boolean isString() { return false; }
    public boolean isSymbol() { return false; }
    public boolean isNull() { return false; }
    public boolean isPair() { return false; }
    public boolean isProcedure() { return false; }
    public boolean isEnvironment() { return false; }

    public static void print(Node t, int n, boolean p) {
        t.print(n, p);
    }

    public static Node getCar(Node t) {
        return t.getCar();
    }

    public static Node getCdr(Node t) {
        return t.getCdr();
    }

    public static boolean isNull(Node t) {
        return t.isNull();
    }

    public static boolean isPair(Node t) {
        return t.isPair();
    }

    public Node getCar() {
        System.err.println("Error: argument of car is not a pair");
        return Nil.getInstance();
    }

    public Node getCdr() {
        System.err.println("Error: argument of cdr is not a pair");
        return Nil.getInstance();
    }

    public void setCar(Node a) {
        System.err.println("Error: argument of set-car! is not a pair");
    }

    public void setCdr(Node d) {
        System.err.println("Error: argument of set-cdr! is not a pair");
    }

    public String getName() { return ""; }

    public int getVal() {
        System.err.println("Error: Node is not a number");
        return 0;
    }

    public String getStrVal() {
        System.err.println("Error: Node is not a string");
        return "";
    }

    public Node eval(Environment env) {
        System.err.println("Error: eval not implemented for " + this.getClass().getSimpleName());
        return Nil.getInstance();
    }

    public Node apply(Node args) {
        System.err.println("Error: apply called on a non-procedure node");
        return Nil.getInstance();
    }
}
