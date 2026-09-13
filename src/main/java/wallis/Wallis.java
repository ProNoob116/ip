package wallis;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Wallis chatbot application.
 * Handles user inputs for managing different types of tasks.
 */
public class Wallis {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static final String FILE_PATH = "./data/wallis.txt";

    public static void main(String[] args) {
        loadTasks(); // Load data from hard drive on startup

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
                } else if (userInput.startsWith("delete")) {
                    deleteTask(userInput);
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

    private static void loadTasks() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.getParentFile().mkdirs(); // Create the /data/ folder if missing
                file.createNewFile();
                return;
            }
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                if (line.length() < 7) continue;
                String type = line.substring(1, 2);
                boolean isDone = line.substring(4, 5).equals("X");
                String rest = line.substring(7);

                if (type.equals("T")) {
                    Todo t = new Todo(rest);
                    if (isDone) t.markAsDone();
                    tasks.add(t);
                } else if (type.equals("D")) {
                    int byIndex = rest.lastIndexOf(" (by: ");
                    String desc = rest.substring(0, byIndex);
                    String by = rest.substring(byIndex + 6, rest.length() - 1);
                    Deadline d = new Deadline(desc, by);
                    if (isDone) d.markAsDone();
                    tasks.add(d);
                } else if (type.equals("E")) {
                    int fromIndex = rest.lastIndexOf(" (from: ");
                    int toIndex = rest.lastIndexOf(" to: ");
                    String desc = rest.substring(0, fromIndex);
                    String from = rest.substring(fromIndex + 8, toIndex);
                    String to = rest.substring(toIndex + 5, rest.length() - 1);
                    Event e = new Event(desc, from, to);
                    if (isDone) e.markAsDone();
                    tasks.add(e);
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            System.out.println("Error loading tasks from file.");
        }
    }

    private static void saveTasks() {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            for (int i = 0; i < tasks.size(); i++) {
                fw.write(tasks.get(i).toString() + "\n"); // Save in standard string format
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }
    }

    private static void printLine() {
        System.out.println("____________________________________________________________");
    }

    private static void printList() {
        printLine();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i).toString());
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
            if (index < 0 || index >= tasks.size()) {
                throw new WallisException("OOPS!!! That task number does not exist.");
            }
            if (isDone) {
                tasks.get(index).markAsDone();
                printLine();
                System.out.println(" Nice! I've marked this task as done:");
            } else {
                tasks.get(index).unmarkAsDone();
                printLine();
                System.out.println(" OK, I've marked this task as not done yet:");
            }
            System.out.println("   " + tasks.get(index).toString());
            printLine();
            saveTasks(); // Save change to hard drive
        } catch (NumberFormatException e) {
            throw new WallisException("OOPS!!! The task number must be a valid integer.");
        }
    }

    private static void deleteTask(String input) throws WallisException {
        String[] parts = input.split(" ");
        if (parts.length < 2) {
            throw new WallisException("OOPS!!! Please provide a task number to delete.");
        }
        try {
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new WallisException("OOPS!!! That task number does not exist.");
            }
            Task removedTask = tasks.remove(index);
            printLine();
            System.out.println(" Noted. I've removed this task:");
            System.out.println("   " + removedTask.toString());
            System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
            printLine();
            saveTasks(); // Save change to hard drive
        } catch (NumberFormatException e) {
            throw new WallisException("OOPS!!! The task number must be a valid integer.");
        }
    }

    private static void addTodo(String input) throws WallisException {
        if (input.trim().equals("todo")) {
            throw new WallisException("OOPS!!! The description of a todo cannot be empty.");
        }
        String description = input.substring(5).trim();
        tasks.add(new Todo(description));
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
        tasks.add(new Deadline(parts[0], parts[1]));
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
        tasks.add(new Event(parts[0], times[0], times[1]));
        confirmAddition();
    }

    private static void confirmAddition() {
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + tasks.get(tasks.size() - 1).toString());
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        printLine();
        saveTasks(); // Save change to hard drive
    }
}