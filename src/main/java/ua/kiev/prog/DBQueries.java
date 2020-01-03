package ua.kiev.prog;

import java.sql.*;
import java.util.List;

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

        public static void addGoods(Connection conn, String article, String description, float price) throws SQLException {
            String sql = "INSERT INTO `goods` (`article`, `description`, `price`) VALUES (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, article);
            ps.setString(2, description);
            ps.setFloat(3, price);;
            ps.execute();
            System.out.printf("Новый товар добавлен! %s %s %.2f %n", article, description, price);

        }

        public static void showClients(Connection conn) throws SQLException {
            String sql = "SELECT * FROM clients";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            printRes(rs);
        }


    public static void showGoods(Connection conn) throws SQLException {
        String sql = "SELECT * FROM goods";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            String article = rs.getString("article");
            String description = rs.getString("description");
            float price = rs.getFloat("price");
            System.out.print(article);
            System.out.printf(" описание товара: %s ", description);
            System.out.printf("цена товара: %.2f %n", price);
        }

    }

    public static void searchClient(Connection conn, int search) throws SQLException {
        String sql = "SELECT * FROM clients WHERE idclients = " + search;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        printRes(rs);
    }

    private static void printRes(ResultSet rs) throws SQLException {
        while (rs.next()){
            int id = rs.getInt("idclients");
            String name = rs.getString("name");
            String phone = rs.getString("phone");
            String address = rs.getString("address");
            System.out.printf("ID клиента: %d - ", id);
            System.out.print(name);
            System.out.printf(" Телефон %s ", phone);
            System.out.println("Адресс " + address);
        }
    }

    public static int checkArticle(Connection conn, String check) throws SQLException {
            int id = 0;
            String sql = "SELECT idgoods FROM goods WHERE article =" + "'"+check+"'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()){
            id = rs.getInt("idgoods");
        }
        else {
            System.out.println("Указанный артикль не найден в базе");
        }
        return id;
    }

    public static void createOrder(Connection conn, int idclients, List<OrderItem> list) throws SQLException {
        conn.setAutoCommit(false);
        Savepoint savepointOne = conn.setSavepoint("SavepointOne");
        try {
            String sql = "INSERT INTO `orders` (`idclients`, `created`, `total`) VALUES (?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            java.util.Date date = new java.util.Date();
            ps.setInt(1, idclients);
            ps.setTimestamp(2, new java.sql.Timestamp(date.getTime()));
            ps.setFloat(3, getTotalPrice(conn, list));
            ps.execute();

            int id = 0;
            PreparedStatement st = conn.prepareStatement("SELECT MAX(idorders) AS id from orders");
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            sql = "INSERT INTO order_item (order_num, goods, goods_num) VALUES (?, ?, ?)";
            PreparedStatement res = conn.prepareStatement(sql);
            for (OrderItem item : list) {
                res.setInt(1, id);
                res.setInt(2, item.getGood());
                res.setInt(3, item.getGood_number());
                res.execute();

            }
            System.out.printf("Заказ %d на сумму %f оформлен!%n", id, getTotalPrice(conn, list));
            conn.commit();
        } catch (SQLException e) {
            System.out.println("SQLException. Executing rollback to savepoint...");
            conn.rollback(savepointOne);
        }


    }

    public static float getTotalPrice(Connection conn, List<OrderItem> list) throws SQLException {
        float total = 0;
        String sql = "SELECT price FROM goods WHERE idgoods = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (OrderItem item : list){
            ps.setInt(1, item.getGood());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = total + rs.getFloat(1);
            }

        }
        return total;
    }

    public static void showOrders(Connection conn) throws SQLException {
        String sql = "SELECT idorders, clients.name, created \n" +
                "FROM orders, clients \n" +
                "WHERE orders.total > 0 \n" +
                "AND clients.idclients = orders.idclients\n" +
                "GROUP BY idorders;";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            int id = rs.getInt("idorders");
            String name = rs.getString("name");
            String create = rs.getString("created");
            System.out.printf("Номер заказа %d Имя клиента %s Дата создания %s %n", id, name, create);
        }

    }
}
