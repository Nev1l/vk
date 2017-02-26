package beans.audio;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Viktar_Kapachou on 18.02.2017.
 */
public class AudioLyrics {
    @SerializedName("lyrics_id")
    private Integer lyricsId;
    @SerializedName("text")
    private String text;

    public Integer getLyricsId() {
        return lyricsId;
    }

    public void setLyricsId(Integer lyricsId) {
        this.lyricsId = lyricsId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
