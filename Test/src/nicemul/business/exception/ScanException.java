package nicemul.business.exception;

public class ScanException extends RollbackException {

    public static final String MISSING_NAME = "Missing name";

    public static final String MISSING_CONSOLE_NAME = "Missing parent console name";
    
    public static final String MISSING_ICON = "Missing icon";

    public static final String MISSING_FOLDER = "Missing folder";

    public static final String MISSING_EXECNAME = "Missing executable name";

    public static final String MISSING_EXTENSIONS = "Missing roms extentions";

    public static final String MISSING_ROM_FOLDER = "Missing rom folder name";

    public static final String MISSING_COVER_FOLDER = "Missing cover folder name";

    public static final String MISSING_COVER_TYPE = "Missing or invalid cover type";

    private static final long serialVersionUID = 1L;

    public static final String MISSING_WEBSITE_NAME = "Missing website name";
    
    public static final String MISSING_WEBSITE_URL = "Missing website home url";
    
    public static final String MISSING_WEBSITE_SEARCH_URL = "Missing website search url";        
    
    public ScanException(String msg) {
        super(msg);
    }
}