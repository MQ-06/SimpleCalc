package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;  // Ensure this import is present
import org.mozilla.javascript.Scriptable; // Ensure this import is present

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        buttonC = assignId(R.id.buttons_c);
        buttonBrackOpen = assignId(R.id.button_open_bracket);
        buttonBrackClose = assignId(R.id.button_close_bracket);
        buttonDivide = assignId(R.id.buttons_divider);
        buttonMultiply = assignId(R.id.buttons_multiply);
        buttonPlus = assignId(R.id.buttons_plus);
        buttonMinus = assignId(R.id.buttons_minus);
        buttonEquals = assignId(R.id.buttons_equal);
        button0 = assignId(R.id.button_0);
        button1 = assignId(R.id.button_1);
        button2 = assignId(R.id.button_2);
        button3 = assignId(R.id.button_3);
        button4 = assignId(R.id.button_4);
        button5 = assignId(R.id.button_5);
        button6 = assignId(R.id.button_6);
        button7 = assignId(R.id.button_7);
        button8 = assignId(R.id.button_8);
        button9 = assignId(R.id.button_9);
        buttonAC = assignId(R.id.button_ac);
        buttonDot = assignId(R.id.button_dot);
    }

    private MaterialButton assignId(int id) {
        MaterialButton btn = findViewById(id);
        btn.setOnClickListener(this);
        return btn;
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }

        if (buttonText.equals("=")) {
            solutionTv.setText(resultTv.getText());
            return;
        }

        if (buttonText.equals("C")) {
            if (!dataToCalculate.isEmpty()) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
        } else {
            dataToCalculate += buttonText;
        }

        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Err")) {
            resultTv.setText(finalResult);
        }
    }

    private String getResult(String data) {
        try {
            // Correct placement of Context initialization inside the method
            Context context = Context.enter();  // Initialize the context
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "JavaScript", 1, null).toString();

            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }

            return finalResult;
        } catch (ArithmeticException e) {
            return "Math Error";
        } catch (Exception e) {
            return "Err";
        } finally {
            Context.exit(); // Ensure proper cleanup
        }
    }
}
