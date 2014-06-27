package br.com.caelum.fj59.carangos.gcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.LeilaoActivity;
import br.com.caelum.fj59.carangos.evento.EventoLeilaoIniciado;
import br.com.caelum.fj59.carangos.infra.MyLog;

/**
 * Created by Pedro on 25/06/2014.
 */
public class GCMBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MyLog.i("Mensagem recebida! " + intent.getExtras());
        //linha nos exercicios anteriores:
        boolean estaRodando = this.aplicacaoEstaRodando(context.getApplicationContext());
        //linha no exercicio 6.8 - 3
        //boolean estaRodando = this.aplicacaoEstaRodando(context);

        if(!estaRodando) {

            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
            String messageType = gcm.getMessageType(intent);

            String mensagem = (String) intent.getExtras().getSerializable("message");

            MyLog.i("TIpo de mensagem: " + messageType + " com conteudo: " + mensagem);
            //6.7 - 3
            //Intent irParaLeilao = new Intent(context, LeilaoActivity.class);
            //6.8 - 1
            Intent irParaLeilao = new Intent(context.getApplicationContext(), LeilaoActivity.class);

            PendingIntent acaoPendente = PendingIntent
                    .getActivity(context, 0, irParaLeilao,
                            Intent.FLAG_ACTIVITY_NEW_TASK);

            irParaLeilao.putExtra("idDaNotificacao", Constantes.ID_NOTIFICACAO);

            //6.7 - 2
            Notification notificacao = new Notification.Builder(context.getApplicationContext())
                    .setContentTitle("Um novo leilao comecou!")
                    //.setContentText("Leilao " + intent.getExtras().getSerializable("message")
                    .setContentText("Leilao " + mensagem)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(acaoPendente) // <-- IMPORTANTE
                    .build();

            NotificationManager manager = (NotificationManager)
                    context.getApplicationContext().getSystemService
                            (Context.NOTIFICATION_SERVICE);

            manager.notify(Constantes.ID_NOTIFICACAO, notificacao);

        } else {
            EventoLeilaoIniciado.notificaSucesso(context,
                    (String)intent.getSerializableExtra("message"));
        }
    }

    private  boolean aplicacaoEstaRodando(final Context context) {
        ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if(!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if(!topActivity.getPackageName()
                    .equals(context.getPackageName())) {
                return false;
            }
        }
        return true;
    }

}
