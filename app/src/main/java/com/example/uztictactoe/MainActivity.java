package com.example.uztictactoe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons = new Button[9];
    private boolean playerXTurn = true; // true = X, false = O
    private int moveCount = 0;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);

        // Initialize buttons
        for (int i = 0; i < 9; i++) {
            String buttonID = "btn" + i;
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = findViewById(resID);

            final int index = i;
            buttons[i].setOnClickListener(v -> onButtonClick(index));
        }

        // Reset Button
        Button btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(v -> resetGame());
    }

    private void onButtonClick(int index) {
        if (!buttons[index].getText().toString().equals("")) return; // Ignore if filled

        if (playerXTurn) {
            buttons[index].setText("X");
            tvStatus.setText("Player O's Turn");
        } else {
            buttons[index].setText("O");
            tvStatus.setText("Player X's Turn");
        }

        moveCount++;
        if (checkWinner()) {
            tvStatus.setText((playerXTurn ? "Player X" : "Player O") + " Wins!");
            tvStatus.setTextColor(getResources().getColor(R.color.green));

            disableButtons();
        } else if (moveCount == 9) {
            tvStatus.setText("It's a Draw!");
        } else {
            playerXTurn = !playerXTurn; // Switch turn
        }
    }

    // renamed helper to avoid conflict with Activity#getText(int)
    private String getButtonText(int index) {
        if (buttons[index] == null) return "";
        CharSequence cs = buttons[index].getText();
        return cs == null ? "" : cs.toString();
    }

    private boolean checkWinner() {
        String[][] winPatterns = {
                {getButtonText(0), getButtonText(1), getButtonText(2)},
                {getButtonText(3), getButtonText(4), getButtonText(5)},
                {getButtonText(6), getButtonText(7), getButtonText(8)},
                {getButtonText(0), getButtonText(3), getButtonText(6)},
                {getButtonText(1), getButtonText(4), getButtonText(7)},
                {getButtonText(2), getButtonText(5), getButtonText(8)},
                {getButtonText(0), getButtonText(4), getButtonText(8)},
                {getButtonText(2), getButtonText(4), getButtonText(6)}
        };

        for (String[] pattern : winPatterns) {
            if (!pattern[0].equals("") && pattern[0].equals(pattern[1]) && pattern[1].equals(pattern[2])) {
                return true;
            }
        }
        return false;
    }

    private void disableButtons() {
        for (Button btn : buttons) {
            if (btn != null) btn.setEnabled(false);
        }
    }

    private void resetGame() {
        for (Button btn : buttons) {
            if (btn != null) {
                btn.setText("");
                btn.setEnabled(true);
            }
        }
        playerXTurn = true;
        moveCount = 0;
        tvStatus.setText("Player X's Turn");
        tvStatus.setTextColor(getResources().getColor(R.color.white));

    }
}
