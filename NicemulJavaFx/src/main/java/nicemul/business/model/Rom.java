package nicemul.business.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "ROM")
@SequenceGenerator(name = "ROM_SEQ", sequenceName = "ROM_SEQ", allocationSize = 1)
public class Rom implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ROM_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROM_SEQ")
	private long id;

	@Column(name = "NAME", nullable = false, length = 255)
	private String name;

	@Column(name = "COVER_NAME", nullable = false, length = 255)
	private String coverName;

	@Column(name = "FORMATED_NAME", nullable = false, length = 255)
	private String formatedName;
	
	@Enumerated
	@Column(name="REGION", nullable=true, length=3)
	private RegionEnum region;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Console console;

	public Rom() {
	}
	
	public Rom(String n) {
		name = n;
		updateFormatedName();
		updateRegion();
		coverName = formatedName.trim() + ".jpg";
	}

	private void updateRegion() {
		if (name.toUpperCase().contains("(E)")) {
			setRegion(RegionEnum.EU);
		} if (name.toUpperCase().contains("(J)")) {
			setRegion(RegionEnum.JAP);
		}		
	}

	public String updateFormatedName() {
		if (StringUtils.isBlank(formatedName)) {
			String romName = name.toUpperCase();
			romName = romName.substring(0, romName.length() - 4);
			romName = StringUtils.substringBeforeLast(romName, ",");
			romName = StringUtils.substringBefore(romName, "(");
			formatedName = romName;
		}
		return formatedName;
	}

	public void updateCoverName() {
		coverName = formatedName.trim() + ".jpg";
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

	public String getCoverName() {
		return coverName;
	}

	public void setCoverName(String coverName) {
		this.coverName = coverName;
	}

	public String getFormatedName() {
		return formatedName;
	}

	public void setFormatedName(String formatedName) {
		this.formatedName = formatedName;
	}

	public RegionEnum getRegion() {
		return region;
	}

	public void setRegion(RegionEnum region) {
		this.region = region;
	}

	public Console getConsole() {
		return console;
	}

	public void setConsole(Console console) {
		this.console = console;
	}
}
