import java.io.*;
import java.util.*;

public class senatorSorter{
    public static void main(String[] args) throws IOException
    {
        File fp; // Create a new File object
        System.out.println("Sorting floor.txt into D and R senators..."); // Prompt user for input
        fp = new File("floor.txt");  // Known input, creater file with file name 
        BufferedReader fpReader = new BufferedReader(new FileReader(fp)); // Pass the file into a BufferedReader
        
        String currentLine;  // Create string to hold lines of the file
        int totalLines = 0; // Assuming there is at least one line of data
        
        PrintWriter demsOut = new PrintWriter("floor-dems.txt"); // Create democrat file writer
        PrintWriter repsOut = new PrintWriter("floor-reps.txt"); // Create republican file writer
        
        while((currentLine = fpReader.readLine()) != null)  // Read through the floor.txt until end of file, passing each line into "currentLine"
        {
            if (currentLine.contains("(D-")) demsOut.println(currentLine);  // if currentLine contains the Dem indicator, write to the dem list
            if (currentLine.contains("(R-")) repsOut.println(currentLine);  // likewise for the rep list
        }
        
        // Close the writers
        demsOut.close();
        repsOut.close();
        
        System.out.println("Democratic and Republican senators sorted.");
    }
}