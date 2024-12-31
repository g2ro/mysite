package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	
	
	public int insert(BoardVo vo) {
		int count = 0;
		int gNo = -1;
		try (
				Connection conn =getConnection();
				PreparedStatement pstmtGetGno = conn.prepareStatement(
						"SELECT IFNULL(MAX(g_no), 0) AS max_g_no " +
						"FROM board"
						);
				ResultSet rs = pstmtGetGno.executeQuery();
				PreparedStatement pstmt = conn.prepareStatement(
						"INSERT INTO board " +
						"VALUES (null, ?, ?, 0, now(), ?, 1, 0, ?)");
				

				){
			if(rs.next()) {
				gNo = rs.getInt(1);
			}
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, gNo + 1);
			pstmt.setLong(4, vo.getUserId());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Board INSERT error:" + e);
		}
		
		return count;
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
		BoardVo vo = new BoardVo();
		ResultSet rs = null;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT id, title, contents, hit, reg_date, g_no, o_no, depth, user_id " +
						"FROM board " +
						"WHERE id = ? "
						);
				){
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				Long userId = rs.getLong(9);
				
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setG_no(gNo);
				vo.setO_no(oNo);
				vo.setDepth(depth);
				vo.setUserId(userId);
				
			}
		} catch (SQLException e) {
			System.out.println("findbyid error :" + e);
		}
		return vo;
	}
	
	public BoardVo findByIdWithUserId(Long id, Long userId) {
		BoardVo vo = new BoardVo();
		ResultSet rs = null;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"SELECT id, title, contents, hit, reg_date, g_no, o_no, depth " +
						"FROM board " +
						"WHERE id = ? " +
						"AND user_id = ? "
						);
				){
			pstmt.setLong(1, id);
			pstmt.setLong(2, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String reg_date = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				
				
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setG_no(gNo);
				vo.setO_no(oNo);
				vo.setDepth(depth);
				vo.setUserId(userId);
				
			}
		} catch (SQLException e) {
			System.out.println("findbyid error :" + e);
		}
		return vo;
	}
	
	public int update(Long id, String title, String content) {
		int count = 0;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"UPDATE board " +
						"set title = ?, contents = ? " +
						"WHERE id = ? "
						);
				
				){
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setLong(3, id);
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Board Update error :" + e);
		}
		return count;
	}
	
	public int updateHit(Long id) {
		int count = 0;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"UPDATE board " +
						"SET hit = hit + 1 " +
						"WHERE id = ? "
						);
				){
			pstmt.setLong(1, id);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("updateHit error :" + e);
		}
		return count;
	}
	
	public int deleteById(Long id, Long UserId) {
		int count = 0;
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"DELETE FROM board " +
						"WHERE id = ? and user_id = ?"
						);
				){
			pstmt.setLong(1, id);
			pstmt.setLong(2, UserId);
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("deleteById error :" + e);
		}
		return count;
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
	    List<BoardVo> result = new ArrayList<>();
	    ResultSet rs = null;
	    try (
	            Connection conn = getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(
	                    "SELECT b.id, title, name, hit, reg_date, g_no, o_no, depth, b.user_id " +
	                    "FROM board b " +
	                    "    JOIN user u ON (b.user_id = u.id) " +
	                    "WHERE title LIKE ? " + // 제목 검색 조건 추가
	                    "ORDER BY g_no DESC, o_no ASC " +
	                    "LIMIT ? OFFSET ?");
	    ) {
	        pstmt.setString(1, "%" + keyword + "%"); // 제목 검색어
	        pstmt.setInt(2, pageSize);
	        pstmt.setInt(3, (currentPage - 1) * pageSize);
	        
	        rs = pstmt.executeQuery();
	        while (rs.next()) {
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
	        System.out.println("findByPageWithSearch error : " + e);
	    }
	    return result;
	}

	public int findByPageWithSearchCount(String keyword) {
	    int count = 0;
	    ResultSet rs = null;
	    try (
	            Connection conn = getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(
	                    "SELECT COUNT(*) " +
	                    "FROM board b " +
	                    "    JOIN user u ON (b.user_id = u.id) " +
	                    "WHERE title LIKE ?"
	            );
	    ) {
	        pstmt.setString(1, "%" + keyword + "%"); // 제목 검색어
	        
	        rs = pstmt.executeQuery();
	        if (rs.next()) {
	            count = rs.getInt(1); // 결과의 첫 번째 컬럼에서 개수를 가져옵니다.
	        }
	    } catch (SQLException e) {
	        System.out.println("findByPageWithSearchCount error : " + e);
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
