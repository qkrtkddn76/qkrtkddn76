package com.example.taiblet;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Customdialog extends Dialog{
    TextView tv1,tv2;
    Button btn;
    ImageView img;
    public Customdialog(Context context,String[][] a, String[][] b,int count_i, int count_j) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.customdialog);     //다이얼로그에서 사용할 레이아웃입니다.
        tv1 = findViewById(R.id.dialog_tv1);
        tv2 = findViewById(R.id.dialog_tv2);
        img = findViewById(R.id.dialog_img);
        btn = (Button) findViewById(R.id.btn);

        tv1.setText(a[count_i][count_j]);
        tv2.setText(b[count_i][count_j]);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();   //다이얼로그를 닫는 메소드입니다.
            }
        });


    }
   public void setName(String s,int s2){
        tv1.setText(s+"\n\n"+s2+"원");
    }
    public void setinfo(String s){
        final String[] array = s.split(",");
        StringBuffer sb = new StringBuffer();
        for(String temp : array){
            sb.append(temp+"\n");
        }
        tv2.setText(sb);
    }
    public void setImg(int src){img.setImageResource(src);}
    public ImageView getImg(){return img;}

}
