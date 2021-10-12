
package savis.main;
/**
 *
 * @author Abel Fleitas Perdomo 5to II
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.EventQueue;
import org.jvnet.substance.SubstanceLookAndFeel;
import savis.vistas.Principal;



public class Main {
   
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } 
        catch (ClassNotFoundException ex) 
        {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) 
        {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) 
        {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex) 
        {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.BusinessBlackSteelSkin");//BusinessBlackSteelSkin
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Principal p = new Principal();
                p.setTitle("SAVIS 2.0");
                p.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/logo_1.png")));
                p.setPreferredSize(new Dimension(1250,800));
                p.setLocationRelativeTo(null);
                p.setVisible(true);
            }
        });        
    }
}
