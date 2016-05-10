import java.io.*;
import java.util.*;

public class tipHelper{
    public static void main(String[] args) throws IOException
    {
        File folder = new File(".");
        File[] allFiles = folder.listFiles();
        ArrayList<File> fileList = new ArrayList<File>();
        
        for(File f : allFiles)
        {
            if (f.getName().startsWith("tips") && f.getName().endsWith(".txt")) fileList.add(f);
        }
        
        if (fileList.isEmpty())
        {
            System.out.println("There are not any txt files in the directory.");
            System.exit(0);
        } 
        
        System.out.println("Please enter the number of which totals you'd like:");
        String line;
        int i = 1;
        
        BufferedReader fIn;
        for(File f : fileList)
        {
            fIn = new BufferedReader(new FileReader(f));
            System.out.println(i++ + ". " + fIn.readLine());
        }
        
        Scanner kb = new Scanner(System.in);
        String indexStr = kb.nextLine();
        int index = Integer.parseInt(indexStr);
        
        double totals = 0.0;
        StringTokenizer tipLine;
        
        fIn = new BufferedReader(new FileReader(fileList.get(index - 1)));
        fIn.readLine();
        
        String currentLine;
        while ((currentLine = fIn.readLine()) != null)
        {
            tipLine = new StringTokenizer(currentLine);
            while (tipLine.hasMoreTokens()) totals += Double.parseDouble(tipLine.nextToken()); 
        }
        
        System.out.println("Total tips for the selected file: $" + totals);
    }
}