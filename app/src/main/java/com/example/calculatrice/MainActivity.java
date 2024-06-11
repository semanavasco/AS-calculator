package com.example.calculatrice;

import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView historique;
    private TextView calculActuel;

    private double premierChiffre;
    private char operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        historique = findViewById(R.id.historique);
        calculActuel = findViewById(R.id.calculActuel);
    }

    public void onClicNombre(View view) {
        if (calculActuel.getText().toString().length() >= 9) return;

        Dictionary<Integer, Integer> equivalenceId = new Hashtable<>();
        equivalenceId.put(R.id.zero, 0);
        equivalenceId.put(R.id.un, 1);
        equivalenceId.put(R.id.deux, 2);
        equivalenceId.put(R.id.trois, 3);
        equivalenceId.put(R.id.quatre, 4);
        equivalenceId.put(R.id.cinq, 5);
        equivalenceId.put(R.id.six, 6);
        equivalenceId.put(R.id.sept, 7);
        equivalenceId.put(R.id.huit, 8);
        equivalenceId.put(R.id.neuf, 9);

        if (calculActuel.getText().toString().endsWith(".")) {
            calculActuel.setText(calculActuel.getText().toString() + Objects.requireNonNull(equivalenceId.get(view.getId())));
        } else if (Float.parseFloat(calculActuel.getText().toString()) == 0f) {
            calculActuel.setText(String.format(Objects.requireNonNull(equivalenceId.get(view.getId())).toString()));
        } else {
            double ancienneValeur = Double.parseDouble(calculActuel.getText().toString());
            int nouvelleValeur = Objects.requireNonNull(equivalenceId.get(view.getId()));
            String afficher;

            if (ancienneValeur - (int) ancienneValeur > 0) {
                afficher = String.valueOf(ancienneValeur) + nouvelleValeur;
            } else {
                afficher = String.valueOf((int) ancienneValeur) + nouvelleValeur;
            }

            calculActuel.setText(afficher);
        }

        if (calculActuel.getText().toString().length() == 7) {
            calculActuel.setTextSize(90);
        } else if (calculActuel.getText().toString().length() == 8) {
            calculActuel.setTextSize(80);
        } else if (calculActuel.getText().toString().length() >= 9) {
            calculActuel.setTextSize(70);
        } else {
            calculActuel.setTextSize(100);
        }
    }

    public void onClicSpecial(View view) {
        Dictionary<Integer, String> equivalenceId = new Hashtable<>();
        equivalenceId.put(R.id.C, "C");
        equivalenceId.put(R.id.CE, "CE");
        equivalenceId.put(R.id.DEL, "DEL");
        equivalenceId.put(R.id.DIVISER, "/");
        equivalenceId.put(R.id.SOUSTRACTION, "-");
        equivalenceId.put(R.id.ADDITION, "+");
        equivalenceId.put(R.id.MULTIPLICATION, "*");
        equivalenceId.put(R.id.EGAL, "=");
        equivalenceId.put(R.id.point, ".");

        switch (Objects.requireNonNull(equivalenceId.get(view.getId()))) {
            case "C":
                calculActuel.setText("0");
                break;
            case "CE":
                historique.setText("");
                calculActuel.setText("0");
                break;
            case "DEL":
                CharSequence ancienneValeur = calculActuel.getText();
                CharSequence nouvelleValeur = ancienneValeur.subSequence(0, ancienneValeur.length() - 1);

                if (nouvelleValeur.length() == 0) calculActuel.setText("0");
                else calculActuel.setText(nouvelleValeur);
                break;
            case "/":
                if (calculActuel.getText().equals("0")) return;

                operation = '/';
                premierChiffre = Float.parseFloat(calculActuel.getText().toString());

                historique.setText(calculActuel.getText() + "/");
                calculActuel.setText("0");
                break;
            case "-":
                if (calculActuel.getText().equals("0")) return;

                operation = '-';
                premierChiffre = Float.parseFloat(calculActuel.getText().toString());

                historique.setText(calculActuel.getText() + "-");
                calculActuel.setText("0");
                break;
            case "+":
                if (calculActuel.getText().equals("0")) return;

                operation = '+';
                premierChiffre = Float.parseFloat(calculActuel.getText().toString());

                historique.setText(calculActuel.getText() + "+");
                calculActuel.setText("0");
                break;
            case "*":
                if (calculActuel.getText().equals("0")) return;

                operation = '*';
                premierChiffre = Float.parseFloat(calculActuel.getText().toString());

                historique.setText(calculActuel.getText() + "*");
                calculActuel.setText("0");
                break;
            case "=":
                double resultat = 0;

                switch (operation) {
                    case '+':
                        resultat = premierChiffre + Float.parseFloat(calculActuel.getText().toString());
                        break;
                    case '-':
                        resultat = premierChiffre - Float.parseFloat(calculActuel.getText().toString());
                        break;
                    case '*':
                        resultat = premierChiffre * Float.parseFloat(calculActuel.getText().toString());
                        break;
                    case '/':
                        resultat = premierChiffre / Float.parseFloat(calculActuel.getText().toString());
                        break;
                    default:
                        historique.setText(calculActuel.getText().toString() + "=");
                        return;
                }

                historique.setText(historique.getText().toString() + calculActuel.getText().toString() + "=");

                if (resultat - (int) resultat > 0) {
                    calculActuel.setText(String.format(Double.toString(resultat)));
                } else {
                    calculActuel.setText(String.format(Integer.toString((int) resultat)));
                }

                operation = ' ';
                break;
            case ".":
                calculActuel.setText(calculActuel.getText() + ".");
                break;
        }
    }
}