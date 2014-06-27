package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;
import br.com.caelum.fj59.carangos.gcm.Constantes;
import br.com.caelum.fj59.carangos.gcm.InformacoesDoUsuario;

/**
 * Created by Pedro on 25/06/2014.
 */
public class RegistraDeviceTask extends AsyncTask<Void, Void, String> {

    private CarangosApplication app;

    public RegistraDeviceTask(CarangosApplication app) {
        this.app = app;
    }

    @Override
    protected  String doInBackground(Void... params) {
        String registrationId = null;

        try {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this.app);
            registrationId = gcm.register(Constantes.GCM_SERVER_ID);

            MyLog.i("Device registrado com o ID: " + registrationId);

            String email = InformacoesDoUsuario.getEmail(this.app);

            new WebClient("device/register/" + email + "/" + registrationId).post();
        } catch (Exception e) {
            MyLog.e("Problema no registro do device no server!" + e.getMessage());
        }

        return registrationId;
    }

    @Override
    protected void onPostExecute(String result){
        //O CarangoApplication deverá lidar com o registro do usuário
        app.lidaComRespostaDoRegistroNoServidor(result);
    }
}
