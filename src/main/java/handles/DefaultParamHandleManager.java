package handles;

import java.util.Map;

/**
 * Created by Viktar_Kapachou on 18.02.2017.
 */
public class DefaultParamHandleManager extends DefaultParamHandle {
    public DefaultParamHandleManager(Map<String, String> context) {
        super(context);
    }

    public DefaultParamHandleManager addHandler(DefaultParamHandle handler){
        handler.setContext(getContext());
        setNext(handler);
        return this;
    }
}
