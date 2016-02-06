package nicemul.business.service.console;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import nicemul.business.exception.BusinessException;
import nicemul.business.exception.ScanException;
import nicemul.business.model.Console;
import nicemul.business.model.Rom;
import nicemul.business.repository.ConsoleRepository;
import nicemul.business.repository.RomRepository;
import nicemul.business.util.Folders;
import nicemul.business.util.PropertiesFileLoader;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsoleService implements IConsoleService {

	@Autowired
	private ConsoleRepository consoleRepository;

	@Autowired
	private RomRepository romRepository;
	
	public ConsoleService() {
	}

	public List<Rom> findConsoleRoms(String consoleName) throws BusinessException {
		return consoleRepository.findRoms(consoleName);
	}
	
	public String findConsoleMiniIcon(String consoleName) throws BusinessException {
		return consoleRepository.findMiniIcon(consoleName);
	}
	
	public void scanConsoles() throws BusinessException {

		String[] consolesFolders = new File(Folders.CONSOLES_DESCRIPTION_FOLDER).list();
		for (String filename : consolesFolders) {
			if (PropertiesFileLoader.isPropertiesFile(filename)) {
				File propsFile = new File(Folders.CONSOLES_DESCRIPTION_FOLDER + filename);
				try {
					Properties prop = PropertiesFileLoader.loadPropertiesFile(propsFile);
					updateConsole(prop, filename);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updateConsole(Properties prop, String filename) throws ScanException {

		String name = prop.getProperty("name");
		Console console = consoleRepository.findByName(name);
		if (console == null) {
			console = new Console(name);
		}

		if (StringUtils.isEmpty(console.getName())) {
			throw new ScanException(ScanException.MISSING_NAME);
		}

		String constructor = prop.getProperty("constructor");
		console.setConstructor(StringUtils.isNotBlank(constructor) ? constructor.toUpperCase() : "ZUNKNOW");

		console.setDockIcon(prop.getProperty("dockIcon"));
		if (StringUtils.isEmpty(console.getDockIcon())) {
			throw new ScanException(ScanException.MISSING_ICON);
		}
		
		console.setIcon(prop.getProperty("icon"));
		if (StringUtils.isEmpty(console.getIcon())) {
			throw new ScanException(ScanException.MISSING_ICON);
		}
		
		console.setMiniIcon(prop.getProperty("miniIcon"));
		if (StringUtils.isEmpty(console.getMiniIcon())) {
			throw new ScanException(ScanException.MISSING_ICON);
		}

		console.setRomsExtensions(prop.getProperty("supportedRomExtensions"));
		if (StringUtils.isEmpty(console.getRomsExtensions())) {
			throw new ScanException(ScanException.MISSING_EXTENSIONS);
		}
		console.setRomFolder(prop.getProperty("romsFolder"));
		if (StringUtils.isEmpty(console.getRomFolder())) {
			throw new ScanException(ScanException.MISSING_ROM_FOLDER);
		}
		console.setCoverFolder(prop.getProperty("coversFolder"));
		if (StringUtils.isEmpty(console.getCoverFolder())) {
			throw new ScanException(ScanException.MISSING_COVER_FOLDER);
		}
		String defaultRomCover = prop.getProperty("noCoverImage");
		console.setDefaultRomCover(defaultRomCover);

		consoleRepository.save(console);
	}

	public void displayConsoles() {
		List<Console> consoles = consoleRepository.findAll();
		for (Console console : consoles) {
			System.out.println(console.getName());
			for (Rom rom : console.getRoms()) {
				System.out.println(" --> " + rom.getFormatedName());
			}

		}		
		/*List<Rom> roms = romRepository.findAll();
		for (Rom rom : roms) {
			System.out.println(rom.getFormatedName());
		}*/
	}

	public void scanAllRoms() throws BusinessException {

		List<Console> consoles = consoleRepository.findAll();

		for (Console console : consoles) {

			String extensions = StringUtils.isBlank(console.getRomsExtensions()) ? "zip" : console.getRomsExtensions();
			String[] romExtensions = extensions.split(";");
			String romFolder = console.getRomFolder();

			// Browse roms present in database, delete it if not physically present in roms folder
			if (console.getRoms() != null) {
				List<Rom> romsToDelete = new ArrayList<Rom>();
				for (Rom rom : console.getRoms()) {
					File f = new File(romFolder + File.separatorChar + rom.getName());
					if (!f.exists()) {
						romsToDelete.add(rom);
					}
				}				
				for (int i = 0; i < romsToDelete.size(); i++) {												
					console.getRoms().remove(romsToDelete.get(i));							
				}
			}

			// Scan the roms folder of the console and add roms not present in DB
			String[] romFiles = new File(romFolder).list();
			if (romFiles != null) {
				for (int j = 0; j < romFiles.length; j++) {
					String romName = romFiles[j];
					if (romName.length() > 4) {
						String extension = StringUtils.substringAfterLast(romName, ".");
						if (isSupportedRomExtension(romExtensions, extension)) {
							console.addRom(new Rom(romFiles[j]));
						}
					}
				}
			}
		}
	}

	private boolean isSupportedRomExtension(String[] extensions, String extension) {
		for (String ext : extensions) {
			if (ext.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}
	
	public List<Rom> findAllRoms() throws BusinessException {
		return romRepository.findAll();
	}

}
