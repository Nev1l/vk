package handles;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
@Deprecated
public class FindParamHandle extends DefaultParamHandle {
    private String paramName;
    /**
     *@findBefore finds position of prefix before searchable value
     *@findAfter finds position of prefix after searchable value
    */
    private String findBefore;
    private String findAfter;

    public FindParamHandle(String paramName, String findBefore, String findAfter) {
        this.paramName = paramName;
        this.findBefore = findBefore;
        this.findAfter = findAfter;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getFindBefore() {
        return findBefore;
    }

    public void setFindBefore(String findBefore) {
        this.findBefore = findBefore;
    }

    public String getFindAfter() {
        return findAfter;
    }

    public void setFindAfter(String findAfter) {
        this.findAfter = findAfter;
    }

    protected String findParamFromResponse(String input, String before, String after) throws IOException {
        int startPosition = input.indexOf(before);
        int endPosition = input.indexOf(after, startPosition);
        if(startPosition==-1 || endPosition==-1) {
            throw new IOException(RESPONSE_GET_PARAM_ERROR_MESSAGE);
        }
        return input.substring(startPosition + before.length(), endPosition);
    }

    public boolean handle(InputStream stream) throws IOException {
        getContext().put(paramName, findParamFromResponse(streamAsString(stream), findBefore, findAfter));
        return true;
    }
}
