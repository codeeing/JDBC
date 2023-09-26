import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Admin extends JFrame implements ActionListener, ItemListener {
	private Container contentPane = new Container();
	private JPanel panel = new JPanel();
	private JScrollPane scrollPane;
//	private JScrollPane[] scrollPanes;
	private JTable table;
//	private JTable[] tables;

	private Object[][] rowData;
	private String[] columnNames = { "ID", "브랜드명", "상품명", "단가", "재고 수량", "누적 판매량", "카테고리", "퍼스널 컬러", "출고일" };
	private String[] btnName = { "상품추가", "상품수정", "상품삭제" };
	private String[] tabName = { "브랜드별", "카테고리별", "퍼스널 컬러별" };
	private String[] brands = { "롬앤", "페리페라", "홀리카홀리카", "에뛰드하우스", "데이지크" };
	private String[] categories = { "립", "블러셔", "아이섀도우" };
	private String[] personalColors = { "봄 웜", "여름 쿨", "가을 웜", "겨울 쿨" };
	// private String[][] filters = {brands, categories, personalColors};

	private JLabel[] lbs;
	private JButton[] btns;
	private JTextField[] tfs;
	private JTabbedPane[] tabs;
	// private JComboBox<String> br, ct, ps;
	private JButton clear;
	private JLabel imLb;
	private ImageIcon image;
	private JTabbedPane tab;
	Connection conn;
	AdminDao dao;
	ArrayList<Bean> lists = null;
	JComboBox<String> cmb = null;

	public Admin(String title, Connection conn) {
		super(title);
		this.conn = conn;
		dao = new AdminDao(this.conn);
		composeAdmin();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1500, 600);
		setLocation(300, 200);
		setVisible(true);

	}

	public void composeAdmin() {
		// JTable & JScrollPane
		lists = dao.getAllProduct(); // 모든 레코드 조회해서 lists에 저장
		rowData = new Object[lists.size()][columnNames.length];
		fillData();
		table = new JTable(rowData, columnNames);
		table.getColumn("상품명").setPreferredWidth(250);
		table.setBackground(Color.pink);
		table.addMouseListener(new MouseHandler());

		scrollPane = new JScrollPane(table);
		scrollPane.setBackground(Color.yellow);
		scrollPane.setBounds(10, 30, 1000, 500);
//		scrollPanes = new JScrollPane[4];
//		tables = new JTable[4];
//		lists = dao.getProductByBrand();
//		tables[0] = new JTable(rowData, columnNames);
//		scrollPanes[0] = new JScrollPane(tables[0]);

		// JPanel
		panel.setLayout(null);
		panel.setBounds(1020, 30, 450, 500);

		// Container
		contentPane = getContentPane();
//		contentPane.setBackground(Color.cyan);
		contentPane.setLayout(null);
		contentPane.add(panel);
		contentPane.add(scrollPane);

		// JLabel
		lbs = new JLabel[9];
		for (int i = 0; i < lbs.length; ++i) {
			lbs[i] = new JLabel(columnNames[i]); // 라벨 객체 생성
			lbs[i].setBounds(150 * (i / 3), 250 + 75 * (i % 3), 80, 30); // 라벨 위치 조정
			lbs[i].setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			panel.add(lbs[i]);
		}

		image = new ImageIcon("images/no image.png");
		imLb = new JLabel();
		imLb.setIcon(image);
		imLb.setBounds(140, 10, 270, 216);
		panel.add(imLb);

		// JTextField
		tfs = new JTextField[9];
		for (int i = 0; i < tfs.length; ++i) {
			tfs[i] = new JTextField(); // 텍필 객체 생성
			if (i == 0) {
				tfs[i].setText("");
				tfs[i].setEnabled(false); // ID 텍스트필드 비활성화 작업
			}
			tfs[i].setBounds(150 * (i / 3), 280 + 75 * (i % 3), 145, 25); // 텍필 위치 조정
			tfs[i].setFont(new Font("맑은 고딕", Font.PLAIN, 13)); // 폰트 조정
			panel.add(tfs[i]); // 패널에 올리기
		}

		// JButton
		btns = new JButton[3];
		for (int i = 0; i < btnName.length; ++i) {
			btns[i] = new JButton(btnName[i]);
			btns[i].setBounds(10, 10 + 83 * (i % 3), 100, 50); // 텍필 위치 조정
			btns[i].setFont(new Font("맑은 고딕", Font.PLAIN, 13)); // 폰트 조정
			btns[i].addActionListener(this);
			panel.add(btns[i]); // 패널에 올리기
		}

		clear = new JButton("Clear!");
		clear.setBounds(0, 465, 140, 35);

		clear.setFont(new Font("맑은 고딕", Font.PLAIN, 13)); // 폰트 조정
		clear.addActionListener(this);
		panel.add(clear); // 패널에 올리기

		// JTabbedPane
		tabs = new JTabbedPane[3];
		for (int i = 0; i < tabs.length; ++i) {
			tabs[i] = new JTabbedPane();
			tabs[i].setFont(new Font("맑은 고딕", Font.PLAIN, 13));
			tabs[i].addTab(tabName[i], scrollPane);
//			tabs[i].addTab(tabName[i], scrollPanes[i]); // 최종목표
			tabs[i].addMouseListener(new MouseHandlerTab());
			contentPane.add(tabs[i]);
		}
		tabs[0].setBounds(60, 6, 80, 30);
		tabs[1].setBounds(136, 6, 100, 30);
		tabs[2].setBounds(225, 6, 120, 30);

		tab = new JTabbedPane();
		tab.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		tab.addTab("전체", new JLabel("전체"));
		tab.addMouseListener(new MouseHandlerTab());
		tab.setBounds(10, 6, 80, 30);
		contentPane.add(tab);

		// JComboBox
		cmb = new JComboBox<String>();
		cmb.setBounds(890, 3, 120, 25);
		cmb.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		cmb.addItemListener(this);
		contentPane.add(cmb);
	}

	public void fillData() {
		Object[] arr = lists.toArray(); // 리스트를 배열로
		int j = 0;
		for (int i = 0; i < arr.length; ++i) {
			Bean bean = (Bean) arr[i];
			rowData[i][j++] = bean.getId();
			rowData[i][j++] = bean.getBrand();
			rowData[i][j++] = bean.getName();
			rowData[i][j++] = bean.getPrice();
			rowData[i][j++] = bean.getStock();
			rowData[i][j++] = bean.getHit();
			rowData[i][j++] = bean.getCategory();
			rowData[i][j++] = bean.getPersonalColor();
			rowData[i][j++] = bean.getOutDate();
			j = 0;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btns[0]) { // 상품 추가
			insertData();
		} else if (e.getSource() == btns[1]) { // 상품 수정
			updateData();
		} else if (e.getSource() == btns[2]) { // 상품 삭제
			deleteData();
		} else { // clear!
			clearData();
		}
	}

	public void clearData() {
		for (JTextField tf : tfs) {
			tf.setText("");
		}
	}

	public void deleteData() {
		int cnt = dao.deleteData(Integer.parseInt(tfs[0].getText()));
		if (cnt == 1) { // 삭제 성공
			getAllProduct(); // 삭제 버튼 누르고 바로 딜리트 확인할 수 있게.
			for (JTextField tf : tfs) {
				tf.setText("");
			}
		} else {
			JOptionPane.showMessageDialog(null, "삭제 실패!", "메시지", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void updateData() {
		if (checkData()) {
			Bean bean = new Bean();
			bean.setId(Integer.parseInt(tfs[0].getText()));
			bean.setBrand(tfs[1].getText());
			bean.setName(tfs[2].getText());
			bean.setPrice(Integer.parseInt(tfs[3].getText()));
			bean.setStock(Integer.parseInt(tfs[4].getText()));
			bean.setHit(Integer.parseInt(tfs[5].getText()));
			bean.setCategory(tfs[6].getText());
			bean.setPersonalColor(tfs[7].getText());
			bean.setOutDate(tfs[8].getText());

			int cnt = dao.updateData(bean);
			if (cnt == 1) { // 수정 성공
				getAllProduct(); // 수정 버튼 누르고 바로 업데이트되는거 확인할 수 있게.
				for (JTextField tf : tfs) {
					tf.setText("");
				}
			} else {
				JOptionPane.showMessageDialog(null, "수정 실패!", "메시지", JOptionPane.WARNING_MESSAGE);
			}
		}

	}

	public void insertData() {
		if (checkData()) {
			Bean bean = new Bean();
			bean.setBrand(tfs[1].getText());
			bean.setName(tfs[2].getText());
			bean.setPrice(Integer.parseInt(tfs[3].getText()));
			bean.setStock(Integer.parseInt(tfs[4].getText()));
			bean.setHit(Integer.parseInt(tfs[5].getText()));
			bean.setCategory(tfs[6].getText());
			bean.setPersonalColor(tfs[7].getText());
			bean.setOutDate(tfs[8].getText());

			int cnt = dao.insertData(bean);
			if (cnt == 1) { // 삽입 성공
				getAllProduct(); // 삽입 버튼 누르고 바로 인서트되는거 확인할 수 있게.
				for (JTextField tf : tfs) {
					tf.setText("");
				}
			} else {
				JOptionPane.showMessageDialog(null, "삽입 실패!", "메시지", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	public boolean checkData() {
		if (tfs[1].getText().equals("")) {
			JOptionPane.showMessageDialog(null, // null로 바꿔봄.
					"브랜드명이 누락되었습니다.", "에러발생", JOptionPane.CANCEL_OPTION);
			tfs[1].requestFocus();
			return false;
		}
		if (tfs[2].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "상품명이 누락되었습니다.", "에러발생", JOptionPane.CANCEL_OPTION);
			tfs[2].requestFocus();
			return false;
		}
		if (tfs[3].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "단가가 누락되었습니다.", "에러발생", JOptionPane.CANCEL_OPTION);
			tfs[3].requestFocus();
			return false;
		}
		if (tfs[4].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "재고 수량이 누락되었습니다.", "에러발생", JOptionPane.CANCEL_OPTION);
			tfs[4].requestFocus();
			return false;
		}
		if (tfs[5].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "누적 판매량이 누락되었습니다.", "에러발생", JOptionPane.CANCEL_OPTION);
			tfs[5].requestFocus();
			return false;
		}
		if (tfs[6].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "카테고리가 누락되었습니다.", "에러발생", JOptionPane.CANCEL_OPTION);
			tfs[6].requestFocus();
			return false;
		}
		if (tfs[7].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "퍼스널 컬러가 누락되었습니다.", "에러발생", JOptionPane.CANCEL_OPTION);
			tfs[7].requestFocus();
			return false;
		}
		if (tfs[8].getText().equals("")) {
			JOptionPane.showMessageDialog(null, "출고일이 누락되었습니다.", "에러발생", JOptionPane.CANCEL_OPTION);
			tfs[8].requestFocus();
			return false;
		}
		return true;
	}

	public void getAllProduct() {
		lists = dao.getAllProduct();
		rowData = new Object[lists.size()][columnNames.length];
		fillData();

		table = new JTable(rowData, columnNames);
		scrollPane.setViewportView(table); // 스크롤패인 새로고침해라.

		table.addMouseListener(new MouseHandler());
	}

	class MouseHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			int col = 0;

			for (JTextField tf : tfs) {
				tf.setText(String.valueOf(table.getValueAt(row, col)));
				++col;
			}
		}

	}

	class MouseHandlerTab extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			System.out.println("mouseClicked");

			cmb.removeAllItems();
			if (e.getSource() == tabs[0]) { // 브랜드별
				for (int i = 0; i < brands.length; ++i)
					cmb.addItem(brands[i]);
			} else if (e.getSource() == tabs[1]) { // 카테고리별
				for (int i = 0; i < categories.length; ++i)
					cmb.addItem(categories[i]);
			} else if (e.getSource() == tabs[2]) { // 퍼스널 컬러별
				for (int i = 0; i < personalColors.length; ++i)
					cmb.addItem(personalColors[i]);
			} else { // 전체
				//contentPane.remove(cmb); // 나중에
			}
		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// 이거는 탭에 스크롤패인부터 연결한 후에 다시 보자.
		dao.getProductBy(cmb.getSelectedItem());
	}
	
}
