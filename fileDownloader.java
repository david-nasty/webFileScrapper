package webFileScrapper;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

public class fileDownloader {
	
	public void download(int fileIndex) throws Exception {
        ArrayList<String> fileList = webFileScrapper.main.getFileList();
        String fileSrc = fileList.get(fileIndex);
        try {
            URL url = new URL(fileSrc);
            String fileName = fileSrc.substring(fileSrc.lastIndexOf('/') + 1, fileSrc.length());
            Path targetPath = new File(webFileScrapper.main.getFileDirectory() + File.separator + fileName).toPath();
            Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Pobrano plik: " + fileSrc);
            if ((fileIndex + 1) < fileList.size()) {
                download(fileIndex + 1);
            } else {
                
                System.out.println("Skonczono pobieranie plikow; pliki zostaly zpisane w " + webFileScrapper.main.getFileDirectory());
                System.out.println("Pobrano " + fileList.size() + " plikow");
                //System.out.println("Podaj link i typ pliku, z którego chcesz pobrac dane przyklad: (scrap https://www.imdb.com/ jpg)");
                //System.out.println("Lub wpisz exit zeby wyjsc z programu.");
                //@SuppressWarnings("resource")
                //Scanner scanner = new Scanner(System.in);
                //String scannerString = scanner.nextLine();
                //String[] scannerArgs = scannerString.split("\\s+");
                //webFileScrapper.commandHandler.process(scannerArgs);
                System.exit(0);
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
	}
}
