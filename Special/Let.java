package Special;

import Tree.Node;
import Tree.Environment;
import Tree.Nil;
import Print.Printer;

public class Let extends Special {

    public void print(Node t, int n, boolean p) {
        Printer.printLet(t, n, p);
    }

    public Node eval(Node t, Environment env) {
        Node bindings = t.getCdr().getCar();
        Node body = t.getCdr().getCdr();

        Environment newEnv = new Environment(env);

        Node currBinding = bindings;
        while (currBinding.isPair()) {
            Node binding = currBinding.getCar();
            Node id = binding.getCar();
            Node val = binding.getCdr().getCar().eval(env);
            newEnv.define(id, val);
            currBinding = currBinding.getCdr();
        }

        Node res = Nil.getInstance();
        while (body.isPair()) {
            res = body.getCar().eval(newEnv);
            body = body.getCdr();
        }

        return res;
    }
}
