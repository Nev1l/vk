package exceptions;

/**
 * Created by Viktar_Kapachou on 2/8/2017.
 */
public class RequestException extends VKException {
    public final static String URI_ERROR_MESSAGE = "URI isn't initilized.";
    public final static String REQUEST_PARAM_ERROR_MESSAGE =  "Field isn't exist in init map!";

    public RequestException(String s) {
        super();
    }
}
