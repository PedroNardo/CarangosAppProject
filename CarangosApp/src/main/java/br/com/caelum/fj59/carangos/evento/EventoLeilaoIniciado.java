package br.com.caelum.fj59.carangos.evento;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import br.com.caelum.fj59.carangos.activity.LeilaoActivity;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;

/**
 * Created by android4549 on 6/26/14.
 */
public class EventoLeilaoIniciado extends BroadcastReceiver {

    private static final String LEILAO_INICIADO = "leilao-iniciado";
    private Activity activity;

    @Override
    public void onReceive(Context context, Intent intent) {

        String mensagem = (String) intent.getSerializableExtra("message");

        MyLog.i("Mensagem do Leilao recebida: " + mensagem);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("NOVO LEILAO! " + mensagem)
                .setPositiveButton("Entrar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                activity.startActivity(new Intent(activity,
                                        LeilaoActivity.class));
                            }
                        })
                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

        builder.create().show();
    }

    public static void notificaSucesso(Context context, String mensagem) {
        Intent intent = new Intent(LEILAO_INICIADO);

        intent.putExtra("message", mensagem);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public void desregistra(CarangosApplication application) {
        LocalBroadcastManager
                .getInstance(application).unregisterReceiver(this);
    }

    public static EventoLeilaoIniciado registraObservador(Activity activity) {
        EventoLeilaoIniciado receiver = new EventoLeilaoIniciado();
        receiver.activity = activity;

        LocalBroadcastManager.getInstance((activity.getApplication()))
                .registerReceiver(receiver, new IntentFilter(LEILAO_INICIADO));

        return receiver;
    }
}
