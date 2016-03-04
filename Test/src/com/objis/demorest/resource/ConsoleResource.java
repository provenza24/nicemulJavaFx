package com.objis.demorest.resource;

import java.io.Serializable;

import nicemul.business.model.Console;

public class ConsoleResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;

	private String name;

	private String constructor;

	private String romFolder;

	private String coverFolder;

	private String defaultRomCover;

	private String romsExtensions;

	private String dockIcon;

	private String icon;

	private String miniIcon;

	
	public ConsoleResource() {
	}

	public ConsoleResource(String pName) {
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

	public void mergeInto(Console console) {
		this.id = console.getId();
		this.name = console.getName();
		this.constructor = console.getConstructor();
	}
	
 }
