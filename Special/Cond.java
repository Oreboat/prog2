package Special;

import Tree.Node;
import Tree.Environment;
import Tree.Nil;
import Print.Printer;

public class Cond extends Special {

    public void print(Node t, int n, boolean p) {
        Printer.printCond(t, n, p);
    }

    public Node eval(Node t, Environment env) {
        Node clauses = t.getCdr();

        while (clauses.isPair()) {
            Node clause = clauses.getCar();
            Node test = clause.getCar();

            if (test.isSymbol() && test.getName().equalsIgnoreCase("else")) {
                return evalBody(clause.getCdr(), env);
            }

            Node val = test.eval(env);
            if (!(val.isBoolean() && val.getName().equals("#f"))) {
                Node body = clause.getCdr();
                if (body.isNull()) {
                    return val;
                }
                return evalBody(body, env);
            }
            clauses = clauses.getCdr();
        }

        return Nil.getInstance();
    }

    private Node evalBody(Node body, Environment env) {
        Node res = Nil.getInstance();
        while (body.isPair()) {
            res = body.getCar().eval(env);
            body = body.getCdr();
        }
        return res;
    }
}
