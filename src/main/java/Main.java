import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Apartment> list = new ArrayList<>();
        list.add(new Apartment("East", "Baker Street, 222", 80, 4, 33333));
        list.add(new Apartment("West", "5 avenue, 111", 60, 2, 22222));
        list.add(new Apartment("South", "Broadway, 333", 40, 1, 11111));
        list.add(new Apartment("North", "Sadova, 444", 120, 5, 44444));

        DbProperties props = new DbProperties();

        try(Connection conn = DriverManager.getConnection(props.getUrl(), props.getUser(), props.getPassword())) {
            ApartmentDAO dao = new ApartmentDAO(conn, "Apartment");
            dao.init(new Apartment());

            for (Apartment ar: list) {
                dao.add(ar);
            }
            Scanner s = new Scanner(System.in);
            StringBuilder st = new StringBuilder();

            System.out.println("Choose district (1 - East, 2 - West, 3 - South, 4 - North):");
            String tmp = s.nextLine();
            if (tmp.equals("1") | tmp.equals("2") | tmp.equals("3") | tmp.equals("4")) {
                switch (Integer.parseInt(tmp)) {
                    case 1:
                        st.append("district='East'");
                        break;
                    case 2:
                        st.append("district='West'");
                        break;
                    case 3:
                        st.append("district='South'");
                        break;
                    case 4:
                        st.append("district='North'");
                        break;
                    default:
                        break;
                }
                st.append(" and ");
            }
            System.out.println("Choose address (1 - Baker Street, 222, 2 - 5 avenue, 111, 3 - Broadway, 333, 4 - Sadova, 444):");
            tmp = s.nextLine();
            if (tmp.equals("1") | tmp.equals("2") | tmp.equals("3") | tmp.equals("4")) {
                switch (Integer.parseInt(tmp)) {
                    case 1:
                        st.append("address='Baker Street, 222'");
                        break;
                    case 2:
                        st.append("address='5 avenue, 111'");
                        break;
                    case 3:
                        st.append("address='Broadway, 333'");
                        break;
                    case 4:
                        st.append("address='Sadova, 444'");
                        break;
                    default:
                        break;
                }
                st.append(" and ");
            }

            System.out.println("Choose area (<, >, <=, >=, =):");
            tmp = s.nextLine();
            if (!tmp.equals("")) {
                if (tmp.charAt(0) == '<' | tmp.charAt(0) == '>' | tmp.charAt(0) == '=') {
                    st.append("area" + tmp + " and ");
                }
            }

            System.out.println("Choose number of rooms (1 - 5):");
            tmp = s.nextLine();
            if (!tmp.equals("")) {
                if (tmp.charAt(0) == '1' | tmp.charAt(0) == '2' | tmp.charAt(0) == '3' | tmp.charAt(0) == '4' | tmp.charAt(0) == '5') {
                    st.append("numberOfRooms=" + tmp + " and ");
                }
            }

            System.out.println("Choose price (<, >, <=, >=, =):");
            tmp = s.nextLine();
            if (!tmp.equals("")) {
                if (tmp.charAt(0) == '<' | tmp.charAt(0) == '>' | tmp.charAt(0) == '=') {
                    st.append("price" + tmp + " and ");
                }
            }

            if (st.toString().equals(""))
//            System.out.println(tmp);
                System.exit(0);

            st.delete(st.length() - 5, st.length());

            String params = st.toString() + ";";

            List<Apartment> list1 = dao.getByParams(Apartment.class, params);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
