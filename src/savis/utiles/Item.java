package savis.utiles;

import javax.swing.ImageIcon;

public class Item {

    private String text;
    private ImageIcon icon;
    
    public Item(String text, ImageIcon icon) {
        this.text = text;
        this.icon = icon;
    }
            
    public String getText() {
        return text;
    }

    public ImageIcon getIcon() {
        return icon;
    }
}
