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
				Logs.warning("UploaderThread[uploadData:" + uploadDataRep + "].run(): Client\'s IP address is unexpected. Disconnecting...");
				net.close();
				sSock.close();
			}
			
			byte[] randomData = new byte[1024];
			for (int i = 0; i < randomData.length; i++)
				randomData[i] = (byte)(Math.random() * 255);
			
			//now upload the random data
			for (int totalBytes = 0; totalBytes < bytesToUpload; totalBytes += randomData.length)
			{
				uploadIter(randomData, net.getOutputStream());
			}
			net.close();
			sSock.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//131072 bytes/sec. = 1 Mbps
	public void uploadIter(byte[] dataArray, OutputStream netOut) throws Exception
	{
		int usPerIter = (int)(1000 / (128 * throughputSim * 1000)); //time, in microseconds, it should take per array upload. Example: 1000 / (128 * 10 Mbps) = 800 microsec.
		String beginTstr = System.nanoTime() + "";
		int beginT = Integer.parseInt(beginTstr.substring(beginTstr.length() - 6, beginTstr.length())) / 1000; //beginT is in microseconds.
		
		netOut.write(dataArray);
		netOut.flush();
		
		String endTstr = System.nanoTime() + "";
		int endT = Integer.parseInt(endTstr.substring(endTstr.length() - 6, endTstr.length())) / 1000;
		
		if (endT - beginT < usPerIter) //if the time to upload the array took shorter than it is supposed to
		{
			int delayT = usPerIter - (endT - beginT);
			Thread.sleep(0, delayT);
		}
	}
	
}






