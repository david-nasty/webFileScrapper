package webFileScrapper;

import java.util.ArrayList;
import java.util.Scanner;

public class commandHandler {

    private static ArrayList<String> commandList = new ArrayList<String>();

    public static void populateCommandList() {
        commandList.add("exit");
        commandList.add("scrap");
    }

    public static void process(String[] args) throws Exception {
        if (commandList.indexOf(args[0]) != -1) {
            switch (args[0]) {
                case "exit":
                    System.exit(0);
                    break;
                case "scrap":
                    if ((!args[1].isEmpty()) && (!args[2].isEmpty())) {
                        webFileScrapper.main.setAddress(args[1]);
                        webFileScrapper.main.setFileType(args[2]);
                        webFileScrapper.main.startScrapper(args);
                    }
                    break;
            }
        } else {
            System.out.println("Niepoprawna komenda!");
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);
            String scannerString = scanner.nextLine();
            String[] scannerArgs = scannerString.split("\\s+");
            process(scannerArgs);
        }
    }

}
