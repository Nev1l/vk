package tasks;

import beans.audio.AudioFull;
import download.Downloader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.text.Normalizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by Viktar_Kapachou on 18.02.2017.
 */
public class AudioTask implements Runnable {
    public final static String EMPTY = "";
    public final static String EXTENSION_MP3 = ".mp3";
    private String delimiter = "-";
    private Integer number;
    private String numberDelimiter = ". ";
    private String outputFolder;
    private AudioFull audio;
    private boolean isDownloaded = false;
    private int part = 1;

    static {
        //SSLSocket socket = (SSLSocket) sslFactory.createSocket(host, port);
        //socket.setEnabledProtocols(new String[]{"SSLv3", "TLSv1"});
        //System.setProperty("https.protocols", "TLSv1.1");
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
    }

    public AudioTask(Integer number, AudioFull audio, String outputFolder) {
        this.number = number;
        this.audio = audio;
        this.outputFolder = outputFolder;
    }

    public AudioTask(Integer number, AudioFull audio, String outputFolder, int part) {
        this.number = number;
        this.audio = audio;
        this.outputFolder = outputFolder;
        this.part = part;
    }

    public AudioTask(AudioFull audio, String outputFolder) {
        this.audio = audio;
        this.outputFolder = outputFolder;
    }

    public void run() {
        Thread.currentThread().setName("Audio" + number + ":");
        String currentThreadName = Thread.currentThread().getName();
        String pathname = outputFolder + (number != null ? number + numberDelimiter : EMPTY) + fixFileName(audio.getArtist().trim() +
                delimiter + audio.getTitle().trim()) + EXTENSION_MP3;
        try {
            File file = new File(pathname);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                URL url = new URL(audio.getUrl());
                if (part == 1) {
                    FileUtils.copyURLToFile(url, file);
                    isDownloaded = true;
                } else {
                    //take it out from this method!!!!
                    //------------
                    RandomAccessFile output = new RandomAccessFile(file, "rw");
                    //download file based on size(separate multithreading by parts)
                    int size = url.openConnection().getContentLength();
                    ExecutorService pool = Executors.newFixedThreadPool(part);
                    for (int i = 1; i <= part; i++) {
                        pool.submit(new Downloader(url, size, part, i, output));
                    }
                    pool.shutdown();
                    //------------
                    isDownloaded = pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected String fixFileName(String pathname) {
        String[] forbiddenSymbols = new String[]{"<", ">", ":", "\"", "/", "\\", "|", "?", "*"};
        String result = pathname;
        for (String forbiddenSymbol : forbiddenSymbols) {
            result = StringUtils.replace(result, forbiddenSymbol, EMPTY);
        }
        return fixDeAccent(StringEscapeUtils.unescapeXml(result));
    }

    private String fixDeAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll(EMPTY);
    }
}
