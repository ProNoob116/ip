import java.util.Scanner;

/**
 * The Wallis chatbot application.
 * Handles user inputs for managing different types of tasks.
 */

public class Wallis {
    private static Task[] tasks = new Task[100];
    private static int taskCount = 0;

    public static void main(String[] args) {
        printLine();
        System.out.println(" Hello! I'm Wallis");
        System.out.println(" What can I do for you?");
        printLine();

        Scanner scanner = new Scanner(System.in);
        String userInput;

        while (true) {
            userInput = scanner.nextLine().trim();

            if (userInput.equals("bye")) {
                break;
            } else if (userInput.equals("list")) {
                printList();
            } else if (userInput.startsWith("mark ")) {
                markTask(userInput, true);
            } else if (userInput.startsWith("unmark ")) {
                markTask(userInput, false);
            } else if (userInput.startsWith("todo ")) {
                addTodo(userInput);
            } else if (userInput.startsWith("deadline ")) {
                addDeadline(userInput);
            } else if (userInput.startsWith("event ")) {
                addEvent(userInput);
            } else {
                System.out.println(" I don't understand that command yet!");
            }
        }

        printLine();
        System.out.println(" Bye. Hope to see you again soon!");
        printLine();
        scanner.close();
    }

    private static void printLine() {
        System.out.println("____________________________________________________________");
    }

    private static void printList() {
        printLine();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println(" " + (i + 1) + "." + tasks[i].toString());
        }
        printLine();
    }

    private static void markTask(String input, boolean isDone) {
        int index = Integer.parseInt(input.split(" ")[1]) - 1;
        if (isDone) {
            tasks[index].markAsDone();
            printLine();
            System.out.println(" Nice! I've marked this task as done:");
        } else {
            tasks[index].unmarkAsDone();
            printLine();
            System.out.println(" OK, I've marked this task as not done yet:");
        }
        System.out.println("   " + tasks[index].toString());
        printLine();
    }

    private static void addTodo(String input) {
        String description = input.substring(5).trim();
        tasks[taskCount] = new Todo(description);
        confirmAddition();
    }

    private static void addDeadline(String input) {
        String content = input.substring(9).trim();
        String[] parts = content.split(" /by ");
        tasks[taskCount] = new Deadline(parts[0], parts[1]);
        confirmAddition();
    }

    private static void addEvent(String input) {
        String content = input.substring(6).trim();
        String[] parts = content.split(" /from ");
        String[] times = parts[1].split(" /to ");
        tasks[taskCount] = new Event(parts[0], times[0], times[1]);
        confirmAddition();
    }

    private static void confirmAddition() {
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks[taskCount].toString());
        taskCount++;
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        printLine();
    }
}