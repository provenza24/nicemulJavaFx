package nicemul.business.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nicemul.business.model.enumeration.CommandType;

@Entity
@Table(name = "EMULATOR")
@SequenceGenerator(name = "EMULATOR_SEQ", sequenceName = "EMULATOR_SEQ", allocationSize = 1)
public class Emulator implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EMULATOR_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMULATOR_SEQ")
    private long id;

	@Column(name = "NAME", nullable = false, unique = true, length = 50)
    private String name;
    
	@Column(name = "EXECUTABLE", nullable = true, length = 50)
    private String execName;
    
	@Column(name = "ARGUMENTS", nullable = true, length = 50)
    private String execArgs;
    
	@Column(name = "ROMS_EXTENSIONS", nullable = true, length = 50)
    private String extensions;
    
	@Column(name = "FOLDER", nullable = true, length = 50)
    private String folder;
    
	@Enumerated(EnumType.STRING)
	@Column(name = "COMMAND_TYPE", nullable = true, length = 50)
    private CommandType commandType;
    	 	 
	@Column(name = "ICON", nullable = true, length = 20)
	private String icon;
	
    public Emulator(){        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int hashCode() {
        return name.hashCode();
    }

    public boolean equals(Object element) {
        Emulator e = (Emulator) element;
        return e.getName().equals(name);
    }

    public String getExecName() {
        return execName;
    }

    public void setExecName(String execName) {
        this.execName = execName;
    }

    public String getExecArgs() {
        return execArgs;
    }

    public void setExecArgs(String execArgs) {
        this.execArgs = execArgs;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
    
}
