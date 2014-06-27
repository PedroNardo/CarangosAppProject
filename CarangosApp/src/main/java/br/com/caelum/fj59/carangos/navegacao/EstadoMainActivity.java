package br.com.caelum.fj59.carangos.navegacao;

import android.app.Fragment;
import android.app.FragmentTransaction;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.fragments.ListaDePostsFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;

public enum  EstadoMainActivity {
    INICIO {
        @Override
        public void executa (MainActivity activity){
            activity.buscaPrimeirosPosts();
            activity.alteraEstadoEExecuta(EstadoMainActivity.AGUARDANDO_POSTS);
        }
    },
    AGUARDANDO_POSTS{
        @Override
        public void executa (MainActivity activity) {
            this.colocaOuBuscaFragmentNaTela(activity, R.id.fragment_principal,
                    ProgressFragment.class, false);
        }
    },
    PRIMEIROS_POSTS_RECEBIDOS{
        @Override
        public void executa(MainActivity activity){
            this.colocaOuBuscaFragmentNaTela(activity, R.id.fragment_principal,
                    ListaDePostsFragment.class, false);
        }
    },
    PULL_TO_REFRESH_REQUISITADO {
        @Override
        public void executa(MainActivity activity){
            activity.getCarangosApplication().getPosts().clear();
            this.colocaOuBuscaFragmentNaTela(activity,
                    R.id.fragment_principal, ProgressFragment.class, false);
            activity.buscaPrimeirosPosts();
        }
    };

    Fragment colocaOuBuscaFragmentNaTela(MainActivity activity, int id,
         Class<? extends Fragment> fragmentClass, boolean backstack){

        Fragment naTela = activity.getFragmentManager()
                .findFragmentByTag(fragmentClass.getCanonicalName());

        if (naTela != null) {
            return naTela;
        }

        try{
            Fragment novoFragment = fragmentClass.newInstance();
            FragmentTransaction tx =
                    activity.getFragmentManager().beginTransaction();
            tx.replace(id, novoFragment, fragmentClass.getCanonicalName()); // replace(<id da activity>, <fragment>, <string com o nome da classe "etiqueta">)
            if(backstack){
                tx.addToBackStack(null);
            }
            tx.commit();

            return novoFragment;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract  void executa(MainActivity activity);

}
