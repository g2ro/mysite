package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

import mysite.vo.UserVo;

public class UserDao {

	public int insert(UserVo vo) {
		int count = 0;
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO user (`id`, `name`, `email`, `password`, `gender`, `join_date`)  "
								+ "VALUES (NULL, ?, ?, ?, ?, DATE_FORMAT(NOW(), '%y-%m-%d'))");) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;

	}
	
	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo userVo = null;
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT id, name FROM user WHERE email= ? AND password = ?");
				){
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			

			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				
				userVo = new UserVo();
				userVo.setId(id);
				userVo.setName(name);
			}
			
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return userVo;
		
	}
	public UserVo findById(Long id) {
		UserVo vo = null;
		try(
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT name, email, gender FROM user WHERE id = ?");
				){
			
			pstmt.setLong(1, id);
			
			ResultSet rs= pstmt.executeQuery();
			if(rs.next()) {
				String name = rs.getString(1);
				String email = rs.getString(2);
				String gender = rs.getString(3);
					
				vo = new UserVo();
				
				vo.setId(id);
				vo.setName(name);
				vo.setEmail(email);
				vo.setGender(gender);
				
			}
			rs.close();
			
			
		}catch(SQLException e) {
			System.out.println("error:" + e);
		}
		
		return vo;
		
	}

	public int update(UserVo vo) {
		int count = 0;
		try(
			Connection conn = getConnection();				
			PreparedStatement pstmt1 = conn.prepareStatement("UPDATE user SET name = ?, gender = ? WHERE id = ?");
			PreparedStatement pstmt2 = conn.prepareStatement("UPDATE user SET name = ?, gender = ?, password = ? WHERE id = ?");
				){
			
			if("".equals(vo.getPassword())) {
				pstmt1.setString(1, vo.getName());
				pstmt1.setString(2, vo.getGender());
				pstmt1.setLong(3, vo.getId());
				
				count = pstmt1.executeUpdate();
			}else {
				pstmt2.setString(1, vo.getName());
				pstmt2.setString(2, vo.getGender());
				pstmt2.setString(3, vo.getPassword());
				pstmt2.setLong(4, vo.getId());
				
				count = pstmt2.executeUpdate();
			}
		} catch(SQLException e) {
			System.out.println("error:" + e);

		}
		return count;
		
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.64.3:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return conn;

	}



}
