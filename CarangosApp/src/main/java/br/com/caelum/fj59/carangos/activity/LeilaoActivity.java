package br.com.caelum.fj59.carangos.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.converter.LanceConverter;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Lance;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by android4549 on 6/26/14.
 */
public class LeilaoActivity extends Activity {

    private List<Lance> lancesAteMomento = new ArrayList<Lance>();
    private Calendar horarioUltimaBusca;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leilao);

        final ListView lancesList = (ListView) findViewById(R.id.lances_list);

        final ArrayAdapter<Lance> adapter = new ArrayAdapter<Lance>(
                LeilaoActivity.this, android.R.layout.simple_list_item_1,
                lancesAteMomento);

        lancesList.setAdapter(adapter);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String json = (String)msg.obj;
                List<Lance> novosLances = new LanceConverter()
                        .toLances(json);

                lancesAteMomento.addAll(novosLances);
                adapter.notifyDataSetChanged();

                Collections.sort(lancesAteMomento);
            }
        };

        this.horarioUltimaBusca = Calendar.getInstance();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                MyLog.i("Efetuando nova busca!");
                WebClient webClient;

                if (horarioUltimaBusca != null) {
                    webClient = new WebClient("leilao/leilaoid54635/" +
                        new SimpleDateFormat("ddMMyy-HHmmss")
                        .format(horarioUltimaBusca.getTime()));
                } else {
                    webClient = new WebClient("leilao/leilaoid54635");
                }

                String json = webClient.get();

                MyLog.i("Lances recebidos: " + json);

                Message message = handler.obtainMessage();

                message.obj = json;
                handler.sendMessage(message);

                horarioUltimaBusca = Calendar.getInstance();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 30*1000);
    }

}
