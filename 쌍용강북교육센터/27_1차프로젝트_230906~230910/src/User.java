import java.sql.Connection;

import javax.swing.JFrame;

public class User extends JFrame{

	public User(String title, Connection conn) {
		super(title);
		//this.conn = conn;
		composeUser();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 350);
		setLocation(1000, 500);
		setVisible(true);
	}

	private void composeUser() {
		
	}

}
