package xyz.bookfarm.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import xyz.bookfarm.action.Action;
import xyz.bookfarm.action.ActionForward;
import xyz.bookfarm.dao.AdminDAO;
import xyz.bookfarm.vo.AdminVO;

public class AdminLoginAction implements Action
{
	private final	Logger				log		= Logger.getLogger(this.getClass());
	private			String				path;
	
	public AdminLoginAction(String path) {
		super();
		log.debug("AdminLoginAction create Start.");
		this.path  = path;
		log.debug("AdminLoginAction create End. path=" + path);
	}
	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception
	{
		log.debug("AdminLoginAction execute Start.");
		String	user_name	= req.getParameter("user_name");
		String	user_pass	= req.getParameter("user_pass");
		log.debug("AdminLoginAction execute Start. 111111");
		AdminDAO	dao		= new AdminDAO();
		AdminVO		vo		= dao.isLogin(user_name, user_pass);
		log.debug("AdminLoginAction execute Start. 222222");
		if(vo!=null) { //null값이 아닌 경우 로그인 성공
			HttpSession session	= req.getSession();
			session				.setAttribute("vo", vo);
			log.debug("AdminLoginAction execute Start. 333333");
		}else {
			log.debug("AdminLoginAction execute Admin user_name, password not found. username, password=" + user_name + "," + user_pass);
			path="error.jsp";
		}	

		log.debug("AdminLoginAction execute End.");
		return new ActionForward(path,true);
	}

}
