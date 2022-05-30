package smthelusive.mova.domain;

public class ByteCodeVariable {
    private String descriptor;
    private int id;
    private Object value;

    public String getDescriptor() {
        return descriptor;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
