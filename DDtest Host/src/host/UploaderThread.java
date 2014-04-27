package host;

import java.io.*;
import java.net.*;
import config.*;

public class UploaderThread implements Runnable 
{
	//for now, uploadData[] is just random bytes.
	public int port;
	public String clientIPaddr;
	//public File dataF;
	public double throughputSim; //in Mbps
	public int bytesToUpload;
	//public byte[] uploadData; //actual data to repetitively upload.
	public String uploadDataRep; //is a code used to indicate the client and host
	
	//class handles a data channel for uploading a file
	public UploaderThread(String uploadDataRep, int port, String clientIPaddr, double throughputSim, int bytesToUpload) //throughputSim represents Mbps. Value of 4 = 4 Mbps simulation.
	{
		//clientIPaddr is used to verify that the correct, expected client has connected.
		this.port = port;
		this.bytesToUpload = bytesToUpload;
		this.throughputSim = throughputSim;
		this.clientIPaddr = clientIPaddr;
		//this.uploadData = uploadData;
		this.uploadDataRep = uploadDataRep;
	}
	
	
	public void run()
	{
		try
		{
			ServerSocket sSock = new ServerSocket(port);
			Socket net = sSock.accept();
			if (!net.getRemoteSocketAddress().toString().equals(clientIPaddr))
			{
				Logs.warning("UploaderThread[uploadData:" + uploadDataRep + "].run(): Client");
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
}






