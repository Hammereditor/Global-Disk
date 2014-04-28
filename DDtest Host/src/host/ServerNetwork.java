package host;

import java.util.Scanner;

import config.Logs;

public class ServerNetwork 
{

	public static void main(String[] args) 
	{
		//test network: 24 hosts, RAID 1+0 simulation, 6 hosts mirrored plus 4 hosts for each which are striped
		//2 clients: each one need to store 120 MB of data.
		//each host can hold 400 MB of data. total capacity: 9600 MB, capacity when mirroring is factored in: 1600 MB.
		//goal: mirror data in this fashion: http://www.hammereditor.net/images/globaldisk_24host_scheme.png
		
		testA();

	}
	
	public static void printUptimes(Server[][] servers, int utcTime) //prints single hour
	{
		System.out.println("------------------------ Server status for time of " + utcTime + " UTC: ------------------------");
		boolean[][] downHosts = new boolean[servers.length][servers[0].length];
		int[] stripeSpeeds = new int[servers.length];
		
		for (int i = 0; i < downHosts.length; i++)
		{
			for (int g = 0; g < downHosts[i].length; g++)
			{
				downHosts[i][g] = !servers[i][g].uptimeScheme.isOnline(utcTime);
			}
		}
		
		System.out.print("Offline hosts: ");
		for (int i = 0; i < downHosts.length; i++)
		{
			for (int g = 0; g < downHosts[i].length; g++)
			{
				String res;
				if (downHosts[i][g])
					res = servers[i][g].hostID + ", ";
				else
				{
					res = "";
					stripeSpeeds[i] += 1;
				}
				System.out.print(res);
			}
		}
		
		System.out.println();
		System.out.println("Stripe availability: ------------");
		for (int i = 0; i < downHosts.length; i++)
		{
			System.out.print("Stripe " + (char)(65 + i) + ": " + stripeSpeeds[i] + " Mbps, ");
		}
		
		System.out.println();
	}
	
	public static void testA() //all hosts will be somewhere in U.S.A. (-5:00 to -8:00 UTC)
	{
		int[] hostTimezoneArray = new int[256]; //24 hosts, not hours
		Server[][] servers = new Server[4][6]; //4 stripes, 6 mirrors
		double uptimeVariance = 0.3;
		
		for (int i = 0; i < hostTimezoneArray.length; i++)
		{
			int randomTime = -5; //EST
			randomTime += (int)(Math.random() * -3); //values: -3, -2, -1, and 0
			hostTimezoneArray[i] = randomTime;
		}
		
		int sID = 0;
		
		for (int s = 0; s < servers.length; s++)
		{
			for (int m = 0; m < servers[s].length; m++, sID++)
			{
				UptimeScheme currUS = new UptimeScheme(hostTimezoneArray[sID], uptimeVariance);
				int currPort = 4000 + (sID * 10);
				
				Server currS = new Server(currPort, currPort + 1, currPort + 9, (char)(65 + s), m, currUS);
				new Thread(currS).start();
				servers[s][m] = currS;
			}
		}
		
		Logs.info("ServerNetwork.testA(): All 24 servers started up.");
		Logs.info("ServerNetwork.testA(): To test some uptimes, enter a range of numbers. Example: \'10 16\' test from 10:00 AM to 4:00 PM.");
		Logs.info("ServerNetwork.testA(): To test a specific hour, enter a single number.");
		
		Scanner kbd = new Scanner(System.in);
		while (true)
		{
			String currL = kbd.nextLine();
			int num = Integer.parseInt(currL);
			printUptimes(servers, num);
			System.out.println("\n");
		}
	}

	
	
}
