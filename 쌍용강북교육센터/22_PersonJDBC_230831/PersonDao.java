import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonDao { // DB 작업을 하는 다오 클래스
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String username = "sqlid";
	String password = "sqlpw";
	Connection conn = null;
	PreparedStatement ps = null; // 모든 sql문에 대해 분석기는 반드시 필요하므로, 인스턴스 변수로 선언
	ResultSet rs = null;
	PersonBean pb = null; // 조회문(쿼리, select)에서 반드시 필요하므로, 인스턴스 변수로 선언
	int cnt; // DML 실행 후 리턴하는 cnt 변수 선언
	
	public PersonDao() { // dao 객체를 생성하면서 반드시 수행해줘야하는 드라이버 로드와 접속 기능을 생성자에서 구현
		System.out.println("PersonDao 생성자");
		try {
			// 1단계
			Class.forName(driver);
			System.out.println("드라이버 로드 성공");
			
			// 2단계
			conn = DriverManager.getConnection(url,username,password);
			System.out.println("접속 성공");
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
		} catch (SQLException e) {
			System.out.println("SQLException 예외 발생");
		}
		
	}
	
	public ArrayList<PersonBean> getAllPerson(){ // 전체 레코드를 조회하는 작업
		int num;
		String name;
		int age;
		String gender;
		String birth;
		
		// List: 순서가짐, 중복가능.
		ArrayList<PersonBean> lists = new ArrayList<PersonBean>();
		try {
			String sql = "select * from person";
			ps = conn.prepareStatement(sql); // 분석하기
			
			rs = ps.executeQuery();
			while(rs.next()) {
				num = rs.getInt("num"); // num칼럼의 int형 셀을 가져와서 저장
				name = rs.getString("name");
				age = rs.getInt("age");
				gender = rs.getString("gender");
				birth = rs.getString("birth");
				//System.out.println(num+"/"+name+"/"+age+"/"+gender+"/"+birth); //콘솔작업은 메인클래스에서 진행해야 하므로 주석처리
				
				//PersonBean객체의 멤버변수들을 가져온 셀들로 초기화하여, 하나로 묶는 작업
				pb = new PersonBean();
				pb.setNum(num);
				pb.setName(name);
				pb.setAge(age);
				pb.setGender(gender);
				pb.setBirth(birth);
				//하나로 묶어진 PersonBean객체를 어레이리스트에 추가
				lists.add(pb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
//			if(conn != null)
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
		}
		return lists;
	}
	

	public ArrayList<PersonBean> getPersonByGender(String gender) {
		// 3단계 : 분석
		String sql = "select * from person where gender = '"+gender+"'";
		ArrayList<PersonBean> lists = new ArrayList<PersonBean>();
		
		try {
			ps = conn.prepareStatement(sql);
			
			// 4단계 : 실행
			rs = ps.executeQuery();
			while(rs.next()) {
				int num = rs.getInt("num");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String birth = rs.getString("birth");
				
				pb = new PersonBean();
				pb.setNum(num);
				pb.setName(name);
				pb.setAge(age);
				pb.setGender(gender);
				pb.setBirth(birth);
				
				lists.add(pb);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return lists;
	}

	public void updateData(int num, String name, int age, String gender, String birth) {
		String sql = "update person set name=?, age=?, gender=?, birth=? where num=?";
		
		try {
			ps = conn.prepareStatement(sql); // 분석
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, gender);
			ps.setString(4, birth);
			ps.setInt(5, num);
			
			cnt = ps.executeUpdate(); // 실행
			System.out.println(cnt + "개의 레코드 수정이 완료되었습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
	}

	public void deleteData(int num) {
		String sql = "delete from person where num="+num;
		
		try {
			ps = conn.prepareStatement(sql); // 분석
			
			cnt = ps.executeUpdate(); // 실행
			System.out.println(cnt + "개의 레코드 삭제가 완료되었습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	public void insertData(String name, int age, String gender, String birth) {
		String sql = "insert into person(num,name,age,gender,birth) "
				+ "values(perseq.nextval,?,?,?,?)";
		
		try {
			// 3단계
			ps = conn.prepareStatement(sql);
			
			// 3-1단계
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setString(3, gender);
			ps.setString(4, birth);
			
			// 4단계
			cnt = ps.executeUpdate();
			System.out.println("cnt:"+cnt);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		
	}
}
