import java.util.*;
import java.io.*;

public class simpleIO{
	public static void main(String[] args) throws FileNotFoundException
	{
		// Create a scanner to read the keyboard input
		Scanner kb = new Scanner(System.in);

		// Prompt user for input filename
		System.out.println("Please enter the name of the file you'd like to read from:");

		// Get input from the reader
		String pathIn = kb.nextLine();

		// (Attempt to) open the file at the given filename
		File fileIn = new File(pathIn);

		// Make sure input file exists
		if (!fileIn.exists())
		{
			System.out.println("File does not exist.  Exiting...");
			System.exit(-1);
		}

		// Prompt user for output filename
		System.out.println("Please enter name for output file:");

		// Save the given output filepath
		String pathOut = kb.nextLine();

		// Create a scanner to read the contents of the input file
		Scanner fIn = new Scanner(fileIn);

		// Create a file at the provided fileout path and write to it.  If a file with this name exists, it will be overwritten.
		PrintWriter upperCaseWriter = new PrintWriter(pathOut);

		// While the input file has more lines: read the next line, convert to all uppercase, and write to the output file
		while(fIn.hasNextLine()) upperCaseWriter.println(fIn.nextLine().toUpperCase());
		
		// Close the file
		upperCaseWriter.close();
	}
}
