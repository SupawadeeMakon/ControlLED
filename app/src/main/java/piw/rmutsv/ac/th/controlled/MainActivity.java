package piw.rmutsv.ac.th.controlled;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference LED,TEXT;
    private static final String TAG = "LEDs Control";
    public Button Switch;
    public Integer Value,Value_refer;
    private TextView ntextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        LED =firebaseDatabase.getReference("Switch");

        Switch = (Button)findViewById(R.id.btSwitch);

        LED.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Value = dataSnapshot.getValue(Integer.class);
                Log.d(TAG, "Value is: " + Value);
                if (Value == 1){
                    Switch.setText("LED ON");
                    Value_refer = 0;
                }else {
                    Switch.setText("LED OFF");
                    Value_refer = 1;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());

            }
        });

        Switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LED.setValue(Value_refer);
            }
        });




    firebaseDatabase =FirebaseDatabase.getInstance();
        TEXT = firebaseDatabase.getReference();

        ntextview = (TextView)findViewById(R.id.textView2);

        TEXT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();
                String led = String.valueOf(map.get("led"));
                ntextview.setText(led);

                //Map map = (Map)dataSnapshot.getValue();
                //String username = String.valueOf(map.get("username"));
                //ntextview.setText(username);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
