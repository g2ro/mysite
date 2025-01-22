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
	public BoardVo insertPrepare(Long id) {
		return sqlSession.selectOne("board.insertPrepare", id);
	}
	public int insertUpdate(int gNo, int oNo) {
		return sqlSession.update("board.insertUpdate", Map.of("gNo", gNo, "oNo", oNo));
	}
	public int insertReply(String title, String content, int gNo, int oNo, int depth,Long userId) {
		Map<String, Object> map = Map.of("title",title,"contents",content, "gNo",gNo,"oNo",oNo, "depth",depth,"userId",userId);
		return sqlSession.insert("board.insertReply", map);
	}
	public List<BoardVo> findByPageWithSearch(int currentPage, int pageSize, String keyword) {
		return sqlSession.selectList("board.findByPageWithSearch", Map.of("keyword", keyword, "pageSize", pageSize, "offset", (currentPage - 1) * pageSize));
	}

	public int findByPageWithSearchCount(String keyword) {
		return sqlSession.selectOne("board.findByPageWithSearchCount", keyword);
	}


	

	

}
