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
            ArrayList<String>  names = new ArrayList<>();
            ArrayList<int[][]> times = new ArrayList<>();
            int[][] sched;
            String[] lineTok, hourTok;
            
            /*
                GENERAL OVERVIEW OF HOW THIS WORKS!
                Since there are 5 days in a week and 10 available time slots (8-5 inclusive)
                A schedule can be represented as a 5x10 grid, which we will do as a 5x10 array
                These times are listed in a .txt file with a name attached
                
                This name, however, is not useful for our purposes.  It make be in the future though so there is an ArrayList means to hold them.
                A quick example of how this will work follows:
                A line is read from the text file, for example
                "Smaltry, Gary /M 10 11 12 /R 10 11 /F 2 5"
                This is then split into pieces, the first piece representing the name, the following pieces representing days.  For example
                "Smaltry, Gary"    "M 10 11 12"    "R 10 11"    "F 2 5"
                The name gets added to the names arraylist, and then a loop considered the remaining tokens
                First considered would be "M 10 11 12"
                Because we know that follows a " /" is always a character that represents a day, 
                and there is not necessarily every day listed in someones schedule, we need to get the index of this day.
                To do this, we again split the day string into two components by splitting on spaces.  the "\\s+" flag will split on spaces, newlines, and tabs.
                This split gives us
                "M"     "10 11 12"
                We then push the first item through our getIndex function, which returns an index for the array.  M = 0, T = 1, etc.
                Once we have this index, we can then push the second portion into our getHours function which creates a day's schedule based on supplied times and whether that person is or is not "busy"
                We will then repeat this process for our remainings "day" strings, which for our example are "R 10 11" and "F 2 5"
                An entire schedule is built from these, and then that schedule is added to the "times" ArrayList
                The BufferedReader then reads the next line, and repeats the above steps for it.
                Once it is finished with the "busyN.txt" (where N is the user's input number), the whole process is repeated with "freeN.txt"
            */
        
            // read the "busyN.txt" first
            BufferedReader bRead = new BufferedReader(new FileReader("busy" + n + ".txt"));  
            String currentLine;
            while ((currentLine = bRead.readLine()) != null)
            {
                sched = new int[5][10];  // Clear the temp schedule array
                lineTok = currentLine.split(" /");  // Tokenize on the "space slash" to separate name from days
                names.add(lineTok[0]); // name will be the first token

                for(int i = 1; i < lineTok.length; i++)  // while there are more day tokens, keep looping
                {
                    hourTok = lineTok[i].split("\\s+", 2); // Split on blankspace to get the individual DAY char and hours
                    sched[getDayIndex(hourTok[0].charAt(0))] = getHours(hourTok[1], true);  // Send the string that has the hours to be built into an array, and place it into the index of sched[] returned by getDayIndex()
                }
                times.add(sched); // Store these in the busyTimes arraylist.  The name of the person busy will match the index of the times
            }
            
            //repeat the process for "freeN.txt"
            bRead = new BufferedReader(new FileReader("free" + n + ".txt"));
            while ((currentLine = bRead.readLine()) != null)  // Read the text file line by line
            {
                sched = new int[5][10]; // Build an empt array to represent a person's schedule
                for (int[] row: sched) Arrays.fill(row, 1);  // set all to 1 by default for the "free times" array
                lineTok = currentLine.split(" /");  // Split a line on the " /" sequence.  For example: "Smaltry, Gary /M 10 11 12 /R 10 11 /F 2 5" gets split into "Smaltry, Gary", "M 10 11 12", "R 10 11", "F 2 5"
                names.add(lineTok[0]);  // Push the first token from the above split string into the "names" list
                for(int i = 1; i < lineTok.length; i++)  // Starting at the first token that ISN'T the name, consider the remaining tokens as days
                {
                    hourTok = lineTok[i].split("\\s+", 2);  // Split the considred day into two parts on the first space, e.g. "M 9 10 11 12" will get split into "M","9 10 11 12"
                    sched[getDayIndex(hourTok[0].charAt(0))] = getHours(hourTok[1], false);  // False this time since we are dealing with "free" schedules
                }
                times.add(sched);  // Add the constructed 2D array into ArrayList
            }
            
            getMeetingTimes(times);  // Pass the ArrayList of times to see when works for everyone
            
            // THE BELOW BLOCK IS FOR DEBUGGING!  The logic for comparing times/dates that are free for everyone will be similar, but there is no need to print out everything
            /*
            int[][] temp;
            for(int i = 0; i < names.size(); i++)
            {
                System.out.println(names.get(i));
                temp = times.get(i);
                for (int j = 0; j < temp.length; j++) {
                    System.out.print(getDayName(j) + ": ");
                    for (int k = 0; k < temp[j].length; k++) {
                        if (temp[j][k] == 1) System.out.print(getTimeName(k) + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
            */
            // END DEBUG BLOCK
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.toString());  // Print out the error details if the file load fails
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
    
    private static int[] getHours(String hourStr, boolean busy)
    {
        String[] hours = hourStr.split("\\s");  // split the provided string on spaces
        int[] temp = new int[10];  // Create a temporary array to serve as the free/not free hours of a day 
        int index;
        
        if (!busy) for(int i = 0; i < temp.length; i++) temp[i] = 1; // Set all indices of the array to 1 if we are considering "free" schedules
        
        for(int i = 0; i < hours.length; i++)
        {
            index = Integer.parseInt(hours[i]) - 8; // Subtract 8 hours, so that the "8" index starts at 0
            index = ((index < 0) ? index + 12 : index);  // If index less that 0 (e.g. 4 - 8) add 12 to get correct array index
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
    
    private static void getMeetingTimes(ArrayList<int[][]> times)
    {
        boolean worksForAll;  // Flag that will be set for checking an available time to see if it is available for everyone
        int count = 3;  // Top 3 times will be printed
        
        System.out.println("The following times work for everyone:");  // Output the possible times for everyone
        
        // The two loops below start at the END of their arrays, as the task is to find the 3 latests meeting times
        outerLoop:  // outerLoop label so that it is possible to break to this point in particular
        for(int i = times.get(0).length - 1; i >= 0; i--)
        {
            for(int j = times.get(0)[0].length - 1; j >= 0; j--) 
            {
                worksForAll = false;  // Reset to false for safety
                innerLoop:  // innerLoop label so that it is possible to break to this point in particular
                for(int[][] sked : times)  // Open all of the created scheduled and check the index to see if it is marked "1" for all
                {
                    worksForAll = (sked[i][j] == 1 ? true : false);  // If the index of the schedule is set to 1; set "works for all" to true, else say that it does not work
                    if (!worksForAll) break innerLoop;  // If it is false, break out of the loop, as it is not necessary to loop through all schedules if ANY schedule does not have the available time
                }
                if (worksForAll)  // Print out the time if it works for everyone
                {
                    System.out.print(getDayName(i) + ": " + getTimeName(j) + " ");  // Formatting for printing out the working time
                    count--;  // Decrement the count
                }
                if (count == 0) break outerLoop;  // if three times have been printed, break out of the entire loop
            }
        }
    }
}