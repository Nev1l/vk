package http;

import handles.IHandle;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.message.BasicLineParser;
import requestbuilder.RequestBuilder;

import java.io.IOException;

/**
 * Created by Viktar_Kapachou on 2/6/2017.
 */
public class Http {
    public final static String SEPARATOR_COOKIE = "; ";
    public final static String SEPARATOR_EQ = "=";
    public final static String SEPARATOR_DOT = ".";

    private HttpClientContext context = HttpClientContext.create();
    private CloseableHttpClient httpclient = null;

    public Http() {
        //HttpHeaderParser parser = new HttpHeaderParser();
        HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory =
                new ManagedHttpClientConnectionFactory(
                        new DefaultHttpRequestWriterFactory(),
                        new DefaultHttpResponseParserFactory(new BasicLineParser()
                                , new DefaultHttpResponseFactory()));
        RequestConfig config = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY)
                .build();
        RedirectStrategy redirectStrategy = new LaxRedirectStrategy();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
                connFactory);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
       /* UserTokenHandler useTokenHandler = new UserTokenHandler() {
            public Object getUserToken(HttpContext context) {
                return context.getAttribute("securityToken");
            }
        };*/
        //String login, String password
        //CredentialsProvider provider = new BasicCredentialsProvider();
        //provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(login, password));
        httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(config)
                .setRedirectStrategy(redirectStrategy)
                        //==[Notes:1. the same as we have for CF velvet AUTHORIZATION header]==
                        // .setDefaultCredentialsProvider(provider)
                        //.setUserTokenHandler(useTokenHandler)
                .build();
    }

    public boolean execute(RequestBuilder builder, IHandle handle) throws IOException {
        return execute(builder.buildRequest(), handle);
    }

    public boolean execute(HttpRequestBase request, IHandle handle) throws IOException {
        boolean result = false;
        CloseableHttpResponse response = httpclient.execute(request, context);
        try {
            result = handle.handle(response);
        } finally {
            response.close();
        }
        return result;
    }



    public HttpClientContext getContext() {
        return context;
    }

    public String getCookie(String host) {
        StringBuilder sb = new StringBuilder();
        for (Cookie cookie : context.getCookieStore().getCookies()) {
            if (cookie.getDomain().equals(host)) {
                sb.append(cookie.getName() +SEPARATOR_EQ + cookie.getValue() + SEPARATOR_COOKIE);
            }
        }
        return sb.toString();
    }

    private String getDomainFromHost(String host) {
        if (host.indexOf(SEPARATOR_DOT) != host.lastIndexOf(SEPARATOR_DOT)) {
            return host.substring(host.indexOf(SEPARATOR_DOT) + 1);
        } else {
            return host;
        }
    }
}
