import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class First extends JFrame
implements ActionListener{
	LoginDao dao = new LoginDao();
	private Container contentPane; // 작업 영역
	private JPanel panel; // 각종 컴포넌트가 올라갈 패널
	
	private JLabel title, bar; // 제목
	private JTextField id, pw;
	private JButton join, delete, login, logout;
	
	Connection conn;
	public First(String title, Connection conn) {
		super(title);
		this.conn = conn;
		composeLogin();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 450);
		setLocation(800, 300);
		setVisible(true);
	}

	public void composeLogin() {
		// JLabel
		title = new JLabel("재고 관리 시스템");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		title.setBounds(50,65,295,50);
		
		bar = new JLabel("|");
		bar.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		bar.setBounds(195,350,3,20);
		
		// JTextField
		id = new JTextField();
		pw = new JTextField();
		id.setBounds(50,120,295,45);
		pw.setBounds(50,175,295,45);
		
		// JButton
		join = new JButton("회원가입");
		join.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		join.setBounds(30,220,100,20);
		join.setBorderPainted(false);
		join.setContentAreaFilled(false);
		join.addActionListener(this);
		
		delete = new JButton("회원탈퇴");
		delete.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		delete.setBounds(198,350,100,20);
		delete.setBorderPainted(false);
		delete.setContentAreaFilled(false);
		delete.addActionListener(this);
		
		login = new JButton("로그인");
		login.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		login.setBounds(50,250,295,45);
		login.addActionListener(this);
		
		logout = new JButton("로그아웃");
		logout.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		logout.setBounds(105,350,90,20);
		logout.setBorderPainted(false);
		logout.setContentAreaFilled(false);
		logout.addActionListener(this);
		
		// JPanel
		panel = new JPanel();
		panel.setLayout(null);
		panel.add(title);
		panel.add(bar);
		panel.add(join);
		panel.add(id);
		panel.add(pw);
		panel.add(login);
		panel.add(delete);
		panel.add(logout);
		
		// Container
		contentPane = new Container();
		contentPane = getContentPane();
		contentPane.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == join) {
			Join join = new Join("회원가입", conn); // 회원가입 버튼을 누르면 회원가입 창 팝업
		
		} else if(e.getSource() == login){
			String type = dao.getId(id.getText(), conn);
			if(type.equals("admin")) {
				Admin admin = new Admin("관리자 화면", conn);
				
			} else if(type.equals("user")) {
				User user = new User("유저 화면", conn);
			}
			else {
				JOptionPane.showMessageDialog(null, 
						"존재하지 않는 아이디입니다.",
						"메시지", 
						JOptionPane.WARNING_MESSAGE);
				id.setText("");
				pw.setText("");
				return;
			}
		} else if(e.getSource() == logout){
			// conn.close(); // 미구현
		} else {
			// Delete delete = new Delete(); // 미구현
		}
	}
}
