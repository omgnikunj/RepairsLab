package net.sf.repairslab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.UIManager;

import net.sf.repairslab.control.CheckUpdates;
import net.sf.repairslab.control.CommonMetodBin;
import net.sf.repairslab.model.BinRelease;
import net.sf.repairslab.ui.VcMainFrame;
import net.sf.repairslab.ui.VcSplashScreen;
import net.sf.repairslab.util.ui.WindowUtil;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class StartApp {
	
	static private Logger  logger = Logger.getLogger(StartApp.class.getName());
	
	/**
	 * @author ferraf01
	 * 
	 * Main di F2Edit.
	 * Questa classe setta il LookAndFeel utilizzando il parametro LOOK
	 * della classe singleton EnvProperties e instanzia il frame F2Edit.java
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		
		initLog4j();
		
		logger.debug("Current working directory : " + System.getProperty("user.dir"));
		
		try {
			logger.debug("Loading look&feel");
			UIManager.setLookAndFeel(EnvProperties.getInstance().getProperty(EnvProperties.LOOK));			
		} catch (Exception e) {
			logger.error("Exception in Loading look&feel\n"+e+"\n");
			//e.printStackTrace();
		}
		
		showSplash();
 
        splash.setStatus("Starting...", 10);
        
        splash.setStatus("Get release...", 20);
    	VersionReader versionReader = new VersionReader();
    	BinRelease binRelease = new BinRelease(versionReader.getVersion(), versionReader.getRelease());
		
        CommonMetodBin.getInstance().setCurrentRelease(binRelease);
        splash.setRelease(CommonMetodBin.getInstance().getCurrentRelease().toString());
        
        splash.setStatus("Setting locale", 30);
        String selVal = EnvProperties.getInstance().getProperty(EnvProperties.LOCALE);
        Locale.setDefault(new Locale(selVal.split("-")[0],selVal.split("-")[1]));

        if(!checkProcess(EnvProperties.getInstance().getProperty(EnvProperties.SERVER_PROCESS))){
        	splash.setStatus("Start Server...", 40);
            startServer();
        }
        
        splash.setStatus("Check for updates...", 50);
        CheckUpdates.check();
        
        splash.setStatus("Testing Connection...", 80);
        testConn();
        
        splash.setStatus("Loading App...", 90);
		VcMainFrame frame = new VcMainFrame();
		logger.debug("Application loaded...");
		
		CommonMetodBin.getInstance().setMainFrame(frame);
		splash.setStatus("Started...", 100);
		frame.setVisible(true);
		hideSplash();
	}
	
	private static void initLog4j(){
		String configFile = EnvProperties.getInstance().getProperty("Log4jConfig");
		String logFile = EnvProperties.getInstance().getProperty("Log4jLogFile");
		
		try {
			System.setProperty("Log4jLogFile", logFile);
			System.out.println("LogFile di Log4j: " + logFile);
        } catch(Exception ex) {
			System.out.println("Eccezione : " + ex.getMessage());
			return;
		}
		if ( configFile != null ) {
			PropertyConfigurator.configure(configFile);
			System.out.println("Configurazione Log4j: " + configFile);
		}
		logger.info("File di log inizializzato con successo....");

	}
	
	private static void testConn(){
		Connection con = CommonMetodBin.getConn();
		CommonMetodBin.closeConn(con);
	}
	
	@Deprecated
	private static void startServer() {
		//cmd.exe /c mysql_start.bat
		final String cmd = EnvProperties.getInstance().getProperty(EnvProperties.START_CMD);
		if(cmd!=null && !cmd.equals("")){
			new Thread(new Runnable() {
		          public void run() {
					try {
						Runtime rt = Runtime.getRuntime();
						Process proc = rt.exec(cmd);
						splash.setStatus("Server Started...", 70);
						proc.waitFor();
					} catch (Throwable t) {
						t.printStackTrace();
					}
		          }
	        }).start();
		}
	}

	private static VcSplashScreen splash;

	public static VcSplashScreen showSplash() {
		if (splash != null) {
            if(splash.isShowing()) {
                return splash;
            } else {
                splash = null;
            }
        }
        splash = new VcSplashScreen();
        WindowUtil.centerWindow(splash);
        splash.setVisible(true);
        return splash;
    }
	
	public static void hideSplash() {
        if (splash != null) {
            splash.dispose();
            splash = null;
        }
    }
	
	@Deprecated
	public static void runInitializer(Runnable r) {
        Thread t = new Thread(r);
        t.start();
    }
	
	@Deprecated
	public static boolean checkProcess(String process){
		if (process != null && process.equals("")){
			return false;
		}
		boolean result = false;
		List<String> processes = listRunningProcesses();
	    Iterator<String> it = processes.iterator();
	    while (it.hasNext()) {
	    	String pr = it.next();
	    	if(pr.equalsIgnoreCase(process))
	    		return true;
	    }
	    return result;
	}
	
	@Deprecated
	public static List<String> listRunningProcesses() {
	    List<String> processes = new ArrayList<String>();
	    try {
	      String line;
	      Process p = Runtime.getRuntime().exec("tasklist.exe /nh");
	      BufferedReader input = new BufferedReader
	          (new InputStreamReader(p.getInputStream()));
	      while ((line = input.readLine()) != null) {
	          if (!line.trim().equals("")) {
	              processes.add(line.substring(0, line.indexOf("  ")));
	          }
	      }
	      input.close();
	    }
	    catch (Exception err) {
	    	//err.printStackTrace();
	    	logger.error("Exception in Remove process running \n"+err+"\n");
	    }
	    return processes;
	  }
	
}