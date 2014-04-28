package host;

import java.io.*;
import java.net.*;

import config.Logs;

public class Server implements Runnable
{
	public int port;
	public int pasvRange_start;
	public int pasvRange_end;
	public char stripeID; //A-D
	public int raidNum; //0-5
	public UptimeScheme uptimeScheme;
	public String hostID;
	
	public Server(int port, int prs, int pre, char stripeID, int raidNum, UptimeScheme uptimeScheme)
	{
		this.port = port;
		this.pasvRange_start = prs;
		this.pasvRange_end = pre;
		this.raidNum = raidNum;
		this.stripeID = stripeID;
		this.uptimeScheme = uptimeScheme;
		
		this.hostID = "stripe " + stripeID + " + mirror " + raidNum;
	}
	
	public void run()
	{
		try
		{
			ServerSocket sSock = new ServerSocket(port);
			while (true)
			{
				Socket net = sSock.accept();
				PrintWriter netOut = new PrintWriter(net.getOutputStream(), true);
				
				String ipAddr = net.getRemoteSocketAddress().toString();
				int randPort = pasvRange_start + (int)(Math.random() * (pasvRange_end - pasvRange_start));
				new Thread(new UploaderThread(hostID, randPort, ipAddr, 3.0, (int)(7.5 * 1024 * 1024))).start();
				
				Logs.info("Server[hostID: " + hostID + ".run(): Created new UploaderThread for port " + randPort);
				netOut.println(randPort);
				netOut.close();
				net.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
