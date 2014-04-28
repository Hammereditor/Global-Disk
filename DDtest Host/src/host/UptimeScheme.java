package host;

public class UptimeScheme //helps simulate when a host will be down and up, according to timezone
{
	public int utcDifference; //0 = GMT, -5 = EST
	public int avgDailyUptime; //24 = 100%, 12 = 50%
	public double[] uptimeChances; //example: putting "0.5" at index 13 would mean, "50% chance of being online at 1:00 PM".
	//The hours are UTC time.
	
	public UptimeScheme (int utcDifference, int avgDailyUptime) //this constructor automatically determines the uptime chances
	{
		//during 0 to 5, 10% chance of being up
		//during 6 to 9, 25% chance
		//during 10 to 14, 40% chance
		//during 15 to 20, 70% chance
		//during 21 to 24, 20% chance
		
		uptimeChances = new double[24];
		
		for (int i = 0; i < uptimeChances.length; i++)
		{
			int utcHour;
			double currUptime;
			
			if (utcDifference >= 0)
				utcHour = 0 + utcDifference;
			else //example: EST at -5:00
				utcHour = 24 - utcDifference;
			
			if (utcHour >= 0 && utcHour <= 5)
				currUptime = 0.10;
			else if (utcHour >= 6 && utcHour <= 9)
				currUptime = 0.25;
			else if (utcHour >= 10 && utcHour <= 14)
				currUptime = 0.40;
			else if (utcHour >= 15 && utcHour <= 20)
				currUptime = 0.70;
			else if (utcHour >= 21 && utcHour <= 24)
				currUptime = 0.20;
			else
				currUptime = 0.5;
			
			uptimeChances[i] = currUptime;
		}
	}

}
