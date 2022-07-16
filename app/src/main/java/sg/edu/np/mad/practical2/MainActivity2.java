package sg.edu.np.mad.practical2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Users").child("mad");
        ArrayList<String> List = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List.add(snapshot.child("username").getValue().toString());
                List.add(snapshot.child("password").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button myLoginButton = findViewById(R.id.LoginButton);
        myLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etMyUserName = findViewById(R.id.editName);
                EditText etMyPassword = findViewById(R.id.editPassword);

                String name = etMyUserName.getText().toString();
                String password = etMyPassword.getText().toString();

                if(name.equals(List.get(0)) && password.equals(List.get(1))){
                    Intent myIntent = new Intent(MainActivity2.this, ListActivity.class);
                    startActivity(myIntent);
                    Toast.makeText(MainActivity2.this,"Valid",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity2.this, "Invalid Login! Try again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}