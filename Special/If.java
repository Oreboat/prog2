package Special;

import Tree.Node;
import Tree.Environment;
import Tree.Nil;
import Print.Printer;

public class If extends Special {

    public void print(Node t, int n, boolean p) {
        Printer.printIf(t, n, p);
    }

    public Node eval(Node t, Environment env) {
        Node cond = t.getCdr().getCar();
        Node res = cond.eval(env);

        if (res.isBoolean() && res.getName().equals("#f")) {
            Node elsePart = t.getCdr().getCdr().getCdr();
            if (elsePart.isNull()) {
                return Nil.getInstance();
            } else {
                return elsePart.getCar().eval(env);
            }
        } else {
            Node thenPart = t.getCdr().getCdr().getCar();
            return thenPart.eval(env);
        }
    }
}
