/**
 * Created by Viktar_Kapachou on 2/6/2017.
 */

import command.dao.AudioCommandDAO;
import command.dao.LoginCommandDAO;
import http.Http;
import handles.*;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Runner {
    public final static String EMAIL = "vik-kopachev@yandex.ru";
    public final static String PASSWORD = "user852456";

    public static void main(String[] args) throws URISyntaxException, IOException {
        Http http = new Http();
        new LoginCommandDAO(http).login(EMAIL, PASSWORD);
        System.out.println(new AudioCommandDAO(http).getAudio(2,0));
    }
}
