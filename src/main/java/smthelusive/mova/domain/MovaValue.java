package smthelusive.mova.domain;

public class MovaValue {
    private MovaType movaType;
    private String rawValue;

    public MovaValue() {}

    public MovaValue(MovaType movaType, String rawValue) {
        this.movaType = movaType;
        this.rawValue = rawValue;
    }

    public void setMovaType(MovaType movaType) {
        this.movaType = movaType;
    }

    public void setRawValue(String rawValue) {
        this.rawValue = rawValue;
    }

    public String getStringValue() {
        return rawValue;
    }

    public int getIntegerValue() {
        return Integer.parseInt(rawValue);
    }

    public double getDoubleValue() {
        if (rawValue.contains(",")) {
            rawValue = rawValue.replace(',', '.');
        }
        return Double.parseDouble(rawValue);
    }

    public MovaType getMovaType() {
        return movaType;
    }
}
