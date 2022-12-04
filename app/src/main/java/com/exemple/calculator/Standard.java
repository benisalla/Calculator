package com.exemple.calculator;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Standard extends Fragment implements View.OnClickListener {
    public String CalcT = "";
    public String CalcB = "0";
    public TextView ObjT;
    public TextView ObjB;
    public ArrayList<String> Data = new ArrayList<String>();
    public String Element = "";
    public ArrayList<Integer> all_ids = new ArrayList<Integer>();


    public void render(){
        ObjB.setText(CalcB);
        ObjT.setText(CalcT);
    }
    //|  0  |  1  |  2  | 3  |  4  |  5  |  6  |  7  |  8  |  9  |  .  |
    public void NumberClickHandler(String Number){
        if(!(CalcB.contains(".") && Number == ".")){
            if(CalcB == "0") {
                CalcB = Number;
            }else {
                CalcB += Number;
            }
            Element += Number;
            render();
        }
    }
    //|    +   |   -   |   *   |   /   |
    public void OperationClickHandler(String Operation){
        Toast.makeText(getContext(), ""+Operation, Toast.LENGTH_SHORT).show();
        if(Element != ""){
            Data.add(Element);
            Element = "";
        }
        boolean test = true;
        String []arr = {"/","+","-","*","%"};
        if(CalcB != "0"){
            for(String str : arr){
                if(str == Data.get(Data.size()-1)){
                    Data.set(Data.size()-1, Operation);
                    CalcB = CalcB.substring(0,CalcB.length()-3)+" "+Operation+" ";
                    test = false;
                    break;
                }
            }
        }
        if(CalcB != "0" &&  test){
            Data.add(Operation);
            CalcB += " "+Operation+" ";
        }
        render();
    }

    public void Result(){
        if(Element != "") {
            Data.add(Element);
            Element = "";
        }

        if(Data.size() >= 1){
            if(Data.size()%2 == 0)
                Data.remove(Data.size()-1);
            CalcT = ""+CalculateWithoutPara(String.join(" ",Data));
        }

        CalcB = "0";
        Data = new ArrayList<String>();
        render();
    }

    public double CalculateWithoutPara(String exp) {
        exp = exp.replaceAll(" \\- ", " \\+ \\-");
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, exp.split(" \\+ ", 0));
        double sum = 0;
        for (String block : list) {
            String[] arr = block.split(" ", 0);
            double result = Double.parseDouble(arr[0]);
            for (int i=1; i<arr.length; i=i+2) {
                if(arr[i].equals("*")) {
                    result = result * Double.parseDouble(arr[i + 1]);
                }
                if(arr[i].equals("%")) {
                    result = result % Double.parseDouble(arr[i + 1]);
                }
                if(arr[i].equals("/")) {
                    result = result / Double.parseDouble(arr[i + 1]);
                }
            }
            sum += result;
        }
        return sum;
    }

    public void power(){
        this.Result();
        if( CalcT == "")
            CalcT = "0";

        CalcT = String.valueOf(Math.pow(Double.parseDouble(CalcT),2));
        render();
    }
    public void sqrt(){
        this.Result();
        if( CalcT == "")
            CalcT = "0";

        CalcT = String.valueOf(Math.sqrt(Double.parseDouble(CalcT)));
        render();
    }

    public void reverse(){
        this.Result();
        if( CalcT == "")
            CalcT = "0";

        CalcT = String.valueOf(1 / Double.parseDouble(CalcT));
        render();
    }

    public void mod(){}

    public void delete(){
        if(Element != ""){
            Data.add(Element);
            Element = "";
        }
        if(CalcB != "0"){
            if(CalcB.charAt(CalcB.length()-1) == ' '){
                CalcB = CalcB.substring(0,CalcB.length()-3);
                Data.remove(Data.size()-1);
            }else{
                CalcB = CalcB.substring(0,CalcB.length()-1);
                if(CalcB.isEmpty())
                    CalcB = "0";

                String Ele = Data.get(Data.size()-1);
                Ele = new String(Ele.substring(0,Ele.length()-1));

                if(Ele.isEmpty()){
                    Data.remove(Data.size()-1);
                }else {
                    Data.set(Data.size() - 1, Ele);
                    Toast.makeText(getContext(), Ele, Toast.LENGTH_SHORT).show();
                }
            }
        }
        render();
    }

    public void deleteAll(){
        Data = new ArrayList<>();
        CalcB = "0";
        CalcT = "";
        Element = "";
        render();
    }

    public void deleteInput(){
        Data = new ArrayList<>();
        CalcB = "0";
        render();
    }

    public Standard() {
        all_ids.add(R.id.bai_one);
        all_ids.add(R.id.bai_two);
        all_ids.add(R.id.bai_three);
        all_ids.add(R.id.bai_four);
        all_ids.add(R.id.bai_five);
        all_ids.add(R.id.bai_six);
        all_ids.add(R.id.bai_seven);
        all_ids.add(R.id.bai_eight);
        all_ids.add(R.id.bai_nine);
        all_ids.add(R.id.bai_div);
        all_ids.add(R.id.bai_eq);
        all_ids.add(R.id.bai_zero);
        all_ids.add(R.id.bai_dot);
        all_ids.add(R.id.bai_plus);
        all_ids.add(R.id.bai_minus);
        all_ids.add(R.id.bai_x);
        all_ids.add(R.id.bai_pow);
        all_ids.add(R.id.bai_not_pow);
        all_ids.add(R.id.bai_opp);
        all_ids.add(R.id.bai_mod);
        all_ids.add(R.id.bai_ce);
        all_ids.add(R.id.bai_c);
        all_ids.add(R.id.bai_delete);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_standard, container, false);

        ObjB = (TextView) rootView.findViewById(R.id.bai_screen_B);
        ObjT = (TextView) rootView.findViewById(R.id.bai_screen_T);



        for (int num : all_ids) {
            rootView.findViewById(num).setOnClickListener(this);
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        v.animate().scaleY(0.6f).scaleX(0.6f).setDuration(200).setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float paramFloat) {
                return Math.abs(paramFloat -1f);
            }
        });
        switch (v.getId()){
            case R.id.bai_one: NumberClickHandler("1"); break;
            case R.id.bai_two: NumberClickHandler("2"); break;
            case R.id.bai_three: NumberClickHandler("3"); break;
            case R.id.bai_four: NumberClickHandler("4"); break;
            case R.id.bai_five: NumberClickHandler("5"); break;
            case R.id.bai_six: NumberClickHandler("6"); break;
            case R.id.bai_seven: NumberClickHandler("7"); break;
            case R.id.bai_eight: NumberClickHandler("8"); break;
            case R.id.bai_nine: NumberClickHandler("9"); break;
            case R.id.bai_zero: NumberClickHandler("0"); break;
            case R.id.bai_dot: NumberClickHandler("."); break;
            case R.id.bai_plus: OperationClickHandler("+"); break;
            case R.id.bai_minus: OperationClickHandler("-"); break;
            case R.id.bai_div: OperationClickHandler("/"); break;
            case R.id.bai_x: OperationClickHandler("*"); break;

            case R.id.bai_eq: Result(); break;
            case R.id.bai_inv: reverse(); break;
            case R.id.bai_pow: power(); break;
            case R.id.bai_not_pow: sqrt(); break;
            case R.id.bai_mod: OperationClickHandler("%"); break;
            case R.id.bai_c: deleteAll(); break;
            case R.id.bai_ce: deleteInput(); break;
            case R.id.bai_delete: delete(); break;
            default: break;
        }
    }
}