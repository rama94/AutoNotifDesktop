package autonotif;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class TrayTask {
	
	private static TrayIcon trayIcon;
	
	public TrayTask() {
		show();
	}
	
	private void show() {
		if (!SystemTray.isSupported()) System.exit(0);
		trayIcon = new TrayIcon(createIcon("/autonotif/ServerListening.png","Icon"));
		trayIcon.setToolTip("AutoNotif Desktop v1.0");
		final SystemTray tray = SystemTray.getSystemTray();
		
		final PopupMenu menu = new PopupMenu(); 
		
		MenuItem about = new MenuItem("About");
		MenuItem exit = new MenuItem("Exit");
		menu.add(about);
		menu.addSeparator();
		menu.add(exit);
		
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(null, "null", "AutoNotif Desktop v1.0 \nAuthor by : \nPatu Rama", 0);
				JOptionPane.showMessageDialog(null, "AutoNotif Desktop v1.0 \n\nAuthor by : \nPatu Rama\n");
			}
		});
		
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		trayIcon.setPopupMenu(menu);
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			
			e.printStackTrace();
		}
	}
	
	private static Image createIcon(String path, String desc) {
		URL imageURL = MainAutoNotif.class.getResource(path);
		return (new ImageIcon(imageURL, desc)).getImage();
	}


}
