package nicemul.business.service.console;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nicemul.business.exception.BusinessException;
import nicemul.business.model.Emulator;
import nicemul.business.model.Rom;
import nicemul.business.model.enumeration.CommandType;
import nicemul.business.util.Folders;

@Service
public class EmulatorService implements IEmulatorService {

	@Autowired
	private IRomService romService;
	
	public void execute(long idRom) throws BusinessException {
		
		Rom rom = romService.findFullRom(idRom);
		
        Emulator emulator = rom.getConsole().getEmulators().get(0);
        CommandType commandType = emulator.getCommandType();
        if (commandType == CommandType.RUNTIME) {
            executeRuntime(emulator, rom);
        } else {
            executeProcess(emulator, rom);
        }
    }

    private void executeRuntime(Emulator emulator, Rom rom) throws RuntimeException {
        try {
            String userDir = System.getProperty("user.dir");
            System.out.println(userDir);
            String cmd = userDir + File.separatorChar + Folders.EMULATORS_DESCRIPTION_FOLDER + emulator.getFolder() + File.separatorChar + emulator.getExecName();
            if (StringUtils.isNotBlank(emulator.getExecArgs())) {
                cmd = cmd + " " + emulator.getExecArgs();
            }
            // Absolute path of the rom
            cmd = cmd + " \"" + userDir + File.separatorChar + rom.getConsole().getRomFolder() + File.separatorChar + rom.getName() + "\"";
            // Relative path of the rom
            //cmd = cmd + " \"" + console.getRomFolder() + File.separatorChar + rom.getName() + "\"";           
            Runtime rt = Runtime.getRuntime();
            Process process = rt.exec(cmd);
            process.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }       

    private void executeProcess(Emulator emulator, Rom rom) throws BusinessException {
        try {            
            String userDir = System.getProperty("user.dir");
            String romName =  userDir + File.separatorChar + rom.getConsole().getRomFolder() + File.separatorChar + rom.getName();
            List<String> args = new ArrayList<String>();
            String cmd = userDir + File.separatorChar +  Folders.EMULATORS_DESCRIPTION_FOLDER+emulator.getFolder() + File.separatorChar + emulator.getExecName();
            args.add(cmd);
            if (StringUtils.isNotBlank(emulator.getExecArgs())) {
                String theArgs[] = emulator.getExecArgs().split(" ");
                for (int i = 0; i < theArgs.length; i++)
                    args.add(theArgs[i]);
            }
            args.add(romName);
            ProcessBuilder pb = new ProcessBuilder(args);            
            pb.directory(new File(userDir + File.separatorChar +Folders.EMULATORS_DESCRIPTION_FOLDER+ emulator.getFolder() + File.separatorChar));
            Process process = pb.start();
            process.waitFor();
        } catch (Exception e) {
            throw new BusinessException("Error while launching emulator", e);
        }
    }
	
}
