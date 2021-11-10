package app.gati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holi);

        Intent intent = getIntent();
        String message = intent.getStringExtra(Login.getMESSAGE());

        TextView text = findViewById(R.id.textito);
        text.setText("@string/hola, " + message);

    }
}