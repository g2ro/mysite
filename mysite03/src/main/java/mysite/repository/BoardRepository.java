package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	private SqlSession sqlSession;
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert", vo);
	}
	
	public int findSize() {
		int count = 0;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT count(*) from board");
				ResultSet rs = pstmt.executeQuery();
				){
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("Board findSize error :" + e);
		}
		return count;
	}
	
	public List<BoardVo> findByPage(int currentPage, int pageSize) {
		List<BoardVo> result = new ArrayList<BoardVo>();
		ResultSet rs = null;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT b.id, title, name, hit, reg_date, g_no, o_no, depth, b.user_id " +
						"FROM board b " +
						"	JOIN user u ON(b.user_id = u.id) " +
						"ORDER BY g_no desc, o_no asc " +
						"LIMIT ? OFFSET ?");
				){
			pstmt.setInt(1, pageSize);
			pstmt.setInt(2, (currentPage - 1) * pageSize);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				Long userId = rs.getLong(9);
				
				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setUserName(name);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setG_no(gNo);
				vo.setO_no(oNo);
				vo.setDepth(depth);
				vo.setUserId(userId);
				
				result.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("findByPage error : " + e);
		}
		return result;
	}
	
	public BoardVo findById(Long id) {
		return sqlSession.selectOne("board.findById", id);
	}
	
	public BoardVo findByIdWithUserId(Long id, Long userId) {
		return sqlSession.selectOne("board.findByIdWithUserId", Map.of("id",id,"userId",userId));
	}
	
	public int update(Long id, String title, String content) {
		return sqlSession.update("board.update", Map.of("title", title, "contents", content, "id", id));
	}
	
	public int updateHit(Long id) {
		return sqlSession.update("board.updateHit", id);
	}
	
	public int deleteById(Long id, Long userId) {
		return sqlSession.delete("board.deleteById", Map.of("id", id, "userId", userId));
	}
	
	public int insertReply(Long id, String title, String content, Long userId) {
		ResultSet rs = null;
		int gNo = 0;
		int oNo = 0;
		int depth = 0;
		
		int count = 0;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmtSelect = conn.prepareStatement(
						"SELECT g_no, o_no, depth " +
						"FROM board " +
						"WHERE id = ? "
						);
				
				PreparedStatement pstmtUpdate = conn.prepareStatement(
						"UPDATE board " +
						"SET o_no = o_no + 1 " +
						"WHERE g_no = ? " +
						"	AND o_no >= ? "
						);
				
				PreparedStatement pstmtInsert = conn.prepareStatement(
						"INSERT INTO board " +
						"VALUES (null, ?, ?, 0, now(), ?, ?, ?, ?)"
						);
				){
			
			pstmtSelect.setLong(1, id);
			rs = pstmtSelect.executeQuery();
			if(rs.next()) {
				gNo = rs.getInt(1);
				oNo = rs.getInt(2) + 1;
				depth = rs.getInt(3) + 1;
			}
			
			pstmtUpdate.setInt(1, gNo);
			pstmtUpdate.setInt(2, oNo);
			pstmtUpdate.executeUpdate();
			
			pstmtInsert.setString(1, title);
			pstmtInsert.setString(2, content);
			pstmtInsert.setInt(3, gNo);
			pstmtInsert.setInt(4, oNo);
			pstmtInsert.setInt(5, depth);
			pstmtInsert.setLong(6, userId);
			
			count = pstmtInsert.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("InsertReply error : " + e);
		}
		return count;
	}
	
	public List<BoardVo> findByPageWithSearch(int currentPage, int pageSize, String keyword) {
		return sqlSession.selectList("board.findByPageWithSearch", Map.of("keyword", keyword, "pageSize", pageSize, "offset", (currentPage - 1) * pageSize));
	}

	public int findByPageWithSearchCount(String keyword) {
		return sqlSession.selectOne("board.findByPageWithSearchCount", keyword);
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
