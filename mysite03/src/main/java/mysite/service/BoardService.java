package mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;

@Service
public class BoardService {
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	public void addContents(BoardVo vo) {
		if(vo.getId() == 0) {
			boardRepository.insert(vo);
			return;
		}
		boardRepository.insertReply(vo.getId(), vo.getTitle(), vo.getContents(), vo.getUserId());
		//insertReply수정 필요
	}
	
	public BoardVo getContents(long id) {
		boardRepository.updateHit(id);
		return boardRepository.findById(id);
	}
	
	public BoardVo getContents(long id, Long userId) {
		return boardRepository.findByIdWithUserId(id, userId);
	}
	
	public void updateContents(BoardVo vo) {
		boardRepository.update(vo.getId(), vo.getTitle(), vo.getContents());
	}

	public void deleteContents(Long id, Long userId) {
		boardRepository.deleteById(id, userId);
	}
	
	public Map<String, Object> getContentsList(String p, String kwd){
		Integer currentPage = 0;
		String keyword = kwd;
		
		if(p == null) {
			currentPage = 1;
		}else {
			currentPage = Integer.parseInt(p);
		}

		if(kwd == null || kwd.isEmpty()) {
			keyword = "";
		}
		
		
		// view의 pagination를 위한 데이터 값 계산
		List<BoardVo> list = null;
		
		int pageSize = 5;
		int pageBlock = 5;
		int listSize = boardRepository.findByPageWithSearchCount(keyword);
		list = boardRepository.findByPageWithSearch(currentPage, pageSize, keyword);
		
		int totalPage = (int) Math.ceil((double)listSize / pageSize);
		if(totalPage < 0) {
			totalPage = 1;
		}
		
		int startPage = currentPage - ((int)pageBlock/2);
		int endPage = currentPage + ((int)pageBlock/2);
		
		//1. startPage가 0보다 작을 경우
		if(startPage < 1) {
			startPage = 1;
			endPage = Math.min(pageBlock, totalPage);
		}
		
		//2. endPage가 totalPage보다 클 경우
		if(endPage>totalPage) {
			endPage = totalPage;
			startPage = Math.max((endPage - (pageBlock - 1)), 1);
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("list", list);
		data.put("listSize", listSize);
		data.put("startPage", startPage);
		data.put("endPage", endPage);
		data.put("totalPage", totalPage);
		data.put("pageSize", pageSize);
		data.put("keyword", keyword);
		data.put("currentPage", currentPage);
		
		return data;
	}
	
	
}
