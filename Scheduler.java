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
            ArrayList<String>  busyNames = new ArrayList<>();
            ArrayList<int[][]> busyTimes = new ArrayList<>();
            ArrayList<String>  freeNames = new ArrayList<>();
            ArrayList<int[][]> freeTimes = new ArrayList<>();
            int[][] sched;
            String[] lineTok, hourTok;
        
            // read the "busyN.txt" first
            BufferedReader bRead = new BufferedReader(new FileReader("busy" + n + ".txt"));  
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
            
            //repeat the process for "freeN.txt"
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
            
            // THE BELOW BLOCK IS FOR DEBUGGING!  The logic for comparing times/dates that are free for everyone will be similar, but there is no need to print out everything
            int[][] temp;
            for(int i = 0; i < busyNames.size(); i++)
            {
                System.out.println(busyNames.get(i));
                temp = busyTimes.get(i);
                for (int j = 0; j < temp.length; j++) {
                    System.out.print(getDayName(j) + ": ");
                    for (int k = 0; k < temp[j].length; k++) {
                        if (temp[j][k] == 1) System.out.print(getTimeName(k) + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
            
            for(int i = 0; i < freeNames.size(); i++)
            {
                System.out.println(freeNames.get(i));
                temp = freeTimes.get(i);
                for (int j = 0; j < temp.length; j++) {
                    System.out.print(getDayName(j) + ": ");
                    for (int k = 0; k < temp[j].length; k++) {
                        if (temp[j][k] == 1) System.out.print(getTimeName(k) + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
            // END DEBUG BLOCK
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.toString());
        }
    }
    
    private static int getDayIndex(char day)  // Get the correct arrayt index from the char of the date
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
    
    private static String getTimeName(int dex)
    {
        switch (dex)
        {
            case 0:
                return "8:00AM";
            case 1:
                return "9:00AM";
            case 2:
                return "10:00AM";
            case 3:
                return "11:00AM";
            case 4:
                return "NOON";
            case 5:
                return "1:00PM";
            case 6:
                return "2:00PM";
            case 7:
                return "3:00PM";
            case 8:
                return "4:00PM";
            case 9:
                return "5:00PM";
        }
        return "";
    }
    
    private static String getDayName(int dex)
    {
        switch (dex)
        {
            case 0:
                return "MON";
            case 1:
                return "TUE";
            case 2:
                return "WED";
            case 3:
                return "THU";
            case 4:
                return "FRI";
        }
        return "";
    }
}