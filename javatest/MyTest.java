import org.junit.Test;

import java.security.MessageDigest;

public class MyTest {

    public static boolean booleanExpression(boolean a, boolean b, boolean c, boolean d) {

        if(a==b){
            return a;
        }else {
            return a || b || c || d;
        }

        //System.out.print(booleanExpression(false, false, false, false));
    }


}