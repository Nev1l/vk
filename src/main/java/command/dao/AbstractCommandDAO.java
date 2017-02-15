package command.dao;

import beans.IContext;
import command.ICommand;
import command.RequestCommand;
import http.Http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public class AbstractCommandDAO implements ICommand, IContext {
    private Http http;
    private List<RequestCommand> requestCommandList = new ArrayList<RequestCommand>();
    private Map<String, String> context = new ConcurrentHashMap<String, String>();

    public AbstractCommandDAO(Http http) {
        this.http = http;
    }

    public AbstractCommandDAO(Http http, Map<String, String> context) {
        this.http = http;
        this.context = context;
    }

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public boolean execute() throws IOException {
        for (RequestCommand command : requestCommandList) {
            if (!command.execute()) {
                return false;
            }
        }
        return true;
    }

    public List<RequestCommand> getRequestCommandList() {
        return requestCommandList;
    }

    public void setRequestCommandList(List<RequestCommand> requestCommandList) {
        this.requestCommandList = requestCommandList;
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }
}
