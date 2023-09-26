import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Join extends JFrame 
implements ActionListener, ItemListener {
	JoinDao dao = new JoinDao();
	private Container contentPane; // 작업 영역
	private JPanel panel; // 각종 컴포넌트가 올라갈 패널

	// 각종 컴포넌트
	private JLabel title, idLb, pwLb;
	private JTextField idTf, pwTf;
	private JButton register;
	private JRadioButton user, administrator;
	private ButtonGroup group;
	private String type;
	Connection conn;

	public Join(String title, Connection conn) {
		super(title);
		this.conn = conn;
		composeJoin();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 350);
		setLocation(1000, 500);
		setVisible(true);

	}

	public void composeJoin() {

		// JLabel
		title = new JLabel("회원가입");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		title.setBounds(50, 45, 295, 50);
		idLb = new JLabel("아이디");
		idLb.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		idLb.setBounds(50, 100, 90, 30);
		pwLb = new JLabel("패스워드");
		pwLb.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		pwLb.setBounds(50, 139, 90, 30);

		// JTextField
		idTf = new JTextField();
		idTf.setBounds(120, 100, 210, 33);
		idTf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (idTf.getText().length() > 9)
					e.consume();
			}
		});
		pwTf = new JTextField();
		pwTf.setBounds(120, 139, 210, 33);
		pwTf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (pwTf.getText().length() > 14)
					e.consume();
			}
		});

		// JButton
		register = new JButton("등록");
		register.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		register.setBounds(50, 205, 280, 45);
		register.addActionListener(this);
		
		// JRadioButton
		group = new ButtonGroup();
		user = new JRadioButton("일반 회원");
		user.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		user.setBounds(50, 173, 100, 30);
		group.add(user);
		user.addItemListener(this);
		
		administrator = new JRadioButton("관리자", true);
		administrator.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		administrator.setBounds(150, 173, 90, 30);
		group.add(administrator);
		administrator.addItemListener(this);
		
		// JPanel
		panel = new JPanel();
		panel.setLayout(null);
		panel.add(title);
		panel.add(idLb);
		panel.add(pwLb);
		panel.add(idTf);
		panel.add(pwTf);
		panel.add(register);
		panel.add(user);
		panel.add(administrator);
		
		// Container
		contentPane = new Container();
		contentPane = getContentPane();
		contentPane.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (checkData()) {
			insertData();
		}
	}

	public void insertData() {
		int cnt = 0;
		cnt = dao.InsertData(idTf.getText(), pwTf.getText(), type, conn);
		if(cnt == 1) {
			JOptionPane.showMessageDialog(null, 
					"회원가입이 완료되었습니다.",
					"메시지", 
					JOptionPane.INFORMATION_MESSAGE);
			idTf.setText("");
			pwTf.setText("");
			user.setSelected(true);
		} else {
			JOptionPane.showMessageDialog(null, 
					"중복된 아이디입니다.",
					"메시지", 
					JOptionPane.WARNING_MESSAGE);
			idTf.setText("");
			pwTf.setText("");
				
		}
	}

	public boolean checkData() {
		UIManager.put("OptionPane.messageFont", new Font("맑은 고딕", Font.PLAIN, 13));
		if (idTf.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "아이디가 누락되었습니다.", "에러발생", JOptionPane.CANCEL_OPTION);
			idTf.setText("");
			idTf.requestFocus();
			return false;
		}

		if (pwTf.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "패스워드가 누락되었습니다.", "에러발생", JOptionPane.CANCEL_OPTION);
			pwTf.setText("");
			pwTf.requestFocus();
			return false;
		}
		return true;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(administrator.isSelected()) {
			type = "admin";
		} else {
			type = "user";
		}
	}

}
