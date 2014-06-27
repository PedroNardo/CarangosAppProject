package br.com.caelum.fj59.carangos.webservice;//package br.com.caelum.fj59.carangos.webservice;
//
//import android.graphics.Bitmap;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.mime.HttpMultipartMode;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.ByteArrayBody;
//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.protocol.BasicHttpContext;
//import org.apache.http.protocol.HttpContext;
//import org.apache.http.util.EntityUtils;
//
//import java.io.ByteArrayOutputStream;
//import java.nio.charset.Charset;
//
//import br.com.caelum.fj59.carangos.infra.MyLog;
//import br.com.caelum.fj59.carangos.modelo.BlogPost;
//
///**
// * Created by erich on 9/3/13.
// */
//public class WebClientMultiParty {
//    private final String url;
//
//    public WebClientMultiParty(String relativeUrl) {
//        this.url = String.format("http://192.168.84.180:8080/%s", relativeUrl);
//    }
//
//    public String enviaImagem(Bitmap imagem, BlogPost blogPost) {
//        try {
//            DefaultHttpClient client = new DefaultHttpClient();
//            HttpContext localContext = new BasicHttpContext();
//
//            MultipartEntity entity = new MultipartEntity(
//                    HttpMultipartMode.BROWSER_COMPATIBLE);
//
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            imagem.compress(Bitmap.CompressFormat.PNG, 75, bos);
//            byte[] data = bos.toByteArray();
//
//            if (data != null) {
//                entity.addPart("file",
//                        new ByteArrayBody(data, "image/png", System.currentTimeMillis() +".png"));
//            }
//
//            if (blogPost != null) {
//                entity.addPart("blogPost.mensagem",
//                        new StringBody(blogPost.getMensagem(), Charset.forName("UTF-8")));
//                entity.addPart("blogPost.autor.id",
//                        new StringBody(blogPost.getAutor().getId().toString(), Charset.forName("UTF-8")));
//            }
//
//            HttpPost post = new HttpPost(url);
//
//            post.setEntity(entity);
//
//            HttpResponse response = client.execute(post, localContext);
//            String jsonDeResposta = EntityUtils.toString(response.getEntity());
//
//            MyLog.i("JSON DE REPOSTA: " + jsonDeResposta);
//
//            return jsonDeResposta;
//
//        } catch (Exception e) {
//            MyLog.i("ZICA NO POST!: " + e.getMessage());
//
//            throw new RuntimeException(e);
//        }
//    }
//}
