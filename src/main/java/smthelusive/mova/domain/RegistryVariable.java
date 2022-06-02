package smthelusive.mova.domain;

public class RegistryVariable {
    private int id;
    private MovaType type;

    public RegistryVariable(int id, MovaType type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovaType getType() {
        return type;
    }

    public void setType(MovaType type) {
        this.type = type;
    }
}
