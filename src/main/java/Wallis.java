import java.util.Scanner;

/**
 * The Wallis chatbot application.
 * Handles user inputs for managing a task list.
 */
public class Wallis {
    public static void main(String[] args) {
        String botName = "Wallis";
        
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm " + botName);
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
        
        // We now use an array of Task objects instead of Strings
        Task[] tasks = new Task[100];
        int taskCount = 0;
        
        Scanner scanner = new Scanner(System.in);
        String userInput;
        
        while (true) {
            userInput = scanner.nextLine();
            
            if (userInput.equals("bye")) {
                break;
            } else if (userInput.equals("list")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i].toString());
                }
                System.out.println("____________________________________________________________");
            } else if (userInput.startsWith("mark ")) {
                // Extract the number from the command (e.g., "mark 2" -> index 1)
                int index = Integer.parseInt(userInput.substring(5)) - 1;
                tasks[index].markAsDone();
                
                System.out.println("____________________________________________________________");
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[index].toString());
                System.out.println("____________________________________________________________");
            } else if (userInput.startsWith("unmark ")) {
                // Extract the number from the command (e.g., "unmark 2" -> index 1)
                int index = Integer.parseInt(userInput.substring(7)) - 1;
                tasks[index].unmarkAsDone();
                
                System.out.println("____________________________________________________________");
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[index].toString());
                System.out.println("____________________________________________________________");
            } else {
                tasks[taskCount] = new Task(userInput);
                taskCount++;
                
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + userInput);
                System.out.println("____________________________________________________________");
            }
        }
        
        System.out.println("____________________________________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
        
        scanner.close();
    }
}