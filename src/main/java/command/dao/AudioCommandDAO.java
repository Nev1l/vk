package command.dao;

import beans.*;
import beans.audio.AudioFull;
import beans.audio.responses.AudioResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import command.RequestCommand;
import handles.DefaultParamHandle;
import handles.SubstringSkipPrefixesHandle;
import handles.SubstringAfterHandle;
import http.Http;
import requestbuilder.RequestBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public class AudioCommandDAO extends AbstractCommandDAO {
    public final static String TEST_USER_ID = "18459327";

    public final static String KEY_AUDIO_RESPONSE = "audio_response";
    public final static String URI_HASH_VALUE = "https://vk.com/dev/audio.get?params[owner_id]=18459327&params[need_user]=1&params[count]=3&params[v]=5.62";
    public final static String URI_GET_VK_AUDIO = "https://vk.com/dev";
    public final static String KEY_HASH = "hash";
    public final static String KEY_ACT = "act";
    public final static String VALUE_ACT = "a_run_method";
    public final static String KEY_AL = "al";
    public final static String VALUE_AL = "1";
    public final static String KEY_METHOD = "method";
    public final static String VALUE_METHOD = "audio.get";
    public final static String KEY_PARAM_VERSION = "param_v";
    public final static String VALUE_PARAM_VERSION = "5.62";
    public final static String KEY_COUNT = "param_count";
    public final static String KEY_OFFSET = "param_offset";
    public final static String KEY_NEED_USER = "param_need_user";
    public final static String KEY_OWNER_ID = "param_owner_id";

    private boolean isUserInfoRequired = false;

    public AudioCommandDAO(Http http) {
        super(http);
    }

    public AudioCommandDAO(Http http, Map<String, String> context) {
        super(http, context);
        //add to http context attribute owner_id(TEST_USER_ID) by LoginCommandDAO
    }

    public void needUser(boolean isUserInfoRequired) {
        this.isUserInfoRequired = isUserInfoRequired;
    }

    //skip count parameter for getting full play list
    public List<AudioFull> getAudio(Integer count, Integer offset) throws IOException {
        getRequestCommandList().add(new RequestCommand(new RequestBuilder(URI_HASH_VALUE, RequestMethod.GET, new RequestContext(getContext())), new DefaultParamHandle(getContext()).addHandler(new SubstringSkipPrefixesHandle(KEY_HASH, "Dev.methodRun('", "', this);")), getHttp()));
        RequestContext requestContext = new RequestContext(getContext());
        requestContext.getRequestFields().add(new RequiredField(KEY_ACT, VALUE_ACT, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_AL, VALUE_AL, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_METHOD, VALUE_METHOD, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_PARAM_VERSION, VALUE_PARAM_VERSION, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_NEED_USER, isUserInfoRequired ? "1" : "0", RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_HASH, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_COUNT, count.toString(), RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_OFFSET, offset.toString(), RequiredFieldType.PARAM));
        //TEST_USER_ID --> get it from context
        requestContext.getRequestFields().add(new RequiredField(KEY_OWNER_ID, TEST_USER_ID, RequiredFieldType.PARAM));
        getRequestCommandList().add(new RequestCommand(new RequestBuilder(URI_GET_VK_AUDIO, RequestMethod.POST, requestContext), new DefaultParamHandle(getContext()).addHandler(new SubstringAfterHandle(KEY_AUDIO_RESPONSE, "{\"response", true)), getHttp()));
        execute();
        //System.out.println(getContext().get(KEY_HASH));
        String responseJSON = getContext().get(KEY_AUDIO_RESPONSE);
        System.out.println(responseJSON);
        Gson gson = new Gson();
        AudioResponse response = gson.fromJson(((JsonObject)gson.fromJson(responseJSON,JsonObject.class).get("response")),AudioResponse.class);
        return response.getItems();
    }
}
