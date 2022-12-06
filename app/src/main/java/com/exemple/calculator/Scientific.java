package com.exemple.calculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scientific extends Fragment implements View.OnClickListener {
    List<Integer> allIds = new ArrayList<Integer>();
    TextView Screen;
    String Result = "0";

    public void render(){Screen.setText(Result);}
    public void NumberClickHandler(String nbr){
        if(Result.equals("0"))Result = nbr; else Result += nbr;
        render();
    }
    public void OperationClickHandler(String op){
        if (isOperator(Result.substring(Result.length()-1,Result.length())))
            delete();
        Result += op;
        render();
    }
    public void FunctionClickHandler(String func){
        if(Result.equals("0"))Result = func; else Result += func;
        render();
    }
    public void ParenthesisHanlder(){
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, Result.split("", 0));
        int rightPara = 0, leftPara = 0;
        for(String str : list){
            if(str.equals("("))
                leftPara++;
            if(str.equals(")"))
                rightPara++;
        }
        if(leftPara > rightPara){
            if(list.get(list.size()-1).equals("("))
                Result += "(";
            else
                Result += ")";
        }else if(leftPara == rightPara){
            if(isOperator(list.get(list.size()-1)))
                Result += "(";
            else
                Result += "*(";
        }else{
            Result += "(";
        }
        render();
    }
    public void Calculate(){
        Result = Result.replaceAll("ex","kk");
        Result = Result.replaceAll("e","2.71828182846");
        Result = Result.replaceAll("π","3.14159265359");
        Result = Result.replaceAll("kk","ex");

        if(Math_Exp_Evaluator.CalculateFromExpression(Result) != 0)
            Result = Math_Exp_Evaluator.CalculateFromExpression(Result)+"";
        else
            Result = "0";
        render();
    }
    public void delete_all(){
        Result = "";
        render();
    }

    public void delete(){
        if(!Result.equals("")){
            Result = Result.substring(0,Result.length()-1);
            render();
        }
    }

    public boolean isOperator(String str){
        if (str.equals("+") || str.equals("-") || str.equals("/") || str.equals("*") ||
                str.equals("%") || str.equals("^")) return true;
        return false;
    }





    public Scientific() {
        allIds.add(R.id.id_sci_zero);allIds.add(R.id.id_sci_one);allIds.add(R.id.id_sci_two);allIds.add(R.id.id_sci_three);
        allIds.add(R.id.id_sci_four);allIds.add(R.id.id_sci_five);allIds.add(R.id.id_sci_six);allIds.add(R.id.id_sci_seven);
        allIds.add(R.id.id_sci_eight);allIds.add(R.id.id_sci_nine);allIds.add(R.id.id_sci_tanh);allIds.add(R.id.id_sci_tan);
        allIds.add(R.id.id_sci_atan);allIds.add(R.id.id_sci_atanh);allIds.add(R.id.id_sci_cos);allIds.add(R.id.id_sci_cosh);
        allIds.add(R.id.id_sci_acos);allIds.add(R.id.id_sci_acosh);allIds.add(R.id.id_sci_sin);allIds.add(R.id.id_sci_sinh);
        allIds.add(R.id.id_sci_asin);allIds.add(R.id.id_sci_asinh);allIds.add(R.id.id_sci_log);allIds.add(R.id.id_sci_logy);
        allIds.add(R.id.id_sci_sq2);allIds.add(R.id.id_sci_sq3);allIds.add(R.id.id_sci_sqy);allIds.add(R.id.id_sci_sqrt2);
        allIds.add(R.id.id_sci_sqrt3);allIds.add(R.id.id_sci_sqrty);allIds.add(R.id.id_sci_plus);allIds.add(R.id.id_sci_minus);
        allIds.add(R.id.id_sci_x);allIds.add(R.id.id_sci_div);allIds.add(R.id.id_sci_equal);allIds.add(R.id.id_sci_e);
        allIds.add(R.id.id_sci_2x);allIds.add(R.id.id_sci_10x);allIds.add(R.id.id_sci_abs);allIds.add(R.id.id_sci_delete_all);
        allIds.add(R.id.id_sci_pi);allIds.add(R.id.id_sci_ln);allIds.add(R.id.id_sci_dot);allIds.add(R.id.id_sci_fact);
        allIds.add(R.id.id_sci_sign);allIds.add(R.id.id_sci_rad);allIds.add(R.id.id_sci_exp);allIds.add(R.id.id_sci_inv);
        allIds.add(R.id.id_sci_delete);allIds.add(R.id.id_sci_mod);allIds.add(R.id.id_sci_para);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scientific, container, false);
        for(int Id : allIds){
            rootView.findViewById(Id).setOnClickListener(this);
        }
        Screen = rootView.findViewById(R.id.id_sci_screen);
        render();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.id_sci_equal){
            v.animate().scaleY(0.6f).scaleX(0.4f).setDuration(200).setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float paramFloat) {
                    return Math.abs(paramFloat -1f);
                }
            });
        }else if(v.getId() == R.id.id_sci_delete){
            v.animate().scaleY(0.4f).scaleX(0.2f).setDuration(200).setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float paramFloat) {
                    return Math.abs(paramFloat -1f);
                }
            });
        }else{
            v.animate().scaleY(0.56f).scaleX(0.56f).setDuration(200).setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float paramFloat) {
                    return Math.abs(paramFloat -1f);
                }
            });
        }

        switch (v.getId()){
            case R.id.id_sci_zero: NumberClickHandler("0"); break;
            case R.id.id_sci_one: NumberClickHandler("1"); break;
            case R.id.id_sci_two: NumberClickHandler("2"); break;
            case R.id.id_sci_three: NumberClickHandler("3"); break;
            case R.id.id_sci_four: NumberClickHandler("4"); break;
            case R.id.id_sci_five: NumberClickHandler("5"); break;
            case R.id.id_sci_six: NumberClickHandler("6"); break;
            case R.id.id_sci_seven: NumberClickHandler("7"); break;
            case R.id.id_sci_eight: NumberClickHandler("8"); break;
            case R.id.id_sci_nine: NumberClickHandler("9"); break;
            case R.id.id_sci_dot: NumberClickHandler("."); break;
            case R.id.id_sci_e: NumberClickHandler("e"); break;
            case R.id.id_sci_pi: NumberClickHandler("π"); break;

            case R.id.id_sci_plus: OperationClickHandler("+"); break;
            case R.id.id_sci_minus: OperationClickHandler("-"); break;
            case R.id.id_sci_div: OperationClickHandler("/"); break;
            case R.id.id_sci_x: OperationClickHandler("*"); break;
            case R.id.id_sci_mod: OperationClickHandler("%"); break;

            case R.id.id_sci_para: ParenthesisHanlder(); break;

            case R.id.id_sci_equal: Calculate(); break;

            case R.id.id_sci_delete: delete(); break;

            case R.id.id_sci_delete_all: delete_all(); break;

            case R.id.id_sci_sin: FunctionClickHandler("sin("); break;
            case R.id.id_sci_asin: FunctionClickHandler("asin("); break;
            case R.id.id_sci_sinh: FunctionClickHandler("sinh("); break;
            case R.id.id_sci_asinh: FunctionClickHandler("asinh("); break;
            case R.id.id_sci_cos: FunctionClickHandler("cos("); break;
            case R.id.id_sci_acos: FunctionClickHandler("acos("); break;
            case R.id.id_sci_cosh: FunctionClickHandler("cosh("); break;
            case R.id.id_sci_acosh: FunctionClickHandler("acosh("); break;
            case R.id.id_sci_tan: FunctionClickHandler("tan("); break;
            case R.id.id_sci_atan: FunctionClickHandler("atan("); break;
            case R.id.id_sci_tanh: FunctionClickHandler("tanh("); break;
            case R.id.id_sci_atanh: FunctionClickHandler("atanh("); break;
            case R.id.id_sci_log: FunctionClickHandler("log("); break;
            case R.id.id_sci_abs: FunctionClickHandler("abs("); break;
            case R.id.id_sci_logy: FunctionClickHandler("logy("); break;
            case R.id.id_sci_ln: FunctionClickHandler("ln("); break;
            case R.id.id_sci_inv: FunctionClickHandler("1/("); break;
            case R.id.id_sci_sq2: FunctionClickHandler("^(2)"); break;
            case R.id.id_sci_sq3: FunctionClickHandler("^(3)"); break;
            case R.id.id_sci_sqy: FunctionClickHandler("^("); break;
            case R.id.id_sci_sqrt2: FunctionClickHandler("sqrt("); break;
            case R.id.id_sci_sqrt3: FunctionClickHandler("^(1/3)"); break;
            case R.id.id_sci_sqrty: FunctionClickHandler("^(1/"); break;
            case R.id.id_sci_exp: FunctionClickHandler("exp("); break;
            case R.id.id_sci_2x: FunctionClickHandler("2^("); break;
            case R.id.id_sci_10x: FunctionClickHandler("10^("); break;
        }
    }
}