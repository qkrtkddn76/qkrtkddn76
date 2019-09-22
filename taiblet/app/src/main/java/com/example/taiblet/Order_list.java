package com.example.taiblet;

public class Order_list {
    private String menuName,orderTime,Amount,price,AVG_time;
    public Order_list(){
        menuName="";
        orderTime="";
        Amount="";
        price="";
        AVG_time="";
    }
    public boolean check(){ //빈메뉴면 true
        if(menuName=="" && price==""){
            return true;
        }else {
            return false;
        }

    }



    public void setAmount(String amount) {
        this.Amount = amount;
    }
    public void setAVG_time(String AVG_time) {
        this.AVG_time = AVG_time;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getAmount() {
        return Amount;
    }
    public String getAVG_time() {
        return AVG_time;
    }
    public String getMenuName() {
        return menuName;
    }
    public String getOrderTime() {
        return orderTime;
    }
    public String getPrice() {
        return price;
    }

}
