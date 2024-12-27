package mysite.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.service.GuestbookService;
import mysite.vo.GuestbookVo;
@RequestMapping("/guestbook")
@Controller
public class GuestbookController {
	private GuestbookService guestbookService;
	
	public GuestbookController(GuestbookService guestbookService) {
		this.guestbookService = guestbookService;
	}
	
	
	@RequestMapping("")
	public String guestbook(Model model) {
		List<GuestbookVo> list = guestbookService.getContentsList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	@RequestMapping("/insert")
	public String insert(GuestbookVo vo) {
		guestbookService.addContents(vo);
		return "redirect:/guestbook";
		
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id) {
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id, @RequestParam("password") String password) {
		guestbookService.deleteContents(id, password);
		return "redirect:/guestbook";
	}
	
}
