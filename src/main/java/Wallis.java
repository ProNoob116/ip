import java.util.Scanner;

public class Wallis {
    public static void main(String[] args) {
        String botName = "Wallis";
        
        // Greet
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm " + botName);
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
        
        // Task memory
        String[] tasks = new String[100];
        int taskCount = 0;
        
        Scanner scanner = new Scanner(System.in);
        String userInput;
        
        while (true) {
            userInput = scanner.nextLine();
            
            if (userInput.equals("bye")) {
                break;
            } else if (userInput.equals("list")) {
                System.out.println("____________________________________________________________");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            } else {
                tasks[taskCount] = userInput;
                taskCount++;
                
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + userInput);
                System.out.println("____________________________________________________________");
            }
        }
        
        // Exit
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
        
        scanner.close();
    }
}