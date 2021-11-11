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
    TextView textoBestTime;

    Button botonEmpezar;
    int estado;
    //#Estados:
    // Empezar:  0
    // Stop:     1
    // Reinciar: 2
    private long startTime;
    long millis;
    long millisbien;
    int seconds;
    int minutes;
    long bestTime = 1;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            millis = System.currentTimeMillis() - startTime;
            if (millis == 0)
                return;
            millisbien = millis;
            if (millis > 1000)
                millisbien = millis % (int) Math.pow(10, (int) Math.log10(millis));
            //System.out.println(millisbien);
            seconds = (int) (millis / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            timerTxt.setText(String.format("%d:%02d:%02d", minutes, seconds, millisbien));
            timerHandler.postDelayed(this, 5);
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
        textoBestTime = findViewById(R.id.bestTime);
        estado = 0;

        textito.setText(getString(R.string.saludo, getIntent().getStringExtra(Login.getMESSAGE())));
        timerTxt.setText(getString(R.string.empezarInit));

        botonEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (estado == 1) {
                    timerHandler.removeCallbacks(timerRunnable);
                    botonEmpezar.setText(getString(R.string.reiniciar));
                    System.out.println(millis);
                    estado = 2;
                    if (millis == 0)
                        return;
                    if (bestTime > millis || bestTime == 1) {
                        bestTime = millis;
                        textoBestTime.setText(String.format("%d ms",millis));
                    }

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