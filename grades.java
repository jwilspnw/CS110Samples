import java.util.*;

public class hello
{
	public static void main(String[] args)
	{

		Random rand = new Random();

		int numScores, n = 0;
		int total = 0;
		double average;
		//numScores++;
		numScores = 10;

		ArrayList<Integer> scores = new ArrayList<Integer>();

		// This following block is used just to populate the arraylist with example scores.
		int temp;
		while (n < numScores)
		{	
			temp = rand.nextInt();
			if (temp > 0)
			{
				scores.add(temp % 101);
				n++;
			}
		}

		// This block prints out the passing scores
		for(int i : scores)
		{
			if ( i >= 60 )
			{
				System.out.println(i);
			}
			// This adds the scores to a big "pool" which will be used to calcualte the averages
			total += i;
		}

		// This divides the total "pool" by how many collected scores to generate the average
		average = (double) total / numScores;

		System.out.println("Average score is: " + average);
	}
}
