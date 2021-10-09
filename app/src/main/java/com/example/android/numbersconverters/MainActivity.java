package com.example.android.numbersconverters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private EditText editTextDecimal;
private EditText editTextBinary;
private EditText editTextOctal;
private EditText editTextHexa;
private Button buttonClear;
    private String value;
    private View.OnFocusChangeListener onFocusChangeListener;
    private int focusedViewid;
    private TextWatcher textWatcher;
    private View.OnClickListener copyClickListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextDecimal=findViewById(R.id.etvdecimal);
        editTextBinary=findViewById(R.id.etvBinary);
        editTextHexa=findViewById(R.id.etvHexa);
        editTextOctal=findViewById(R.id.etvOctal);

        buttonClear=findViewById(R.id.btnClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearfields();
            }
        });
        textWatcher=new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                value = ((EditText) findViewById(focusedViewid)).getText().toString().trim();
                if (value.length() > 0) {
                    convertNumber();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        onFocusChangeListener=new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
               if (b){
                   //Toast.makeText(getApplicationContext(), "FOCUS IN", Toast.LENGTH_SHORT).show();
                   focusedViewid=view.getId();
                   ((EditText) findViewById(focusedViewid)).addTextChangedListener(textWatcher);
                   GradientDrawable gradientDrawable=new GradientDrawable(
                           GradientDrawable.Orientation.TR_BL,
                           new int[]{Color.parseColor("#ffdab9"),Color.parseColor("#ffdead")}
                   );
                   gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                   gradientDrawable.setCornerRadius(10);

                   if(focusedViewid==R.id.etvdecimal){
                       gradientDrawable.setStroke(8, ContextCompat.getColor(getApplicationContext(), android.R.color.holo_green_light));
                   }
                   else{
                       gradientDrawable.setStroke(8,ContextCompat.getColor(getApplicationContext(), android.R.color.holo_blue_light));
                   }

                   view.setBackground(gradientDrawable);
               }else{
                   ((EditText) findViewById(focusedViewid)).removeTextChangedListener(textWatcher);
                   if(focusedViewid==R.id.etvdecimal){
                       view.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.focusview));
                   }
                   else {
                       view.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.view_design));
                   }
               }
            }
        };
        editTextDecimal.setOnFocusChangeListener(onFocusChangeListener);
        editTextBinary.setOnFocusChangeListener(onFocusChangeListener);
        editTextHexa.setOnFocusChangeListener(onFocusChangeListener);
        editTextOctal.setOnFocusChangeListener(onFocusChangeListener);

        copyClickListner=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager=(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData=null;
                switch (view.getId()){
                    case R.id.copy_decimal:
                        clipData=ClipData.newPlainText("COPIED DATA",editTextDecimal.getText().toString());
                        break;
                    case R.id.copy_binary:
                        clipData=ClipData.newPlainText("COPIED DATA",editTextBinary.getText().toString());
                        break;
                    case R.id.copy_Octal:
                        clipData=ClipData.newPlainText("COPIED DATA",editTextOctal.getText().toString());
                        break;
                    case R.id.copy_Hexa:
                        clipData=ClipData.newPlainText("COPIED DATA",editTextHexa.getText().toString());
                        break;
                }
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), ""+clipData.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        findViewById(R.id.copy_decimal).setOnClickListener(copyClickListner);
        findViewById(R.id.copy_Hexa).setOnClickListener(copyClickListner);
        findViewById(R.id.copy_binary).setOnClickListener(copyClickListner);
        findViewById(R.id.copy_Octal).setOnClickListener(copyClickListner);


    }

    private void clearfields() {
        editTextBinary.setText("");
        editTextOctal.setText("");
        editTextHexa.setText("");
        editTextDecimal.setText("");
    }

    private void convertNumber() {
        try {
            long num=0;

            switch (focusedViewid){
                case R.id.etvdecimal:
                   num= Long.parseLong(value);
                    editTextBinary.setText(String.valueOf(Long.toBinaryString(num)));
                    editTextOctal.setText(String.valueOf(Long.toOctalString(num)));
                    editTextHexa.setText(String.valueOf(Long.toHexString(num)));
                    break;


                case R.id.etvBinary:
                    num= Long.parseLong(value,2);
                    editTextDecimal.setText(String.valueOf(num));
                    editTextOctal.setText(String.valueOf(Long.toOctalString(num)));
                    editTextHexa.setText(String.valueOf(Long.toHexString(num)));
                    break;
                case R.id.etvOctal:
                    num= Long.parseLong(value,8);
                    editTextDecimal.setText(String.valueOf(num));
                    editTextBinary.setText(String.valueOf(Long.toBinaryString(num)));
                    editTextHexa.setText(String.valueOf(Long.toHexString(num)));
                    break;
                case R.id.etvHexa:
                    num= Long.parseLong(value,16);
                    editTextDecimal.setText(String.valueOf(num));
                    editTextBinary.setText(String.valueOf(Long.toBinaryString(num)));
                    editTextOctal.setText(String.valueOf(Long.toOctalString(num)));
                    break;


            }


        }catch (NumberFormatException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}