package mysite.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	private SqlSession sqlSession;
	
	public SiteRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public SiteVo getSite() {
		return sqlSession.selectOne("site.getSite");
	}

	public void updateSite(String title, String welcom, String url, String description) {
		sqlSession.update("site.updateSite", Map.of("title",title,"welcom",welcom,"url",url,"description",description));
		
	}
	
	
}
