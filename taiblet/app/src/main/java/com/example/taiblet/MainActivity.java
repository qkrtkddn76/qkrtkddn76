package com.example.taiblet;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends TabActivity {
    static TabHost tabHost;
    Spinner[][] spinner= new Spinner[4][9];;
    ImageButton[][] info_btn=new ImageButton[4][9];
    ImageButton basket_btn;
    int count_i,count_j,ii;
    String select_str="";
    String phpStr;
    Menus menus[][] = new Menus[4][9];
    Menus basket_menus[] =new Menus[6];
    TextView prices[][] = new TextView[4][9];
    Customdialog cd;
    Order_list order_list[] = new Order_list[15];


    String tableName,tableID="tablet";
    DecimalFormat formatter = new DecimalFormat("###,###");
    static String name[][] =
            { {"제육볶음","갈비찜","뚝배기불고기","","","","","",""},
                    {"파전","된장찌개","김치찌개","","","","","",""},
                    {"콜라","사이다","환타","","","","","",""},
                    {"소주","맥주","","","","","","",""}};

    TextView name_tv[][] = new TextView[4][9];
    static int menuPrice[][] =
            {{7000,9000,8000,0,0,0,0,0,0},
                    {5000,5000,5000,0,0,0,0,0,0},
                    {1500,1500,1500,0,0,0,0,0,0},
                    {3500,3500,0,0,0,0,0,0,0}};
    ArrayList<TextView> basket_tvNameList =new ArrayList<>();
    ArrayList<TextView> basket_tvPriceList=new ArrayList<>();
    ArrayList<LinearLayout> basket_linearLayout=new ArrayList<>();
    ArrayList<ImageView> basket_imgViews=new ArrayList<>();
    ArrayList<ImageButton> basket_minus = new ArrayList<>();
    ArrayList<ImageButton> basket_plus = new ArrayList<>();
    ArrayList<TextView> basket_amont = new ArrayList<>();

    ArrayList<TableRow> tableRows =new ArrayList<>();
    ArrayList<TextView> list_names = new ArrayList<>();
    ArrayList<TextView> list_times = new ArrayList<>();
    ArrayList<TextView> list_amounts = new ArrayList<>();
    ArrayList<TextView> list_prices = new ArrayList<>();
    ArrayList<TextView> list_AVG = new ArrayList<>();


    ArrayList<ArrayList<LinearLayout>> mainLinear = null;
    String info[][] = new String[4][9];
    ImageView menuImg[][] = new ImageView[4][9];
    GettingPHP gPHP;
    GettingPHP2 gPHP2;
    GettingPHP3 gPHP3;
    String url="http://52.78.174.71/android/simple_menu.php"; ///var/www/html/android
    String url2="http://52.78.174.71/android/order.php";
    String url3="http://52.78.174.71/android/order_list.php";
    String url_img="http://52.78.174.71/image/tablet/";
    TextView tableName_tv,selectMenu_tv,sql_test,textSum;
    TableRow tableRowSum;
    String[] arrayTable ={"t1","t2","t3","t4","t5","t6","t7","t8"};
    int[] arrayImage ={R.drawable.check_icon,R.drawable.check_icon,R.drawable.check_icon,R.drawable.check_icon,R.drawable.check_icon,R.drawable.check_icon,R.drawable.check_icon,R.drawable.check_icon};
    List<Map<String, Object>> dialogItemList;
    private static final String TAG_TEXT = "text";
    private static final String TAG_IMAGE = "image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        basket_btn = findViewById(R.id.basket_btn);
        dialogItemList = new ArrayList<>();

        for(int i=0;i<arrayTable.length;i++)
        {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put(TAG_IMAGE, arrayImage[i]);
            itemMap.put(TAG_TEXT, arrayTable[i]);

            dialogItemList.add(itemMap);
        }
        select_Table();
        gPHP = new GettingPHP();
        gPHP.execute(url);
        for(count_i=0;count_i<4;count_i++) {
            for (count_j = 0; count_j < 9; count_j++) {
                menus[count_i][count_j] = new Menus();
            }
        }
        for(count_i=0;count_i<6;count_i++){
            basket_menus[count_i] = new Menus();
        }
        sql_test = findViewById(R.id.sql_test);

        final String packName = this.getPackageName();
        final String[] number = {"0","1","2","3","4","5"};

        tableName_tv = findViewById(R.id.tableName_tv);
        tableName = "tablet";
        tableName_tv.setText("테이블명: "+tableName);
        selectMenu_tv = findViewById(R.id.selectMenu_tv);
        tabHost = getTabHost();
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("tab1");
        spec.setContent(R.id.tabmenu);
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(),R.drawable.select_menu2,null));
        tabHost.addTab(spec);
        spec = tabHost.newTabSpec("tab2");
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(),R.drawable.select_basket2,null));
        spec.setContent(R.id.tabbasket);
        tabHost.addTab(spec);
        spec = tabHost.newTabSpec("tab3");
        spec.setIndicator(null, ResourcesCompat.getDrawable(getResources(),R.drawable.select_list2,null));
        spec.setContent(R.id.tablist);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);
        menuImg[0][0]=findViewById(R.id.img_mainmenu00); menuImg[0][1]=findViewById(R.id.img_mainmenu01); menuImg[0][2]=findViewById(R.id.img_mainmenu02);
        menuImg[0][3]=findViewById(R.id.img_mainmenu03); menuImg[0][4]=findViewById(R.id.img_mainmenu04); menuImg[0][5]=findViewById(R.id.img_mainmenu05);
        menuImg[1][0]=findViewById(R.id.img_mainmenu10); menuImg[1][1]=findViewById(R.id.img_mainmenu11); menuImg[1][2]=findViewById(R.id.img_mainmenu12);
        menuImg[1][3]=findViewById(R.id.img_mainmenu13); menuImg[1][4]=findViewById(R.id.img_mainmenu14); menuImg[1][5]=findViewById(R.id.img_mainmenu15);
        menuImg[2][0]=findViewById(R.id.img_mainmenu20); menuImg[2][1]=findViewById(R.id.img_mainmenu21); menuImg[2][2]=findViewById(R.id.img_mainmenu22);
        menuImg[2][3]=findViewById(R.id.img_mainmenu23); menuImg[2][4]=findViewById(R.id.img_mainmenu24); menuImg[2][5]=findViewById(R.id.img_mainmenu25);
        menuImg[3][0]=findViewById(R.id.img_mainmenu30); menuImg[3][1]=findViewById(R.id.img_mainmenu31); menuImg[3][2]=findViewById(R.id.img_mainmenu32);
        menuImg[3][3]=findViewById(R.id.img_mainmenu33); menuImg[3][4]=findViewById(R.id.img_mainmenu34); menuImg[3][4]=findViewById(R.id.img_mainmenu34);



        spinner[0][0] = findViewById(R.id.spin_mainmenu00); spinner[0][1] = findViewById(R.id.spin_mainmenu01); spinner[0][2] = findViewById(R.id.spin_mainmenu02);
        spinner[0][3] = findViewById(R.id.spin_mainmenu03); spinner[0][4] = findViewById(R.id.spin_mainmenu04); spinner[0][5] = findViewById(R.id.spin_mainmenu05);
        spinner[1][0] = findViewById(R.id.spin_mainmenu10); spinner[1][1] = findViewById(R.id.spin_mainmenu11); spinner[1][2] = findViewById(R.id.spin_mainmenu12);
        spinner[1][3] = findViewById(R.id.spin_mainmenu13); spinner[1][4] = findViewById(R.id.spin_mainmenu14); spinner[1][5] = findViewById(R.id.spin_mainmenu15);
        spinner[2][0] = findViewById(R.id.spin_mainmenu20); spinner[2][1] = findViewById(R.id.spin_mainmenu21); spinner[2][2] = findViewById(R.id.spin_mainmenu22);
        spinner[2][3] = findViewById(R.id.spin_mainmenu23); spinner[2][4] = findViewById(R.id.spin_mainmenu24); spinner[2][5] = findViewById(R.id.spin_mainmenu25);
        spinner[3][0] = findViewById(R.id.spin_mainmenu30); spinner[3][1] = findViewById(R.id.spin_mainmenu31); spinner[3][2] = findViewById(R.id.spin_mainmenu32);
        spinner[3][3] = findViewById(R.id.spin_mainmenu33); spinner[3][4] = findViewById(R.id.spin_mainmenu34); spinner[3][5] = findViewById(R.id.spin_mainmenu35);
        final ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,number);


        info_btn[0][0] = findViewById(R.id.info_mainmenu00); info_btn[0][1] = findViewById(R.id.info_mainmenu01); info_btn[0][2] = findViewById(R.id.info_mainmenu02);
        info_btn[0][3] = findViewById(R.id.info_mainmenu03); info_btn[0][4] = findViewById(R.id.info_mainmenu04); info_btn[0][5] = findViewById(R.id.info_mainmenu05);
        info_btn[1][0] = findViewById(R.id.info_mainmenu10); info_btn[1][1] = findViewById(R.id.info_mainmenu11); info_btn[1][2] = findViewById(R.id.info_mainmenu12);
        info_btn[1][3] = findViewById(R.id.info_mainmenu13); info_btn[1][4] = findViewById(R.id.info_mainmenu14); info_btn[1][5] = findViewById(R.id.info_mainmenu15);
        info_btn[2][0] = findViewById(R.id.info_mainmenu20); info_btn[2][1] = findViewById(R.id.info_mainmenu21); info_btn[2][2] = findViewById(R.id.info_mainmenu22);
        info_btn[2][3] = findViewById(R.id.info_mainmenu23); info_btn[2][4] = findViewById(R.id.info_mainmenu24); info_btn[2][5] = findViewById(R.id.info_mainmenu25);
        info_btn[3][0] = findViewById(R.id.info_mainmenu30); info_btn[3][1] = findViewById(R.id.info_mainmenu31); info_btn[3][2] = findViewById(R.id.info_mainmenu32);
        info_btn[3][3] = findViewById(R.id.info_mainmenu33); info_btn[3][4] = findViewById(R.id.info_mainmenu34); info_btn[3][5] = findViewById(R.id.info_mainmenu35);


        prices[0][0] = findViewById(R.id.price00); prices[0][1] = findViewById(R.id.price01); prices[0][2] = findViewById(R.id.price02);
        prices[0][3] = findViewById(R.id.price03); prices[0][4] = findViewById(R.id.price04); prices[0][5] = findViewById(R.id.price05);
        prices[1][0] = findViewById(R.id.price10); prices[1][1] = findViewById(R.id.price11); prices[1][2] = findViewById(R.id.price12);
        prices[2][0] = findViewById(R.id.price20); prices[2][1] = findViewById(R.id.price21); prices[2][2] = findViewById(R.id.price22);
        prices[3][0] = findViewById(R.id.price30); prices[3][1] = findViewById(R.id.price31); prices[3][2] = findViewById(R.id.price32);
        prices[1][3] = findViewById(R.id.price13); prices[1][4] = findViewById(R.id.price14); prices[1][5] = findViewById(R.id.price15);
        prices[2][3] = findViewById(R.id.price20); prices[2][4] = findViewById(R.id.price24); prices[2][5] = findViewById(R.id.price25);
        prices[3][3] = findViewById(R.id.price30); prices[3][4] = findViewById(R.id.price34); prices[3][5] = findViewById(R.id.price35);



        name_tv[0][0] = findViewById(R.id.name_mainmenu00); name_tv[0][1] = findViewById(R.id.name_mainmenu01); name_tv[0][2] = findViewById(R.id.name_mainmenu02);
        name_tv[0][3] = findViewById(R.id.name_mainmenu00); name_tv[0][4] = findViewById(R.id.name_mainmenu01); name_tv[0][5] = findViewById(R.id.name_mainmenu02);
        name_tv[1][0] = findViewById(R.id.name_mainmenu10); name_tv[1][1] = findViewById(R.id.name_mainmenu11); name_tv[1][2] = findViewById(R.id.name_mainmenu12);
        name_tv[2][0] = findViewById(R.id.name_mainmenu20); name_tv[2][1] = findViewById(R.id.name_mainmenu21); name_tv[2][2] = findViewById(R.id.name_mainmenu22);
        name_tv[3][0] = findViewById(R.id.name_mainmenu30); name_tv[3][1] = findViewById(R.id.name_mainmenu31); name_tv[3][2] = findViewById(R.id.name_mainmenu32);
        name_tv[1][3] = findViewById(R.id.name_mainmenu13); name_tv[1][4] = findViewById(R.id.name_mainmenu14); name_tv[1][5] = findViewById(R.id.name_mainmenu15);
        name_tv[2][3] = findViewById(R.id.name_mainmenu23); name_tv[2][4] = findViewById(R.id.name_mainmenu24); name_tv[2][5] = findViewById(R.id.name_mainmenu25);
        name_tv[3][3] = findViewById(R.id.name_mainmenu33); name_tv[3][4] = findViewById(R.id.name_mainmenu34); name_tv[3][5] = findViewById(R.id.name_mainmenu35);
        textSum =findViewById(R.id.textSum);
        tableRowSum =findViewById(R.id.tableRowSum);
        ArrayList<LinearLayout> tempLinear = null;
        mainLinear = new ArrayList<ArrayList<LinearLayout>>();
        for(int i=0; i<4;i++){
            tempLinear = new ArrayList<LinearLayout>();
            for(int j=0; j<6; j++){
                tempLinear.add(((LinearLayout)findViewById(getResources().getIdentifier("linear"+i+j, "id", getPackageName()))));
                tempLinear.get(j).setVisibility(View.GONE);
            }
            mainLinear.add(tempLinear);
        }
        String T;
        for(ii=1;ii<16;ii++){//MAKING
            order_list[ii-1] = new Order_list();
            if(ii<10){
                T="0"+ii;
            }else{
                T=""+ii;
            }
            System.out.println("tableRow"+T);
            tableRows.add(((TableRow)findViewById(getResources().getIdentifier("tableRow"+T, "id", getPackageName()))));
            list_amounts.add(((TextView)findViewById(getResources().getIdentifier("list_amount"+T, "id", getPackageName()))));
            list_AVG.add(((TextView)findViewById(getResources().getIdentifier("list_AVGtime"+T, "id", getPackageName()))));
            list_times.add(((TextView)findViewById(getResources().getIdentifier("list_time"+T, "id", getPackageName()))));
            list_names.add(((TextView)findViewById(getResources().getIdentifier("list_name"+T, "id", getPackageName()))));
            list_prices.add(((TextView)findViewById(getResources().getIdentifier("list_price"+T, "id", getPackageName()))));

        }

        for(ii=0;ii<6;ii++){
            basket_tvNameList.add(((TextView)findViewById(getResources().getIdentifier("basket_menu"+ii+"_name", "id", getPackageName()))));
            basket_tvPriceList.add(((TextView)findViewById(getResources().getIdentifier("basket_menu"+ii+"_price", "id", getPackageName()))));
            basket_linearLayout.add(((LinearLayout)findViewById(getResources().getIdentifier("basket_layout"+ii, "id", getPackageName()))));
            basket_imgViews.add(((ImageView)findViewById(getResources().getIdentifier("basket_menu"+ii+"_img", "id", getPackageName()))));
            basket_minus.add(((ImageButton)findViewById(getResources().getIdentifier("basket_menu"+ii+"_L", "id", getPackageName()))));
            basket_plus.add(((ImageButton)findViewById(getResources().getIdentifier("basket_menu"+ii+"_R", "id", getPackageName()))));
            basket_amont.add(((TextView)findViewById(getResources().getIdentifier("basket_menu"+ii+"_amont", "id", getPackageName()))));

            basket_plus.get(ii).setOnClickListener(new View.OnClickListener() {
                int temp_index=ii;
                @Override
                public void onClick(View v) {
                    if(basket_menus[temp_index].getAmount()<10){
                        basket_menus[temp_index].setPlus();
                        basket_amont.get(temp_index).setText(""+basket_menus[temp_index].getAmount());
                        System.out.println("setPlus\t\t(index):"+temp_index);
                        String str="";
                        int count=0,sum=0;
                        for(int x=0; x<6; x++) { //수정필요
                            if(basket_menus[x].checkMenu()){
                                int s = basket_menus[x].getAmount();
                                if(s==0){continue;}
                                else{
                                    if(count!=0){ str=str.concat(",  "); }
                                    str=str.concat(basket_menus[x].getMenuName()+": "+s+"개");
                                    sum+=basket_menus[x].getAmount()*basket_menus[x].getMenuPrice();
                                    count++;
                                }
                            }

                        }
                        select_str = select_str.concat(str);
                        selectMenu_tv.setText(select_str);
                        select_str="";
                        tableName_tv.setText("합계 : "+formatter.format(sum)+"원");
                    }
                }
            });
            basket_minus.get(ii).setOnClickListener(new View.OnClickListener() {
                int temp_index2=ii;
                @Override
                public void onClick(View v) {
                    if(basket_menus[temp_index2].getAmount()>0){
                        basket_menus[temp_index2].setMinus();
                        basket_amont.get(temp_index2).setText(""+basket_menus[temp_index2].getAmount());
                        System.out.println("setMinus\t(index):"+temp_index2);
                        String str="";
                        int count=0;
                        int sum=0;
                        for(int x=0; x<6; x++) { //수정필요
                            if(basket_menus[x].checkMenu()){
                                int s = basket_menus[x].getAmount();
                                if(s==0){continue;}
                                else{
                                    if(count!=0){ str=str.concat(",  "); }
                                    str=str.concat(basket_menus[x].getMenuName()+": "+s+"개");
                                    sum+=basket_menus[x].getAmount()*basket_menus[x].getMenuPrice();
                                    count++;
                                }
                            }

                        }
                        select_str = select_str.concat(str);
                        selectMenu_tv.setText(select_str);
                        select_str="";
                        tableName_tv.setText("합계 : "+formatter.format(sum)+"원");
                    }
                }
            });

        }


        for(count_i=0;count_i<4;count_i++){
            for(count_j=0;count_j<9;count_j++){
                menus[count_i][count_j].setMenuName(name[count_i][count_j]);
                menus[count_i][count_j].setMenuPrice(menuPrice[count_i][count_j]);
                menus[count_i][count_j].setInfo("( 음식정보들 )");
                if(!menus[count_i][count_j].checkMenu()){ continue;}
            }
        }




        for(count_i=0; count_i<4; count_i++){ //수정필요
            for(count_j=0; count_j<6;count_j++){

                spinner[count_i][count_j].setAdapter(adapter);
                spinner[count_i][count_j].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String str = adapter.getItem(i);
                        str="";
                        int count=0;
                        for(int x=0; x<4; x++) { //수정필요
                            for (int  y= 0; y< 9; y++) {
                                if(menus[x][y].checkMenu()){
                                    int s = spinner[x][y].getSelectedItemPosition();
                                    boolean flag = true;
                                    for(int k=0;k<6;k++){
                                        if(menus[x][y].getMenuName()==basket_menus[k].getMenuName()){
                                            flag=false; //장바구니에 있는내용은 변경안하게
                                        }
                                    }

                                    if(flag) {
                                        menus[x][y].setAmount(s);
                                    }
                                    if(s==0){continue;}
                                    else{
                                        if(count!=0){ str=str.concat(",  "); }
                                        str=str.concat(menus[x][y].getMenuName()+": "+s+"개");
                                        count++;
                                    }
                                }
                            }
                        }
                        //$$$$$$$$$$$$$
                        if(tabHost.getCurrentTabTag().equals("tab2")){
                            str=""; count=0;
                            for(int k=0;k<6;k++) {
                                if (count != 0) {
                                    str = str.concat(",  ");
                                }
                                str = str.concat(basket_menus[k].getMenuName() + ": " + basket_menus[k].getAmount() + "개");
                                count++;
                            }
                        }

                        if(!str.equals("")){ selectMenu_tv.setVisibility(View.VISIBLE);}
                        select_str = select_str.concat(str);
                        selectMenu_tv.setText(select_str);
                        select_str="";


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                name[count_i][count_j] = new String(menus[count_i][count_j].getMenuName());
                //info[count_i][count_j] = new String(menus[count_i][count_j].getMenuInfo());
                prices[count_i][count_j].setText(formatter.format(menus[count_i][count_j].getMenuPrice()));

                DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics(); //디바이스 화면크기를 구하기위해
                final int width1 = dm.widthPixels; //디바이스 화면 너비
                final int height1 = dm.heightPixels; //디바이스 화면 높이
                cd = new Customdialog(this,name,info,count_i,count_j);
                WindowManager.LayoutParams wm = cd.getWindow().getAttributes();  //다이얼로그의 높이 너비 설정하기위해
                wm.copyFrom(cd.getWindow().getAttributes());  //여기서 설정한값을 그대로 다이얼로그에 넣겠다는의미
                wm.width = width1 / 2;  //화면 너비의 절반
                wm.height = height1 / 2;  //화면 높이의 절반

                if(menus[count_i][count_j].checkMenu()){
                    info_btn[count_i][count_j].setOnClickListener(new View.OnClickListener() {
                        int temp_i=count_i,temp_j=count_j;
                        @Override
                        public void onClick(View view) {
                            cd.setName(menus[temp_i][temp_j].getMenuName(),menus[temp_i][temp_j].getMenuPrice());
                            cd.setinfo(menus[temp_i][temp_j].getMenuInfo());
                            //cd.setImg(getResources().getIdentifier("@drawable/img_"+menus[temp_i][temp_j].getSrcNum(),"drawable",getPackageName()));
                            try {
                                new DownLoadImageTask(cd.getImg()).execute(menus[temp_i][temp_j].getImgURL());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            cd.show();  //다이얼로그

                        }
                    });
                }

            }

        }
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                /*!!!!!!!!!*/
                if(tabHost.getCurrentTabTag().equals("tab1")){
                    for(int x=0; x<4; x++) { //수정필요
                        for (int  y= 0; y< 9; y++) {
                            if(menus[x][y].checkMenu()){
                                spinner[x][y].setSelection(0);
                            }
                        }
                    }
                    for(int x=0;x<6;x++){
                        //basket_menus[x] = new Menus();
                    }
                    tableName_tv.setText("테이블명 : "+tableName);
                    basket_btn.setImageResource(R.drawable.basket);
                    tableName_tv.setVisibility(View.VISIBLE);
                }else if(tabHost.getCurrentTabTag().equals("tab2")){
                    basket_btn.setImageResource(R.drawable.send_icon);
                    tableName_tv.setVisibility(View.VISIBLE);
                }else if(tabHost.getCurrentTabTag().equals("tab3")){
                    basket_btn.setImageResource(R.drawable.repost);
                    tableName_tv.setVisibility(View.INVISIBLE);
                }
                //if(tabHost.getCurrentTabTag().equals("tab2")){
                int count=0,sum=0;
                for(count_i=0;count_i<4;count_i++){
                    for(count_j=0;count_j<9;count_j++){
                        if(!menus[count_i][count_j].checkMenu()){continue;}
                        if(menus[count_i][count_j].getAmount()==0) {continue;}

                            //basket_menus[count] = menus[count_i][count_j];

                        basket_linearLayout.get(count).setVisibility(View.VISIBLE);
                        //basket_imgViews.get(count).setImageResource(basket_menus[count].getImgSrc());
                        basket_tvNameList.get(count).setText(""+basket_menus[count].getMenuName());
                        basket_tvPriceList.get(count).setText(""+basket_menus[count].getMenuPrice());
                        basket_amont.get(count).setText(""+basket_menus[count].getAmount());
                        sum+= basket_menus[count].getAmount()*basket_menus[count].getMenuPrice();
                        try {
                            new DownLoadImageTask(basket_imgViews.get(count)).execute(basket_menus[count].getImgURL());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        count++;
                    }
                }
                for( ; count < 6 ; count++){
                    basket_menus[count] = new Menus();
                    basket_linearLayout.get(count).setVisibility(View.INVISIBLE);
                }

                if(!tabHost.getCurrentTabTag().equals("tab1")) {
                    tableName_tv.setText("합계 : " + formatter.format(sum) + "원");
                }
                if(tabHost.getCurrentTabTag().equals("tab2")){
                    String str=""; count=0;
                    for(int k=0;k<6;k++) {
                        if(!basket_menus[k].checkMenu()){break;}
                        if (count != 0) {
                            str = str.concat(",  ");
                        }
                        str = str.concat(basket_menus[k].getMenuName() + ": " + basket_menus[k].getAmount() + "개");
                        count++;
                    }
                    if(!str.equals("")){ selectMenu_tv.setVisibility(View.VISIBLE);}
                    select_str = select_str.concat(str);
                    selectMenu_tv.setText(select_str);
                    select_str="";

                }



            }
        });



        basket_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.tabHost.getCurrentTab()==0){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    dlg.setTitle("확인창");
                    if(selectMenu_tv.getText().equals("")){
                        dlg.setMessage("아무것도 선택되지 않았습니다.\n그래도 장바구니로 이동하시겠습니까?");
                    }else {
                        dlg.setMessage("선택한 "+selectMenu_tv.getText() + "를\n장바구니에 담으시겠습니까?");
                    }
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.tabHost.setCurrentTab(1);

                            int count=0,sum=0;
                            for(count_i=0;count_i<4;count_i++){
                                for(count_j=0;count_j<9;count_j++){
                                    if(!menus[count_i][count_j].checkMenu()){continue;}
                                    if(menus[count_i][count_j].getAmount()==0) {continue;}
                                    basket_menus[count] = menus[count_i][count_j];
                                    basket_linearLayout.get(count).setVisibility(View.VISIBLE);
                                    //basket_imgViews.get(count).setImageResource(basket_menus[count].getImgSrc());
                                    basket_tvNameList.get(count).setText(""+basket_menus[count].getMenuName());
                                    basket_tvPriceList.get(count).setText(""+basket_menus[count].getMenuPrice());
                                    basket_amont.get(count).setText(""+basket_menus[count].getAmount());
                                    sum+= basket_menus[count].getAmount()*basket_menus[count].getMenuPrice();
                                    try {
                                        new DownLoadImageTask(basket_imgViews.get(count)).execute(basket_menus[count].getImgURL());
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                    count++;
                                }
                            }
                            tableName_tv.setText("합계 : "+formatter.format(sum)+"원");
                            basket_btn.setImageResource(R.drawable.send_icon);

                        }
                    });
                    dlg.setNegativeButton("취소",null);
                    dlg.show();


                }
                else if(MainActivity.tabHost.getCurrentTab()==1){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    dlg.setTitle("확인창");
                    dlg.setMessage(selectMenu_tv.getText()+"를\n주문하시겠습니까?");
                    dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.tabHost.setCurrentTab(2);
                            System.out.println(url2);
                            for(int t=0;t<6;t++) {
                                if(!basket_menus[t].checkMenu()){break;}
                                gPHP2 = new GettingPHP2();
                                gPHP2.execute(url2,t+"");
                            }
                            gPHP3 =new GettingPHP3();
                            gPHP3.execute(url3);


                        }
                    });
                    dlg.setNegativeButton("취소",null);
                    dlg.show();
                    basket_btn.setImageResource(R.drawable.repost);

                        String str=""; int count=0;
                        for(int k=0;k<6;k++) {
                            if(!basket_menus[k].checkMenu()){break;}
                            if (count != 0) {
                                str = str.concat(",  ");
                            }
                            str = str.concat(basket_menus[k].getMenuName() + ": " + basket_menus[k].getAmount() + "개");
                            count++;
                        }
                        if(!str.equals("")){ selectMenu_tv.setVisibility(View.VISIBLE);}
                        select_str = select_str.concat(str);
                        selectMenu_tv.setText(select_str);
                        select_str="";


                } else if(MainActivity.tabHost.getCurrentTab()==2){
                    gPHP3 =new GettingPHP3();
                    gPHP3.execute(url3);
                }
            }
        });


    }


    private void select_Table(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.listdialog, null);
        builder.setView(view);
        final ListView listview = (ListView)view.findViewById(R.id.listview_alterdialog_list);
        final AlertDialog dialog1 = builder.create();
        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, dialogItemList,
                R.layout.alert_dialog_row,
                new String[]{TAG_IMAGE, TAG_TEXT},
                new int[]{R.id.alertDialogItemImageView, R.id.alertDialogItemTextView});

        listview.setAdapter(simpleAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tableName_tv.setText("테이블명 : "+arrayTable[position]);
                tableName = arrayTable[position];
                dialog1.dismiss();
            }
        });

        dialog1.setCancelable(false);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog1.show();
    }




    public class GettingPHP extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL phpUrl = new URL(params[0]);
                System.out.println(phpUrl);

                HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();
                conn.setRequestMethod("POST");
                if ( conn != null ) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    conn.connect();
                    //conn.setDefaultUseCaches(false);
                    OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                    BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
                    final String parameta = "id="+tableID;
                    writer.write(parameta);
                    writer.flush();
                    writer.close();
                    out.close();



                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {


                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ( true ) {
                            String line = br.readLine();
                            if ( line == null )
                                break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }

            return jsonHtml.toString();
        }
        protected void onPostExecute(String str) {
            substr_sql(str,1);
            System.out.println(str);
            phpStr=str;
        }
    }


    public class GettingPHP2 extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL phpUrl = new URL(params[0]);

                int count = Integer.parseInt(params[1]);

                HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();
                conn.setRequestMethod("POST");
                if ( conn != null ) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    conn.connect();
                    //conn.setDefaultUseCaches(false);
                    OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                    BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));

                    final StringBuffer parameta = new StringBuffer("");

                            parameta.append("id=").append("tablet").append("&");
                            parameta.append("table=").append(tableName).append("&");
                            parameta.append("menu=").append(basket_menus[count].getMenuName()).append("&");
                            parameta.append("count=").append(basket_menus[count].getAmount());

                            writer.write(parameta.toString());
                            writer.flush();
                            writer.close();
                            out.close();
                             System.out.println(parameta);


                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ( true ) {
                            String line = br.readLine();
                            if ( line == null )
                                break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }

            return jsonHtml.toString();
        }
        protected void onPostExecute(String str) {

        }
    }
    public class GettingPHP3 extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL phpUrl = new URL(params[0]);
                System.out.println(phpUrl);


                HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();
                conn.setRequestMethod("POST");
                if ( conn != null ) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    conn.connect();
                    //conn.setDefaultUseCaches(false);
                    OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                    BufferedWriter writer =new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));

                    final StringBuffer parameta = new StringBuffer("");


                            parameta.setLength(0);
                            parameta.append("id=").append("tablet").append("&");
                            parameta.append("table=").append(tableName);

                            System.out.println(parameta);
                            writer.write(parameta.toString());
                            writer.flush();
                            writer.close();

                    out.close();

                    System.out.println("@@@@@@@@@@@@@");

                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ( true ) {
                            String line = br.readLine();
                            if ( line == null )
                                break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                    System.out.println("#############");
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }

            return jsonHtml.toString();
        }
        protected void onPostExecute(String str) {
            substr_sql(str,3);
            System.out.println(str);
        }
    }

    public void substr_sql(String str,int phpN){
        System.out.println("파싱 시작(phpN: "+phpN+" )");
        System.out.println(str);
        if(phpN==1) {
            int[] count = new int[4];
            int h, flag = 0;
            final String[] array = str.split("@");
            for (int i = 0; i < array.length - 1; i++) {
                h = i;
                if (i == 0) {
                    h = 5;
                }
                ;
                switch (h % 5) {
                    case 0: //카테고리
                        array[i]=array[i].trim();
                        if (array[i].contains("메인메뉴")) {
                            flag = 0;
                        } else if (array[i].contains("서브메뉴")) {
                            flag = 1;
                        } else if (array[i].contains("음료")) {
                            flag = 2;
                        } else if (array[i].contains("주류")) {
                            flag = 3;
                        }
                        System.out.println(flag + ", " + count[flag] + "," + array[i]);
                        break;
                    case 1:     //메뉴이름
                        name[flag][count[flag]] = array[i];
                        System.out.println(flag + ", " + count[flag] + "," + array[i]);
                        name_tv[flag][count[flag]].setText(array[i]);
                        menus[flag][count[flag]].setMenuName(array[i]);
                        break;
                    case 2:     //가격
                        menuPrice[flag][count[flag]] = Integer.parseInt(array[i]);
                        menus[flag][count[flag]].setMenuPrice(Integer.parseInt(array[i]));
                        System.out.println(flag + ", " + count[flag] + "," + array[i]);
                        prices[flag][count[flag]].setText(formatter.format(Integer.parseInt(array[i])) + "");
                        break;
                    case 3:     //메뉴정보
                        menus[flag][count[flag]].setInfo(array[i] + "");
                        System.out.println(flag + ", " + count[flag] + "," + array[i]);
                        break;
                    case 4:     //메뉴이미지
                        menus[flag][count[flag]].setURL(array[i]);
                        try {
                            System.out.println(menus[flag][count[flag]].getImgURL());
                            new DownLoadImageTask(menuImg[flag][count[flag]]).execute(menus[flag][count[flag]].getImgURL());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mainLinear.get(flag).get(count[flag]).setVisibility(View.VISIBLE);
                        if (count[flag] % 3 == 0) {
                            mainLinear.get(flag).get(count[flag] + 1).setVisibility(View.INVISIBLE);
                            mainLinear.get(flag).get(count[flag] + 2).setVisibility(View.INVISIBLE);
                        }
                        count[flag] += 1;
                        break;
                }

            }
        }else if(phpN==3){ //메뉴리스트
            int h, flag = 0;
            final String[] array2 = str.split("@");
            for (int i = 0; i < array2.length - 1; i++) {
                h = i;
                if (i == 0) {
                    h = 5;
                }

                switch (h % 5) {
                    case 0: //메뉴이름
                        order_list[flag].setMenuName(array2[i].trim());
                        list_names.get(flag).setText(array2[i]);
                        System.out.println(array2[i].trim());
                        break;
                    case 1:     //가격
                        order_list[flag].setPrice(array2[i]);
                        list_times.get(flag).setText(array2[i]);
                        System.out.println(array2[i]);
                        break;
                    case 2:     //수량
                        order_list[flag].setAmount(array2[i]);
                        list_amounts.get(flag).setText(array2[i]);
                        System.out.println(array2[i]);
                        break;
                    case 3:     //주문시간
                        order_list[flag].setOrderTime(array2[i]);
                        list_prices.get(flag).setText(array2[i]);
                        System.out.println(array2[i]);
                        break;
                    case 4:     //평균소요시간
                        order_list[flag].setAVG_time(array2[i]);
                        list_AVG.get(flag).setText(array2[i]);
                        System.out.println(array2[i]);
                        tableRows.get(flag).setVisibility(View.VISIBLE);
                        flag++;
                        break;
                }

            }
            int sum=0;
            while(flag<15){
                tableRows.get(flag).setVisibility(View.INVISIBLE);
                flag++;
            }
            for(int i=0;i<16;i++) {
                if(order_list[i].check()){break;}
                sum += Integer.parseInt(order_list[i].getPrice()) * Integer.parseInt(order_list[i].getAmount());

            }

            textSum.setText(sum+"");
            tableRowSum.setVisibility(View.VISIBLE);
        }
    }
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap>{
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }


}
