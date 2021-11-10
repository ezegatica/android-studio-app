package app.gati;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registrarse extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String MESSAGE = "app.gati.usuario.email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_registrarse);
    }
    public void registrarse(View view){
        System.out.print("Intentando iniciar sesión con: ");
        EditText email = findViewById(R.id.textoEmailRegistro);
        EditText password = findViewById(R.id.textoPasswordRegistro);
        EditText passwordConfirm = findViewById(R.id.textoPasswordRegistro2);
        if (email.getText().toString().equals("") || password.getText().toString().equals("") || passwordConfirm.getText().toString().equals("")) {
            Toast.makeText(Registrarse.this, "Faltan 1 o más parametros necesarios.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.getText().toString().equals(passwordConfirm.getText().toString())){
            Toast.makeText(Registrarse.this, "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.print(email.getText().toString() + " - ");
        System.out.println(password.getText().toString());

        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println("Success!");
                            String email = user.getEmail();
                            Intent intent = new Intent(Registrarse.this, HomeScreen.class);
                            intent.putExtra(MESSAGE, email);
                            startActivity(intent);
                        } else {
                            Log.w("RegisterScreen", "createUserWithEmail:failure", task.getException());
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Registrarse.this, "Fallo al crear cuenta.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Button loginBoton = findViewById(R.id.irLogin);
        loginBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Registrarse.this, Login.class);
                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.botonRegistrarse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                registrarse(v);
            }
        });
    }
}