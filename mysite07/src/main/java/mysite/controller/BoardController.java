package mysite.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
			@RequestParam(name = "p", required = false) String p ,
			@RequestParam(name = "kwd", required = false) String kwd) {
		
		Map<String, Object> data = boardService.getContentsList(p, kwd);
		model.addAttribute("data", data);

		return "board/list";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
//	@Auth
	@RequestMapping(value = "/write/{id}", method = RequestMethod.POST)
	public String write(Authentication authentication,BoardVo vo, @PathVariable("id") Long id) {
		UserVo authUser = (UserVo)authentication.getPrincipal();
		vo.setUserId(authUser.getId());
		vo.setId(id);
		boardService.addContents(vo);
		return "redirect:/board";
		
		
	}
	
//	@Auth
	@RequestMapping(value = "/write/{id}", method = RequestMethod.GET)
	public String write(Authentication authentication, @PathVariable("id") Long id, Model model) {
		UserVo authUser = (UserVo)authentication.getPrincipal();
		model.addAttribute("id", id);
		return "board/write";
	}
	
	
	@RequestMapping("/view/{id}")
	public String view(@PathVariable("id") Long id, Model model) {
		model.addAttribute("Bvo", boardService.getContents(id));
		return "board/view";
		
	}
	
//	@Auth
	@RequestMapping("/modify/{id}")
	public String modify(@PathVariable("id") Long id, Authentication authentication, Model model) {
		UserVo authUser = (UserVo)authentication.getPrincipal();
		model.addAttribute("Bvo", boardService.getContents(id, authUser.getId()));
		return "board/modify";
	}
	
	@RequestMapping("/update")
	public String update(BoardVo vo) {
		boardService.updateContents(vo);
		return "redirect:/board";
	}
	
//	@Auth
	@RequestMapping("/delete")
	public String delete(Authentication authentication, @RequestParam("id") Long id) {
		UserVo authUser = (UserVo)authentication.getPrincipal();
		boardService.deleteContents(id, authUser.getId());
		return "redirect:/board";
	}
}
