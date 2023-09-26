// 230905
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class ProductDao {
	String driver ="oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String userid ="sqlid";
	String pw = "sqlpw";
	Connection conn = null;
	
	public ProductDao() {
		
		try {
			// 1단계 : 드라이버 로드
			Class.forName(driver);
			
			// 2단계 : 접속
			conn = DriverManager.getConnection(url, userid, pw);
			System.out.println("드라이버 로드 성공, 접속 성공");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<ProductBean> getAllProduct() {
		
		String sql = "select * from products";
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProductBean pd = null;
		int id;
		String name;
		int stock;
		int price;
		String category;
		Date d;
		ArrayList<ProductBean> list = new ArrayList<ProductBean>();
		
		try {
			ps = conn.prepareStatement(sql); // 분석
			rs = ps.executeQuery(); // 실행
			while(rs.next()) {
				id = rs.getInt("id");
				name = rs.getString("name");
				stock = rs.getInt("stock");
				price = rs.getInt("price");
				category = rs.getString("category");
				d = rs.getDate("inputdate");
				
				String inputdate = String.valueOf(d);
				
				
				
				
				
				
				pd = new ProductBean();
				pd.setId(id);
				pd.setName(name);
				pd.setStock(stock);
				pd.setPrice(price);
				pd.setCategory(category);
				pd.setInputdate(inputdate);
				
				list.add(pd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return list;
	}

	public ProductBean getProductById(int id) {
		String sql = "select * from products where id="+id;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProductBean pb = null;
		
		try {
			ps = conn.prepareStatement(sql); // 분석
			rs = ps.executeQuery(); // 실행
			
			if(rs.next()) { // rs에는 한 줄의 레코드이므로, while이 아닌 if로 처리해보자.
				String name = rs.getString("name");
				int stock = rs.getInt("stock");
				int price = rs.getInt("price");
				String category = rs.getString("category");
				//Date d = rs.getDate("inputdate");
				String inputdate = String.valueOf(rs.getDate("inputdate"));	
				
				pb = new ProductBean();
				pb.setId(id);
				pb.setName(name);
				pb.setStock(stock);
				pb.setPrice(price);
				pb.setCategory(category);
				pb.setInputdate(inputdate);
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
		return pb; // 없는 id가 들어오면 null 리턴하게됨
	}

	public ArrayList<ProductBean> getProductByCategory(String category) {
		String sql = "select * from products where upper(category)=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProductBean bean = null;
		ArrayList<ProductBean> list = new ArrayList<ProductBean>();
		try {
			ps = conn.prepareStatement(sql); // 분석
			ps.setString(1, category.toUpperCase());
			rs = ps.executeQuery(); // 실행
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int stock = rs.getInt("stock");
				int price = rs.getInt("price");
				//String inputdate = rs.getString("inputdate");
				String inputdate = String.valueOf(rs.getDate("inputdate"));	
				
				bean = new ProductBean();
				bean.setId(id);
				bean.setName(name);
				bean.setStock(stock);
				bean.setPrice(price);
				bean.setCategory(category);
				bean.setInputdate(inputdate);
				
				list.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
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

	public int insertData(ProductBean bean) {
//		System.out.println(bean.getId());
//		System.out.println(bean.getName());
//		System.out.println(bean.getStock());
//		System.out.println(bean.getPrice());
//		System.out.println(bean.getCategory());
//		System.out.println(bean.getInputdate());
		
		String sql = "insert into products values(prdseq.nextval,?,?,?,?,?)";
		PreparedStatement ps = null;
		int cnt = -1;
		
		try {
			ps = conn.prepareStatement(sql); // 분석
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getStock());
			ps.setInt(3, bean.getPrice());
			ps.setString(4, bean.getCategory());
			ps.setString(5, bean.getInputdate());
			
			cnt = ps.executeUpdate(); // 실행
	
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

	public int updateData(ProductBean bean) {
		String sql = 
				"update products set name=?,stock=?,price=?,category=?,inputdate=? where id=?";
		PreparedStatement ps = null;
		int cnt = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getStock());
			ps.setInt(3, bean.getPrice());
			ps.setString(4, bean.getCategory());
			ps.setString(5, bean.getInputdate());
			ps.setInt(6, bean.getId());
			
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

	public int deleteData(int id) {
		String sql = "delete products where id="+id;
		PreparedStatement ps = null;
		int cnt = -1;
		try {
			ps = conn.prepareStatement(sql);
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
	
	public void exit() {
		try {
			if(conn != null) {
				System.out.println("DB 접속 끊기");
				conn.close();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
