public class MathHelper {

    final public int simple_int = 7;
    public static int static_int = 7;

    public void changeSimpleInt() {
        //this.simple_int = 8;
        static_int = 8;//Из нестатических методов работать со статическими данными можно
    }

    public static void changeSijmpleIntByStaticFunction() {
        //ошибка в этом поле так как не можем обращаться к нестатическим переменным при помощи статического метода
        //this.simple_int = 8;
    }
    //публичный метод
    public int calc(int a, int b, char action) {
        if (action == '+'){
            return this.plus(a, b);
        } else if (action == '-') {
            return this.minus(a,b);
        } else if (action == '*') {
            return this.multiply(a,b);
        } else if (action == '/') {
            return this.divide(a,b);
        } else {
            return this.typeAnErrorAndReturnDefaultValue("wrong action " + action);
        }
    }

    //Приватный метод
    private int typeAnErrorAndReturnDefaultValue(String error_meesage) {
        System.out.println(error_meesage);
        return 0;
    }
    private int plus(int a, int b) {
        return a + b;
    }
    private int minus(int a, int b) {
        return a - b;
    }
    private int multiply(int a, int b) {
        return a * b;
    }
    private int divide(int number, int divider) {
        if (divider == 0) {
            return this.typeAnErrorAndReturnDefaultValue("Cannot divide by 0");
        }else {
            return number/divider;
        }
    }
}
