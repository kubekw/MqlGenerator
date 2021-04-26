package pl.mqlgenerator.model;

public class Input {

    private String type;
    private String name;
    private String value;
    private String displayName;

    public Input(String type, String name, String value, String displayName) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.displayName = displayName;
    }

    public Input(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.displayName = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "input "+type+" "+name+" = "+value+"; "+" //"+ displayName +"\n";
    }

    public String inputsToList() {
        return ""+type+" "+name+" = "+value+";";
    }
}
