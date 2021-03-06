package gq.bookfarm.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import gq.bookfarm.action.Action;
import gq.bookfarm.action.ActionForward;
import gq.bookfarm.dao.AdminDAO;
import gq.bookfarm.dao.ProductDAO;
import gq.bookfarm.vo.AdminVO;
import gq.bookfarm.vo.ProductVO;

public class AdminProductViewAction implements Action
{
	private final	Logger		log		=	Logger.getLogger(this.getClass());
	private 		String		path;
	
	public AdminProductViewAction(String path) {
		super();
		log.debug("AdminProductViewAction create Start.");
		this.path  = path;
		log.debug("AdminProductViewAction create End. path=" + path);
	}
	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		log.debug("AdminProductViewAction execute Start.");
		
		HttpSession	session		= req.getSession();
		AdminVO		adminVO		= (AdminVO) session.getAttribute("adminVO");
		AdminDAO	adminDAO	= new AdminDAO();
		if (adminDAO.isAdmin(adminVO) == null) {
			log.info("AdminProductSearchAction execute Authorization Fail!!!!!!!!!!!!!!!!");
			path="error.html";
			return new ActionForward(path, false);
		}
		
		int			current_page			=	Integer.parseInt(req.getParameter("page"));
		int			idx						=	Integer.parseInt(req.getParameter("idx"));
		log.debug("AdminProductViewAction execute Page=" + current_page);
		
		ProductDAO 	dao						=	new ProductDAO();
		ProductVO 	productVO				=	dao.productGetRow(idx);
		
		if(productVO!=null) {
			req.setAttribute("productVO", productVO);
			req.setAttribute("page", current_page);
			path+="?page="+current_page;
		} else {
			log.debug("AdminProductViewAction execute Failed.");
			path="error.html"; 
		}
		
		log.debug("AdminProductViewAction execute End.");
		return new ActionForward(path, false);
	}
}
