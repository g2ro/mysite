package mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private DataSource dataSource;
	
	private SqlSession sqlSession;
	
	
	
	public UserRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	public int insert(UserVo vo) {
		return sqlSession.insert("user.insert", vo);
		
	}
	
	public UserVo findByEmailAndPassword(String email, String password) {
//		StopWatch sw = new StopWatch();
//		sw.start();
		
		return sqlSession.selectOne("user.findByEmailAndPassword", Map.of("email", email, "password", password));
		
//		sw.stop();
//		long totalTime = sw.getTotalTimeMillis();
//		System.out.println("[Execution Time][UserRepository.findByEmailAndPassword] " + totalTime + "millisecond");
		// 이렇게 하면 안됨. AOP(Aspect)를 이용해서 진행하는 것이 추천됨. 
		
	}
	public UserVo findById(Long id) {
		return sqlSession.selectOne("user.findById", id);
		
	}

	public int update(UserVo vo) {
		
		return sqlSession.update("user.update", vo);
		
	}
	
//	private Connection getConnection() throws SQLException {
//		Connection conn = null;
//
//		try {
//			Class.forName("org.mariadb.jdbc.Driver");
//
//			String url = "jdbc:mariadb://192.168.64.3:3306/webdb";
//			conn = DriverManager.getConnection(url, "webdb", "webdb");
//		} catch (ClassNotFoundException e) {
//			System.out.println("드라이버 로딩 실패: " + e);
//		}
//
//		return conn;
//
//	}



}
