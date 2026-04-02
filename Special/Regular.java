package Special;

import Tree.Node;
import Tree.Environment;
import Tree.Cons;
import Tree.Nil;
import Print.Printer;

public class Regular extends Special {

    public void print(Node t, int n, boolean p) {
        Printer.printRegular(t, n, p);
    }

    public Node eval(Node t, Environment env) {
        Node function = t.getCar().eval(env);
        Node args = evalArgs(t.getCdr(), env);

        return function.apply(args);
    }

    private Node evalArgs(Node args, Environment env) {
        if (args.isNull()) {
            return Nil.getInstance();
        }
        return new Cons(args.getCar().eval(env), evalArgs(args.getCdr(), env));
    }
}
