package com.example.kunkumamithunbalajivenkatesan.sipher;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class createMessage extends Fragment {

    public OnFragmentInteractionListener fil;
    public EditText mes, shift;
    Spinner spinner;
    public String msg = "" ;
    int selection = 0;
    StringBuilder mess = new StringBuilder();
    StringBuilder pass = new StringBuilder();

    public createMessage() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_message, container, false);
        mes = view.findViewById(R.id.message);
        shift = view.findViewById(R.id.shift);
        final FloatingActionButton send = view.findViewById(R.id.fabSend);
        final FloatingActionButton save = view.findViewById(R.id.fabSave);
        send.setVisibility(View.INVISIBLE);
        spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selection = i;
                save.setVisibility(View.VISIBLE);
                send.setVisibility(View.INVISIBLE);
                checkSelection();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mes.getText().toString().length() > 0 && shift.getText().toString().length()>0 && selection < 2){
                int key = Integer.parseInt(shift.getText().toString());
                key = checkKey(key);
                msg = fil.sendData(mes.getText().toString(), Integer.toString(key), selection);
                send.setVisibility(View.VISIBLE);
                    save.setVisibility(View.INVISIBLE);

                }

                else if (mes.getText().toString().length() > 0 && shift.getText().toString().length()>0 && selection == 2){
                    msg = fil.sendData(mes.getText().toString(), shift.getText().toString(), selection);
                    save.setVisibility(View.INVISIBLE);
                    send.setVisibility(View.VISIBLE);
                }
                else if (mes.getText().toString().length() > 0 && shift.getText().toString().length()>0 && selection > 2){

                    if (checkWordLength(mes.getText().toString(), shift.getText().toString())) {

                            msg = fil.sendData(mess.toString(), pass.toString(), selection);
                            save.setVisibility(View.INVISIBLE);
                            send.setVisibility(View.VISIBLE);


                    }
                    else {
                        Toast.makeText(getContext(),"Both the words should be of same character length", Toast.LENGTH_SHORT).show();
                    }
                }

                else{
                    Toast.makeText(getContext(),"Pls enter valid data", Toast.LENGTH_SHORT).show();
                    save.setVisibility(View.VISIBLE);
                    send.setVisibility(View.INVISIBLE);
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fil.shareData(msg);
                msg = "";
                save.setVisibility(View.VISIBLE);
                send.setVisibility(View.INVISIBLE);
                checkSelection();
            }
        });

        send.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (selection == 0){
                            mes.setText("");
                            shift.setText(R.string.rot13);
                        }
                        else if (selection >= 1){
                            mes.setText("");
                            shift.setText("");
                        }
                        return false;
                    }
                }
        );
        return view;
    }

    private int checkKey(int key) {
        if (key < 0){
            key = key * -1;
        }
        if (key > 26){
            while (key > 26){
                key = key % 26;
            }
        }
        return key;
    }


    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        Activity act = (Activity) context;
        fil = (OnFragmentInteractionListener) act;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fil = null;
    }


    public interface OnFragmentInteractionListener {

        String sendData(String message, String shift, int type);
        void shareData(String message);
    }

//    public class Encrypt implements View.OnClickListener{
//        @Override
//        public void onClick(View v) {
//            msg = fil.sendData(mes.getText().toString(), shift.getText().toString(), selection);
//        }
//    }

    private void checkSelection(){
        if (selection == 0){
            shift.setVisibility(View.INVISIBLE);
            shift.setText(R.string.rot13);
        }
        else if (selection == 1 ){
            shift.setInputType(InputType.TYPE_CLASS_NUMBER);
            shift.setVisibility(View.VISIBLE);
            shift.setText("");
        }
        else if (selection >= 2){
            shift.setInputType(InputType.TYPE_CLASS_TEXT);
            shift.setVisibility(View.VISIBLE);
            shift.setText("");
        }
    }

    private boolean checkWordLength(String s1, String s2){
        boolean flag = false;
        for (int i =0; i < s1.length(); i++){
            if (s1.charAt(i) != ' '){
                mess.append(s1.charAt(i));
            }
        }
        for (int i= 0;  i < s2.length(); i++){
            if (s2.charAt(i) != ' '){
                pass.append(s2.charAt(i));
            }
        }
        if (mess.toString().length() == pass.toString().length()) flag = true;
        return flag;
    }


}
