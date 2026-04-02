// BuiltIn.java -- the data structure for function closures

// Class BuiltIn is used for representing the value of built-in functions
// such as +.  Populate the initial environment with
// (name, new BuiltIn(name)) pairs.

// The object-oriented style for implementing built-in functions would be
// to include the Java methods for implementing a Scheme built-in in the
// BuiltIn object.  This could be done by writing one subclass of class
// BuiltIn for each built-in function and implementing the method apply
// appropriately.  This requires a large number of classes, though.
// Another alternative is to program BuiltIn.apply() in a functional
// style by writing a large if-then-else chain that tests the name of
// of the function symbol.

package Tree;

import java.io.*;
import Parse.Parser;
import Parse.Scanner;

public class BuiltIn extends Node {
    private Node symbol;
    private static Environment globalEnv = null;

    public BuiltIn(Node s) {
        symbol = s;
    }

    public static void setGlobalEnv(Environment env) {
        globalEnv = env;
    }

    public Node getSymbol() {
        return symbol;
    }

    public boolean isProcedure() {
        return true;
    }

    public void print(int n) {
        for (int i = 0; i < Math.abs(n); i++)
            System.out.print(' ');
        System.out.print("#{Built-in Procedure ");
        if (symbol != null)
            symbol.print(-Math.abs(n) - 1);
        System.out.print('}');
        if (n >= 0)
            System.out.println();
    }

    public Node apply(Node args) {
        String name = symbol.getName();
        Node arg1 = args.getCar();
        Node arg2 = args.getCdr().getCar();

        if (name.equals("symbol?")) return new BoolLit(arg1.isSymbol());
        if (name.equals("number?")) return new BoolLit(arg1.isNumber());
        if (name.equals("procedure?")) return new BoolLit(arg1.isProcedure());
        if (name.equals("null?")) return new BoolLit(arg1.isNull());
        if (name.equals("pair?")) return new BoolLit(arg1.isPair());

        if (name.equals("car")) return arg1.getCar();
        if (name.equals("cdr")) return arg1.getCdr();
        if (name.equals("cons")) return new Cons(arg1, arg2);
        if (name.equals("set-car!")) { arg1.setCar(arg2); return Nil.getInstance(); }
        if (name.equals("set-cdr!")) { arg1.setCdr(arg2); return Nil.getInstance(); }
        if (name.equals("eq?")) return new BoolLit(arg1 == arg2);

        if (name.equals("b+")) return new IntLit(arg1.getVal() + arg2.getVal());
        if (name.equals("b-")) return new IntLit(arg1.getVal() - arg2.getVal());
        if (name.equals("b*")) return new IntLit(arg1.getVal() * arg2.getVal());
        if (name.equals("b/")) return new IntLit(arg1.getVal() / arg2.getVal());
        if (name.equals("b=")) return new BoolLit(arg1.getVal() == arg2.getVal());
        if (name.equals("b<")) return new BoolLit(arg1.getVal() < arg2.getVal());

        if (name.equals("read")) {
            Parser parser = new Parser(new Scanner(System.in));
            return parser.parseExp();
        }
        if (name.equals("write") || name.equals("display")) {
            arg1.print(0);
            return Nil.getInstance();
        }
        if (name.equals("newline")) {
            System.out.println();
            return Nil.getInstance();
        }

        if (name.equals("eval")) return arg1.eval((Environment) arg2);
        if (name.equals("apply")) return arg1.apply(arg2);
        if (name.equals("interaction-environment")) return globalEnv;

        if (name.equals("load")) {
            if (!arg1.isString()) return Nil.getInstance();
            try {
                Parser parser = new Parser(new Scanner(new FileInputStream(arg1.getStrVal())));
                Node root = parser.parseExp();
                while (root != null) {
                    root.eval(globalEnv);
                    root = parser.parseExp();
                }
            } catch (IOException e) {
                System.err.println("Could not find file " + arg1.getStrVal());
            }
            return Nil.getInstance();
        }

        return Nil.getInstance();
    }
}
