package com.example.testebattery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;
    TextView estadoCarregamento, percentagemCarregamento, temperaturaCarregamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        estadoCarregamento = findViewById(R.id.estado_carregamento);
        percentagemCarregamento = findViewById(R.id.percentagem_carregamento);
        temperaturaCarregamento = findViewById(R.id.temperatura_carregamento);

        ReceptorMensagem receptor = new ReceptorMensagem(new Messagem());

        Intent intent = new Intent(this, ServicoTempo.class);
        intent.putExtra("time", 10);
        intent.putExtra("receptor", receptor);
        startService(intent);



        intentFilterAndBroadcast();

    }

    private void intentFilterAndBroadcast() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {

                    // PERCENTAGEM
                    percentagemCarregamento.setText(String.valueOf(intent.getIntExtra("level", 0)) + "%");

                    // TEMPERATURA
                    float temp = (float) intent.getIntExtra("temperature", -1)/10;
                    temperaturaCarregamento.setText(temp + " °C");

                    // ESTADO
                    mudarEstado(intent);

                }
            }
        };
    }

    private void mudarEstado(Intent intent) {
        int estadoTemp = intent.getIntExtra("status", -1);

        switch (estadoTemp) {
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                estadoCarregamento.setText("Desconhecido");
                break;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                estadoCarregamento.setText("Carregando");
                SmsManager smsManager1 = SmsManager.getDefault();
                smsManager1.sendTextMessage("843222586", null, "O dispositivo está carregando!", null, null);
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                estadoCarregamento.setText("Não está carregando");
                SmsManager smsManager3 = SmsManager.getDefault();
                smsManager3.sendTextMessage("843222586", null, "O dispositivo não está carregando!", null, null);
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                estadoCarregamento.setText("Bateria cheia");
                break;
            default:
                estadoCarregamento.setText("...");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}
