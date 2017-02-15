package handles;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public class SubstringAfterHandle extends SubstringHandle {
    public SubstringAfterHandle(String paramName, String findAfter, boolean includeAfterPrefixToResult) {
        super(paramName, findAfter, null, includeAfterPrefixToResult, false);
    }
}
