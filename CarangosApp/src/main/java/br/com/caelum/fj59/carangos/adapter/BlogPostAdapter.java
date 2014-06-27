package br.com.caelum.fj59.carangos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

/**
 * Created by erich on 7/16/13.
 */
public class BlogPostAdapter extends BaseAdapter {
    private Context context;
    private final List<BlogPost> posts;

    public BlogPostAdapter(Context mContext, List<BlogPost> posts) {
        this.context = mContext;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;
/*
        // >> EXERCICIO 05 PAG 74 (OPCIONAL)

        if (position % 2 == 0) {
            if(convertView != null
                    && convertView.findViewById(R.id.root_linha_par) != null) {
                holder = (ViewHolder) convertView.getTag();
                MyLog.i("Aproveitou cell PAR");
            } else {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.post_linha_par, viewGroup, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
                MyLog.i("Nao aproveitou cell PAR, CRIOU NOVA!");
            }
        } else {
            if(convertView != null
                    && convertView.findViewById(R.id.root_linha_impar) != null) {
                holder = (ViewHolder) convertView.getTag();
                MyLog.i("Aproveitou cell IMPAR");
            } else {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.post_linha_impar, viewGroup, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
                MyLog.i("NAO aproveitou cell IMPAR, CRIOU NOVA!");
            }
        }
*/
        int layout = position % 2 == 0 ?
                R.layout.post_linha_par : R.layout.post_linha_impar;

        if(convertView == null) {
            MyLog.i("Criou a linha!");
            convertView = LayoutInflater.from(context)
                                            .inflate(layout, viewGroup, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            MyLog.i("Aproveitou a linha!!");
        }

        BlogPost blogPost = (BlogPost) getItem(position);

        //Estamos usando o holder para popular nosso layout
        holder.mensagem.setText(blogPost.getMensagem());
        holder.nomeAutor.setText(blogPost.getAutor().getNome());

        UrlImageViewHelper.setUrlDrawable(holder.foto, blogPost.getFoto(),
                this.context.getResources().getDrawable(R.drawable.loading));

        int idImagem = 0;
        switch (blogPost.getEstadoDeHumor()) {
            case ANIMADO: idImagem = R.drawable.ic_muito_feliz; break;
            case INDIFERENTE: idImagem = R.drawable.ic_feliz; break;
            case TRISTE: idImagem = R.drawable.ic_indiferente; break;
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }

    class ViewHolder {
        ImageView foto;
        ImageView emoticon;
        TextView mensagem;
        TextView nomeAutor;

        ViewHolder(View view) {
            this.foto = (ImageView) view.findViewById(R.id.foto);
            this.emoticon = (ImageView) view.findViewById(R.id.emoticon);
            this.mensagem = (TextView) view.findViewById(R.id.mensagem);
            this.nomeAutor = (TextView) view.findViewById(R.id.nome_autor);
        }
    }
}
