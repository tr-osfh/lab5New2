package root.files.commands;

import root.files.collection.CollectionManager;

public class FilterContainsNameCommand implements Command{
    private CollectionManager manager;

    public FilterContainsNameCommand(CollectionManager manager){
        this.manager = manager;
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 2) {
            String namePart = args[1];
            manager.filterContainsName(namePart);
        } else {
            throw new IllegalArgumentException("Неверное количество аргументов. Используйте: remove_by_id <id>");
        }
    }

    @Override
    public String getDescription() {
        return "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку";
    }
}
