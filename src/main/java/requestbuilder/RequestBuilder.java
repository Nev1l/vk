package requestbuilder;

import beans.RequestContext;
import beans.RequestMethod;
import beans.RequiredField;
import beans.RequiredFieldType;
import exceptions.RequestException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Viktar_Kapachou on 2/8/2017.
 */
public class RequestBuilder {
    public final static String SEPARATOR_AMP = "&";
    public final static String SEPARATOR_EQ = "=";
    public final static String SEPARATOR_GET_PARAM_START = "?";
    public final static String UTF_8 = "UTF-8";
    public final static Charset CHARSET = Charset.forName("windows-1251");
    public final static String CONTENT_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded";

    private String initURI;
    private RequestMethod initMethod;
    private RequestContext context;

    public RequestBuilder(String initURI, RequestMethod initMethod, RequestContext context) {
        this.initURI = initURI;
        this.initMethod = initMethod;
        this.context = context;
    }

    public RequestBuilder(RequestContext context) {
        this.context = context;
    }

    public HttpRequestBase buildRequest() {
        return RequestMethod.POST.equals(initMethod) ? buildPostRequest() : buildGetRequest();
    }

    public HttpGet buildGetRequest(String uri) {
        if (uri.contains(SEPARATOR_GET_PARAM_START)) {
            uri += encodeParams(getRequestParams());
        } else {
            uri += SEPARATOR_GET_PARAM_START + encodeParams(getRequestParams());
        }
        HttpGet request = new HttpGet(uri);
        setHeaders(request);
        return request;
    }

    public HttpGet buildGetRequest() {
        return buildGetRequest(getRequestURI());
    }

    public HttpPost buildPostRequest(String uri) {
        HttpPost request = new HttpPost(uri);
        setHeaders(request);
        request.setEntity(new StringEntity(encodeParams(getRequestParams()), ContentType.create(CONTENT_TYPE_FORM_URLENCODED, CHARSET)));
        return request;
    }

    public HttpRequestBase buildPostRequest() {
        return buildPostRequest(getRequestURI());
    }

    public static String encodeParams(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry<String, String> e : params.entrySet()
                    ) {
                sb.append(e.getKey() + SEPARATOR_EQ + URLEncoder.encode(e.getValue(), UTF_8)).append(SEPARATOR_AMP);
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return sb.toString();
    }

    private void setHeaders(HttpRequestBase request) {
        for (Map.Entry<String, String> e : getRequestHeaders().entrySet()) {
            request.setHeader(new BasicHeader(e.getKey(), e.getValue()));
        }
    }

    public String getParam(String key) {
        if (!context.getContext().containsKey(key)) {
            throw new RequestException(RequestException.REQUEST_PARAM_ERROR_MESSAGE);
        }
        return context.getContext().get(key);
    }

    public Map<String, String> getRequestFieldsByType(RequiredFieldType type) {
        Map<String, String> params = new HashMap<String, String>();
        for (RequiredField field : context.getRequestFields()) {
            if (type == field.getType()) {
                if (field.getDefaultValue() != null) {
                    params.put(field.getName(), field.getDefaultValue());
                } else {
                    params.put(field.getName(), getParam(field.getKey()));
                }
            }
        }
        return params;
    }

    public Map<String, String> getRequestParams() {
        return getRequestFieldsByType(RequiredFieldType.PARAM);
    }

    public Map<String, String> getRequestHeaders() {
        return getRequestFieldsByType(RequiredFieldType.HEADER);
    }

    public String getRequestURI() throws RequestException {
        if (initURI == null) {
            throw new RequestException(RequestException.URI_ERROR_MESSAGE);
        }
        return initURI;
    }


}
