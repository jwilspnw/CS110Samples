import java.util.*;
import java.io.*;

public class Lab6{
    public static void main(String[] args)
    {
        System.out.println(sumToN(50));
        System.out.println();
        drawSquare(10);
        System.out.println();
        drawCharFrame(12, "X");
        System.out.println();
        System.out.println(isSuffix("wtfisthis", "his"));
        System.out.println();
        System.out.println(numberOfLines("Lab6.java"));
    }
    
    private static int sumToN(int n)
    {
        return (n * (n + 1)) / 2;  // formula for summation from 1 + 2 + ... + n
    }
    
    private static void drawSquare(int n)
    {
        String tempStr = ""; // Create string to hold stars
        for (int j = 0; j < n; j++)
        {
            tempStr += "*";
        }
        for (int i = 0; i < n; i++)
        {
            System.out.println(tempStr);
        }
    }
    
    private static void drawCharFrame(int n, String symb)
    {
        String topBot = "", mid;
        for (int i = 0; i < n; i++) topBot += symb;  // build top/bottom frame edge
        System.out.println(topBot); // print top edge
        for (int i = 0; i < (n - 2); i++)
        {
            mid = symb;
            for (int j = 0; j < (n - 2); j++) mid += " "; // n-2 is used becasue we are not filling the first or last positions in the string with a space
            mid += symb;
            System.out.println(mid);  // print middle of frame
        }
        System.out.println(topBot); // print bottom edge
    }
    
    private static boolean isSuffix(String base, String sufx)
    {
        return base.endsWith(sufx);  // String has a built-in method to test this, it makes sense to use it
    }
    
    private static int numberOfLines(String f1)
    {
        int numLines = -1;
        try
        {
            BufferedReader sRead = new BufferedReader(new FileReader(f1));
            numLines = 0;
            while (sRead.readLine() != null) numLines++;
        }
        catch (Exception e)
        {
            System.out.println("The file " + f1 + " was not found.  Is this file in the same directory as this program?");
        }
        return numLines;
    }
}