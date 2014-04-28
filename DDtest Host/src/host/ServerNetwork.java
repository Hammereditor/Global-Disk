package host;

public class ServerNetwork {

	public static void main(String[] args) 
	{
		//test network: 24 hosts, RAID 1+0 simulation, 6 hosts mirrored plus 4 hosts for each which are striped
		//2 clients: each one need to store 120 MB of data.
		//each host can hold 400 MB of data. total capacity: 9600 MB, capacity when mirroring is factored in: 1600 MB.
		//goal: mirror data in this fashion: http://www.hammereditor.net/images/globaldisk_24host_scheme.png

	}

}
