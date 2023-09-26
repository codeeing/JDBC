import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Jtab01 extends JFrame{

	/*필요한 콤포넌트 준비*/
	   
    JTabbedPane t = new JTabbedPane();  //JTabbedPane생성
   
    JPanel p1 = new JPanel(); //JPanel 생성
   
    JButton btn1 = new JButton("탭 연습1");
    JButton btn2 = new JButton("탭 연습2");
    JButton btn3 = new JButton("탭 연습3");
    JTextField txt_1 = new JTextField("기본값",25);
    Container contentPane;
    
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    String[] colName = {"이름","직업","나이"};
	Object[][] data1 = {
						{"양세찬","개그맨",30},
						{"김연아","운동선수",new Integer(40)},
						{"송혜교","배우",20},
						{"아이유","가수",50}
					};
	Object[][] data2 = {
			{"양세찬2","개그맨",30},
			{"김연아2","운동선수",new Integer(40)},
			{"송혜교2","배우",20},
			{"아이유2","가수",50}
		};
	
	JTable table1 = new JTable(data1,colName);
	JTable table2 = new JTable(data2,colName);
	
    public Jtab01() {  //생성자
    	
		
        super("TabTitleTextPosition"); //프레임 타이틀 제목 지정
        scrollPane1 =  new JScrollPane();
        scrollPane2 = new JScrollPane();
        scrollPane1 = new JScrollPane(table1);
        scrollPane2 = new JScrollPane(table2);
		contentPane = getContentPane();
		
        /*try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //원래 자바 고유의 룩앤필을 쓰는데 이것을 사용할 경우 자신의 오에스의 룩앤필을 사용하게 된다.
        }catch(Exception e) {
            e.printStackTrace();
        }*/
       
        p1.add(btn1); //패널에 버튼추가
        p1.add(btn2); //패널에 버튼추가
        p1.add(btn3); //패널에 버튼추가
        p1.add(txt_1); //패널에 텍스트필드추가
               
        t.add("기록 일지", scrollPane1); //JTabbedPane에 탭추가
        //Component javax.swing.JTabbedPane.add(String title, Component component)
       
        t.add("상태 보기", p1);
        t.add("회원정보변경", scrollPane2);
        t.add("About",new JTextArea());
       
        add(t);
      
		
        //frame.pack();
        setSize(450, 350);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               
    }//생성자 끝
 
    public static void main(String[] args) {
        new Jtab01();
       
    }//메인메소드 끝
 

}
