package Special;

import Tree.Node;
import Tree.Environment;
import Print.Printer;

public class Quote extends Special {

    public void print(Node t, int n, boolean p) {
        Printer.printQuote(t, n, p);
    }

    public Node eval(Node t, Environment env) {
        return t.getCdr().getCar();
    }
}
