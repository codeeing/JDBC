import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url ="jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "sqlid";
		String pw = "sqlpw";
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			// 1단계
			Class.forName(driver);
			
			// 2단계
			conn = DriverManager.getConnection(url, id, pw);
			
			String update_name = "아이유";
			String update_addr = "경주";
			int update_num = 3;
			// 3단계
			//String sql = "update test set name='"+update_name+"', addr='"+update_addr+"' where num="+update_num;
			String sql = "update test set name=?, addr=? where num=?";
			ps = conn.prepareStatement(sql); // ps: 분석한 정보가 담김
			
			// 3-1단계
			ps.setString(1, update_name);
			ps.setString(2, update_addr);
			ps.setInt(3, update_num);
			
			// 4단계
			int cnt = ps.executeUpdate(); // 성공적으로 실행된 개수를 리턴
			System.out.println("cnt:"+cnt);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5딘계
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

}
