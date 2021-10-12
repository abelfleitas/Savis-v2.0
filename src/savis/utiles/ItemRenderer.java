package savis.utiles;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ItemRenderer extends JLabel implements ListCellRenderer{

    private JLabel jalabel;
    @Override
    public Component getListCellRendererComponent(JList list, Object value,int index, boolean isSelected,boolean cellHasFocus) 
    {
      Item entry = (Item) value;  
      setText(entry.getText());
      setIcon(entry.getIcon());  
      if (isSelected)
      {
         setBackground(list.getSelectionBackground());
         setForeground(list.getSelectionForeground());
      }
      else
      {
         setBackground(list.getBackground());
         setForeground(list.getForeground());
      }
      setEnabled(list.isEnabled());
      setFont(new java.awt.Font("Segoe UI", 1, 16));
      setOpaque(true);
      return this;
   }
    
}
