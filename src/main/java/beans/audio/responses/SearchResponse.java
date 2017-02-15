package beans.audio.responses;

import beans.audio.AudioFull;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public class SearchResponse {
    @SerializedName("count")
    private Integer count;
    @SerializedName("items")
    private List<AudioFull> items;

    public Integer getCount() {
        return count;
    }

    public List<AudioFull> getItems() {
        return items;
    }

}
