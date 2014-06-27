package br.com.caelum.fj59.carangos.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.caelum.fj59.carangos.R;

/**
 * Created by andresilva on 7/23/13.
 */
public class MessagemFragment extends Fragment {
    private TextView mensagem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View linha = inflater.inflate(R.layout.fragment_messagem, container, false);

        mensagem = (TextView) linha.findViewById(R.id.text_message);

        return linha;
    }

}
