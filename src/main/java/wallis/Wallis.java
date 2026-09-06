package wallis;
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
            }

            try {
                if (userInput.equals("list")) {
                    printList();
                } else if (userInput.startsWith("mark")) {
                    markTask(userInput, true);
                } else if (userInput.startsWith("unmark")) {
                    markTask(userInput, false);
                } else if (userInput.startsWith("todo")) {
                    addTodo(userInput);
                } else if (userInput.startsWith("deadline")) {
                    addDeadline(userInput);
                } else if (userInput.startsWith("event")) {
                    addEvent(userInput);
                } else {
                    throw new WallisException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (WallisException e) {
                printLine();
                System.out.println(" " + e.getMessage());
                printLine();
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

    private static void markTask(String input, boolean isDone) throws WallisException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new WallisException("OOPS!!! Please provide a task number.");
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= taskCount) {
                throw new WallisException("OOPS!!! That task number does not exist.");
            }
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
        } catch (NumberFormatException e) {
            throw new WallisException("OOPS!!! The task number must be a valid integer.");
        }
    }

    private static void addTodo(String input) throws WallisException {
        if (input.trim().equals("todo")) {
            throw new WallisException("OOPS!!! The description of a todo cannot be empty.");
        }
        String description = input.substring(5).trim();
        tasks[taskCount] = new Todo(description);
        confirmAddition();
    }

    private static void addDeadline(String input) throws WallisException {
        if (input.trim().equals("deadline")) {
            throw new WallisException("OOPS!!! The description of a deadline cannot be empty.");
        }
        if (!input.contains(" /by ")) {
            throw new WallisException("OOPS!!! A deadline must contain a '/by' date.");
        }
        String content = input.substring(9).trim();
        String[] parts = content.split(" /by ");
        tasks[taskCount] = new Deadline(parts[0], parts[1]);
        confirmAddition();
    }

    private static void addEvent(String input) throws WallisException {
        if (input.trim().equals("event")) {
            throw new WallisException("OOPS!!! The description of an event cannot be empty.");
        }
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            throw new WallisException("OOPS!!! An event must contain '/from' and '/to' times.");
        }
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