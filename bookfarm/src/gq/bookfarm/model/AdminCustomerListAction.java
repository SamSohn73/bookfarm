package gq.bookfarm.model;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import gq.bookfarm.action.Action;
import gq.bookfarm.action.ActionForward;
import gq.bookfarm.dao.AdminDAO;
import gq.bookfarm.dao.CustomerDAO;
import gq.bookfarm.vo.AdminVO;
import gq.bookfarm.vo.CustomerVO;
import gq.bookfarm.vo.PageVO;

public class AdminCustomerListAction implements Action
{
	private final Logger log = Logger.getLogger(this.getClass());
	
	private String path;

	public AdminCustomerListAction(String path) 
	{
		super();
		this.path = path;
		log.debug("AdminCustomerListAction Constructor. Destination path = " + path);
	}
	
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res)
	{
		log.debug("AdminCustomerListAction execute Start.");
		
		HttpSession	session		= req.getSession();
		AdminVO		adminVO		= (AdminVO) session.getAttribute("adminVO");
		AdminDAO	adminDAO	= new AdminDAO();
		if (adminDAO.isAdmin(adminVO) == null) {
			log.info("AdminCustomerListAction execute Authorization Fail!!!!!!!!!!!!!!!!");
			path="error.html";
			return new ActionForward(path, false);
		}
		
		int page = 1;
		if (req.getParameter("page") != null)
			page = Integer.parseInt(req.getParameter("page"));
		
		CustomerDAO			dao		= new CustomerDAO();
		
		int totalRows				= dao.totalRows();
		int limit					= 10;
		
		int totalPages				= (int) ((double) totalRows / limit + 0.999999);
		int startPage				= (((int) ((double) page / 10 + 0.9)) -1) * 10 + 1;
		int endPage					= startPage + 10 -1;
		if (endPage > totalPages)	endPage = totalPages;
		
		PageVO pageInfo				= new PageVO();
		pageInfo					.setPage(page);
		pageInfo					.setStartPage(startPage);
		pageInfo					.setEndPage(endPage);
		pageInfo					.setTotalRows(totalRows);
		pageInfo					.setTotalPages(totalPages);
		req							.setAttribute("pageInfo", pageInfo);
		
		log.debug("AdminCustomerListAction execute totalRows= "		+ totalRows);
		log.debug("AdminCustomerListAction execute totalPages= "	+ totalPages);
		log.debug("AdminCustomerListAction execute startPage= "		+ startPage);
		log.debug("AdminCustomerListAction execute endPage= "		+ endPage);
		log.debug("AdminCustomerListAction execute page= "			+ page);
		
		Vector<CustomerVO>	customers	= dao.customerList(page, limit);
		if (customers != null)			req.setAttribute("customers", customers);
		// if result failed change path here
		else					path="error.html";
		
		log.debug("AdminCustomerListAction execute End.");
		return new ActionForward(path, false);
	}
}
