import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

	public Main(Connection conn) {
		First first = new First("재고 관리 시스템", conn);

	}

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "sqlid";
		String pw = "sqlpw";
		Connection conn = null;

		try {
			// 로드
			Class.forName(driver);
			System.out.println("로드 성공");

			// 접속
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("접속 성공");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		new Main(conn);
	}

}
