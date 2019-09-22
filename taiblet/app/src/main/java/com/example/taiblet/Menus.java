package com.example.taiblet;

public class Menus {
        private String menuName,menuInfo,srcNum,imgURL;
        private int menuPrice,amount,menuImgSrc;

        public Menus(){ //디폴트생성자
                this.menuName="";
                this.menuPrice=0;
                this.amount=0;
        }

        public Menus(String menuName,int menuPrice){ //조건생성자
                this.menuName=menuName;
                this.menuPrice=menuPrice;
                this.menuInfo="";
        }
        public boolean checkMenu(){ //메뉴등록이 안된 (빈)메뉴이면 false반환
                if(menuName==""||menuPrice==0){
                        return false;
                }
                else return true;
        }
        public void setMenuName(String s){ this.menuName=s; }
        public void setMenuPrice(int n){ this.menuPrice= n; }
        public void setInfo(String s){this.menuInfo = s;}
       // public void setImage(int s) {this.menuImgSrc=s;}
        public void setAmount(int n){this.amount=n;}
        public void setMinus(){this.amount--;}
        public void setPlus(){this.amount++;}
        //public void setSrcNum(String n){this.srcNum = n;}
        public void setURL(String s){this.imgURL=s;}


        //public int getImgSrc(){return menuImgSrc;}
        public String getMenuName(){
                return this.menuName;
        }
        public int getMenuPrice(){ return this.menuPrice; }
        public String getMenuInfo(){ return this.menuInfo;}
        //public int getMenuImg(){return  menuImgSrc;}
        public int getAmount(){return amount;}
        public String getSrcNum(){return srcNum;}
        public String getImgURL(){return imgURL;}




}
