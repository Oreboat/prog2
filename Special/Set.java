package Special;

import Tree.Node;
import Tree.Environment;
import Tree.Nil;
import Print.Printer;

public class Set extends Special {

    public void print(Node t, int n, boolean p) {
        Printer.printSet(t, n, p);
    }

    public Node eval(Node t, Environment env) {
        Node variable = t.getCdr().getCar();
        Node value = t.getCdr().getCdr().getCar().eval(env);

        if (variable.isSymbol()) {
            env.assign(variable, value);
        } else {
            System.err.println("Error: set! requires a symbol");
        }

        return Nil.getInstance();
    }
}
