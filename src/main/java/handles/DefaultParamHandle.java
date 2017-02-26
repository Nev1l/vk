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
    public final static String RESPONSE_CODE_ERROR_MESSAGE = "HTTP code: ";

    private DefaultParamHandle next;

    public DefaultParamHandle(Map<String, String> context) {
        setContext(context);
    }

    public DefaultParamHandle() {
    }

    public boolean handle(CloseableHttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        int code = response.getStatusLine().getStatusCode();
        InputStream body = response.getEntity().getContent();
        boolean result = false;
        if (code < 400){
            if (entity.getContent() != null) {
                result = handle(body);
            }
        }else{
            System.out.println(RESPONSE_CODE_ERROR_MESSAGE +code);
        }
        return result;
    }

    protected byte[] duplicateStream(InputStream stream) throws IOException {
        return IOUtils.toByteArray(stream);
    }
    public final boolean handle(InputStream stream) throws IOException {
        byte[] content = IOUtils.toByteArray(stream);
        boolean result = false;
        DefaultParamHandle current = getNext();
        while(current!=null){
            result = current.handle(content.clone());
            current = current.getNext();
        }
        return result;
    }

    public boolean handle(byte[] streamBytes) throws IOException {
        boolean result = false;
        DefaultParamHandle current = getNext();
        while(current!=null){
            result = current.handle(streamBytes.clone());
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
