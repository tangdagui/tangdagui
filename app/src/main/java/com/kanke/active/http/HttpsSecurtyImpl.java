package com.kanke.active.http;

import android.content.Context;
import android.content.res.Resources.NotFoundException;

import com.kanke.active.activity.R;
import com.kanke.active.util.Logger;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * HTTPS安全连接-导入认证实现
 * 
 * @author <a href="mailto:heiantiankongxia@163.com">sherly.wen</a>
 * @version 1.0
 * @since 2014-2-3
 */
public class HttpsSecurtyImpl implements IHttpsSecurty {
    private static final String TAG = HttpsSecurtyImpl.class.getName();

    @Override
    public HttpClient initClient(Context context) {
        HttpClient client = null;
        try {
            KeyStore trustStore = KeyStore.getInstance("BKS");
            trustStore.load(context.getResources().openRawResource(R.raw.server_trust),
                    Configurations.SERVER_PASSWORD.toCharArray());
            SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
            Scheme sch = new Scheme("https", socketFactory, 8443);
            client = new DefaultHttpClient();
            client.getConnectionManager().getSchemeRegistry().register(sch);
        } catch (KeyStoreException e) {
            Logger.w(TAG, "建立HTTPS安全连接KeyStoreException：", e);
        } catch (NoSuchAlgorithmException e) {
            Logger.w(TAG, "建立HTTPS安全连接NoSuchAlgorithmException：", e);
        } catch (CertificateException e) {
            Logger.w(TAG, "建立HTTPS安全连接CertificateException：", e);
        } catch (NotFoundException e) {
            Logger.w(TAG, "建立HTTPS安全连接NotFoundException：", e);
        } catch (IOException e) {
            Logger.w(TAG, "建立HTTPS安全连接IOException：", e);
        } catch (KeyManagementException e) {
            Logger.w(TAG, "建立HTTPS安全连接KeyManagementException：", e);
        } catch (UnrecoverableKeyException e) {
            Logger.w(TAG, "建立HTTPS安全连接UnrecoverableKeyException：", e);
        }
        return client;
    }
}
