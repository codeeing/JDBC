import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDao {
	private Connection conn;
	public AdminDao(Connection conn) {
		this.conn = conn;
	}
	
	public ArrayList<Bean> getAllProduct() {
		String sql = "select * from beauty";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Bean bean = null;
		ArrayList<Bean> lists = new ArrayList<Bean>();
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new Bean();
				bean.setId(rs.getInt("id"));
				bean.setBrand(rs.getString("brand"));
				bean.setName(rs.getString("name"));
				bean.setPrice(rs.getInt("price"));
				bean.setStock(rs.getInt("stock"));
				bean.setHit(rs.getInt("hit"));
				bean.setCategory(rs.getString("category"));
				bean.setPersonalColor(rs.getString("personalcolor"));
				bean.setOutDate(String.valueOf(rs.getDate("outDate")));
				
				lists.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					if(rs != null) rs.close();
					if(ps != null) ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return lists;
	}

	public int insertData(Bean bean) {
		String sql = "insert into beauty "
				+ "values(bty_seq.nextval,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		int cnt = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getBrand());
			ps.setString(2, bean.getName());
			ps.setInt(3, bean.getPrice());
			ps.setInt(4, bean.getStock());
			ps.setInt(5, bean.getHit());
			ps.setString(6, bean.getCategory());
			ps.setString(7, bean.getPersonalColor());
			ps.setString(8, bean.getOutDate());
			
			cnt = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}

	public int updateData(Bean bean) {
		String sql = "update beauty set brand=?,name=?,price=?,stock=?,"
				+ "hit=?,category=?,personalcolor=?,outDate=? where id=?";
		PreparedStatement ps = null;
		int cnt = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getBrand());
			ps.setString(2, bean.getName());
			ps.setInt(3, bean.getPrice());
			ps.setInt(4, bean.getStock());
			ps.setInt(5, bean.getHit());
			ps.setString(6, bean.getCategory());
			ps.setString(7, bean.getPersonalColor());
			ps.setString(8, bean.getOutDate());
			ps.setInt(9, bean.getId());
			
			cnt = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}

	public int deleteData(int id) {
		String sql = "delete from beauty where id="+id;
		PreparedStatement ps = null;
		int cnt = -1;
		try {
			ps = conn.prepareStatement(sql);
			cnt = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}

	public void getProductBy(Object selectedItem) {
		//String sql = "select * from beauty where "
	}

//	public ArrayList<Bean> getProductByBrand() {
//		String sql = "select * from beauty where brand";
//		return null;
//	}

}
