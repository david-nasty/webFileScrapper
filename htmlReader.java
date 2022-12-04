package webFileScrapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.ChangedCharSetException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class htmlReader {

    private static ArrayList<htmlTag> htmlTagList = new ArrayList<htmlTag>();

    public void populateTagList() {
        htmlTagList.add(new htmlTag(HTML.Tag.A, HTML.Attribute.HREF));
        htmlTagList.add(new htmlTag(HTML.Tag.IMG, HTML.Attribute.SRC));
        //htmlTagList.add(new htmlTag(HTML.Tag.SCRIPT, HTML.Attribute.SRC));
    }

    public String getDomainName() throws URISyntaxException {
        URI uri = new URI(webFileScrapper.main.getAddress());
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public ArrayList<String> read() throws Exception, ChangedCharSetException, BadLocationException {
		ArrayList<String> fileList = new ArrayList<String>();
        Set<String> fileSet = new HashSet<>(fileList);
        fileList.clear();
        fileList.removeAll(fileList);
        fileList.addAll(fileSet);
        URL url = new URL(webFileScrapper.main.getAddress());
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        HTMLEditorKit htmlKit = new HTMLEditorKit();
        HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
        htmlDoc.putProperty("IgnoreCharsetDirective", true);
        htmlKit.read(br, htmlDoc, 0);
        for (int i = 0; i < htmlTagList.size(); i++) {
            for (HTMLDocument.Iterator iterator = htmlDoc.getIterator(htmlTagList.get(i).tag); iterator.isValid(); iterator.next()) {
                AttributeSet attributes = iterator.getAttributes();
                String fileSrc = (String) attributes.getAttribute(htmlTagList.get(i).attribute); 
                if (fileSrc != null && (fileSrc.toLowerCase().endsWith(webFileScrapper.main.getFileType()))) {
                    if (!(fileSrc.startsWith("https:")) && !(fileSrc.startsWith("http:")) && (webFileScrapper.main.getAddress().startsWith("https:"))) {
                        fileSrc = "https:" + fileSrc;
                    } else if (!(fileSrc.startsWith("https:")) && !(fileSrc.startsWith("http:")) && (webFileScrapper.main.getAddress().startsWith("http:"))) {
                        fileSrc = "http:" + fileSrc;
                    }
                    fileList.add(fileSrc);
                    System.out.println("Znaleziono plik: " + fileSrc);
                }
            }
        }
        System.out.println("Znaleziono " + fileList.size() + " plikow typu: " + webFileScrapper.main.getFileType());
        return fileList;
	}
}
