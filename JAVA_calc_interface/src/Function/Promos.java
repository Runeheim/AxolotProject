package Function;

public class Promos extends Object {
    private int values;
    private String unit;
    private String application;

    public Promos(int values, String unit, String application) {
        this.values = values;
        this.unit = unit;
        this.application = application;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getValues() {
        return values;
    }

    public void setValues(int values) {
        this.values = values;
    }


    @Override //surcharge de Tostring
    public String toString() {
        return "-" + values + " " + unit;
    }
}

