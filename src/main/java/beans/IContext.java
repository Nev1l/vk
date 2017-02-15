package beans;

import java.util.Map;

/**
 * Created by Viktar_Kapachou on 2/8/2017.
 */
public interface IContext {
    Map<String, String> getContext();
    void setContext(Map<String, String> context);
}
