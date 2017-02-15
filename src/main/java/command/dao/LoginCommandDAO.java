package command.dao;

import beans.*;
import command.ICommand;
import command.RequestCommand;
import handles.*;
import http.Http;
import requestbuilder.RequestBuilder;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Viktar Kapachou on 2/8/2017.
 */
public class LoginCommandDAO extends AbstractCommandDAO {
    public final static String URL_VK = "http://vk.com";
    public final static String URL_LOGIN_VK = "https://login.vk.com/";
    public final static String PARAM_KEY_LG_H = "lg_h";
    public final static String PARAM_KEY_IP_H = "ip_h";
    public final static String PARAM_KEY_ACT = "act";
    public final static String PARAM_VALUE_ACT = "login";
    public final static String PARAM_KEY_ROLE = "role";
    public final static String PARAM_VALUE_ROLE = "al_frame";
    public final static String PARAM_KEY_EMAIL = "email";
    public final static String PARAM_KEY_PASS = "pass";
    public final static String PARAM_KEY_EXPIRE = "expire";
    public final static String PARAM_KEY_CAPTCHA_SID = "captcha_sid";
    public final static String PARAM_KEY_CAPTCHA_KEY = "captcha_key";
    public final static String PARAM_KEY_ORIGIN = "_origin";
    public final static String PARAM_KEY_Q = "q";
    public final static String PARAM_VALUE_Q = "1";
    public final static String PARAM_EMPTY_VALUE = "";
    public final static String SEARCH_EQ = "=";
    public final static String SEARCH_AMP = "&";

    private boolean isAuthorized;

    public LoginCommandDAO(Http http) {
        super(http);
    }

    public LoginCommandDAO(Http http, Map<String, String> context) {
        super(http,context);
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void login(String login, String password) throws IOException {
        getRequestCommandList().add(new RequestCommand(new RequestBuilder(URL_VK, RequestMethod.GET, new RequestContext(getContext())), new DefaultParamHandle(getContext())
                .addHandler(new FindParamHandle(PARAM_KEY_LG_H, PARAM_KEY_LG_H + SEARCH_EQ, SEARCH_AMP))
                .addHandler(new FindParamHandle(PARAM_KEY_IP_H, PARAM_KEY_IP_H + SEARCH_EQ, SEARCH_AMP)), getHttp()));
        RequestContext requestContext = new RequestContext(getContext());
        requestContext.getRequestFields().add(new RequiredField(PARAM_KEY_ACT, PARAM_VALUE_ACT, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(PARAM_KEY_ROLE, PARAM_VALUE_ROLE, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(PARAM_KEY_EMAIL, login, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(PARAM_KEY_PASS, password, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(PARAM_KEY_LG_H, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(PARAM_KEY_IP_H, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(PARAM_KEY_EXPIRE, PARAM_EMPTY_VALUE, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(PARAM_KEY_CAPTCHA_SID, PARAM_EMPTY_VALUE, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(PARAM_KEY_CAPTCHA_KEY, PARAM_EMPTY_VALUE, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(PARAM_KEY_ORIGIN, URL_VK, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(PARAM_KEY_Q, PARAM_VALUE_Q, RequiredFieldType.PARAM));
        getRequestCommandList().add(new RequestCommand(new RequestBuilder(URL_LOGIN_VK, RequestMethod.POST, requestContext), new DefaultParamHandle(getContext()), getHttp()));
        this.isAuthorized = execute();
    }
}
