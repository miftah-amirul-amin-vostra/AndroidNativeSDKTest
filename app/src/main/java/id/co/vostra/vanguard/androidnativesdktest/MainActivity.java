package id.co.vostra.vanguard.androidnativesdktest;

import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import id.co.vostra.vanguard.dpc.Vanguard_SDK;
import id.co.vostra.vanguard.sdk.VanguardAgent;
import id.co.vostra.vanguard.sdk.VanguardAgentCallback;


public class MainActivity extends AppCompatActivity {
    TextView textView1, textView2, textView3;
    Button button1, button2, button3;
    Vanguard_SDK vanguardSdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi view
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        VanguardAgent vanguardService = new VanguardAgent(getApplicationContext());
        vanguardService.connectToVanguardAgent(new VanguardAgentCallback() {
            @Override
            public void onConnected(Vanguard_SDK vanguardSdk) {
                MainActivity.this.vanguardSdk = vanguardSdk;
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                Toast.makeText(MainActivity.this, "Service connected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Exception e) {
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                vanguardSdk = null;
                Toast.makeText(MainActivity.this, "Service disconnected, message : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Aksi saat tombol 1 diklik
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    textView1.setText(vanguardSdk.getWifiMacAddress());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Aksi saat tombol 2 diklik
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    textView2.setText(vanguardSdk.getDeviceSerialNumber());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Aksi saat tombol 2 diklik
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    textView3.setText(vanguardSdk.getImei());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}