package beans.audio.responses;

import beans.audio.AudioFull;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public class AudioResponse {
    @SerializedName("count")
    private Integer count;
    @SerializedName("items")
    private List<AudioFull> items;

    public AudioResponse() {
    }

    public AudioResponse(Integer count, List<AudioFull> items) {
        this.count = count;
        this.items = items;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<AudioFull> getItems() {
        return items;
    }

    public void setItems(List<AudioFull> items) {
        this.items = items;
    }
}
