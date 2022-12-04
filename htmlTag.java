package webFileScrapper;

import javax.swing.text.html.HTML.Attribute;
import javax.swing.text.html.HTML.Tag;

public class htmlTag {
    
    public Tag tag;
    public Attribute attribute;

    public htmlTag(Tag tag, Attribute attribute) {
        this.tag = tag;
        this.attribute = attribute;
    }
}
