package beans.audio;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public class AudioFull extends Audio {
    @SerializedName("duration")
    private Integer duration;
    @SerializedName("date")
    private Integer date;
    @SerializedName("album_id")
    private Integer albumId;
    @SerializedName("lyrics_id")
    private Integer lyricsId;
    @SerializedName("genre_id")
    private AudioGenre genre;
    @SerializedName("no_search")
    private YesNoOption noSearchOption;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getLyricsId() {
        return lyricsId;
    }

    public void setLyricsId(Integer lyricsId) {
        this.lyricsId = lyricsId;
    }

    public AudioGenre getGenre() {
        return genre;
    }

    public void setGenre(AudioGenre genre) {
        this.genre = genre;
    }

    public YesNoOption getNoSearchOption() {
        return noSearchOption;
    }

    public void setNoSearchOption(YesNoOption noSearchOption) {
        this.noSearchOption = noSearchOption;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
