// Environment.java -- a data structure for Scheme environments

// An Environment is a list of frames.  Each frame represents a scope
// in the program and contains a set of name-value pairs.  The first
// frame in the environment represents the innermost scope.

// For the code below, I am assuming that a scope is implemented
// as an association list, i.e., a list of two-element lists.  E.g.,
// the association list ((x 1) (y 2)) associates the value 1 with x
// and the value 2 with y.

// To implement environments in an object-oriented style, it would
// be better to define a Frame class and make an Environment a list
// of such Frame objects.  If we simply use the parse tree structure
// for lists of association lists, we end up having to write the
// lookup functions in a more functional style.

// You need the following methods for modifying environments:
//  - constructors:
//      - create the empty environment (an environment with an empty frame)
//      - add an empty frame to the front of an existing environment
//  - lookup the value for a name (for implementing variable lookup)
//      if the name exists in the innermost scope, return the value
//      if it doesn't exist, look it up in the enclosing scope
//      if we don't find the name, it is an error
//  - define a name (for implementing define and parameter passing)
//      if the name already exists in the innermost scope, update the value
//      otherwise add a name-value pair as first element to the innermost scope
//  - assign to a name (for implementing set!)
//      if the name exists in the innermost scope, update the value
//      if it doesn't exist, perform the assignment in the enclosing scope
//      if we don't find the name, it is an error

package Tree;

public class Environment extends Node {
    private Node scope;
    private Environment env;

    public Environment() {
        scope = Nil.getInstance();
        env = null;
    }

    public Environment(Environment e) {
        scope = Nil.getInstance();
        env = e;
    }

    public boolean isEnvironment() {
        return true;
    }

    public void print(int n) {
        for (int i = 0; i < n; i++)
            System.out.print(' ');
        System.out.println("#{Environment");
        if (scope != null)
            scope.print(Math.abs(n) + 2);
        if (env != null)
            env.print(Math.abs(n) + 2);
        for (int i = 0; i < Math.abs(n); i++)
            System.out.print(' ');
        System.out.println('}');
    }

    private static Node find(Node id, Node alist) {
        if (!alist.isPair())
            return null;
        else {
            Node bind = alist.getCar();
            if (id.getName().equals(bind.getCar().getName()))
                return bind.getCdr();
            else
                return find(id, alist.getCdr());
        }
    }

    public Node lookup(Node id) {
        Node val = find(id, scope);
        if (val == null && env == null) {
            System.err.println("Error: undefined variable " + id.getName());
            return Nil.getInstance();
        } else if (val == null)
            return env.lookup(id);
        else
            return val.getCar();
    }

    public void define(Node id, Node val) {
        Node pair = find(id, scope);
        if (pair != null) {
            pair.setCar(val);
        } else {
            Node newBinding = new Cons(id, new Cons(val, Nil.getInstance()));
            scope = new Cons(newBinding, scope);
        }
    }

    public void assign(Node id, Node val) {
        Node pair = find(id, scope);
        if (pair != null) {
            pair.setCar(val);
        } else if (env != null) {
            env.assign(id, val);
        } else {
            System.err.println("Error: undefined variable " + id.getName());
        }
    }
}
