package sg.edu.np.mad.practical2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    ArrayList<User> userList = new ArrayList<>();
    private myAdaptor.RecyclerViewClickListener listener;
    DBHandler dbHandler = new DBHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        /*Random ran = new Random();
        for(int i = 1; i<=20; i++) {
            int name = ran.nextInt();
            int description = ran.nextInt();
            int follow = ran.nextInt(2);
            int id = i;
            User myUser = new User();
            myUser.setName("Name" + name);
            myUser.setDescription("Description " + description);
            myUser.setId(i);
            if (follow == 0) {
                myUser.setFollowed(false);
            } else {
                myUser.setFollowed(true);
            }
            dbHandler.addUser(myUser);
        }*/

        userList = dbHandler.getUsers();

        setOnClickListener();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        myAdaptor mAdaptor = new myAdaptor(userList, listener);
        LinearLayoutManager myLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myLayoutManager);
        recyclerView.setAdapter(mAdaptor);

        /*ImageView myImage = findViewById(R.id.imageClick);

        myImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                QueryActivity();
                return false;
            }
        });*/
    }

    private void setOnClickListener(){
        listener = new myAdaptor.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                QueryActivity(userList.get(position));
            }
        };
    }

    private void QueryActivity(User u){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(u.getName()).setCancelable(false);
        builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*Random ran = new Random();
                int value = ran.nextInt();
                Bundle extras = new Bundle();
                extras.putString("RanNum", String.valueOf(value));*/
                Bundle extras = new Bundle();
                extras.putString("name", u.getName());
                extras.putString("description", u.getDescription());
                extras.putString("follow", String.valueOf(u.isFollowed()));

                Intent myNewCreate = new Intent(ListActivity.this, MainActivity.class);
                myNewCreate.putExtras(extras);
                startActivity(myNewCreate);
            }
        });
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.setTitle("Profile");
        alert.show();
        /*Extra comment because I had issues pulling and pushing and need to recommit
        Also the file is called Practical 2 since I copy pasted the files but I do not
        know how to change the name.*/
    }

}