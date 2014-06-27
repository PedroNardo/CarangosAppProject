package br.com.caelum.fj59.carangos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.Override;
import java.lang.String;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.adapter.BlogPostAdapter;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.evento.EventoBlogPostsRecebidos;
import br.com.caelum.fj59.carangos.evento.EventoLeilaoIniciado;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPostsDelegate;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPostsTask;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class MainActivity extends Activity implements BuscaMaisPostsDelegate {
    private ListView postsList;
    private BlogPostAdapter adapter;
    private EstadoMainActivity estado;
    private static final String ESTADO_ATUAL = "ESTADO_ATUAL";
    private List<BlogPost> posts;
    private EventoBlogPostsRecebidos evento; //Guardando o evento como atributo
    private PullToRefreshAttacher attacher;
    private EventoLeilaoIniciado observadorLeilaoIniciado;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.main);

        this.attacher = PullToRefreshAttacher.get(this);

        this.estado = EstadoMainActivity.INICIO;

        //Primeiro registrando a Activity como observador
        this.evento = EventoBlogPostsRecebidos.registraObservador(this);

        this.observadorLeilaoIniciado =
                EventoLeilaoIniciado.registraObservador(this);
    }

    public PullToRefreshAttacher getAttacher(){
        return attacher;
    }

    public void alteraEstadoEExecuta(EstadoMainActivity estado){
        this.estado = estado;
        this.estado.executa(this);
    }

    @Override
    public void lidaComRetorno(List<BlogPost> resultado){

        CarangosApplication application = (CarangosApplication) getApplication();

        application.getPosts().clear();
        application.getPosts().addAll(resultado);

        this.estado = EstadoMainActivity.PRIMEIROS_POSTS_RECEBIDOS;
        this.estado.executa(this);

        this.attacher.setRefreshComplete();
    }

    @Override
    public void lidaComErro(Exception e){
        Toast.makeText(this, "Erro ao buscar dados", Toast.LENGTH_LONG).show();
    }

    @Override
    public CarangosApplication getCarangosApplication() {
        return (CarangosApplication) getApplication();
    }

    public void atualizaListaCom(List<BlogPost> posts) {

        CarangosApplication application = (CarangosApplication) getApplication();

        application.getPosts().clear();

        application.getPosts().addAll(posts);

        this.adapter.notifyDataSetChanged();
    }

    public void buscaPrimeirosPosts() {
        new BuscaMaisPostsTask(getCarangosApplication()).execute();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyLog.i("SALVANDO ESTADO!");

        outState.putSerializable(ESTADO_ATUAL, this.estado);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        MyLog.i("RESTAURANDO ESTADO!");
        this.estado = (EstadoMainActivity) savedInstanceState.
            getSerializable(ESTADO_ATUAL);
    }

    @Override
    protected void onResume(){
        super.onResume();

        MyLog.i("EXECUTANDO ESTADO: " + this.estado);
        this.estado.executa(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        this.evento.desregistra(getCarangosApplication());
        this.observadorLeilaoIniciado.desregistra(getCarangosApplication());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem compras = menu.add("Compras");
        compras.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        String acaoCustomizada = getResources()
                                    .getString(R.string.action_compra);
        Intent intent = new Intent(acaoCustomizada);

        compras.setIntent(intent);

        return super.onCreateOptionsMenu(menu);
    }
}
