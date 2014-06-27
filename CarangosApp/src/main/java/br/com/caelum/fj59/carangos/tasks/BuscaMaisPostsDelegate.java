package br.com.caelum.fj59.carangos.tasks;

import java.lang.Exception;
import java.util.List;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

public interface BuscaMaisPostsDelegate {

    void lidaComRetorno(List<BlogPost> retorno);

    void lidaComErro(Exception e);

    //O delegate agora precisa nos fornecer a instancia de Application:
    CarangosApplication getCarangosApplication();

}