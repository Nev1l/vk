package http;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.util.CharArrayBuffer;

/**
 * Created by Viktar_Kapachou on 2/6/2017.
 */
@Deprecated
public class HttpHeaderParser extends BasicLineParser {
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public Header parseHeader(
            CharArrayBuffer buffer) throws ParseException {
        String header = buffer.toString();
        if(header.contains("Location: ")){
            location = header.substring("Location: ".length());
        }
        try {
            return super.parseHeader(buffer);
        } catch (ParseException ex) {
            return new BasicHeader(header,null);
        }
    }
}
