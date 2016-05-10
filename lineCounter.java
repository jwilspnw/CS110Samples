import java.io.*;
import java.util.*;

public class lineCounter{
    public static void main(String[] args) throws IOException
    {
        File fp; // Create a new File object
        System.out.println("What is the name of the file you'd like to read?"); // Prompt user for input
        Scanner kb = new Scanner(System.in);  // Create a scanner to listen to the keyboard
        fp = new File(kb.nextLine());  // Pass the user input as the potential file name (skipping creation of String here since it will not be user for anything else)
        
        BufferedReader fpReader = new BufferedReader(new FileReader(fp)); // Pass the file into a BufferedReader
        int totalLines = 1; // Assuming there is at least one line of data
        
        while(fpReader.readLine() != null) totalLines++;  // Keep reading lines until there is a null (end of file)
        
        System.out.println("The provided file has " + totalLines + " lines.");  // Display totals
    }
}