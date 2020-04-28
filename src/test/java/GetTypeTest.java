import java.lang.reflect.Field;

public class GetTypeTest {
    public static class Test {
        String x;
        int y;
        double z;
        long v;
    }

    public static void main(String[] args) {
        Test gtt = new Test();
        Field[] fields = gtt.getClass().getDeclaredFields();
        System.out.println(fields[0].getType());
        System.out.println(fields[1].getType());
        System.out.println(fields[2].getType());
        System.out.println(fields[3].getType());
    }
}
