import Parse.Scanner;
import Parse.Parser;
import Tokens.Token;
import Tokens.TokenType;
import Tree.*;

public class Scheme4101 {

    private static Environment builtinEnv = null;
    private static Environment globalEnv = null;

    public static void main(String argv[]) {

        Scanner scanner = new Scanner(System.in);

        if (argv.length > 1 || (argv.length == 1 && !argv[0].equals("-d"))) {
            System.err.println("Usage: java Scheme4101 [-d]");
            System.exit(1);
        }

        if (argv.length == 1 && argv[0].equals("-d")) {
            Token tok = scanner.getNextToken();
            while (tok != null) {
                TokenType tt = tok.getType();
                System.out.print(tt.name());
                if (tt == TokenType.INT)
                    System.out.println(", intVal = " + tok.getIntVal());
                else if (tt == TokenType.STRING)
                    System.out.println(", strVal = " + tok.getStrVal());
                else if (tt == TokenType.IDENT)
                    System.out.println(", name = " + tok.getName());
                else
                    System.out.println();
                tok = scanner.getNextToken();
            }
            System.exit(0);
        }

        Parser parser = new Parser(scanner);

        builtinEnv = new Environment();
        globalEnv = new Environment(builtinEnv);
        BuiltIn.setGlobalEnv(globalEnv);

        String[] primitives = {
            "symbol?", "number?", "procedure?", "null?", "pair?", "eq?",
            "car", "cdr", "cons", "set-car!", "set-cdr!",
            "b+", "b-", "b*", "b/", "b=", "b<",
            "read", "write", "display", "newline",
            "eval", "apply", "interaction-environment", "load"
        };

        for (String name : primitives) {
            builtinEnv.define(new Ident(name), new BuiltIn(new Ident(name)));
        }

        Node loadSym = new Ident("load");
        Node iniFile = new StrLit("ini.scm");
        Node loadCall = new Cons(loadSym, new Cons(iniFile, Nil.getInstance()));
        loadCall.eval(globalEnv);

        while (true) {
            System.out.print("> ");
            Node root = parser.parseExp();
            if (root == null) {
                break;
            }
            Node result = root.eval(globalEnv);
            if (result != null && !result.isNull()) {
                result.print(0);
            }
        }
        System.exit(0);
    }
}
