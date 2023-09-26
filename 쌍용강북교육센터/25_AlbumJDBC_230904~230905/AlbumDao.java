import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlbumDao {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url ="jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "sqlid";
	String pw = "sqlpw";
	Connection conn = null;
	
	public AlbumDao() {
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

	public ArrayList<AlbumBean> getAllAlbums() {
		String sql = "select * from albums order by num";
		PreparedStatement ps = null;
		ResultSet rs = null;
		AlbumBean bean = null;
		ArrayList<AlbumBean> list = new ArrayList<AlbumBean>();
		try {
			// 분석
			ps = conn.prepareStatement(sql);
			// 실행
			rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new AlbumBean();
				bean.setNum(rs.getInt("num"));
				bean.setSong(rs.getString("song"));
				bean.setSinger(rs.getString("singer"));
				bean.setCompany(rs.getString("company"));
				bean.setPrice(rs.getInt("price"));
				bean.setPub_day(String.valueOf(rs.getDate("pub_day")));
				
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

	public ArrayList<AlbumBean> getAlbumsBy(String column, String word) {
		String sql = "select * from albums where upper("+column+") like ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		AlbumBean bean = null;
		ArrayList<AlbumBean> list = new ArrayList<AlbumBean>();
		try {
			// 분석
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+word.toUpperCase()+"%");
			// 실행
			rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new AlbumBean();
				bean.setNum(rs.getInt("num"));
				bean.setSong(rs.getString("song"));
				bean.setSinger(rs.getString("singer"));
				bean.setCompany(rs.getString("company"));
				bean.setPrice(rs.getInt("price"));
				bean.setPub_day(String.valueOf(rs.getDate("pub_day")));
				
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

	public ArrayList<AlbumBean> getAlbumsByPrice(int start, int end) {
		String sql = "select * from(select dense_rank() over(order by price desc) rank, num, song, singer, company, price, pub_day "
							+ "from albums)"
							+ "where rank between "+start+" and "+end;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AlbumBean bean = null;
		ArrayList<AlbumBean> list = new ArrayList<AlbumBean>();
		
		try {
			// 분석
			ps = conn.prepareStatement(sql);
			// 실행
			rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new AlbumBean();
				bean.setNum(rs.getInt("num"));
				bean.setSong(rs.getString("song"));
				bean.setSinger(rs.getString("singer"));
				bean.setCompany(rs.getString("company"));
				bean.setPrice(rs.getInt("price"));
				bean.setPub_day(String.valueOf(rs.getDate("pub_day")));
				
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null)	ps.close();
				if(rs != null)	rs.close();
			} catch(SQLException e) {;}
		}
		
		return list;
	}

	public int insertData(AlbumBean bean) {
		String sql = "insert into albums values(albumseq.nextval,?,?,?,?,?)";
		PreparedStatement ps = null;
		int cnt = -1;
		
		try {
			// 분석
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getSong());
			ps.setString(2, bean.getSinger());
			ps.setString(3, bean.getCompany());
			ps.setInt(4, bean.getPrice());
			ps.setString(5, bean.getPub_day());
			
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

	public int updateData(AlbumBean bean) {
		String sql = "update albums set song=?,singer=?,company=?,price=?,pub_day=?"
				+ "where num=?";
		PreparedStatement ps = null;
		int cnt = -1;
		
		try {
			// 분석
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getSong());
			ps.setString(2, bean.getSinger());
			ps.setString(3, bean.getCompany());
			ps.setInt(4, bean.getPrice());
			ps.setString(5, bean.getPub_day());
			ps.setInt(6, bean.getNum());
			
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

	public int deleteData(int num) {
		String sql = "delete from albums where num = "+num;
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
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return cnt;
	}

	public ArrayList<AlbumBean> align(String column, String align) {
		String sql = "select * from albums order by "+column+" "+align;
		PreparedStatement ps = null;
		ResultSet rs = null;
		AlbumBean bean = null;
		ArrayList<AlbumBean> list = new ArrayList<AlbumBean>();
		try {
			// 분석
			ps = conn.prepareStatement(sql);
			// 실행
			rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new AlbumBean();
				bean.setNum(rs.getInt("num"));
				bean.setSong(rs.getString("song"));
				bean.setSinger(rs.getString("singer"));
				bean.setCompany(rs.getString("company"));
				bean.setPrice(rs.getInt("price"));
				bean.setPub_day(String.valueOf(rs.getDate("pub_day")));
				
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null)	ps.close();
				if(rs != null)	rs.close();
			} catch(SQLException e) {;}
		}
		
		return list;
		
	}

	
	public AlbumBean groupAvg(String company) {
		String sql = "select company, avg(price) avg from albums where company='"+company+"' group by company";
		PreparedStatement ps = null;
		ResultSet rs = null;
		AlbumBean bean = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				bean = new AlbumBean();
				bean.setCompany(rs.getString("company"));
				bean.setPrice(rs.getInt("avg"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null)	ps.close();
				if(rs != null)	rs.close();
			} catch(SQLException e) {;}
		}
		
		return bean;
				
	}
}
