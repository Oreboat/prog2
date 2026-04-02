// Closure.java -- the data structure for function closures

// Class Closure is used to represent the value of lambda expressions.
// It consists of the lambda expression itself, together with the
// environment in which the lambda expression was evaluated.

// The method apply() takes the environment out of the closure,
// adds a new frame for the function call, defines bindings for the
// parameters with the argument values in the new frame, and evaluates
// the function body.

package Tree;

public class Closure extends Node {
    private Node fun;
    private Environment env;

    public Closure(Node f, Environment e) {
        fun = f;
        env = e;
    }

    public Node getFun() {
        return fun;
    }

    public Environment getEnv() {
        return env;
    }

    public boolean isProcedure() {
        return true;
    }

    public void print(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(' ');
        System.out.println("#{Procedure");
        if (fun != null)
            fun.print(Math.abs(n) + 2);
        for (int i = 0; i < Math.abs(n); i++)
            System.out.print(' ');
        System.out.println(" }");
    }

    public Node apply(Node args) {
        Node params = fun.getCdr().getCar();
        Node body = fun.getCdr().getCdr();
        Environment newEnv = new Environment(env);

        Node p = params;
        Node a = args;

        while (p.isPair()) {
            if (a.isPair()) {
                newEnv.define(p.getCar(), a.getCar());
                p = p.getCdr();
                a = a.getCdr();
            } else {
                System.err.println("Error: too few arguments provided to procedure");
                return Nil.getInstance();
            }
        }

        if (a.isPair()) {
            System.err.println("Error: too many arguments provided to procedure");
            return Nil.getInstance();
        }

        Node res = Nil.getInstance();
        while (body.isPair()) {
            res = body.getCar().eval(newEnv);
            body = body.getCdr();
        }

        return res;
    }
}
