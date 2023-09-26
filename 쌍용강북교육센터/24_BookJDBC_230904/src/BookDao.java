import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDao {
	
	Connection conn = null;
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "sqlid";
	String pw = "sqlpw";
	
	public BookDao() {
		
		try {
			// 로드
			Class.forName(driver);
			// 접속
			conn = DriverManager.getConnection(url,id,pw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<BookBean> getAllBook() {
		String sql = "select * from book order by no";
		PreparedStatement ps = null;
		ResultSet rs = null;
		BookBean bean = null;
		ArrayList<BookBean> list = new ArrayList<BookBean>();
		
		try {
			// 분석
			ps = conn.prepareStatement(sql);
			// 실행
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no"); // 칼럼명 혹은 칼럼번호를 쓰는 것도 가능
				String title = rs.getString("title");
				String author = rs.getString("author");
				String publisher = rs.getString("publisher");
				int price = rs.getInt("price");
				String pub_day = String.valueOf(rs.getDate("pub_day"));
				
				// bean 객체로 묶기
				bean = new BookBean();
				bean.setNo(no);
				bean.setTitle(title);
				bean.setAuthor(author);
				bean.setPublisher(publisher);
				bean.setPrice(price);
				bean.setPub_day(pub_day);
				
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return list;
	}

	public ArrayList<BookBean> getBookBy(String col, String word) {
		//String sql = "select * from book where "+col+" like '%"+word+"%' order by no";
		String sql = "select * from book where "+col+" like ? order by no";
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		BookBean bean = null;
		ArrayList<BookBean> list = new ArrayList<BookBean>();

		try {
			// 분석
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+word+"%");
			
			// 실행
			rs = ps.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String publisher = rs.getString("publisher");
				int price = rs.getInt("price");
				String pub_day = String.valueOf(rs.getDate("pub_day"));
				
				bean = new BookBean();
				bean.setNo(no);
				bean.setTitle(title);
				bean.setAuthor(author);
				bean.setPublisher(publisher);
				bean.setPrice(price);
				bean.setPub_day(pub_day);
				
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		} 
		return list;
	}

	public int insertData(BookBean bean) {
		String sql = "insert into book values(bseq.nextval, ?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		int cnt = -1;
		
		try {
			// 분석
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getTitle());
			ps.setString(2, bean.getAuthor());
			ps.setString(3, bean.getPublisher());
			ps.setInt(4, bean.getPrice());
			ps.setString(5, bean.getPub_day());
			
			// 실행
			cnt = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps. close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return cnt;
	}

	public int updateData(BookBean bean) {
		String sql = "update book set title=?,author=?,publisher=?,price=?,pub_day=? where no = ?";
		PreparedStatement ps = null;
		int cnt = -1;
		
		try {
			// 분석
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getTitle());
			ps.setString(2, bean.getAuthor());
			ps.setString(3, bean.getPublisher());
			ps.setInt(4, bean.getPrice());
			ps.setString(5, bean.getPub_day());
			ps.setInt(6, bean.getNo());
	
			// 실행
			cnt = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps. close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return cnt;
	}

	public int deleteData(int no) {
		String sql = "delete book where no="+no;
		PreparedStatement ps = null;
		int cnt = -1;
		try {
			// 분석
			ps = conn.prepareStatement(sql);
			// 실행
			cnt = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps. close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return cnt;
	}
}
