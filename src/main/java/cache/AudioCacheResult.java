package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Viktar_Kapachou on 20.02.2017.
 */
public class AudioCacheResult {
    private static AudioCacheResult ourInstance = new AudioCacheResult();
    private Map<String,byte[]> cache = new ConcurrentHashMap<>();

    public static AudioCacheResult getInstance() {
        return ourInstance;
    }

    public Map<String, byte[]> getCache() {
        return cache;
    }

    public void setCache(Map<String, byte[]> cache) {
        this.cache = cache;
    }

    private AudioCacheResult() {
    }
}
