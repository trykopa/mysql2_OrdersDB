package ua.kiev.prog;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException, ClassNotFoundException {

        System.out.println("Connecting to a selected database...");
        Connection conn = DBSettings.getConnection();
        System.out.println("Connected database successfully...");

        DBMenu.showMenu();

        String choice = null;
        Scanner scan = new Scanner(System.in);
        do {
            choice = scan.nextLine();
            switch (choice) {
                case "a":
                    DBMenu.menuItemA(conn);
                    break;
                case "b":
                    DBMenu.menuItemB(conn);
                    break;
                case "d":
                    DBMenu.menuItemD(conn);
                    break;
                case "e":
                    DBMenu.menuItemE(conn);
                    break;
                case "?":
                    DBMenu.showMenu();
                    break;

            } // end of switch
        } while (!choice.equals("q"));
        System.out.println("Bye!");
        conn.close();
    }
}
