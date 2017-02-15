package beans;

/**
 * Created by Viktar_Kapachou on 2/8/2017.
 */
public class RequiredField {
    private String key;
    private String defaultValue;
    private String name;
    private RequiredFieldType type;

    public RequiredField(String key, String defaultValue, RequiredFieldType type) {
        this.key = key;
        this.name = key;
        this.defaultValue = defaultValue;
        this.type = type;
    }

    public RequiredField(String key, RequiredFieldType type) {
        this.key = key;
        this.name = key;
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequiredFieldType getType() {
        return type;
    }

    public void setType(RequiredFieldType type) {
        this.type = type;
    }
}
