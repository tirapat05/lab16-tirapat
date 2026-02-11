public class ExpressionReader {
    private Tokenizer tkz;
    [cite_start]// เรียกใช้ Singleton Factory
    private ArithExprFactory factory = ArithExprFactory.getInstance();

    public Expr parse(String expression) throws SyntaxError {
        this.tkz = new Tokenizer(expression);
        Expr e = parseE();
        if (tkz.hasNext()) throw new SyntaxError("Leftover tokens: " + tkz.peek());
        return e;
    }

    // E -> E + T | E - T | T
    private Expr parseE() throws SyntaxError {
        Expr e = parseT();
        while (tkz.peek("+") || tkz.peek("-")) {
            String op = tkz.consume();
            [cite_start]// ปรับปรุง ใช้ Factory แทนการ new BinaryArithExpr()
            e = factory.createBinaryArithExpr(e, op, parseT());
        }
        return e;
    }

    // T -> T * F | T / F | T % F | F
    private Expr parseT() throws SyntaxError {
        Expr e = parseF();
        while (tkz.peek("*") || tkz.peek("/") || tkz.peek("%")) {
            String op = tkz.consume();
            [cite_start]// ปรับปรุง: ใช้ Factory แทนการ new BinaryArithExpr()
            e = factory.createBinaryArithExpr(e, op, parseF());
        }
        return e;
    }

    // F -> n | x | ( E )
    private Expr parseF() throws SyntaxError {
        if (!tkz.hasNext()) throw new SyntaxError("Unexpected end of input");

        if (Character.isDigit(tkz.peek().charAt(0))) {
            [cite_start]// ปรับปรุง: ใช้ Factory แทนการ new IntLit()
            return factory.createIntLit(Integer.parseInt(tkz.consume()));
        } else if (Character.isLetter(tkz.peek().charAt(0))) {
            [cite_start]// ปรับปรุง: ใช้ Factory แทนการ new Variable()
            return factory.createVariable(tkz.consume());
        } else if (tkz.peek("(")) {
            tkz.consume();
            Expr e = parseE();
            if (!tkz.peek(")")) throw new SyntaxError("Missing )");
            tkz.consume();
            return e;
        }
        throw new SyntaxError("Unexpected token: " + tkz.peek());
    }
}