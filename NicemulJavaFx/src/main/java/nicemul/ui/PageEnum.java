package nicemul.ui;

import java.util.HashMap;
import java.util.Map;

public enum PageEnum {

	HOME ("home"),
	CONSOLE_ROMS("roms");
	    
    /** enumLiteralMap */
    private static Map<String, PageEnum> enumLiteralMap = null;


    /** label */
    private String label;
        
    /**
     * full Constructor
     * @param label parameter
     */
    private PageEnum(String label) {
        this.label = label;
    }

    /**
     * Return an SecurityMessageTypeEnum instance according to the literal value
     * @param literal String value that represent the key value value of an enum.
     *   the key value is the sum of each field
     * @return the enum find or null if no enum found.
     */
    public static synchronized PageEnum fromLiteral(String literal) {
        if (enumLiteralMap == null) {
            enumLiteralMap = new HashMap<String, PageEnum>();
            for (int i = 0; i < values().length; i++) {
                enumLiteralMap.put(values()[i].toLiteral(), values()[i]);
            }
        }
        return enumLiteralMap.get(literal);
    }

    /**
     * Return a string value to use as a key to retrieve the enum from the Map
     * @return The concatenation of each field of the enum
     */
    public String toLiteral() {
         return label;
     
    }
	
}
