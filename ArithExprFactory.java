
public class ArithExprFactory {
    [cite_start]// 1. สร้างตัวแปร static เพื่อเก็บอินสแตนซ์หนึ่งเดียวของคลาส
    private static ArithExprFactory instance;

    [cite_start]// 2. กำหนด Constructor เป็น private เพื่อป้องกันการสร้างออบเจกต์จากภายนอก
    private ArithExprFactory() {}

    [cite_start]// 3. เมธอดสำหรับเข้าถึงอินสแตนซ์ของ Factory (Singleton) 
    public static ArithExprFactory getInstance() {
        if (instance == null) {
            [cite_start]// ใช้ Lazy Instantiation ในการสร้างออบเจกต์เมื่อถูกเรียกใช้ครั้งแรก
            instance = new ArithExprFactory();
        }
        return instance;
    }

    public Expr createIntLit(int val) {
        return new IntLit(val);
    }


    public Expr createVariable(String name) {
        return new Variable(name);
    }


    public Expr createBinaryArithExpr(Expr left, String op, Expr right) {
        return new BinaryArithExpr(left, op, right);
    }
}