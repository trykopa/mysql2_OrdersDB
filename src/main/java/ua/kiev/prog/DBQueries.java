package ua.kiev.prog;

import java.sql.*;

public class DBQueries {

        public static void addClient(Connection conn, String clNa, String clPh, String clAd) throws SQLException {
            String sql = "INSERT INTO `clients` (`name`, `phone`, `address`) VALUES (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, clNa);
            ps.setString(2, clPh);
            ps.setString(3, clAd);;
            ps.execute();
            System.out.printf("Новый клиент добавлен! %s %s %s %n", clNa, clPh, clAd);
        }

        public static void showClients(Connection conn) throws SQLException {
            String sql = "SELECT * FROM clients";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                System.out.print(name);
                System.out.printf(" Телефон %s ", phone);
                System.out.println("Адресс " + address);
            }

        }


}
