package br.com.caelum.fj59.carangos.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

/**
 * Created by erich on 7/18/13.
 */
public class PostSelecionadoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.post_selecionado, container, false);

//        PostSelecionadoDelegate delegate = (PostSelecionadoDelegate) getActivity();
//
//        BlogPost selecionado = delegate.getPostSelecionado();
//
//        TextView mensagem = (TextView) layout.findViewById(R.id.mensagem);
//        mensagem.setText(selecionado.getMensagem());

        return layout;
    }
}
