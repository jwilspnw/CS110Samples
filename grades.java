import java.util.*;

public class grades
{
	public static void main(String[] args)
	{
		int numScores = 0;
		int total = 0;
		double average;

		ArrayList<Integer> scores = new ArrayList<Integer>(); // to store the scores in
		Scanner kb = new Scanner(System.in);
		
		//Prompt for input
		System.out.println("Please enter grades.  If you are done entering grades, enter -1:");
		int currentScore = Integer.parseInt(kb.nextLine()); 
		//Takes whatever the user enters and parses it to an integer if possible.

        while (currentScore >= 0) // Keeps taking input until a value lower than 0 is entered
        {
            scores.add(currentScore);  // Adds the score to an arraylist
            numScores++;  // Increments the total number of scores
            currentScore = Integer.parseInt(kb.nextLine()); //Gets the next scroe from the user
        }
		

		// This block prints out the passing scores
		System.out.println("The following scores are passing:");
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
		// so long as at least one score has been entered
		if (numScores > 0)
		{
    		average = (double) total / numScores;
	    	System.out.println("\nAverage score is: " + average);
		}
	}
}
