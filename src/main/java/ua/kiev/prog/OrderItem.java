package ua.kiev.prog;

public class OrderItem {
    private int good;
    private int good_number;

    public OrderItem (int good, int good_number){
        this.good = good;
        this.good_number = good_number;
    }
    public int getGood() {
        return good;
    }
    public int getGood_number() {
        return good_number;
    }

    public void setGood(int good) {
        this.good = good;
    }
    public void setGood_number(int good_number) {
        this.good_number = good_number;
    }

}
