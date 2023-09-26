// 230830
// jdk를 설치하면 이런 패키지를 가져올 수 있음
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connect_1 {

	public static void main(String[] args) {
		
		try {
			// oracle.jdbc.driver패키지 안의 OracleDriver클래스
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			System.out.println("드라이버 로드 성공");
			
			// localhost: 내컴퓨터 주소, 1521: 포트번호, orcl: 전역데이터베이스 이름
			// 내컴퓨터에 설치된 오라클에 1521 포트번호를 통해서 통신하겠다.
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String user = "sqlid";
			String pw = "sqlpw";
			System.out.println("DB와의 연결을 시도합니다.");
			// 접속하기 위한 getConnection() 메서드. Connection을 리턴하므로 Connection 참조 변수로 받아줌.
			// conn: 해당 계정에 접속이 되었다는 정보가 들어옴.
			Connection conn = DriverManager.getConnection(url,user,pw); // SQLException이 발생할 수 있음
//			=================== 이 선 위로는 필수 코드 ===================
			
			System.out.println("DB와의 연결 성공");
			
			// 이곳에 들어가는 코드가 굉장히 길 것.
			conn.close(); // SQLException이 발생할 수 있음
			System.out.println("DB와의 연결끊기 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			// getConnection()의 url, user, pw 셋 중 하나라도 오타가 있을시 발생
			// 혹은 close()에서 발생
			System.out.println("SQLException 발생");
		}
	}

}
