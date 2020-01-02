package ua.kiev.prog;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBMenu {

    public static void showMenu() {
        System.out.println("Command Options: ");
        System.out.println("a: Добавить клиента");
        System.out.println("b: Добавить товар");
        System.out.println("c: Создать заказ");
        System.out.println("d: Вывести список клиентов");
        System.out.println("e: Вывести список товаров");
        System.out.println("f: Вывести список заказов");
        System.out.println("?: Показать это меню повторно");
        System.out.println("q: Quit");
    }

    public static void menuItemA(Connection conn) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите Имя клиента:");
        String name = sc.nextLine();
        System.out.println("Введите телефон клиента:");
        String phone = sc.nextLine();
        System.out.println("Введите адресс клиента:");
        String address = sc.nextLine();
        DBQueries.addClient(conn, name, phone, address);
    }

    public static void menuItemB(Connection conn) throws SQLException{
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите артикль товара");
        String article = sc.nextLine();
        System.out.println("Введите описание товара");
        String description = sc.nextLine();
        System.out.println("Введите стоимость товара хх.хх");
        float price = sc.nextFloat();
        DBQueries.addGoods(conn, article, description, price);
    }

    public static void menuItemC(Connection conn) throws SQLException{
        Scanner sc = new Scanner(System.in);
        boolean tmp = false;
        int clientID = 0;
        do {
            System.out.println("Введите выбранный ID клиента:");
            if (sc.hasNextInt()) {
                clientID = sc.nextInt();
                DBQueries.searchClient(conn, clientID);
                tmp = true;
            }
        } while (!tmp);
        List<OrderItem> list = createList(conn);
        DBQueries.createOrder(conn, clientID, list);


    }

    public static void menuItemD(Connection conn) throws SQLException {
        DBQueries.showClients(conn);
    }

    public static void menuItemE(Connection conn) throws SQLException {
        DBQueries.showGoods(conn);
    }

    public static void menuItemF(Connection conn) throws SQLException{
        DBQueries.showOrders(conn);
    }

    public static List<OrderItem> createList(Connection conn) throws SQLException {
        boolean ready = false;
        String choice = null;
        List<OrderItem> itemsList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Введите артикль товара или /? для вывода списка товаров " +
                    "или /q для выхода и оформления заказа:");
            choice = sc.nextLine();
            switch (choice) {
                case "/?":
                    DBQueries.showGoods(conn);
                    break;
                case "/q":
                    ready = true;
                    break;
                default:
                    if (DBQueries.checkArticle(conn, choice)!=0){
                        System.out.println("Артикль найден, введите количество товара");
                        int article = DBQueries.checkArticle(conn, choice);
                        int num = sc.nextInt();
                        itemsList.add(new OrderItem(article, num));
                        System.out.println("Товар и его количество к заказу добавлены");
                        break;
                    } else {
                        System.out.println("Попробуйте выбрать артикль повторно");
                        break;
                    }
            }

        } while (!ready);

        return itemsList;
    }
}
