package root.files.collection;

import java.util.*;

import root.files.commands.Command;
import root.files.commands.CommandManager;
import root.files.console.ConsoleManager;
import root.files.console.DragonManager;
import root.files.file.FileManager;
import root.files.seClasses.Dragon;

public class CollectionManager {

    PriorityQueue<Dragon> dragons = new PriorityQueue<>();
    private String fileName;
    FileManager fm;
    ConsoleManager cm = new ConsoleManager();
    private CommandManager commandManager;
    private DragonManager dragonManager = new DragonManager(cm);
    private java.time.LocalDateTime creationDate = java.time.LocalDateTime.now();

    public void setFileManager(FileManager fm) {
        this.fm = fm;
    }

    public PriorityQueue<Dragon> getDragons() {
        return dragons;
    }

    public void setDragons(PriorityQueue<Dragon> dragons) {
        this.dragons = dragons;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public DragonManager getDragonManager() {
        return dragonManager;
    }


    public void show() {
        if (!dragons.isEmpty()) {
            for (Dragon dragon : dragons) {
                cm.printLine(dragon);
            }
        } else {
            cm.printLine("Коллекция пуста.\n");
        }
    }

    public void save() {
        try {
            fm.saveCSV(dragons);
            cm.printLine("Коллекция сохранена в файл.\n");
        } catch (Exception e) {
            e.printStackTrace();
            cm.printLine("Запись в файл не возможна\n");
        }
    }

    public void add(Dragon dragon) {
        boolean inCollection = false;
        for (Dragon dragonTmp : dragons) {
            if (dragonTmp.equals(dragon)) {
                inCollection = true;
            }
        }
        if (inCollection) {
            cm.printLine("Этот дракон уже есть в коллекции.\n");
        } else {
            dragons.add(dragon);
            cm.printLine("Дракон успешно добавлен.\n");
        }
    }

    public void help(HashMap<String, Command> commands) {
        for (Command command : commands.values()) {
            cm.printLine(command.getDescription());
        }
    }

    public void info() {
        cm.printLine("Тип хранимых данных в коллекции: Dragon\n");
        cm.printLine("Дата и время инициализации: " + creationDate + "\n");
        cm.printLine("Колличество элементов в коллеции: " + dragons.size() + "\n");
    }

    public void updateById(Long dragonId) {
        boolean inCollection = false;
        for (Dragon dragonToRemove : dragons) {
            if (dragonToRemove.getId() == dragonId) {
                dragons.remove(dragonToRemove);
                Dragon dragon = getDragonManager().setDragon();
                dragon.setId(dragonId);
                dragons.add(dragon);
                inCollection = true;
            }
        }
        if (inCollection) {
            cm.printLine("Данные дракона успешно обновлены.\n");
        } else {
            cm.printLine("Дракона с ID " + dragonId + " нет в коллекции.\n");
        }
    }

    public void removeById(Long dragonId) {
        try {
            boolean inCollection = false;
            Iterator<Dragon> iterator = dragons.iterator();

            while (iterator.hasNext()) {
                Dragon dragonToRemove = iterator.next();
                if (dragonToRemove.getId() == dragonId) {
                    iterator.remove();
                    cm.printLine("Дракон с ID " + dragonId + " успешно удален.\n");
                    dragons.remove(dragonToRemove);
                    inCollection = true;
                }
            }

            if (!inCollection) {
                cm.printLine("Дракона с ID " + dragonId + " нет в коллекции.\n");
            }
        } catch (NumberFormatException e) {
            cm.printLine("Ошибка: ID должен быть числом.\n");
        }
    }

    public void exit() {
        System.exit(0);
    }

    public void clear() {
        dragons.clear();
        cm.printLine("Коллекция успешно удалена.\n");
    }

    public void executeScript(String file) {
        commandManager.executeScriptCommand(fm.loadScript(file));
    }

    public void head() {
        if (!dragons.isEmpty()) {
            cm.printLine(dragons.peek());
        } else {
            cm.printLine("Коллекция пуста.\n");
        }
    }

    public void addIfMin(Dragon dragon) {

        boolean inCollection = false;
        for (Dragon dragonTmp : dragons) {
            if (dragonTmp.equals(dragon)) {
                inCollection = true;
            }
        }
        if (inCollection) {
            cm.printLine("Этот дракон уже есть в коллекции.\n");
        } else {
            if (dragon.getCoordinates().getX() < dragons.peek().getCoordinates().getX()) {
                dragons.add(dragon);
                cm.printLine("Дракон успешно добавлен.\n");
            } else {
                cm.printLine("Данный дракон не имеет минимального значения.\n");
            }
        }

    }

    public void removeLower(Dragon dragon) {

        for (Dragon dragonTmp : dragons) {
            boolean flag = false;
            List<Dragon> toRemove = new ArrayList<>();
            for (Dragon dragonToRemove : dragons) {
                if (dragonToRemove.getCoordinates().getX() < dragon.getCoordinates().getX()) {
                    toRemove.add(dragonToRemove);
                    flag = true;
                }
            }
            if (flag){
                dragons.removeAll(toRemove);
                cm.printLine("Драконы, меньшие чем заданный, удалены.\n");
            } else {
                cm.printLine("Драконов меньше, чем заданный, нет в коллекции\n");
            }

        }
    }

    public void sumOfAge(){
        Long sAge = 0L;
        if (!dragons.isEmpty()){
            for (Dragon dragon : dragons){
                if (dragon.getAge() != null){
                    sAge += dragon.getAge();
                }
            }
            if (sAge == 0L){
                cm.printLine("Нет данных о возрасте драконов.\n");
            } else {
                cm.printLine("Суммарный возраст всех драконов: " + sAge);
            }
        } else {
            cm.printLine("В коллекции нет драконов.\n");
        }
    }

    public void filterContainsName(String name){
        boolean flag = false;
        for (Dragon dragon : dragons){
            if (dragon.getName().contains(name)){
                cm.printLine(dragon);
                flag = true;
            }
        }
        if (!flag){
            cm.printLine("Поиск не дал результатов.\n");
        }
    }

    public void filterStartsWithName(String name){
        boolean flag = false;
        int len = name.length();
        for (Dragon dragon : dragons){
            if (dragon.getName().length() >= len){
                if (dragon.getName().substring(0, len).equals(name)){
                    cm.printLine(dragon);
                    flag = true;
                }
            }
        }
        if (!flag){
            cm.printLine("Поиск не дал результатов.\n");
        }
    }
}
