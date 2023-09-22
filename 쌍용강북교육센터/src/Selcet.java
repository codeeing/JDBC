// 230830

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Selcet {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			// 1단계
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2단계
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			String id = "sqlid";
			String pw = "sqlpw";
			conn = DriverManager.getConnection(url, id, pw); // conn: 접속한 정보 담김
			
			// 3단계
			String sql = "select * from test order by num";
			ps = conn.prepareStatement(sql); // ps: 분석한 정보 담김
			
			// 4단계
			rs = ps.executeQuery(); // rs: 실행한 결과 담김
			while(rs.next()) { // rs는 iterator랑 비슷한 기능인듯.
				int num = rs.getInt("num"); // num칼럼의 정수형 하나를 가져와라
				String name = rs.getString("name");
				String addr = rs.getString("addr");
				System.out.println(num+","+name+","+addr);
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					
				}
			}
			if(conn != null) {
				try {
					// 5단계
					conn.close();
					
				} catch (SQLException e) {
					
				}
			}
			
		}
		
		
	}

}
