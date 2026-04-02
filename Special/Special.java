package Special;

import Tree.Node;
import Tree.Environment;

abstract public class Special {
    public abstract void print(Node t, int n, boolean p);
    public abstract Node eval(Node t, Environment env);
}
