package mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.security.Auth;
import mysite.security.AuthUser;
import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

@RequestMapping("/board")
@Controller
public class BoardController {
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@RequestMapping("")
	public String board(Model model,
			@RequestParam(name = "p", required = false) Integer p ,
			@RequestParam(name = "kwd", required = false) String kwd) {
		Integer currentPage = 0;
		if(p == null) {
			currentPage = 1;
		}else {
			currentPage = p;
		}

		if(kwd == null || kwd.isEmpty()) {
			kwd = "%";
		}
		
		Map<String, Object> data = boardService.getContentsList(currentPage, kwd);
		model.addAttribute("data", data);
		model.addAttribute("kwd", kwd);
		model.addAttribute("currentPage", currentPage);
		return "board/list";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value = "/write/{id}", method = RequestMethod.POST)
	public String write(@AuthUser UserVo authUser,BoardVo vo, @PathVariable("id") Long id) {
		vo.setUserId(authUser.getId());
		vo.setId(id);
		boardService.addContents(vo);
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value = "/write/{id}", method = RequestMethod.GET)
	public String write(@AuthUser UserVo authUser, @PathVariable("id") Long id, Model model) {
		model.addAttribute("id", id);
		return "board/write";
	}
	
	
	@RequestMapping("/view/{id}")
	public String view(@PathVariable("id") Long id, Model model) {
		model.addAttribute("Bvo", boardService.getContents(id));
		return "board/view";
		
	}
	
	@RequestMapping("/modify")
	public String modify(@RequestParam("id") Long id, @RequestParam("userId") Long userId, Model model) {
		model.addAttribute("Bvo", boardService.getContents(id, userId));
		return "board/modify";
	}
	
	@RequestMapping("/update")
	public String update(BoardVo vo) {
		boardService.updateContents(vo);
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping("/delete")
	public String delete(@AuthUser UserVo authUser, @RequestParam("id") Long id) {
		boardService.deleteContents(id, authUser.getId());
		return "redirect:/board";
	}
}
