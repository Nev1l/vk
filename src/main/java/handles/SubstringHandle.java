package handles;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public class SubstringHandle extends DefaultParamHandle {
    private String paramName;
    /**
     * @findBefore finds position of prefix before searchable value
     * @findAfter finds position of prefix after searchable value
     */
    private String findBefore;
    private String findAfter;
    private boolean includeAfterPrefixToResult = false;
    private boolean includeBeforePrefixToResult = false;

    public SubstringHandle(String paramName, String findAfter, String findBefore, boolean includeAfterPrefixToResult, boolean includeBeforePrefixToResult) {
        this.paramName = paramName;
        this.findBefore = findBefore;
        this.findAfter = findAfter;
        this.includeAfterPrefixToResult = includeAfterPrefixToResult;
        this.includeBeforePrefixToResult = includeBeforePrefixToResult;
    }

    public boolean handle(InputStream stream) throws IOException {
        String result = null;
        if (findBefore != null && findBefore != "" && findAfter != null && findAfter != "") {
            result = substringParamByPrefixesBeforeAndAfter(streamAsString(stream), findBefore, findAfter, includeBeforePrefixToResult, includeAfterPrefixToResult);
        } else if (findBefore != null && findBefore != "") {
            result = substringParamBefore(streamAsString(stream), findBefore, includeBeforePrefixToResult);
        } else if (findAfter != null && findAfter != "") {
            result = substringParamAfter(streamAsString(stream), findAfter, includeAfterPrefixToResult);
        } else {
            result = streamAsString(stream);
        }
        getContext().put(paramName, result);
        return true;
    }

    /**
     * substring value from @after position of prefix till the end
     */
    protected String substringParamAfter(String input, String after, boolean includeAfterPrefixToResult) throws IOException {
        int startPosition = input.indexOf(after);
        if (startPosition == -1) {
            throw new IOException(RESPONSE_GET_PARAM_ERROR_MESSAGE);
        }
        return input.substring(startPosition + (includeAfterPrefixToResult ? 0 : after.length()));
    }

    /**
     * substring value from start till @before position of prefix
     */
    protected String substringParamBefore(String input, String before, boolean includeBeforePrefixToResult) throws IOException {
        int endPosition = input.indexOf(before);
        if (endPosition == -1) {
            throw new IOException(RESPONSE_GET_PARAM_ERROR_MESSAGE);
        }
        return input.substring(0, endPosition + (includeBeforePrefixToResult ? before.length() : 0));
    }

    /**
     * @before finds position of prefix before searchable value
     * @after finds position of prefix after searchable value
     */
    protected String substringParamByPrefixesBeforeAndAfter(String input, String before, String after, boolean includeBeforePrefixToResult, boolean includeAfterPrefixToResult) throws IOException {
        int startPosition = input.indexOf(before);
        int endPosition = input.indexOf(after, startPosition);
        if (startPosition == -1 || endPosition == -1) {
            throw new IOException(RESPONSE_GET_PARAM_ERROR_MESSAGE);
        }
        startPosition += (includeBeforePrefixToResult ? 0 : before.length());
        endPosition += (includeAfterPrefixToResult ? after.length() : 0);
        return input.substring(startPosition, endPosition);
    }

    protected String substringParamWithoutPrefixesBeforeAndAfter(String input, String before, String after) throws IOException {
        return substringParamByPrefixesBeforeAndAfter(input, before, after, false, false);
    }

    public boolean isIncludeBeforePrefixToResult() {
        return includeBeforePrefixToResult;
    }

    public void setIncludeBeforePrefixToResult(boolean includeBeforePrefixToResult) {
        this.includeBeforePrefixToResult = includeBeforePrefixToResult;
    }

    public boolean isIncludeAfterPrefixToResult() {
        return includeAfterPrefixToResult;
    }

    public void setIncludeAfterPrefixToResult(boolean includeAfterPrefixToResult) {
        this.includeAfterPrefixToResult = includeAfterPrefixToResult;
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
}
