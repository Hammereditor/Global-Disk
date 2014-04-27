package test;

public class Test {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		String beginTstr = System.nanoTime() + "";
		int beginT = Integer.parseInt(beginTstr.substring(beginTstr.length() - 6, beginTstr.length())); //beginT is in nanoseconds.
		
		System.out.println(beginT);
	}

}
