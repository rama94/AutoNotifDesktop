package autonotif;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class NotifFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected NotifFrame(String message) {
		super("Auto Notification");
		String notification, number = message.split("\\|")[1];
		
		switch(message.split("\\|")[0]) {
			case "call":
				notification = "You get a call from "+number+"!!"; break;
			case "sms":
				notification = "You get a message from "+number+"!!"; break;
			default : notification = ""; break;
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(325,150);
		//setLayout( new FlowLayout(1,1,3) );
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel(notification), gbc);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dimension.width/2-getSize().width/2, dimension.height/2-getSize().height/2);
		setAlwaysOnTop(false);
		setAlwaysOnTop(true);
		setVisible(true);
	}
}
