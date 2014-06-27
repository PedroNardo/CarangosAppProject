package br.com.caelum.fj59.carangos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

public class BlogPostAdapterComAdds extends BaseAdapter {

    private Context mContext;
    private final List<BlogPost> posts;

    public BlogPostAdapterComAdds(Context mContext, List<BlogPost> posts) {
        this.mContext = mContext;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        int propagandas = posts.size() / 10;
        return posts.size() + propagandas + 1;
    }

    @Override
    public Object getItem(int i) {
        if (getItemViewType(i) == 1) {
            return null;
        }

        int propagandas = i / 10;

        return posts.get(i - propagandas);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if ((position + 1) % 10 == 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        int itemViewType = getItemViewType(position);

        if (itemViewType == 0) {
            ViewHolder holder;

            if (convertView == null || convertView instanceof ImageView) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.
                        post_linha_par, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            BlogPost blogPost = (BlogPost) getItem(position);

            holder.mensagem.setText(blogPost.getMensagem());
            holder.nomeAutor.setText(blogPost.getAutor().getNome());
            /* Busca avatar na Web */
        } else {
            convertView = new ImageView(mContext);
            convertView.setBackgroundResource(R.drawable.ic_launcher);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView foto;
        TextView mensagem;
        TextView nomeAutor;

        ViewHolder(View view) {
            this.foto = (ImageView) view.findViewById(R.id.foto);
            this.mensagem = (TextView) view.findViewById(R.id.mensagem);
            this.nomeAutor = (TextView) view.findViewById(R.id.nome_autor);
        }
    }
}
