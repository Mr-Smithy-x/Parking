package net.bcc.parking;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.bcc.parkingticket.ParkingViolation;
import net.bcc.parkingticket.models.Violation;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submit = findViewById(R.id.submit_btn);
        final EditText field = findViewById(R.id.license_field);
        final TextView textView = findViewById(R.id.how_many_violations);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String license = field.getText().toString();
                ParkingViolation.getViolationAsync(license, new ParkingViolation.OnParkingViolationCallback() {
                    @Override
                    public void OnResult(List<Violation> list) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Stream<Integer> integerStream = list.stream().map(Violation::getFineAmount);
                        }
                        textView.setText(String.format(Locale.getDefault(), "You have %d violations",list.size()));
                    }

                    @Override
                    public void OnError(Exception e) {
                        textView.setText(e.getMessage());
                    }
                });
            }
        });
    }
}
