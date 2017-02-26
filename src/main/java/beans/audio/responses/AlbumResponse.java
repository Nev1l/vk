package beans.audio.responses;

/**
 * Created by Viktar_Kapachou on 18.02.2017.
 */

import beans.audio.AudioAlbum;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlbumResponse{
    @SerializedName("count")
    private Integer count;
    @SerializedName("items")
    private List<AudioAlbum> items;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<AudioAlbum> getItems() {
        return items;
    }

    public void setItems(List<AudioAlbum> items) {
        this.items = items;
    }
}
