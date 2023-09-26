// 230905
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ProductMain extends JFrame implements ActionListener{
	private String[] columnNames = {"아이디", "이름", "입고수량", "단가", "카테고리", "입고일"};
	private Object[][] rowData;
	private JTable table;
	private JScrollPane scrollPane;
	
	private JButton[] btn = new JButton[4];
	private JTextField txtId = new JTextField();
	private JTextField txtName = new JTextField();
	private JTextField txtStock = new JTextField();
	private JTextField txtPrice = new JTextField();
	private JTextField txtCategory = new JTextField();
	private JTextField txtInputdate = new JTextField();
	ArrayList<ProductBean> lists;
	Container contentPane;
	ProductDao dao = new ProductDao();
	
	public ProductMain(String title) {
		super(title);
		
		compose(); // 화면구성
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 550);
		setVisible(true);
	}
	public void compose(){
		dao = new ProductDao();
		lists = dao.getAllProduct();
		rowData = new Object[lists.size()][columnNames.length];
		fillData();
		
		// JTable
		table =  new JTable(rowData, columnNames);
		scrollPane = new JScrollPane(table);
		contentPane = getContentPane();
		
		contentPane.setLayout(null);
		scrollPane.setBounds(0,0,490,200);
		contentPane.add(scrollPane);
		
		// JLabel
		JLabel lbId = new JLabel("아이디");
		JLabel lbName = new JLabel("이름");
		JLabel lbStock = new JLabel("입고 수량");
		JLabel lbPrice = new JLabel("단가");
		JLabel lbCategory = new JLabel("카테고리");
		JLabel lbInputdate = new JLabel("입고일자(yyyy/mm/dd)");
		
		JPanel pCenter = new JPanel(); // flowLayout이 기본
		pCenter.setLayout(null);
		pCenter.setBounds(0,220,490,220);
		pCenter.setBackground(Color.orange);
		contentPane.add(pCenter);
		
		int vposition = 20;
		lbId.setBounds(20,1*vposition,150,20);
		lbName.setBounds(20,2*vposition,150,20);
		lbStock.setBounds(20,3*vposition,150,20);
		lbPrice.setBounds(20,4*vposition,150,20);
		lbCategory.setBounds(20,5*vposition,150,20);
		lbInputdate.setBounds(20,6*vposition,150,20);
		
		pCenter.add(lbId);
		pCenter.add(lbName);
		pCenter.add(lbStock);
		pCenter.add(lbPrice);
		pCenter.add(lbCategory);
		pCenter.add(lbInputdate);
		
		// JTextField
		txtId.setBounds(200,1*vposition,150,20);
		txtName.setBounds(200,2*vposition,150,20);
		txtStock.setBounds(200,3*vposition,150,20);
		txtPrice.setBounds(200,4*vposition,150,20);
		txtCategory.setBounds(200,5*vposition,150,20);
		txtInputdate.setBounds(200,6*vposition,150,20);
		
		txtId.setText("0");
		txtId.setEnabled(false); // ID 텍스트필드 비활성화 작업
		
		pCenter.add(txtId);
		pCenter.add(txtName);
		pCenter.add(txtStock);
		pCenter.add(txtPrice);
		pCenter.add(txtCategory);
		pCenter.add(txtInputdate);
		
		// JButton
		JPanel pSouth = new JPanel(); // flowLayout이 기본
		pSouth.setLayout(new GridLayout(1,4));
		pSouth.setBounds(0,440,490,40);
		pSouth.setBackground(Color.blue);
		contentPane.add(pSouth);
		
		String[] btnTitle = {"등록", "수정", "삭제", "종료"};
		for(int i=0;i<btn.length;++i) {
			btn[i] = new JButton(btnTitle[i]);
			btn[i].addActionListener(this); // new ActionEvent();
			pSouth.add(btn[i]);
		}
		
		table.addMouseListener(new MouseHandler());
		txtStock.addKeyListener(new KeyHandler());
		txtPrice.addKeyListener(new KeyHandler());
	}
	
	public void fillData() { // ArrayList => 2차원배열
		Object[] arr = lists.toArray();
		int j = 0;
		for(int i=0; i<arr.length; ++i) {
			ProductBean pb = (ProductBean)arr[i];
			rowData[i][j++] = pb.getId();
			rowData[i][j++] = pb.getName();
			rowData[i][j++] = pb.getStock();
			rowData[i][j++] = pb.getPrice();
			rowData[i][j++] = pb.getCategory();
			rowData[i][j++] = pb.getInputdate();
			j = 0;
		}
	}
	public static void main(String[] args) {
		new ProductMain("상품관리 프로그램");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == btn[0]) {
			System.out.println("등록");
			insertData();
		} else if(obj == btn[1]) {
			System.out.println("수정");
			updateData();
		} else if(obj == btn[2]) {
			System.out.println("삭제");
			deleteData();
		} else {
			System.out.println("종료");
			int answer = JOptionPane.showConfirmDialog(this,
					"종료하시겠습니까?",
					"confirm",
					JOptionPane.YES_NO_OPTION);
			if(answer == JOptionPane.YES_OPTION)
				dao.exit();
				System.exit(0);
				
		}
	}
	
	public void deleteData() {
		int row = table.getSelectedRow();
		if(row != -1){
			Integer.parseInt(table.getValueAt(row, 0).toString());
			int cnt = dao.deleteData(Integer.parseInt(txtId.getText()));
				if(cnt == 1) {
					System.out.println("삭제성공");
					getAllProduct(); // 삭제 버튼 누르고 바로 딜리트되는거 확인할 수 있게.
					clearTextField();
				} else System.out.println("삭제실패");
		} else {
			JOptionPane.showMessageDialog(this,
										"삭제할 레코드를 선택하세요",
										"에러발생",
										JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public void updateData() {
		if(checkData()) {
			ProductBean bean = new ProductBean();
			bean.setId(Integer.parseInt(txtId.getText()));
			bean.setName(txtName.getText());
			bean.setStock(Integer.parseInt(txtStock.getText()));
			bean.setPrice(Integer.parseInt(txtPrice.getText()));
			bean.setCategory(txtCategory.getText());
			bean.setInputdate(txtInputdate.getText());
			
			int cnt = dao.updateData(bean);
			if(cnt == 1) {
				System.out.println("수정성공");
				getAllProduct(); // 수정 버튼 누르고 바로 업데이트되는거 확인할 수 있게.
				clearTextField();
			}
			else System.out.println("수정실패");
			
		}
	}
	
	public void insertData() {
		if(checkData()) {
			ProductBean bean = new ProductBean();
			bean.setName(txtName.getText());
			bean.setStock(Integer.parseInt(txtStock.getText()));
			bean.setPrice(Integer.parseInt(txtPrice.getText()));
			bean.setCategory(txtCategory.getText());
			bean.setInputdate(txtInputdate.getText());
			
			int cnt = dao.insertData(bean);
			if(cnt == 1) {
				System.out.println("삽입성공");
				getAllProduct(); // 삽입 버튼 누르고 바로 인서트되는거 확인할 수 있게.
				clearTextField();
			}
			else System.out.println("삽입실패");
			
		}
		
	}
	
	public boolean checkData() {
		if(txtName.getText().equals("")) {
			JOptionPane.showMessageDialog(txtName, 
					"이름이 누락되었습니다.",
					"에러발생", 
					JOptionPane.CANCEL_OPTION);
			txtName.setText("");
			txtName.requestFocus();
			return false;
		}
		if(txtStock.getText().equals("")) {
			JOptionPane.showMessageDialog(txtStock, 
					"입고수량이 누락되었습니다.",
					"에러발생", 
					JOptionPane.CANCEL_OPTION);
			txtStock.setText("");
			txtStock.requestFocus();
			return false;
		}
		if(txtPrice.getText().equals("")) {
			JOptionPane.showMessageDialog(txtPrice, 
					"단가가 누락되었습니다.",
					"에러발생", 
					JOptionPane.CANCEL_OPTION);
			txtPrice.setText("");
			txtPrice.requestFocus();
			return false;
		}
		if(txtCategory.getText().equals("")) {
			JOptionPane.showMessageDialog(txtCategory, 
					"카테고리가 누락되었습니다.",
					"에러발생", 
					JOptionPane.CANCEL_OPTION);
			txtCategory.setText("");
			txtCategory.requestFocus();
			return false;
		}
		if(txtInputdate.getText().equals("")) {
			JOptionPane.showMessageDialog(txtInputdate, 
					"입고일이 누락되었습니다.",
					"에러발생", 
					JOptionPane.CANCEL_OPTION);
			txtInputdate.setText("");
			txtInputdate.requestFocus();
			return false;
		}
		return true;
	}
	public void clearTextField(){
		txtId.setText("");
		txtName.setText("");
		txtStock.setText("");
		txtPrice.setText("");
		txtCategory.setText("");
		txtInputdate.setText("");
	}
	public void getAllProduct() {
		dao = new ProductDao();
		lists = dao.getAllProduct();
		rowData = new Object[lists.size()][columnNames.length];
		fillData();
		
		table = new JTable(rowData,columnNames);
		scrollPane.setViewportView(table); // 스크롤패인 새로고침해라.
		
		table.addMouseListener(new MouseHandler());
	}

	class MouseHandler extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("mouseClicked");
			int row = table.getSelectedRow();
//			int col = table.getSelectedColumn();
			System.out.println("row:"+row);
//			System.out.println("col:"+col);
			
//			Object obj = table.getValueAt(row, col);
//			System.out.println("obj:"+obj);
			
			int col = 0;
			txtId.setText(String.valueOf(table.getValueAt(row, col++)));
			txtName.setText(String.valueOf(table.getValueAt(row, col++)));
			txtStock.setText(String.valueOf(table.getValueAt(row, col++)));
			txtPrice.setText(String.valueOf(table.getValueAt(row, col++)));
			txtCategory.setText(String.valueOf(table.getValueAt(row, col++)));
			txtInputdate.setText(String.valueOf(table.getValueAt(row, col++)));
			
		}
		
		
	}
	
	class KeyHandler extends KeyAdapter{

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getSource() == txtStock) {
				try {
					Integer.parseInt(txtStock.getText());
				} catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(txtStock, 
							"숫자로 입력하세요",
							"에러발생", 
							JOptionPane.CANCEL_OPTION);
					txtStock.setText("");
				}
				
			} else if(e.getSource() == txtPrice) {
				try {
					Integer.parseInt(txtPrice.getText());
				} catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(txtPrice, 
							"숫자로 입력하세요",
							"에러발생", 
							JOptionPane.CANCEL_OPTION);
					txtPrice.setText("");
				}
				
			} else {;}
		}

	}
}

