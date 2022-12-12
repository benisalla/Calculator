package com.exemple.calculator;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Programmer extends Fragment implements View.OnClickListener {
    public TextView topScreen;
    public TextView bottomScreen;
    public TextView HexText, DecText, OctText, BinText;
    public LinearLayout HexBody, DecBody, OctBody, BinBody;
    public TextView Btn_A, Btn_B, Btn_C, Btn_D, Btn_E, Btn_F, Btn_2, Btn_3, Btn_4,Btn_5,Btn_6,Btn_7,Btn_8,Btn_9;
    public ArrayList<Integer> all_ids = new ArrayList<Integer>();
    public String currentExp = "0", hexValue = "0", decValue = "0", octValue = "0", binValue = "0";
    public String lastExp = "0";
    public String hexBehindExp = "0";
    public String binBehindExp = "0";
    public String decBehindExp = "0";
    public String octBehindExp = "0";
    public String provider = "Dec";

    public void render(){
        bottomScreen.setText(currentExp);
        topScreen.setText(lastExp);
        HexText.setText(hexValue);
        BinText.setText(binValue);
        DecText.setText(decValue);
        OctText.setText(octValue);
    }

    public void NumberClickHandler(String nbr){
        if(currentExp.equals("0"))currentExp = nbr; else currentExp += nbr;
        UpdateTransformation();
    }
    public void OperationClickHandler(String op){
        if(!currentExp.equals("0")){
            if(lastExp.substring(lastExp.length()-1,lastExp.length()).equals("=")) {
                lastExp = "0";
                hexBehindExp = "0";
                binBehindExp = "0";
                decBehindExp = "0";
                octBehindExp = "0";
            }
            if(lastExp.equals("0")) {
                lastExp = currentExp + op;
                hexBehindExp = hexValue + op;
                binBehindExp = binValue + op;
                decBehindExp = decValue + op;
                octBehindExp = octValue + op;
            }
            else {
                lastExp += currentExp + op;
                hexBehindExp += hexValue + op;
                binBehindExp += binValue + op;
                decBehindExp += decValue + op;
                octBehindExp += octValue + op;
            }
        }else{
            if (isOperator(lastExp.substring(lastExp.length()-1,lastExp.length()))){
                lastExp = lastExp.substring(0,lastExp.length()-1)+op;
                hexBehindExp += hexBehindExp.substring(0,hexBehindExp.length()-1) + op;
                binBehindExp += binBehindExp.substring(0,binBehindExp.length()-1) + op;
                decBehindExp += decBehindExp.substring(0,decBehindExp.length()-1) + op;
                octBehindExp += octBehindExp.substring(0,octBehindExp.length()-1) + op;
            }
        }
        currentExp = "0";
        UpdateTransformation();
    }
    public void ParenthesisHanlder(String para){
        if(para.equals("(")){
            if(lastExp.equals("0")){
                lastExp = para;
                hexBehindExp = para;
                binBehindExp = para;
                decBehindExp = para;
                octBehindExp = para;
            }
            else{
                lastExp += para;
                hexBehindExp += para;
                binBehindExp += para;
                decBehindExp += para;
                octBehindExp += para;
            }
        }else{
            if(!lastExp.equals("0")) {
                lastExp += currentExp + para+"+";
                hexBehindExp += hexValue + para+"+";
                binBehindExp += binValue + para+"+";
                decBehindExp += decValue + para+"+";
                octBehindExp += octValue + para+"+";
            }
        }

        render();
    }
    public void Calculate(){
        if(!decBehindExp.equals("0")){
            if(!currentExp.equals("0"))
                decBehindExp += decValue;
            else
                decBehindExp = decBehindExp.substring(0,decBehindExp.length()-1);

            lastExp += currentExp+"=";

            if(Math_Exp_Evaluator.CalculateFromExpression(decBehindExp) != 0)
                currentExp = ((int) Math_Exp_Evaluator.CalculateFromExpression(decBehindExp)) +"";
            else
                currentExp = "0";

            hexBehindExp="0"; binBehindExp="0"; decBehindExp="0"; octBehindExp="0";
            UpdateTransformation();
        }
    }

    public void delete_all(){
        currentExp = "0";
        lastExp = "0"; hexBehindExp="0"; binBehindExp="0"; decBehindExp="0"; octBehindExp="0";

        UpdateTransformation();
    }

    public void delete(){
        if(!currentExp.equals("")){
            currentExp = currentExp.substring(0,currentExp.length()-1);
            if(currentExp.equals(""))
                currentExp = "0";
            UpdateTransformation();
        }
    }

    public boolean isOperator(String str){
        if (str.equals("+") || str.equals("-") || str.equals("/") || str.equals("*") ||
                str.equals("%") || str.equals("^")) return true;
        return false;
    }

    public void UpdateTransformation(){
        if(provider.equals("Dec")){
            hexValue = Integer.toHexString(Integer.parseInt(currentExp));
            binValue = Integer.toBinaryString(Integer.parseInt(currentExp));
            octValue = Integer.toOctalString(Integer.parseInt(currentExp));
            decValue = currentExp;
        }else if(provider.equals("Hex")){
            hexValue = currentExp;
            decValue = Integer.parseInt(currentExp,16)+"";
            binValue = Integer.toBinaryString(Integer.parseInt(decValue));
            octValue = Integer.toOctalString(Integer.parseInt(decValue));
        }else if(provider.equals("Bin")){
            binValue = currentExp;
            decValue = Integer.parseInt(currentExp,2)+"";
            hexValue = Integer.toHexString(Integer.parseInt(decValue));
            octValue = Integer.toOctalString(Integer.parseInt(decValue));
        }else{
            octValue = currentExp;
            decValue = Integer.parseInt(currentExp,8)+"";
            hexValue = Integer.toHexString(Integer.parseInt(decValue));
            binValue = Integer.toBinaryString(Integer.parseInt(decValue));
        }
        render();
    }

    public void setProviderTo(String newProvider){
        provider = newProvider;
        if(provider.equals("Dec")){
            currentExp = decValue;
            lastExp = decBehindExp;
        }else if(provider.equals("Hex")){
            currentExp = hexValue;
            lastExp = hexBehindExp;
        }else if(provider.equals("Bin")){
            currentExp = binValue;
            lastExp = binBehindExp;
        }else{
            currentExp = octValue;
            lastExp = octBehindExp;
        }
        HexBody.setBackground(getResources().getDrawable(R.drawable.no_orange_border));
        DecBody.setBackground(getResources().getDrawable(R.drawable.no_orange_border));
        BinBody.setBackground(getResources().getDrawable(R.drawable.no_orange_border));
        OctBody.setBackground(getResources().getDrawable(R.drawable.no_orange_border));

        Btn_A.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_B.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_C.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_D.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_E.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_F.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_2.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_3.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_4.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_5.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_6.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_7.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_8.setTextColor(getResources().getColor(R.color.no_blocked));
        Btn_9.setTextColor(getResources().getColor(R.color.no_blocked));

        if(provider.equals("Hex")) {
            HexBody.setBackground(getResources().getDrawable(R.drawable.orange_border));
        }
        else if(provider.equals("Dec")) {
            DecBody.setBackground(getResources().getDrawable(R.drawable.orange_border));
            Btn_A.setTextColor(getResources().getColor(R.color.blocked));
            Btn_B.setTextColor(getResources().getColor(R.color.blocked));
            Btn_C.setTextColor(getResources().getColor(R.color.blocked));
            Btn_D.setTextColor(getResources().getColor(R.color.blocked));
            Btn_E.setTextColor(getResources().getColor(R.color.blocked));
            Btn_F.setTextColor(getResources().getColor(R.color.blocked));
        }
        else if(provider.equals("Bin")) {
            BinBody.setBackground(getResources().getDrawable(R.drawable.orange_border));
            Btn_A.setTextColor(getResources().getColor(R.color.blocked));
            Btn_B.setTextColor(getResources().getColor(R.color.blocked));
            Btn_C.setTextColor(getResources().getColor(R.color.blocked));
            Btn_D.setTextColor(getResources().getColor(R.color.blocked));
            Btn_E.setTextColor(getResources().getColor(R.color.blocked));
            Btn_F.setTextColor(getResources().getColor(R.color.blocked));
            Btn_2.setTextColor(getResources().getColor(R.color.blocked));
            Btn_3.setTextColor(getResources().getColor(R.color.blocked));
            Btn_4.setTextColor(getResources().getColor(R.color.blocked));
            Btn_5.setTextColor(getResources().getColor(R.color.blocked));
            Btn_6.setTextColor(getResources().getColor(R.color.blocked));
            Btn_7.setTextColor(getResources().getColor(R.color.blocked));
            Btn_8.setTextColor(getResources().getColor(R.color.blocked));
            Btn_9.setTextColor(getResources().getColor(R.color.blocked));
        }
        else {
            OctBody.setBackground(getResources().getDrawable(R.drawable.orange_border));
            Btn_A.setTextColor(getResources().getColor(R.color.blocked));
            Btn_B.setTextColor(getResources().getColor(R.color.blocked));
            Btn_C.setTextColor(getResources().getColor(R.color.blocked));
            Btn_D.setTextColor(getResources().getColor(R.color.blocked));
            Btn_E.setTextColor(getResources().getColor(R.color.blocked));
            Btn_F.setTextColor(getResources().getColor(R.color.blocked));
            Btn_8.setTextColor(getResources().getColor(R.color.blocked));
            Btn_9.setTextColor(getResources().getColor(R.color.blocked));
        }

        UpdateTransformation();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        all_ids.add(R.id.id_prog_1);all_ids.add(R.id.id_prog_2);all_ids.add(R.id.id_prog_3);all_ids.add(R.id.id_prog_4);
        all_ids.add(R.id.id_prog_5);all_ids.add(R.id.id_prog_6);all_ids.add(R.id.id_prog_7);all_ids.add(R.id.id_prog_8);
        all_ids.add(R.id.id_prog_9);all_ids.add(R.id.id_prog_A);all_ids.add(R.id.id_prog_B);all_ids.add(R.id.id_prog_C);
        all_ids.add(R.id.id_prog_D);all_ids.add(R.id.id_prog_E);all_ids.add(R.id.id_prog_F);all_ids.add(R.id.id_prog_sign);
        all_ids.add(R.id.id_prog_dot);all_ids.add(R.id.id_prog_zero);all_ids.add(R.id.id_prog_equal);all_ids.add(R.id.id_prog_plus);
        all_ids.add(R.id.id_prog_minus);all_ids.add(R.id.id_prog_div);all_ids.add(R.id.id_prog_x);all_ids.add(R.id.id_prog_delete);
        all_ids.add(R.id.id_prog_delete_all);all_ids.add(R.id.id_prog_mod);all_ids.add(R.id.id_prog_Hex_body);all_ids.add(R.id.id_prog_Bin_body);
        all_ids.add(R.id.id_prog_Dec_body);all_ids.add(R.id.id_prog_open_para);all_ids.add(R.id.id_prog_close_para);all_ids.add(R.id.id_prog_Oct_body);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_programmer, container, false);
          topScreen = (TextView) rootView.findViewById(R.id.id_prog_top_screen);
          bottomScreen = (TextView) rootView.findViewById(R.id.id_prog_bottom_screen);
          HexText = (TextView) rootView.findViewById(R.id.id_prog_Hex_text);
          DecText = (TextView) rootView.findViewById(R.id.id_prog_Dec_text);
          OctText = (TextView) rootView.findViewById(R.id.id_prog_Oct_text);
          BinText = (TextView) rootView.findViewById(R.id.id_prog_Bin_text);

          HexBody = (LinearLayout) rootView.findViewById(R.id.id_prog_Hex_body);
          DecBody = (LinearLayout) rootView.findViewById(R.id.id_prog_Dec_body);
          OctBody = (LinearLayout) rootView.findViewById(R.id.id_prog_Oct_body);
          BinBody = (LinearLayout) rootView.findViewById(R.id.id_prog_Bin_body);

          Btn_A = (TextView) rootView.findViewById(R.id.id_prog_A);
          Btn_B = (TextView) rootView.findViewById(R.id.id_prog_B);
          Btn_C = (TextView) rootView.findViewById(R.id.id_prog_C);
          Btn_D = (TextView) rootView.findViewById(R.id.id_prog_D);
          Btn_E = (TextView) rootView.findViewById(R.id.id_prog_E);
          Btn_F = (TextView) rootView.findViewById(R.id.id_prog_F);
          Btn_2 = (TextView) rootView.findViewById(R.id.id_prog_2);
          Btn_3 = (TextView) rootView.findViewById(R.id.id_prog_3);
          Btn_4 = (TextView) rootView.findViewById(R.id.id_prog_4);
          Btn_5 = (TextView) rootView.findViewById(R.id.id_prog_5);
          Btn_6 = (TextView) rootView.findViewById(R.id.id_prog_6);
          Btn_7 = (TextView) rootView.findViewById(R.id.id_prog_7);
          Btn_8 = (TextView) rootView.findViewById(R.id.id_prog_8);
          Btn_9 = (TextView) rootView.findViewById(R.id.id_prog_9);

          Btn_A.setTextColor(getResources().getColor(R.color.blocked));
          Btn_B.setTextColor(getResources().getColor(R.color.blocked));
          Btn_C.setTextColor(getResources().getColor(R.color.blocked));
          Btn_D.setTextColor(getResources().getColor(R.color.blocked));
          Btn_E.setTextColor(getResources().getColor(R.color.blocked));
          Btn_F.setTextColor(getResources().getColor(R.color.blocked));

          DecBody.setBackground(getResources().getDrawable(R.drawable.orange_border));

          for(int id : all_ids)
              rootView.findViewById(id).setOnClickListener(this);


        return rootView;
    }

    public void littleJump(View view, float x, float y){
        view.animate().scaleY(y).scaleX(x).setDuration(120).setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float paramFloat) {
                return Math.abs(paramFloat -1f);
            }
        });
    }

    public void BlockClickEvent(View view){
        ((TextView)view).setTextColor(getResources().getColor(R.color.blocked));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.id_prog_delete)
            littleJump(v, 0.4f,0.4f);
        else if(v.getId() == R.id.id_prog_Hex_body ||v.getId() == R.id.id_prog_Bin_body
                ||v.getId() == R.id.id_prog_Dec_body || v.getId() == R.id.id_prog_Oct_body)
            littleJump(v,0.95f,0.9f);
        else
            littleJump(v, 0.76f,0.76f);

        switch (v.getId()){
            case R.id.id_prog_1: NumberClickHandler("1"); break;
            case R.id.id_prog_2: if(!provider.equals("Bin")) NumberClickHandler("2"); break;
            case R.id.id_prog_3: if(!provider.equals("Bin")) NumberClickHandler("3"); break;
            case R.id.id_prog_4: if(!provider.equals("Bin")) NumberClickHandler("4"); break;
            case R.id.id_prog_5: if(!provider.equals("Bin")) NumberClickHandler("5"); break;
            case R.id.id_prog_6: if(!provider.equals("Bin")) NumberClickHandler("6"); break;
            case R.id.id_prog_7: if(!provider.equals("Bin")) NumberClickHandler("7"); break;
            case R.id.id_prog_8: if(!provider.equals("Bin") && !provider.equals("Oct")) NumberClickHandler("8"); break;
            case R.id.id_prog_9: if(!provider.equals("Bin") && !provider.equals("Oct")) NumberClickHandler("9"); break;
            case R.id.id_prog_zero: NumberClickHandler("0"); break;
            case R.id.id_prog_A: if(provider.equals("Hex")) NumberClickHandler("A"); break;
            case R.id.id_prog_D: if(provider.equals("Hex")) NumberClickHandler("D"); break;
            case R.id.id_prog_C: if(provider.equals("Hex")) NumberClickHandler("C"); break;
            case R.id.id_prog_B: if(provider.equals("Hex")) NumberClickHandler("B"); break;
            case R.id.id_prog_E: if(provider.equals("Hex")) NumberClickHandler("E"); break;
            case R.id.id_prog_F: if(provider.equals("Hex")) NumberClickHandler("F"); break;

            case R.id.id_prog_plus: OperationClickHandler("+"); break;
            case R.id.id_prog_x: OperationClickHandler("*"); break;
            case R.id.id_prog_minus: OperationClickHandler("-"); break;
            case R.id.id_prog_div: OperationClickHandler("/"); break;
            case R.id.id_prog_mod: OperationClickHandler("%"); break;

            case R.id.id_prog_close_para: ParenthesisHanlder(")"); break;
            case R.id.id_prog_open_para: ParenthesisHanlder("("); break;

            case R.id.id_prog_delete: delete(); break;
            case R.id.id_prog_delete_all: delete_all(); break;

            case R.id.id_prog_Hex_body: setProviderTo("Hex"); break;
            case R.id.id_prog_Dec_body: setProviderTo("Dec"); break;
            case R.id.id_prog_Oct_body: setProviderTo("Oct"); break;
            case R.id.id_prog_Bin_body: setProviderTo("Bin"); break;

            case R.id.id_prog_equal: Calculate(); break;

            default:break;
        }
    }
}