import java.sql.Connection;

public class ApartmentDAO extends AbstractDAO<Integer, Apartment> {
    public ApartmentDAO(Connection conn, String table) {
        super(conn, table);
    }
}
