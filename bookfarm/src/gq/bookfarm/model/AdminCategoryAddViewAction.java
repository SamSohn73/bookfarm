package gq.bookfarm.model;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import gq.bookfarm.action.Action;
import gq.bookfarm.action.ActionForward;
import gq.bookfarm.dao.AdminDAO;
import gq.bookfarm.dao.CategoryDAO;
import gq.bookfarm.vo.AdminVO;
import gq.bookfarm.vo.CategoryVO;

public class AdminCategoryAddViewAction implements Action
{
	private final Logger log = Logger.getLogger(this.getClass());
	
	private String path;

	public AdminCategoryAddViewAction(String path) 
	{
		super();
		this.path = path;
		log.debug("AdminCategoryAddViewAction Constructor. Destination path = " + path);
	}
	
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res)
	{
		log.debug("AdminCategoryAddViewAction execute Start.");

		HttpSession	session		= req.getSession();
		AdminVO		adminVO		= (AdminVO) session.getAttribute("adminVO");
		AdminDAO	adminDAO	= new AdminDAO();
		if (adminDAO.isAdmin(adminVO) == null) {
			log.info("AdminCategoryAddViewAction execute Authorization Fail!!!!!!!!!!!!!!!!");
			path="error.html";
			return new ActionForward(path, false);
		}
		
		int curPage						= Integer.parseInt(req.getParameter("page"));
		
		CategoryDAO			dao			= new CategoryDAO();
		Vector<CategoryVO>	categories	= dao.categoryList();
		if (categories != null) {
			req.setAttribute("categories", categories);
			path += "?page=" + curPage;
		}
		// if result failed change path here
		else {
			log.debug("AdminCategoryAddViewAction execute Failed!!!!!!!!!!!!!!!!!!!!");
			path="error.html";
		}
		
		log.debug("AdminCategoryAddViewAction execute End.");
		return new ActionForward(path, false);
	}
}

