package handles;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public class SubstringBeforeHandle extends SubstringHandle {
    public SubstringBeforeHandle(String paramName, String findBefore, boolean includeBeforePrefixToResult) {
        super(paramName, null, findBefore, false, includeBeforePrefixToResult);
    }
}
