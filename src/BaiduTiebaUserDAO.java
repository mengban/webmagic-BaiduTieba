import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaiduTiebaUserDAO {
	
	public static Connection getConn() {    
		//Statement stmt = null;
		Connection conn = null;
		String driver="com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/webmagic";
	    String user="root";
	    String psd="187127";
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,psd);
			//stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;

	}
	
	public static int add(BaiduTiebaUser bduser) {
		Connection conn=getConn();
		int i=0;
		String sql = "INSERT INTO bduser_a_copy (id, sex, barage,concernnum, concernednum, postnum, url) VALUES (?, ?, ?, ?, ?, ?,?);";
		PreparedStatement ps;
		try {		
			ps = conn.prepareStatement(sql);
			ps.setString(1, bduser.getId());
			ps.setInt(2, bduser.getSex());
			ps.setDouble(3, bduser.getBarAge());
			ps.setInt(4, bduser.getConcernNum());
			ps.setInt(5, bduser.getConcernedNum());
			ps.setInt(6, bduser.getPostNum());
			ps.setString(7, bduser.getUrl());
			i=ps.executeUpdate();
			conn.close();
			//return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

}
