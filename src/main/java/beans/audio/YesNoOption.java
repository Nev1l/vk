package beans.audio;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Viktar_Kapachou on 15.02.2017.
 */
public enum YesNoOption {
    @SerializedName("0")
    NO(0),
    @SerializedName("1")
    YES(1);

    private final Integer value;

    YesNoOption(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
