package Special;

import Tree.Node;
import Tree.Environment;
import Tree.Cons;
import Tree.Nil;
import Tree.Ident;
import Print.Printer;

public class Define extends Special {

    public void print(Node t, int n, boolean p) {
        Printer.printDefine(t, n, p);
    }

    public Node eval(Node t, Environment env) {
        Node target = t.getCdr().getCar();
        Node value;

        if (target.isSymbol()) {
            value = t.getCdr().getCdr().getCar().eval(env);
            env.define(target, value);
        } else if (target.isPair()) {
            Node name = target.getCar();
            Node params = target.getCdr();
            Node body = t.getCdr().getCdr();
            Node lambda = new Cons(new Ident("lambda"), new Cons(params, body));
            value = lambda.eval(env);
            env.define(name, value);
        } else {
            System.err.println("Error: Illegal define");
        }
        return Nil.getInstance();
    }
}
