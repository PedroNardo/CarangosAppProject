package br.com.caelum.fj59.carangos.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.adapter.BlogPostAdapter;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

/**
 * Created by erich on 9/11/13.
 */
public class ListaDePostsFragment extends Fragment
            implements PullToRefreshAttacher.OnRefreshListener {
    private ListView postsList;
    private BlogPostAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.postsList = (ListView) inflater.inflate(R.layout.posts_list, container, false);

        final MainActivity activity = ((MainActivity)this.getActivity());
        CarangosApplication app = activity.getCarangosApplication();

        this.adapter = new BlogPostAdapter(getActivity(), app.getPosts());
        this.postsList.setAdapter(this.adapter);

        activity.getAttacher().addRefreshableView(this.postsList, this);

        return this.postsList;
    }

    @Override
    public void onRefreshStarted(View view) {
        MyLog.i("PULL TO REFRESH INICIADO!!!");

        MainActivity activity = (MainActivity) this.getActivity();
        activity.alteraEstadoEExecuta(EstadoMainActivity.PULL_TO_REFRESH_REQUISITADO);
    }

}
