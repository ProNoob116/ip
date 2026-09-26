package wallis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws WallisException {
        ArrayList<Task> loadedTasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs(); 
                file.createNewFile();
                return loadedTasks;
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
                    loadedTasks.add(t);
                } else if (type.equals("D")) {
                    int byIndex = rest.lastIndexOf(" (by: ");
                    String desc = rest.substring(0, byIndex);
                    String by = rest.substring(byIndex + 6, rest.length() - 1);
                    Deadline d = new Deadline(desc, by);
                    if (isDone) d.markAsDone();
                    loadedTasks.add(d);
                } else if (type.equals("E")) {
                    int fromIndex = rest.lastIndexOf(" (from: ");
                    int toIndex = rest.lastIndexOf(" to: ");
                    String desc = rest.substring(0, fromIndex);
                    String from = rest.substring(fromIndex + 8, toIndex);
                    String to = rest.substring(toIndex + 5, rest.length() - 1);
                    Event e = new Event(desc, from, to);
                    if (isDone) e.markAsDone();
                    loadedTasks.add(e);
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            throw new WallisException("Error loading tasks from file.");
        }
        return loadedTasks;
    }

    public void save(ArrayList<Task> tasks) throws WallisException {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (int i = 0; i < tasks.size(); i++) {
                fw.write(tasks.get(i).toString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new WallisException("Error saving tasks to file.");
        }
    }
}