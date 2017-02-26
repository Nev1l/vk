package download;

import cache.AudioCacheResult;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Viktar_Kapachou on 19.02.2017.
 */
public class Downloader implements Runnable {
    private URL downloadURL;
    private int size;
    private int partCount;
    private int partSize;
    private int startByte;
    private int endByte;
    private int remainingBytes;
    private RandomAccessFile outputFile;
    private int partNumber;

    public Downloader(URL downloadURL, int size, int partCount, int partNumber, RandomAccessFile outputFile) {
        init(size, partCount, partNumber);
        this.downloadURL = downloadURL;
        this.partNumber = partNumber;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        String threadName = "Downloader" + partNumber + ": ";
        Thread.currentThread().setName(threadName);
        //System.out.println(threadName + "size = " + size);
        //System.out.println(threadName+"size / part(" + partCount + ") = " + size / partCount);
        //System.out.println(threadName+"remainingBytes = " + remainingBytes);
        //System.out.println(threadName+"remainingBytes(%test) = " + size%partCount);
        //System.out.println(threadName+"partSize = " + partSize);
        download();
        long end = System.currentTimeMillis();
        System.out.println(threadName + (end - start));
    }

    private void init(int size, int partCount, int partNumber) {
        this.size = size;
        this.partCount = partCount;
        this.partSize = size / partCount;
        this.startByte = 0;
        this.endByte = partSize - 1;
        remainingBytes = size%partCount;//size - (size / partCount) * partCount;
        for (int i = 1; i < partNumber; i++) {
            startByte = endByte + 1;
            endByte += partSize;
        }
        if (partNumber == partCount) {
            endByte += remainingBytes + 1;
        }
    }

    private void download() {
        String threadName = Thread.currentThread().getName();
        InputStream stream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) downloadURL.openConnection();
            System.out.println(threadName + "size = " + size + "  Range bytes = " + startByte + " - " + endByte);
            httpURLConnection.setRequestProperty("Range", "bytes=" + startByte + "-" + endByte);
            httpURLConnection.connect();
            stream = httpURLConnection.getInputStream();
            ReadableByteChannel src = Channels.newChannel(stream);
            FileChannel channel = outputFile.getChannel();
            FileLock lock = null;
            //partSize doesn't consider remainingPart
            int length =  endByte - startByte + 1;
            if(threadName.contains("20")) {
                System.out.println(threadName + "length = " + length);
            }
            ByteBuffer buffer = ByteBuffer.allocate(length);
            //wrap(IOUtils.toByteArray(stream))
            //byte[] bytes = new byte[1024];
            int count = -1;
            /*while ((count = stream.read(bytes)) != -1) {
                buffer.put(bytes, 0, count);
            }
            */
            while ((count = src.read(buffer)) >0){
                //read
            };
            buffer.flip();
            AudioCacheResult.getInstance().getCache().put(threadName, buffer.array());
            try {
                lock = channel.lock(startByte, length, false);
                channel.write(buffer, startByte);
            } finally {
                lock.release();
                if (channel != null) {
                    //don't close channel(another threads use the same channel!)
                    //channel.close(); //?? what is about another threads?
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }
}
