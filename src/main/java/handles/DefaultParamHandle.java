package handles;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Viktar_Kapachou on 12.02.2017.
 */
public class DefaultParamHandle extends AbstractHandle {
    public final static String RESPONSE_GET_PARAM_ERROR_MESSAGE = "Can't get param from HttpResponse";
    private DefaultParamHandle next;

    public DefaultParamHandle(Map<String, String> context) {
        setContext(context);
    }

    public DefaultParamHandle() {
    }

    public DefaultParamHandle addHandler(DefaultParamHandle handler){
        handler.setContext(getContext());
        setNext(handler);
        return this;
    }

    public boolean handle(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        int code = response.getStatusLine().getStatusCode();
        InputStream body = response.getEntity().getContent();
        boolean result = false;
        if (entity.getContent() != null) {
            result = handle(body);
        }
        return result;
    }

    public boolean handle(InputStream stream) throws IOException {
        byte[] content = IOUtils.toByteArray(stream);
        boolean result = false;
        DefaultParamHandle current = getNext();
        while(current!=null){
            result = current.handle(new ByteArrayInputStream(content));
            current = current.getNext();
        }
        return result;
    }

    public void setNext(DefaultParamHandle next){
        if(this.next==null) {
            this.next = next;
        }else{
            this.next.setNext(next);
        }
    }

    public DefaultParamHandle getNext() {
        return next;
    }

}
