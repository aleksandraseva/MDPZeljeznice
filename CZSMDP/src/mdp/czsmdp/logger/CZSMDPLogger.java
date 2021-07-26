package mdp.czsmdp.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class CZSMDPLogger {
	
	private static final String PATH=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"czsmdp.log";
	
	public static final Logger logger=Logger.getLogger("czsmdp");
	private static CZSMDPLogger instance;
	
	public static CZSMDPLogger getInstance() {
		if(instance==null) {
			priprema();
			instance=new CZSMDPLogger();
		}
		return instance;
	}
	
	private static void priprema() {
		FileHandler czsmdpHandler;
		try {
			czsmdpHandler = new FileHandler(PATH,true);
			czsmdpHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(czsmdpHandler);
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
