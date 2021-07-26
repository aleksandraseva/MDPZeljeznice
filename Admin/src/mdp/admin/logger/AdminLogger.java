package mdp.admin.logger;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AdminLogger {
	
	private static final String PATH=System.getProperty("user.dir")+File.separator+"resources"+File.separator+"admin.log";

	public static final Logger logger=Logger.getLogger("admin");
	private static AdminLogger instance;
	
	public static AdminLogger getInstance() {
		if(instance==null) {
			priprema();
			instance=new AdminLogger();
		}
		return instance;
	}
	
	private static void priprema() {
		try {
			FileHandler adminHandler=new FileHandler(PATH,true);
			adminHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(adminHandler);
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
