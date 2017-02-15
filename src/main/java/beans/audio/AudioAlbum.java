package beans.audio;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public class AudioAlbum {
    @SerializedName("id")
    private Integer id;
    @SerializedName("owner_id")
    private Integer ownerId;
    @SerializedName("title")
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
