package config;

public class Config 
{
	//holds global configuration data
	
	//////////////////////////
	//ConnectionMain
	//////////////////////////
	public static int procMonitor_port = 3001;
	public static String procMonitor_ipAddr = "127.0.0.1";
	public static String procMonitor_password = "Rb13s!";
	
	public static int cm_sSockPort = 3000;
	public static String passwordTableFilePath = "C:\\HammerHost\\passwordTable.txt";
	public static int logLevel = 5;
	
	public static int blProcess_bufSize = 5000;
	public static int blProcess_readDelay = 10; //milliseconds
	public static int blProcess_isRunningDelay = 1250;
	public static int blProcess_softTerminateDelay = 1250;
	public static int blProcess_forceTerminateDelay = 500;
	
	public static int authWait_checkCycleDelay = 20; //milliseconds
	public static int authWait_checkCycleLimit = 6000; //2 minutes
}
