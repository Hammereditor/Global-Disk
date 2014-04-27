package host;

import java.io.*;
import java.net.*;

public class UploaderThread implements Runnable 
{
	//class handles a data channel for uploading a file
	public UploaderThread(int port, String clientIPaddr, File dataF, int throughputSim) //throughputSim represents Mbps. Value of 4 = 4 Mbps simulation.
	{
		//clientIPaddr is used to verify that the correct client has connected.
		
	}
}
