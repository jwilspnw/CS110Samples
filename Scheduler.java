import java.util.*;
import java.io.*;

public class Scheduler{
    public static void main(String[] args) throws IOException
    {
        System.out.println("Please enter the number of the free/busy group you'd like to schedule for:");
        Scanner kb = new Scanner(System.in);
        int n = Integer.parseInt(kb.nextLine()); // Not any safer than kb.nextInt() because Java doesn't have tryParseInt(), but consumes the next line (making life easier)
        try
        {
            BufferedReader bRead = new BufferedReader(new FileReader("busy" + n + ".txt"));
            
            ArrayList<String> busyNames = new ArrayList<>();
            ArrayList<int[][]> busyTimes = new ArrayList<>();
            ArrayList<String> freeNames = new ArrayList<>();
            ArrayList<int[][]> freeTimes = new ArrayList<>();
            int[][] sched;
            String[] lineTok, hourTok;
            
            String currentLine;
            while ((currentLine = bRead.readLine()) != null)
            {
                sched = new int[5][10];  // Clear the temp schedule array
                lineTok = currentLine.split(" /");  // Tokenize on the "space slash" to separate name from days
                busyNames.add(lineTok[0]); // name will be the first token

                for(int i = 1; i < lineTok.length; i++)  // while there are more day tokens, keep looping
                {
                    hourTok = lineTok[i].split("\\s+", 2); // Split on blankspace to get the individual DAY char and hours
                    sched[getDayIndex(hourTok[0].charAt(0))] = getHours(hourTok[1], true);  // Send the string that has the hours to be built into an array, and place it into the index of sched[] returned by getDayIndex()
                }
                busyTimes.add(sched); // Store these in the busyTimes arraylist.  The name of the person busy will match the index of the times
            }
            
            //repeat the process for free
            bRead = new BufferedReader(new FileReader("free" + n + ".txt"));
            while ((currentLine = bRead.readLine()) != null)
            {
                sched = new int[5][10];
                for (int[] row: sched) Arrays.fill(row, 1);  // set all to 1 by default for the "free times" array
                lineTok = currentLine.split(" /");
                freeNames.add(lineTok[0]);
                for(int i = 1; i < lineTok.length; i++)
                {
                    hourTok = lineTok[i].split("\\s+", 2);
                    sched[getDayIndex(hourTok[0].charAt(0))] = getHours(hourTok[1], false);  // False this time since we are dealing with "free" schedules
                }
                freeTimes.add(sched);
            }
            
            int[][] temp;
            for(int i = 0; i < busyNames.size(); i++)
            {
                System.out.println(busyNames.get(i));
                temp = busyTimes.get(i);
                for (int[] day: temp) {
                    for (int hour: day) {
                        System.out.print(hour + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
            
            for(int i = 0; i < freeNames.size(); i++)
            {
                System.out.println(freeNames.get(i));
                temp = freeTimes.get(i);
                for (int[] day: temp) {
                    for (int hour: day) {
                        System.out.print(hour + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.toString());
        }
    }
    
    private static int getDayIndex(char day)
    {
        switch (day)
        {
            case 'M':
                return 0;
            case 'T':
                return 1;
            case 'W':
                return 2;
            case 'R':
                return 3;
            case 'F':
                return 4;
        }
        return -1;
    }
    
    private static int[] getHours(String hourStr, Boolean busy)
    {
        String[] hours = hourStr.split("\\s");
        int[] temp = new int[10];
        int index;
        
        if (!busy)
        {
            for(int i = 0; i < temp.length; i++) temp[i] = 1;
        }
        
        for(int i = 0; i < hours.length; i++)
        {
            index = Integer.parseInt(hours[i]) - 8;
            index = ((index < 0) ? index + 8 : index);
            temp[index] = (busy ? 1 : 0); // Shorthand if that writes 1 to the hour index if busy is true and 0 if it is not
        }
        return temp;
    }
    
    
}