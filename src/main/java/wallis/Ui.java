package wallis;

import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        showLine();
        System.out.println(" Hello! I'm Wallis");
        System.out.println(" What can I do for you?");
        showLine();
    }

    public void showGoodbye() {
        showLine();
        System.out.println(" Bye. Hope to see you again soon!");
        showLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }
    
    public void showError(String message) {
        System.out.println(" " + message);
    }

    public void showLoadingError() {
        System.out.println(" Error loading tasks from file.");
    }
}