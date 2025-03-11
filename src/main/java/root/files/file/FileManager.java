package root.files.file;

import root.files.seClasses.*;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class FileManager {
    private final String fileName;
    Parser parser = new Parser();

    public FileManager(String fileName){
        this.fileName = fileName;
    }

    public void saveCSV(PriorityQueue<Dragon> dragons) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(this.fileName))) {
             System.out.println("Сохраняем " + dragons.size() + " драконов.");
             for (Dragon dragon : dragons) {
                 writer.write(parser.parseDragonToLine(dragon));
            }
        } catch (IOException e) {
            System.out.println("Невозможно записать в файл: " + e.getMessage());
        }
    }

    public PriorityQueue<Dragon> loadCSV(){
        File file = new File(this.fileName);
        PriorityQueue<Dragon> dragons = new PriorityQueue<Dragon>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                dragons.add(parser.parseLineToDragon(line));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Загружена коллекция из " + dragons.size() + " драконов.");
        return dragons;
    }
}
