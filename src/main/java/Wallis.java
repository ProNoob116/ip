import java.util.Scanner;

public class Wallis {
    public static void main(String[] args) {
        String botName = "Wallis";
        
        // Greet
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm " + botName);
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
        
        // Echo loop
        Scanner scanner = new Scanner(System.in);
        String userInput;
        
        while (true) {
            userInput = scanner.nextLine();
            
            if (userInput.equals("bye")) {
                break;
            }
            
            System.out.println("____________________________________________________________");
            System.out.println(" " + userInput);
            System.out.println("____________________________________________________________");
        }
        
        // Exit
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
        
        scanner.close();
    }
}