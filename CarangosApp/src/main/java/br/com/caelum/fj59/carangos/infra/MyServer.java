package br.com.caelum.fj59.carangos.infra;

import android.app.Application;
import android.os.Build;

public class MyServer {
    private static String uri;

    public MyServer(Application application) {
        if(taNoEmulador()) {
            uri = "http://10.0.2.2:8080/%s";
        } else {
            uri = application.getResources().getString(R.string.server_uri);
        }
    }

    public String uriFor(String value) {
        return String.format(uri, value);
    }

    private boolean taNoEmulador() {
        return Build.PRODUCT.equals("google_sdk")
                || Build.PRODUCT.equals("sdk");
//		return true;
    }

}
