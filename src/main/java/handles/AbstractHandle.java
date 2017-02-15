package handles;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
/**
 * Created by Viktar_Kapachou on 2/6/2017.
 */
public abstract class AbstractHandle implements IHandle {
    private final static Charset charset = Charset.forName("windows-1251");
    private Map<String, String> context = new HashMap<String, String>();

    public String streamAsString(InputStream stream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        IOUtils.copy(new ByteArrayInputStream(IOUtils.toByteArray(stream)), baos);
        return new String(baos.toByteArray(), charset);
    }

    protected String fixFileName(String pathname) {
        String[] forbiddenSymbols = new String[]{"<", ">", ":", "\"", "/", "\\", "|", "?", "*"};
        String result = pathname;
        for (String forbiddenSymbol : forbiddenSymbols) {
            result = StringUtils.replace(result, forbiddenSymbol, "");
        }
        return fixDeAccent(StringEscapeUtils.unescapeXml(result));
    }

    private String fixDeAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public Map<String, String> getContext() {
        return context;
    }
}
