package Special;

import Tree.Node;
import Tree.Environment;
import Tree.Closure;
import Print.Printer;

public class Lambda extends Special {

    public void print(Node t, int n, boolean p) {
        Printer.printLambda(t, n, p);
    }

    public Node eval(Node t, Environment env) {
        return new Closure(t, env);
    }
}
