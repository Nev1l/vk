package handles;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public class SubstringSkipPrefixesHandle extends SubstringHandle {
    public SubstringSkipPrefixesHandle(String paramName, String findBefore, String findAfter) {
        super(paramName, findAfter, findBefore, false,false);
    }
}
