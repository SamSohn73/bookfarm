package xyz.bookfarm.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import xyz.bookfarm.action.Action;
import xyz.bookfarm.action.ActionForward;
import xyz.bookfarm.dao.CustomerDAO;
import xyz.bookfarm.vo.CustomerVO;

public class CustomerLogoutAction implements Action {
	private final	Logger				log		= Logger.getLogger(this.getClass());
	private String path;
	
	public CustomerLogoutAction(String path) {
		super();
		this.path = path;
	}

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		HttpSession		session	=	req.getSession();
		CustomerVO		vo		=	(CustomerVO)session.getAttribute("LoginedUserVO");
		int				idx		=	vo.getIdx();
						session	=	req.getSession(false);
		if(session.getAttribute("LoginedUserVO") != null)
		{			
			CustomerDAO	dao		=	new	CustomerDAO();
									dao.logout(idx);
									session.invalidate();
									log.info("Successfully logout...");
		}
		else
		{
						path	=	"";
		}
		
		return new ActionForward(path, true);
	}

}
