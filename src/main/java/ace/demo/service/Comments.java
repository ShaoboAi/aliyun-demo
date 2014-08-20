package ace.demo.service;

import ace.demo.service.RDS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Comments {
	private RDS db = RDS.getInstance();;
	public Comments() {
	}
	
	public void put(String name) {
		String sql = "INSERT INTO blog values( '"+name+"', 'undefined.png', 0) ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rs ;
		try {
			conn = db.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e){
				e.printStackTrace();
			}
		}		
	}
	public  List<String> get()  {
		List<String> blogs = new ArrayList<String>();
		String sql = "select * from blog";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = db.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				blogs.add(rs.getString("blog"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
		return blogs;
		
	}
}
