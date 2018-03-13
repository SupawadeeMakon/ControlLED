package piw.rmutsv.ac.th.controlled;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference LED;
    private static final String TAG = "LEDs Control";
    public Button Switch;
    public Integer Value,Value_refer;


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
    }


}
