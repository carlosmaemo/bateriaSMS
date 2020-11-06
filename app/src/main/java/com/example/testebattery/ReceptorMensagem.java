//package com.example.testebattery;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.ResultReceiver;
//
//@SuppressLint("ParcelCreator")
//public class ReceptorMensagem extends ResultReceiver {
//    private MainActivity.Messagem mensagem;
//
//    public ReceptorMensagem(MainActivity.Messagem mensagem) {
//        super(new Handler());
//
//        this.mensagem = mensagem;
//    }
//
//    @Override
//    protected void onReceiveResult(int resultCode, Bundle resultData) {
//        super.onReceiveResult(resultCode, resultData);
//        mensagem.mostrarMensagem(resultCode, resultData);
//    }
//}
