import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JoinDao {

	
	public int InsertData(String id, String pw, String type, Connection conn) {
		String sql = "insert into join values(?,?,?)";
		PreparedStatement ps = null;
		int cnt = -1;
		try {
			// 분석
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			ps.setString(3, type);
			// 실행
			cnt = ps.executeUpdate();
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
		return cnt;
	}

}
