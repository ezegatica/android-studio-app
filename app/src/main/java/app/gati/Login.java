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

public class Login extends AppCompatActivity {
    private static final String MESSAGE = "app.gati.usuario.email";
    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("es");

    }

    public void iniciarSesion(View view){
        System.out.print("Intentando iniciar sesión con: ");
        EditText email = findViewById(R.id.textoEmail);
        EditText password = findViewById(R.id.textoPassword);
        if (email.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(Login.this, "Faltan 1 o más parametros necesarios.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.print(email.getText().toString() + " - ");
        System.out.println(password.getText().toString());
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        System.out.println("ADENTRO DE LA FUNCION");
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //System.out.println(user);
                            System.out.println("Success!");
                            String email = user.getEmail();
                            Intent intent = new Intent(Login.this, HomeScreen.class);
                            intent.putExtra(MESSAGE, email);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "El email o contraseña son incorrectos",
                                    Toast.LENGTH_LONG).show();
                            System.out.println("Error");
                        }
                    }
                });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        System.out.println(currentUser.getEmail());
        if(currentUser == null){
            System.out.println("error?");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Button button = findViewById(R.id.botoncito);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                iniciarSesion(v);
            }
        });

        Button registrarseBoton = findViewById(R.id.irRegistro);
        registrarseBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Login.this, Registrarse.class);
                startActivity(intent);
            }
        });
    }

    public void sendMessage(View view){
        EditText editText = findViewById(R.id.textoEmail);
        String message = editText.getText().toString();

        Intent intent = new Intent(Login.this, HomeScreen.class);
        intent.putExtra(MESSAGE, message);
        startActivity(intent);
    }

    public static String getMESSAGE(){
        return MESSAGE;
    }
}