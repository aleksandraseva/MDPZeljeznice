package mdp.azsmdp.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AZSMDPLogger {
	
	private static final String PATH=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"azsmdp.log";
	
	public static final Logger logger=Logger.getLogger("azsmdp");
	private static AZSMDPLogger instance;
	
	public static AZSMDPLogger getInstance() {
		if(instance==null) {
			priprema();
			instance=new AZSMDPLogger();
		}
		return instance;
	}
	
	private static void priprema() {
		try {
			FileHandler azsmdpHandler=new FileHandler(PATH,true);
			azsmdpHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(azsmdpHandler);
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
