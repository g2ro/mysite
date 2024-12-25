package mysite.controller;

import java.util.Map;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.ActionServlet.Action;
import mysite.controller.action.board.BoardListAction;
import mysite.controller.action.board.DeleteBoard;
import mysite.controller.action.board.ModifyBoard;
import mysite.controller.action.board.ModifyFormBoard;
import mysite.controller.action.board.ViewBoard;
import mysite.controller.action.board.WriteBoard;
import mysite.controller.action.board.WriteForm;
import mysite.controller.action.board.WriteReply;


@WebServlet("/board")
public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
			"writeform", new WriteForm(),
			"writeboard", new WriteBoard(),
			"view", new ViewBoard(),
			"modifyform", new ModifyFormBoard(),
			"modify", new ModifyBoard(),
			"delete", new DeleteBoard(),
			"writereply", new WriteReply()
			);

	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new BoardListAction());
	}

	

}
