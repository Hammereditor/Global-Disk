package host;

public class UptimeScheme //helps simulate when a host will be down and up, according to timezone
{
	public int utcDifference; //0 = GMT, -5 = EST
	public double avgUptimeAtChecks = 0.5; //1.0 = 100%, 0.5 = 50%
	public double[] uptimeChances; //example: putting "0.5" at index 13 would mean, "50% chance of being online at 1:00 PM".
	
	public double uptimeVariance; //how much the actual chances of being up differ from the set values.
	//Example: 0.0 = zero variance, 1.0 = completely random
	//The hours are UTC time.
	
	public UptimeScheme(int utcDifference, double uptimeVariance) //this constructor automatically determines the uptime chances
	{
		//during 0 to 5, 10% chance of being up
		//during 6 to 9, 25% chance
		//during 10 to 14, 40% chance
		//during 15 to 20, 70% chance
		//during 21 to 24, 20% chance
		
		this.uptimeVariance = uptimeVariance;
		uptimeChances = new double[24];
		
		for (int i = 0; i < uptimeChances.length; i++)
		{
			int utcHour;
			double currUptime; //
			
			if (utcDifference >= 0)
				utcHour = 0 + utcDifference;
			else //example: EST at -5:00
				utcHour = 24 - utcDifference;
			
			if (i >= 0 && i <= 5)
				currUptime = 0.10;
			else if (i >= 6 && i <= 9)
				currUptime = 0.25;
			else if (i >= 10 && i <= 14)
				currUptime = 0.40;
			else if (i >= 15 && i <= 20)
				currUptime = 0.70;
			else if (i >= 21 && i <= 24)
				currUptime = 0.20;
			else
				currUptime = 0.5;
			
			uptimeChances[i] = currUptime;
		}
	}
	
	
	public boolean isOnline(int utcHour) //utcHour = current hour in UTC time.
	{
		/*double randUptime = 50 - (Math.random() * 100) * uptimeVariance;
		double uptimeChance = uptimeChances[utcHour] * 100; //50 or above = up
		
		System.out.println("randUptime: " + randUptime + ", uptimeChance: " + uptimeChance);
		
		if (uptimeChance + randUptime < 50.0)
			return false;
		else
			return true;*/
		double rand = (0.5 - Math.random()) * uptimeVariance * 10; //example: (0.5 - rnd(0.4)) * 0.15 * 10 =  
		rand = 0;
		
		return (uptimeChances[utcHour] + (0.5 - Math.random()) + rand) >= 0.50;
	}
	

}





