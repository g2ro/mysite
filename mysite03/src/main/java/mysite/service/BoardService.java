package mysite.service;

import org.springframework.stereotype.Service;

@Service
public class BoardService {
	
	public void addContents(BoardVo vo) {
	}
	
	public BoardVo getContents(long id) {
	}
	
	public BoardVo getContents(long id, Long userId) {
	}
	
	public void updateContents(BoardVo vo) {
	}
	
	public void eleteContents(Long id, Long userId) {
	}
	
	public Map<String, Object> getContentsList(int currentage, String keyword){
		List<BoardVo> list = null;
		// view의 pagination를 위한 데이터 값 계산
	}
	
	
}
