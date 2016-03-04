package nicemul.business.model.enumeration;

public enum CommandType {

    RUNTIME(1, "runtime", 1), PROCESS(2, "runtime", 2);

    private final Integer value;

    private final String label;

    private final Integer order;

    private CommandType(Integer value, String label, Integer order) {
        this.value = value;
        this.label = label;
        this.order = order;
    }

    public Integer value() {
        return value;
    }

    public String label() {
        return label;
    }

    public Integer order() {
        return order;
    }

    /**
     * R�cup�re la couleur selon sa valeur.
     * 
     * @param value
     * @return
     */
    public static CommandType fromValue(Integer value) {
        switch (value) {
        case 1:
            return RUNTIME;
        case 2:
            return PROCESS;        
        default:
            return null;
        }
    }
    
}
