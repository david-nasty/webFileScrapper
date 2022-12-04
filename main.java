package webFileScrapper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

    private static String address = "https://www.imdb.com/",
                          fileType = "png",
                          baseFileDirectory = "C://Users/" + System.getProperty("user.name") + "/Documents/webFileScrapper/",
                          fileDirectory = "";
    private static ArrayList<String> fileList = new ArrayList<String>();
    
    public static ArrayList<String> getFileList() {
        return fileList;
    }

    public static String getBaseFileDirectory() {
        return baseFileDirectory;
    }

    public static void setBaseFileDirectory(String baseFileDirectory) {
        webFileScrapper.main.baseFileDirectory = baseFileDirectory;
    }

    public static void setFileList(ArrayList<String> fileList) {
        webFileScrapper.main.fileList = fileList;
    }

    public static void clearFileList() {
        webFileScrapper.main.fileList.clear();
        webFileScrapper.main.fileList.removeAll(webFileScrapper.main.fileList);
    }

    public static String getFileDirectory() {
        return fileDirectory;
    }

    public static void setFileDirectory(String fileDirectory) {
        webFileScrapper.main.fileDirectory = fileDirectory;
    }

    public static String getFileType() {
        return fileType;
    }

    public static void setFileType(String fileType) {
        webFileScrapper.main.fileType = fileType;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        webFileScrapper.main.address = address;
    }

    public static void startScrapper(String[] scannerArgs) throws Exception {
        htmlReader htmlReader = new htmlReader();
        fileDownloader fileDownloader = new fileDownloader();
        
        htmlReader.populateTagList();

        ArrayList<String> newFileList = new ArrayList<String>();
        webFileScrapper.main.clearFileList();

        newFileList = htmlReader.read();
        webFileScrapper.main.setFileList(newFileList);
        
        if ((newFileList.size() > 0)) {
        	String domainName = htmlReader.getDomainName();
        	String newFileDirectory = webFileScrapper.main.getBaseFileDirectory() + domainName + "/" +  webFileScrapper.main.getFileType();
        	Files.createDirectories(Paths.get(newFileDirectory));
        	webFileScrapper.main.setFileDirectory(newFileDirectory);
        	fileDownloader.download(0);
        } else {
            System.out.println("Nie znalazłem tego typu pliku na stronie!");
            System.out.println("Podaj link i typ pliku, z którego chcesz pobrac dane przyklad: (scrap https://www.imdb.com/ jpg)");
            System.out.println("Lub wpisz exit zeby wyjsc z programu.");
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(System.in);
            String scannerString = scanner.nextLine();
            scannerArgs = scannerString.split("\\s+");
            webFileScrapper.commandHandler.process(scannerArgs);
        }
    }

    public static void main(String[] args) throws Exception {
        webFileScrapper.commandHandler.populateCommandList();
    	Files.createDirectories(Paths.get(fileDirectory));
        System.out.println("Podaj link i typ pliku, z którego chcesz pobrac dane przyklad: (scrap https://www.imdb.com/ jpg)");
        System.out.println("Lub wpisz exit zeby wyjsc z programu.");
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        String scannerString = scanner.nextLine();
        String[] scannerArgs = scannerString.split("\\s+");
        webFileScrapper.commandHandler.process(scannerArgs);
    }
}