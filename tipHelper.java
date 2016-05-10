import java.io.*;
import java.util.*;

public class tipHelper{
    public static void main(String[] args) throws IOException
    {
        File folder = new File("."); // Create a folder of all the files in the current directory  
        File[] allFiles = folder.listFiles();  // Pass all the files in the folder into an array
        ArrayList<File> fileList = new ArrayList<File>();  // Create an ArrayList to hold the specific files we want
        
        for(File f : allFiles)  // Loop through all of the files in the array
        {
            if (f.getName().startsWith("tips") && f.getName().endsWith(".txt")) fileList.add(f);  // If they match what we want, pass them into the array list
        }
        
        if (fileList.isEmpty())  // If no text files are in the directory...
        {
            System.out.println("There are not txt files in the directory.");
            System.exit(0);  // ...exit
        } 
        
        System.out.println("Please enter the number of which totals you'd like:");  // PRompt the user for the index (which will displayed by the loop below)
        int i = 1;  // create and initialize index int
        
        BufferedReader fIn;  // Create a BufferedReader
        for(File f : fileList)  // Loop through all files in fileList
        {
            fIn = new BufferedReader(new FileReader(f));  // Initialize the BufferedReader with the current file 
            System.out.println(i++ + ". " + fIn.readLine()); // Show the index and and first line of the files in the ArrayList
        }
        
        Scanner kb = new Scanner(System.in);  // Create scanner to take user input
        String indexStr = kb.nextLine();  // Read the user's input
        int index = Integer.parseInt(indexStr);  // Turn user input into an int
        
        fIn = new BufferedReader(new FileReader(fileList.get(index - 1)));  // Get the file indicated by the user. Offset of -1 is used so we can take user input from 1 rather than 0
        
        double totals = 0.0;  // Create double to hold the totals
        StringTokenizer tipLine;  // Greate a string tokenizer to split the given line of the file.
        String currentLine; // Create a String to hold the current line of the file
        
        fIn.readLine();  // Consume the first line of the tips texts, as they do not contain useful values for totaling tips
        while ((currentLine = fIn.readLine()) != null)  // Continue the loop for as long as there is input to read
        {
            tipLine = new StringTokenizer(currentLine);  // Create a StringTokenizer of the current String
            while (tipLine.hasMoreTokens())  // Continue the loop for as long as there are tokens
            {
                totals += Double.parseDouble(tipLine.nextToken());  // Pasre the current token into a double and add it to the totals
            } 
        }
        
        System.out.println("Total tips for the selected file: $" + totals);  // Display the totals
    }
}