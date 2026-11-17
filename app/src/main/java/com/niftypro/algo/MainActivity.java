
package com.niftypro.algo;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView signal, paperStatus, entryLabel, stopLabel, targetLabel;
    EditText entryInput, stopInput, targetInput;
    boolean paperOn = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(24, 20, 24, 20);
        scroll.addView(root);
        setContentView(scroll);

        TextView title = text("NIFTY PRO ALGO", 26, true);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView subtitle = text("Trading Dashboard - Demo", 15, false);
        subtitle.setGravity(Gravity.CENTER);
        root.addView(subtitle);

        addButton(root, "NIFTY 50", v -> setSignal("NIFTY 50 SELECTED"));
        addButton(root, "BANKNIFTY", v -> setSignal("BANKNIFTY SELECTED"));

        paperStatus = text("PAPER TRADING: OFF", 18, true);
        root.addView(paperStatus);

        addButton(root, "PAPER TRADING ON / OFF", v -> {
            paperOn = !paperOn;
            paperStatus.setText("PAPER TRADING: " +
                    (paperOn ? "ON" : "OFF"));
        });

        signal = text("SIGNAL: WAIT", 22, true);
        root.addView(signal);

        addButton(root, "BUY (DEMO)", v -> setSignal("SIGNAL: BUY (DEMO)"));
        addButton(root, "SELL (DEMO)", v -> setSignal("SIGNAL: SELL (DEMO)"));
        addButton(root, "WAIT", v -> setSignal("SIGNAL: WAIT"));

        root.addView(text("TRADE LEVELS", 20, true));

        entryInput = input(root, "Enter Entry Price");
        stopInput = input(root, "Enter Stop Loss");
        targetInput = input(root, "Enter Target");

        addButton(root, "SET TRADE LEVELS", v -> updateLevels());

        entryLabel = text("ENTRY PRICE: —", 17, true);
        stopLabel = text("STOP LOSS: —", 17, true);
        targetLabel = text("TARGET: —", 17, true);

        root.addView(entryLabel);
        root.addView(stopLabel);
        root.addView(targetLabel);

        root.addView(text(
            "Demo only. No live market data or real orders.",
            14, false
        ));
    }

    TextView text(String value, int size, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setPadding(5, 12, 5, 12);
        if (bold) {
            t.setTypeface(null, Typeface.BOLD);
        }
        return t;
    }

    EditText input(LinearLayout root, String hint) {
        EditText e = new EditText(this);
        e.setSingleLine(true);
        e.setInputType(8194);
        e.setHint(hint);
        root.addView(e);
        return e;
    }

    void addButton(LinearLayout root, String name,
                   View.OnClickListener listener) {
        Button b = new Button(this);
        b.setText(name);
        b.setOnClickListener(listener);
        root.addView(b);
    }

    void setSignal(String value) {
        signal.setText(value);
    }

    void updateLevels() {
        entryLabel.setText("ENTRY PRICE: " + value(entryInput));
        stopLabel.setText("STOP LOSS: " + value(stopInput));
        targetLabel.setText("TARGET: " + value(targetInput));
    }

    String value(EditText e) {
        String s = e.getText().toString().trim();
        return s.isEmpty() ? "—" : "₹" + s;
    }
}
