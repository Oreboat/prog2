package Special;

import Tree.Node;
import Tree.Environment;
import Tree.Nil;
import Print.Printer;

public class Begin extends Special {

    public void print(Node t, int n, boolean p) {
        Printer.printBegin(t, n, p);
    }

    public Node eval(Node t, Environment env) {
        Node res = Nil.getInstance();
        Node exprs = t.getCdr();

        while (!exprs.isNull()) {
            res = exprs.getCar().eval(env);
            exprs = exprs.getCdr();
        }

        return res;
    }
}
