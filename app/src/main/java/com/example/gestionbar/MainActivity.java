package com.example.gestionbar; // deja el package que te generó Android Studio

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etTotal;
    CheckBox cbPropina;
    SeekBar sbPropina;
    TextView tvPropina, tvResultado;
    RadioButton rbEfectivo;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTotal = findViewById(R.id.etTotal);
        cbPropina = findViewById(R.id.cbPropina);
        sbPropina = findViewById(R.id.sbPropina);
        tvPropina = findViewById(R.id.tvPropina);
        tvResultado = findViewById(R.id.tvResultado);
        rbEfectivo = findViewById(R.id.rbEfectivo);
        ratingBar = findViewById(R.id.ratingBar);
        Button btnCalcular = findViewById(R.id.btnCalcular);

        // Muestra el % de propina al mover el SeekBar
        sbPropina.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPropina.setText(getString(R.string.propina, progress));
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        btnCalcular.setOnClickListener(v -> calcular());
    }

    private void calcular() {
        String texto = etTotal.getText().toString().trim();

        // Los errores se muestran en rojo
        tvResultado.setTextColor(Color.RED);

        if (texto.isEmpty()) {
            tvResultado.setText(R.string.error_vacio);
            return;
        }

        double total;
        try {
            total = Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            tvResultado.setText(R.string.error_formato);
            return;
        }

        if (total <= 0) {
            tvResultado.setText(R.string.error_minimo);
            return;
        }

        if (cbPropina.isChecked()) {
            total = total + total * sbPropina.getProgress() / 100;
        }

        String pago = rbEfectivo.isChecked() ? getString(R.string.efectivo)
                : getString(R.string.tarjeta);

        tvResultado.setTextColor(Color.parseColor("#2E7D32")); // verde
        tvResultado.setText(getString(R.string.resultado, total, pago, ratingBar.getRating()));
    }
}