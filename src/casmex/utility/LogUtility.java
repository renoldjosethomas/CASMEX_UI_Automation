package casmex.utility;

import org.apache.log4j.Logger;

import casmex.utility.ExcelUtility;
import casmex.utility.LogUtility;


public class LogUtility 
{
	private static Logger log;
	private ExcelUtility excel;
	
	public LogUtility()
	{
		log = Logger.getLogger(LogUtility.class.getName());
	}
	
	public void logTestDetails(int fromRow, int toRow)
	//Log test details like Sprint, Version, Author, Date, etc.. in Log4j file
	{
		try
		{
			String logMessage = "";
			excel = new ExcelUtility();
			
			for (int itr = fromRow ; itr <= toRow ; itr++)
	        {
				for(int cellNum = 0; cellNum <= 1; cellNum++)
				{
					logMessage = logMessage + "  " + excel.getCellData(itr, cellNum).toString();
				}
				info(logMessage);
				logMessage = "";
			}		
		}
		catch(Exception sysEx)
		{
			log.error("logTestDetails", sysEx);
		}
	}
		
	public void info(String logMessage)
	{
		try
		{
			log.info(logMessage);
		}
		catch(Exception sysEx)
		{
			log.error("info", sysEx);
		}
	}

	public void error(String methodName, Exception ex) 
	{
		try
		{
			log.error("FAILED:  " + methodName + "   -   " + ex.getMessage());
			ex.printStackTrace();	   
		}
		catch(Exception sysEx)
		{
			log.error("error", sysEx);
		}
	}

}