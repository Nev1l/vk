package beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Viktar_Kapachou on 2/8/2017.
 */
public class RequestContext implements IContext {
    private Map<String, String> context;
    private List<RequiredField> requestFields;

    public RequestContext() {
        this.context = new HashMap<String, String>();
        this.requestFields = new ArrayList<RequiredField>();
    }

    public RequestContext(Map<String, String> context) {
        this.context = context;
        this.requestFields = new ArrayList<RequiredField>();
    }

    public RequestContext(Map<String, String> context, List<RequiredField> requestFields) {
        this.context = context;
        this.requestFields = requestFields;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public List<RequiredField> getRequestFields() {
        return requestFields;
    }

    public void setRequestFields(List<RequiredField> requestFields) {
        this.requestFields = requestFields;
    }

}
