// 230830
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "sqlid";
		String pw = "sqlpw";
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			// 1단계
			Class.forName(driver);
		
		
			// 2단계
			conn = DriverManager.getConnection(url, id, pw);
			
			// 3단계
			int delete_num = 2;
			//String sql = "delete from test where num=3";
			//String sql = "delete from test where num="+delete_num;
			String sql = "delete from test where num=?";
			ps = conn.prepareStatement(sql);
			
			// 3-1단계
			ps.setInt(1, delete_num);
			
			// 4단계
			int cnt = ps.executeUpdate();
			System.out.println("cnt:"+cnt);
			
		} catch (Exception e) {
			
		} finally {
			//5단계
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
