package ua.kiev.prog;

import java.sql.Connection;
import java.sql.SQLException;
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

    public static void menuItemD(Connection conn) throws SQLException {
        DBQueries.showClients(conn);
    }

}
