import java.util.Scanner;

public class Linear{
    public static void main(String[] args)
    {
        Double m1, b1, m2, b2;
        Scanner kb = new Scanner(System.in);
        System.out.println("What is m1?");
        m1 = Double.parseDouble(kb.nextLine());
        System.out.println("What is b1?");
        b1 = Double.parseDouble(kb.nextLine());
        System.out.println("What is m2?");
        m2 = Double.parseDouble(kb.nextLine());
        System.out.println("What is b2?");
        b2 = Double.parseDouble(kb.nextLine());
        
        Double x, y;
        x = XIntercept(m1, m2, b1, b2);
        y = SolveLinear(m1 * x, b1);
        
        System.out.println("The intersection of the lines is at (" + x + ", " + y + ")");
        
    }
    
    private static double SolveLinear(double m, double b)
    {
        return m + b;
    }
    
    private static double XIntercept(double m1, double m2, double b1, double b2)
    {
        if (m1 == m2)
        {
            System.out.println("Answer for given m values is either undefined or infinitely many.  Exiting...");
            System.exit(-1);
        }
        return (b2 - b1) / (m1 - m2);
    }
}
