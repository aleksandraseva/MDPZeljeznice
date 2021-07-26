package mdp.zsmdp.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ZSMDPLogger {
	
	private static final String PATH=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"zsmdp.log";
	
	public static final Logger logger=Logger.getLogger("zsmdp");
	private static ZSMDPLogger instance;

	public static ZSMDPLogger getInstance() {
		if(instance==null) {
			priprema();
			instance=new ZSMDPLogger();
		}
		return instance;
	}
	
	private static void priprema() {
		FileHandler zsmdpHandler;
		try {
			zsmdpHandler = new FileHandler(PATH,true);
			zsmdpHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(zsmdpHandler);
			logger.setLevel(Level.ALL);
			logger.setUseParentHandlers(false);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
