package handles;

import beans.IContext;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Viktar_Kapachou on 2/6/2017.
 */
public interface IHandle extends IContext {
    boolean handle(CloseableHttpResponse response) throws IOException;
    boolean handle(InputStream stream) throws IOException;
}
