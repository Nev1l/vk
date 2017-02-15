package beans.audio;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public enum AudioGenre {
    // missing: 9, 20, ?1001
    @SerializedName("1")
    ROCK("1"),

    @SerializedName("2")
    POP("2"),

    @SerializedName("3")
    RAP_AND_HIP_HOP("3"),

    @SerializedName("4")
    EASY_LISTENING("4"),

    @SerializedName("5")
    HOUSE_AND_DANCE("5"),

    @SerializedName("6")
    INSTRUMENTAL("6"),

    @SerializedName("7")
    METAL("7"),

    @SerializedName("8")
    DUBSTEP("8"),

    @SerializedName("10")
    DRUM_AND_BASS("10"),

    @SerializedName("11")
    TRANCE("11"),

    @SerializedName("12")
    CHANSON("12"),

    @SerializedName("13")
    ETHNIC("13"),

    @SerializedName("14")
    ACOUSTIC_AND_VOCAL("14"),

    @SerializedName("15")
    REGGAE("15"),

    @SerializedName("16")
    CLASSICAL("16"),

    @SerializedName("17")
    INDIE_POP("17"),

    @SerializedName("18")
    OTHER("18"),

    @SerializedName("19")
    SPEECH("19"),

    @SerializedName("21")
    ALTERNATIVE("21"),

    @SerializedName("22")
    ELECTROPOP_AND_DISCO("22"),

    @SerializedName("1001")
    JAZZ_AND_BLUES("1001");

    private final String value;

    AudioGenre(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}