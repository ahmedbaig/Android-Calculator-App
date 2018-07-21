package com.kashmire.ahmedbaig.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onButtonClick(View v){
        TextView display = findViewById(R.id.display);
        TextView history = findViewById(R.id.history);
        // display is assumed to be the TextView used for the Calculator display
        String currDisplayValue = display.getText().toString();
        String currHistoryValue = display.getText().toString();
        Button b = (Button)v;  // We assume only buttons have onClickListeners for this App
        String label = b.getText().toString();  // read the label on the button clicked
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
        switch (v.getId())
        {
            case R.id.buttonPlus:
            case R.id.buttonMinus:
            case R.id.buttonMultiply:
            case R.id.buttonDivide:
                String operator = label;
                display.append(operator);
                break;
            case R.id.buttonEqual:
                try{
                    double result = (double)engine.eval(currDisplayValue);
                    currHistoryValue = currDisplayValue+" = "+(result)+"\n";
                    history.append(currHistoryValue);
                    currDisplayValue = Double.toString(result);
                    display.setText(currDisplayValue);
                }catch(ScriptException e){
                    Toast.makeText(this, "Exception Raised", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonDel:
                try{
                    currDisplayValue = currDisplayValue.substring(0, currDisplayValue.length()-1);
                    display.setText(currDisplayValue);
                }catch (Exception e){

                    Toast.makeText(this,"Nothing to remove", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.clear:
                history.setText("");
                display.setText("");
                break;
            default:
                // If the button isn't one of the above, it must be a digit
                String digit = label;// This is the digit pressed
                display.append(digit);
                break;
        }
    }
}
