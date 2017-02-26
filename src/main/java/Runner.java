/**
 * Created by Viktar_Kapachou on 2/6/2017.
 */

import beans.audio.AudioFull;
import cache.AudioCacheResult;
import command.dao.AudioCommandDAO;
import command.dao.LoginCommandDAO;
import download.DownloadService;
import http.Http;
import handles.*;
import org.apache.http.client.methods.HttpGet;
import tasks.AudioTask;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Runner {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Http http = new Http();
        Map<String, String> globalContent = new ConcurrentHashMap<String, String>();
        new LoginCommandDAO(http, globalContent).login(args[0], args[1]);
        AudioCommandDAO audioDAO = new AudioCommandDAO(http, globalContent);
        //audioDAO.setUserId("156100830");
        List<AudioFull> audioList = audioDAO.getAudio(1, 0);
        System.out.println(audioList);
        long start = System.currentTimeMillis();
        String output = "d:\\music\\";
        new DownloadService(output).download(audioList);
        long end = System.currentTimeMillis();
        System.out.println("Application was stopped(time:" + (end - start)+ ")");
    }
}
