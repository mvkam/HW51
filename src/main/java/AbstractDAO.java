import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractDAO <K, T> {
    private final Connection conn;
    private final String table;

    public AbstractDAO(Connection conn, String table) {
        this.conn = conn;
        this.table = table;
    }

    public void init(T t) {
        Field[] filds = t.getClass().getDeclaredFields();
        StringBuilder names = new StringBuilder();
        for (Field f: filds) {
            f.setAccessible(true);
            if (f.isAnnotationPresent(Id.class)) {
                names.append("id int NOT NULL AUTO_INCREMENT PRIMARY KEY, ");
                continue;
            }
            names.append(f.getName()).append(' ').append("varchar(255)").append(',');
        }
        names.deleteCharAt(names.length() - 1);

        String sql = "CREATE TABLE " + t.getClass().getName() + "(" + names.toString() + ");";

        try (Statement st = conn.createStatement()) {
            st.execute("drop table if exists " + t.getClass().getName());
            st.execute(sql);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public void add(T t) {
        try {
            Field[] fields = t.getClass().getDeclaredFields();

            StringBuilder names = new StringBuilder();
            StringBuilder values = new StringBuilder();

            for (Field f : fields) {
                f.setAccessible(true);

                names.append(f.getName()).append(',');
                values.append('"').append(f.get(t)).append("\",");
            }
            names.deleteCharAt(names.length() - 1); // last ','
            values.deleteCharAt(values.length() - 1); // last ','

            String sql = "INSERT INTO " + table + "(" + names.toString() +
                    ") VALUES(" + values.toString() + ")";

            try (Statement st = conn.createStatement()) {
                st.execute(sql);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<T> getByParams(Class<T> cls, String params) {
        List<T> res = new ArrayList<>();
        try {
            try (Statement st = conn.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT * FROM " + table + " WHERE " + params)) {
                    ResultSetMetaData md = rs.getMetaData();

//                    while (rs.next()) {
//                        T t = cls.newInstance();
//
//                        for (int i = 1; i <= md.getColumnCount(); i++) {
//                            String columnName = md.getColumnName(i);
//
//                            Field field = cls.getDeclaredField(columnName);
//                            field.setAccessible(true);
//
//                            field.set(t, (Integer) rs.getObject(columnName));
//                        }
//
//                        res.add(t);
//                    }

                    while (rs.next()) {
                        System.out.print(rs.getString(1) + "\t");
                        System.out.print(rs.getString(2) + "\t");
                        System.out.printf("%-30s", rs.getString(3));
                        System.out.print(rs.getString(4) + "\t");
                        System.out.print(rs.getString(5) + "\t");
                        System.out.print(rs.getString(6) + "\t");
                        System.out.println();
                    }
                    return res;
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
