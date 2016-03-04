package nicemul.business.service.console;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import nicemul.business.exception.BusinessException;
import nicemul.business.exception.ScanException;
import nicemul.business.model.Console;
import nicemul.business.model.Emulator;
import nicemul.business.model.Rom;
import nicemul.business.model.enumeration.CommandType;
import nicemul.business.repository.ConsoleRepository;
import nicemul.business.repository.EmulatorRepository;
import nicemul.business.repository.RomRepository;
import nicemul.business.util.Folders;
import nicemul.business.util.PropertiesFileLoader;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsoleService implements IConsoleService {

	@Autowired
	private ConsoleRepository consoleRepository;

	@Autowired
	private RomRepository romRepository;
	
	@Autowired
	private EmulatorRepository emulatorRepository;
	
	public ConsoleService() {
	}

	public List<Rom> findConsoleRoms(String consoleName) throws BusinessException {		
		return consoleRepository.findRoms(consoleName);
	}
	
	public List<Rom> findFullConsoleRoms(String consoleName) throws BusinessException {
		List<Rom> romList = consoleRepository.findRoms(consoleName);
		for (Rom rom : romList) {			
			Hibernate.initialize(rom.getConsole().getEmulators());			
		}		
		return romList;
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
			for (Emulator emulator : console.getEmulators()) {
				System.out.println("    -> " +emulator.getName());
			}
			for (Rom rom : console.getRoms()) {
				System.out.println("        -> " + rom.getFormatedName());
			}			
		}
		
		System.out.println("########## TOUS LES ROMS ##########");
		List<Rom> roms = romRepository.findAll();
		for (Rom rom : roms) {
			System.out.println(rom.getFormatedName());
		}
		
		System.out.println("########## TOUS LES EMULATEURS ##########");		
		List<Emulator> emulators = emulatorRepository.findAll();
		for (Emulator emulator : emulators) {
			System.out.println(emulator.getName());
		}
	}

	public void scanAllRoms() throws BusinessException {

		List<Console> consoles = consoleRepository.findAll();

		for (Console console : consoles) {

			String extensions = StringUtils.isBlank(console.getRomsExtensions()) ? "zip" : console.getRomsExtensions();
			String[] romExtensions = extensions.split(";");
			String romFolder = console.getRomFolder();

			// Browse roms presence in database, delete it if not physically present in roms folder
			if (console.getRoms() != null) {
				List<Rom> romsToDelete = new ArrayList<Rom>();
				for (Rom rom : console.getRoms()) {
					File f = new File(System.getProperty("user.home") + File.separatorChar + romFolder + File.separatorChar + rom.getName());
					if (!f.exists()) {
						romsToDelete.add(rom);
					}
				}				
				for (int i = 0; i < romsToDelete.size(); i++) {												
					console.getRoms().remove(romsToDelete.get(i));							
				}
			}

			// Scan the roms folder of the console and add roms not present in DB
			String[] romFiles = new File(System.getProperty("user.home") + File.separatorChar + romFolder).list();
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
	
	
	
	public void scanEmulators() throws BusinessException {

     
        try {
            
            // First, retrieve all emulators from database, check physical existence and
            // update database in consequence
            /*List<Emulator> emulators = emulatorService.retrieveAllEmulators();
            for (Emulator emulator : emulators) {
                String emulPath = Folders.EMUL_FOLDER + emulator.getFolder();
                File folder = new File(emulPath);
                File propsFile = new File(emulPath + File.separatorChar + "nicemul.properties");
                if (!folder.exists() || !propsFile.exists()) {
                    log.info("Removing emulator " + emulator.getName());
                    emulatorService.removeEmulator(consoles, emulator);
                }
            }*/

            // Second, scan all ressources/emulateurs sub-directories
            // Update database in consequence
            
        	String[] emulatorsFolders = new File(Folders.EMULATORS_DESCRIPTION_FOLDER).list();
            for (String folder : emulatorsFolders) {
                String filename = Folders.EMULATORS_DESCRIPTION_FOLDER + folder + File.separatorChar + "nicemul.properties";
                File propsFile = new File(filename);
                if (propsFile.exists()) {
                    // A folder with an emulateur description file was found
                    try {
                        Properties prop = PropertiesFileLoader.loadPropertiesFile(propsFile);
                        updateEmulator(prop, folder);
                                                
                        // On a second side, remove consoles not emulated anymore by this emulator
                        /*List<Console> emulatedConsoles = emulatorService.retrieveConsolesUsing(consoles, emulator);
                        for (Console console : emulatedConsoles) {
                            boolean exists = false;
                            for (String consoleName : consolesName) {
                                if (consoleName.equals(console.getName())) {
                                    exists = true;
                                }
                            }
                            if (!exists) {
                                log.info("Removing emulator " + emulator.getName() + " for console " + console.getName());
                                emulatorService.removeEmulator(emulator, console);
                            }
                        }*/

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ScanException e) {
                    	e.printStackTrace();
                    }
                }
            }

           
        } catch (Exception e) {
           
        }
       
    }

    private void updateEmulator(Properties prop, String folder) throws ScanException {
    
    	String name = prop.getProperty("name");
        
    	Emulator emulator = emulatorRepository.findByName(name);
        
    	if (emulator == null) {
            emulator = new Emulator();            
        }

        emulator.setName(name);        
        if (StringUtils.isEmpty(emulator.getName())) {
            throw new ScanException(ScanException.MISSING_NAME);
        }
        emulator.setFolder(folder);

        emulator.setExecName(prop.getProperty("execName"));
        if (StringUtils.isEmpty(emulator.getExecName())) {
            throw new ScanException(ScanException.MISSING_EXECNAME);
        }

        emulator.setExtensions(prop.getProperty("supportedRomExtensions"));
        
        String execArgs = prop.getProperty("execArgs");
        if (StringUtils.isNotBlank(execArgs)) {
            emulator.setExecArgs(execArgs);
        }

        try {
            emulator.setCommandType(CommandType.valueOf(prop.getProperty("commandType")));
        } catch (Exception e) {
            emulator.setCommandType(CommandType.PROCESS);
        }
        
        emulator.setIcon(prop.getProperty("icon"));        
        
        // On a first side, add the emulator in the console emulator list if not already present
        String emulatedConsolesProp = prop.getProperty("consoles");
        String consoleNames[] = emulatedConsolesProp.split(";");
        for (String consoleName : consoleNames) {
            Console console = consoleRepository.findByName(consoleName);
            if (console!=null) {            	
            	console.addEmulator(emulator);
            	if (console.getDefaultEmulator()==null) {
            		console.setDefaultEmulator(emulator);
            	}
            }            
        }
        
        emulatorRepository.save(emulator);
    }
	
}
