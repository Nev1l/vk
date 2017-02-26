package command.dao;

import beans.*;
import beans.audio.AudioFull;
import beans.audio.responses.AudioResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import command.RequestCommand;
import handles.*;
import http.Http;
import requestbuilder.RequestBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public class AudioCommandDAO extends AbstractCommandDAO {
    //public final static String TEST_USER_ID = "18459327";
    public final static String KEY_AUDIO_RESPONSE = "audio_response";
    //public final static String URI_HASH_VALUE = "https://vk.com/dev/audio.get?params[owner_id]=18459327&params[need_user]=1&params[count]=3&params[v]=5.62";
    public final static String URI_HASH_VALUE = "https://vk.com/dev/video.get?params[owner_id]=66748&params[count]=2&params[offset]=12&params[extended]=1&params[v]=5.62";
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
    public final static String USER_ID = "user_id";

    private boolean isUserInfoRequired = false;
    private String userId;
    //if(isUserInfoRequired) ..
    //private ProfileInfo profile;

    public AudioCommandDAO(Http http) {
        super(http);
    }

    public AudioCommandDAO(Http http, Map<String, String> context) {
        super(http, context);
    }

    public void needUser(boolean isUserInfoRequired) {
        this.isUserInfoRequired = isUserInfoRequired;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    //skip count parameter for getting full play list

    //act=reload_audio&
    //act=playback
    //act=listened

    //https://vk.com/al_audio.php
    //act=load_silent&al=1&album_id=-2&band=false&owner_id=18459327
    //Response:..{"type":"album","ownerId":18459327,"albumId":-2,"title":"Viktor Kopachyov&#39;s audio files",
    // "list":[["456239626","18459327","","кн.05 Второе кольцо Силы (1977)","Кастанеда, Карлос",29934,0,0,"",0,32772,"","[]","\/ad00f5962a1dc26431\/407b259d9be99e13b9"],
    // ["456239624","18459327","","Kill Your Idols ","Static-X",241,0,0,"",192206,32774,"","[]","\/9fa31a21847cc117a2\/d0a058322180f654f9"],
    // ["456239623","18459327","","Turns To Gray","Ill Nino",203,0,0,"",1004481,32774,"","[]","\/e3ab939cd5d85f3e58\/3933be969fa0b133c0"],
    public List<AudioFull> getAudio(Integer count, Integer offset) throws IOException {
        getRequestCommandList().add(new RequestCommand(new RequestBuilder(URI_HASH_VALUE, RequestMethod.GET, new RequestContext(getContext())), new DefaultParamHandleManager(getContext()).addHandler(new SubstringSkipPrefixesHandle(KEY_HASH, "Dev.methodRun('", "', this);")) , getHttp()));
        RequestContext requestContext = new RequestContext(getContext());
        requestContext.getRequestFields().add(new RequiredField(KEY_ACT, VALUE_ACT, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_AL, VALUE_AL, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_METHOD, VALUE_METHOD, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_PARAM_VERSION, VALUE_PARAM_VERSION, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_NEED_USER, isUserInfoRequired ? "1" : "0", RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_HASH, RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_COUNT, count.toString(), RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(KEY_OFFSET, offset.toString(), RequiredFieldType.PARAM));
        requestContext.getRequestFields().add(new RequiredField(USER_ID, userId, RequiredFieldType.PARAM).asKey(KEY_OWNER_ID));
        getRequestCommandList().add(new RequestCommand(new RequestBuilder(URI_GET_VK_AUDIO, RequestMethod.POST, requestContext), new DefaultParamHandleManager(getContext()).addHandler(new SubstringAfterHandle(KEY_AUDIO_RESPONSE, "{\"response", true)), getHttp()));
        execute();
        //System.out.println(getContext().get(KEY_HASH));
        String responseJSON = getContext().get(KEY_AUDIO_RESPONSE);
        //System.out.println(responseJSON);
        Gson gson = new Gson();
        AudioResponse response = gson.fromJson(((JsonObject)gson.fromJson(responseJSON,JsonObject.class).get("response")),AudioResponse.class);
        return response.getItems();
    }
}
