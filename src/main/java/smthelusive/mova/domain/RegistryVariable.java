package smthelusive.mova.domain;

public class RegistryVariable {
    private final int id;
    private final MovaType type;

    public RegistryVariable(int id, MovaType type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public MovaType getType() {
        return type;
    }
}
