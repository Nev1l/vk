package command;

import handles.IHandle;
import http.Http;
import requestbuilder.RequestBuilder;

import java.io.IOException;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public class RequestCommand implements ICommand {
    private RequestBuilder requestBuilder;
    private IHandle responseHandler;
    private Http http;

    public RequestCommand(RequestBuilder requestBuilder, IHandle responseHandler, Http http) {
        this.requestBuilder = requestBuilder;
        this.responseHandler = responseHandler;
        this.http = http;
    }

    public RequestBuilder getRequestBuilder() {
        return requestBuilder;
    }

    public void setRequestBuilder(RequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    public IHandle getResponseHandler() {
        return responseHandler;
    }

    public void setResponseHandler(IHandle responseHandler) {
        this.responseHandler = responseHandler;
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public boolean execute() throws IOException {
        return http.execute(requestBuilder.buildRequest(),responseHandler);
    }
}
