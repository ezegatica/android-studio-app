package app.gati;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {
    TextView timerTxt;
    TextView textito;
    Button botonEmpezar;
    int estado;
    //#Estados:
    // Empezar:  0
    // Stop:     1
    // Reinciar: 2
    private long startTime;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timerTxt.setText(String.format("%d:%02d:%02d", minutes, seconds,millis));
            timerHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holi);
        botonEmpezar = findViewById(R.id.empezarButton);
        botonEmpezar.setText(getString(R.string.empezar));
        textito =  findViewById(R.id.textito);
        timerTxt = findViewById(R.id.textoTimer);
        estado = 0;

        textito.setText(getString(R.string.saludo, getIntent().getStringExtra(Login.getMESSAGE())));
        timerTxt.setText(getString(R.string.empezarInit));

        botonEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estado == 1) {
                    timerHandler.removeCallbacks(timerRunnable);
                    botonEmpezar.setText(getString(R.string.reiniciar));
                    estado = 2;
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    botonEmpezar.setText(getString(R.string.detener));
                    estado = 1;
                }
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        botonEmpezar.setText(getString(R.string.empezar));
    }
}