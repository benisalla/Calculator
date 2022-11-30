package com.exemple.calculator;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
//    public String CalcT = "";
//    public String CalcB = "0";
//    public TextView ObjT;
//    public TextView ObjB;
//    public ArrayList<String> Data = new ArrayList<String>();
//    public String Element = "";
//
//    public void initializeAtt(){
//        ObjT = (TextView) findViewById(R.id.bai_screen_T);
//        ObjB = (TextView) findViewById(R.id.bai_screen_B);
//    }
//    public void render(){
//        ObjB.setText(CalcB);
//        ObjT.setText(CalcT);
//    }
//    //|  0  |  1  |  2  | 3  |  4  |  5  |  6  |  7  |  8  |  9  |  .  |
//    public void NumberClickHandler(String Number){
//        if(!(CalcB.contains(".") && Number == ".")){
//            if(CalcB == "0") {
//                CalcB = Number;
//                Element += Number;
//            }else {
//                CalcB += Number;
//                Element += Number;
//            }
//            render();
//        }
//    }
//    //|    +   |   -   |   *   |   /   |
//    public void OperationClickHandler(String Operation){
//        if(Element != ""){
//            Data.add(Element);
//            Element = "";
//        }
//        boolean test = true;
//        String []arr = {"/","+","-","*"};
//        if(CalcB != "0"){
//            for(String str : arr){
//                if(str == Data.get(Data.size()-1)){
//                    Data.set(Data.size()-1, Operation);
//                    CalcB = CalcB.substring(0,CalcB.length()-3)+" "+Operation+" ";
//                    test = false;
//                    break;
//                }
//            }
//        }
//        if(CalcB != "0" &&  test){
//            Data.add(Operation);
//            CalcB += " "+Operation+" ";
//        }
//        render();
//    }
//
//    public void one(View view){ NumberClickHandler("1"); }
//    public void two(View view){ NumberClickHandler("2"); }
//    public void three(View view){ NumberClickHandler("3"); }
//    public void four(View view){ NumberClickHandler("4"); }
//    public void five(View view){ NumberClickHandler("5"); }
//    public void six(View view){ NumberClickHandler("6"); }
//    public void seven(View view){ NumberClickHandler("7"); }
//    public void eight(View view){ NumberClickHandler("8"); }
//    public void nine(View view){ NumberClickHandler("9"); }
//    public void dot(View view){ NumberClickHandler("."); }
//    public void zero(View view){ NumberClickHandler("0"); }
//
//    public void plus(View view){ OperationClickHandler("+");}
//    public void minus(View view){ OperationClickHandler("-");}
//    public void divide(View view){ OperationClickHandler("/");}
//    public void time(View view){ OperationClickHandler("*");}
//
//    public void Result(View view){
//        if(Element != "") {
//            Data.add(Element);
//            Element = "";
//        }
//
//        if(Data.size() >= 1){
//            if(Data.size()%2 == 0)
//                Data.remove(Data.size()-1);
//
//            double result = Double.valueOf(Data.get(0));
//            for(int i = 1; i<Data.size(); i+=2){
//                if(Data.get(i) == "+")
//                    result += Double.valueOf(Data.get(i+1));
//                else if(Data.get(i) == "*")
//                    result *= Double.valueOf(Data.get(i+1));
//                else if(Data.get(i) == "-")
//                    result -= Double.valueOf(Data.get(i+1));
//                else if(Data.get(i) == "/")
//                    result /= Double.valueOf(Data.get(i+1));
//            }
//            CalcT = ""+result;
//        }
//
//        CalcB = "0";
//        Data = new ArrayList<String>();
//        render();
//    }
//
//    public void power(View view){
//        this.Result(view);
//        if( CalcT == "")
//            CalcT = "0";
//
//        CalcT = String.valueOf(Math.pow(Double.parseDouble(CalcT),2));
//        render();
//    }
//    public void sqrt(View view){
//        this.Result(view);
//        if( CalcT == "")
//            CalcT = "0";
//
//        CalcT = String.valueOf(Math.sqrt(Double.parseDouble(CalcT)));
//        render();
//    }
//
//    public void reverse(View view){
//        this.Result(view);
//        if( CalcT == "")
//            CalcT = "0";
//
//        CalcT = String.valueOf(1 / Double.parseDouble(CalcT));
//        render();
//    }
//
//    public void delete(View view){
//        if(Element != ""){
//            Data.add(Element);
//            Element = "";
//        }
//        if(CalcB != "0"){
//            if(CalcB.charAt(CalcB.length()-1) == ' '){
//                CalcB = CalcB.substring(0,CalcB.length()-3);
//                Data.remove(Data.size()-1);
//            }else{
//                CalcB = CalcB.substring(0,CalcB.length()-1);
//                if(CalcB.isEmpty())
//                    CalcB = "0";
//
//                String Ele = Data.get(Data.size()-1);
//                Ele = new String(Ele.substring(0,Ele.length()-1));
//
//                if(Ele.isEmpty()){
//                    Data.remove(Data.size()-1);
//                }else {
//                    Data.set(Data.size() - 1, Ele);
//                    Toast.makeText(this, Ele, Toast.LENGTH_SHORT).show();
//                }
//            }
//            render();
//        }
//    }
//
//    public void deleteAll(View view){
//        Data = new ArrayList<>();
//        CalcB = "0";
//        CalcT = "";
//        Element = "";
//        render();
//    }
//
//    public void deleteInput(View view){
//        Data = new ArrayList<>();
//        CalcB = "0";
//        render();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializeAtt();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.logOut:
//                Toast.makeText(this,"logout is invoked ...",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.firstItem:
//                Toast.makeText(this,"first itemn is invoked ...",Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//        return true;
//    }
}