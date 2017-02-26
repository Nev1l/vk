package download;

import beans.audio.AudioFull;
import cache.AudioCacheResult;
import tasks.AudioTask;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Viktar_Kapachou on 20.02.2017.
 */
public class DownloadService {
    private ExecutorService service = Executors.newFixedThreadPool(3);
    private String output = "d:\\music\\";

    public DownloadService(String output) {
        this.output = output;
    }

    public ExecutorService getService() {
        return service;
    }

    public void setService(ExecutorService service) {
        this.service = service;
    }

    public void download(List<AudioFull> audioList) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < audioList.size(); i++) {
            AudioFull currentAudio = audioList.get(i);
            service.submit(new AudioTask(i+1,currentAudio,output+currentAudio.getOwnerId()+"\\",10));
        }
        //it doesn't required!
        //service.shutdown();
        try {
            //replace by invoke all
            //create prioritize queue
            final boolean done = service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            System.out.println("DownloadService:status="+done);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("DownloadService: sections downloaded = "+AudioCacheResult.getInstance().getCache().size());
    }

}
