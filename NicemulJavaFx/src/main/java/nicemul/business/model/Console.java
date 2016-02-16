package nicemul.business.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CONSOLE")
@SequenceGenerator(name = "CONSOLE_SEQ", sequenceName = "CONSOLE_SEQ", allocationSize = 1)
public class Console implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CONSOLE_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSOLE_SEQ")
	private long id;

	@Column(name = "NAME", nullable = false, unique = true, length = 50)
	private String name;

	@Column(name = "CONSTRUCTOR", nullable = true, length = 50)
	private String constructor;

	@Column(name = "ROM_FOLDER", nullable = true, length = 100)
	private String romFolder;

	@Column(name = "COVER_FOLDER", nullable = true, length = 100)
	private String coverFolder;

	@Column(name = "DEFAULT_ROM_COVER")
	private String defaultRomCover;

	@Column(name = "ROMS_EXTENSIONS")
	private String romsExtensions;

	@Column(name = "DOCK_ICON")
	private String dockIcon;

	@Column(name = "ICON")
	private String icon;

	@Column(name = "MINI_ICON")
	private String miniIcon;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "console", orphanRemoval = true)
	public List<Rom> roms;
	
	@OneToOne
	private Emulator defaultEmulator;

	@ManyToMany
	@JoinTable(name = "CONSOLE_EMULATORS", 
	joinColumns = { @JoinColumn(name = "CONSOLE_ID", referencedColumnName = "CONSOLE_ID") }, 
	inverseJoinColumns = { @JoinColumn(name = "EMULATOR_ID", referencedColumnName = "EMULATOR_ID") })
	private List<Emulator> emulators;

	{
		roms = new ArrayList<Rom>();
		emulators = new ArrayList<Emulator>();
	}

	public Console() {
	}

	public Console(String pName) {
		name = pName;
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

	public String getConstructor() {
		return constructor;
	}

	public void setConstructor(String constructor) {
		this.constructor = constructor;
	}

	public String getRomFolder() {
		return romFolder;
	}

	public void setRomFolder(String romFolder) {
		this.romFolder = romFolder;
	}

	public String getCoverFolder() {
		return coverFolder;
	}

	public void setCoverFolder(String coverFolder) {
		this.coverFolder = coverFolder;
	}

	public String getDefaultRomCover() {
		return defaultRomCover;
	}

	public void setDefaultRomCover(String defaultRomCover) {
		this.defaultRomCover = defaultRomCover;
	}

	public String getRomsExtensions() {
		return romsExtensions;
	}

	public void setRomsExtensions(String romsExtensions) {
		this.romsExtensions = romsExtensions;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<Rom> getRoms() {
		return roms;
	}

	public void setRoms(List<Rom> roms) {
		this.roms = roms;
	}

	public boolean addRom(Rom rom) {
		for (Rom consoleRom : roms) {
			if (consoleRom.getName().equals(rom.getName())) {
				return false;
			}
		}
		roms.add(rom);
		rom.setConsole(this);
		return true;
	}
	
	public boolean addEmulator(Emulator emulator) {
		for (Emulator consoleEmulator : emulators) {
			if (consoleEmulator.getName().equals(emulator.getName())) {
				return false;
			}
		}
		emulators.add(emulator);		
		return true;
	}

	public String getDockIcon() {
		return dockIcon;
	}

	public void setDockIcon(String dockIcon) {
		this.dockIcon = dockIcon;
	}

	public String getMiniIcon() {
		return miniIcon;
	}

	public void setMiniIcon(String miniIcon) {
		this.miniIcon = miniIcon;
	}

	public List<Emulator> getEmulators() {
		return emulators;
	}

	public Emulator getDefaultEmulator() {
		return defaultEmulator;
	}

	public void setDefaultEmulator(Emulator defaultEmulator) {
		this.defaultEmulator = defaultEmulator;
	}

	public void setEmulators(List<Emulator> emulators) {
		this.emulators = emulators;
	}

}
