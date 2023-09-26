// 230830

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 드라이버 로드
			System.out.println("드라이버 로드 성공");

			String url ="jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "sqlid";
			String pw = "sqlpw";
			System.out.println("DB와의 연결을 시도합니다.");

			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("DB와의 연결 성공");

			int insert_num = 4;
			String insert_name = "슬기"; // 실행 불가한 문장
			String insert_addr = "인천";
			
			
			// String sql = "insert into test(num, name, addr) values(1,'윤아','서울')";
			// String sql = "insert into test(num, name, addr) values("
			// 			+insert_num+",'"+insert_name+"','"+insert_addr+"')"; // 매번 작은 따옴표로 감싸는 작업을 해야함.
			String sql = "insert into test(num, name, addr) values(?,?,?)"; // 가장 많이 사용하는 방식
			// ?는 values 자리에만 들어갈 수 있다.
			// ps에는 sql문장을 분석한 정보가 들어감
			// 3. sql문 분석 : prepareStatement()
			ps = conn.prepareStatement(sql); // 이 문장을 먼저 분석하라.
			System.out.println(1); // 어디서 sqlException이 발생하는지 알아보기 위해 작성
			
			// 3-1. ?(위치홀더)에 값 넣기
			ps.setInt(1, insert_num); // 1: 첫번째 물음표
			ps.setString(2, insert_name); // 2: 두번째 물음표
			ps.setString(3, insert_addr); // 3: 세번째 물음표
			
			// 4. 분석한 sql문 실행 : executeUpdate()
			int cnt = ps.executeUpdate(); // insert할때 이 메서드를 사용. 성공적으로 insert한 개수를 리턴
			System.out.println(2);

			System.out.println("cnt:" + cnt);
			
			
		
			//System.out.println(3);

		} catch (ClassNotFoundException e) {
			// forName(): jar 파일 빌드 경로에 포함 안시키거나, 오타가 발생 할 경우
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			
			System.out.println("SQLException 발생");
			
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
				}
			}
			
		}
	}

}

