package gq.bookfarm.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import gq.bookfarm.action.Action;
import gq.bookfarm.action.ActionForward;
import gq.bookfarm.dao.ReviewDAO;
import gq.bookfarm.vo.ReviewVO;

public class AdminReviewsViewAction implements Action {
	
	private final	Logger				log		= Logger.getLogger(this.getClass());
	private String path;
	
	public AdminReviewsViewAction(String path) {
		super();
		this.path = path;
	}
	
	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int		page				=	Integer.parseInt(req.getParameter("page"));
		int		idx					=	Integer.parseInt(req.getParameter("idx"));
		int		products_idx		=	Integer.parseInt(req.getParameter("p"));
		int		parents_idx			=	Integer.parseInt(req.getParameter("par"));
		int		category_idx		=	Integer.parseInt(req.getParameter("cat"));
		String	searchCondition		=	req.getParameter("con");
		String	searchWord			=	req.getParameter("word");
		
		
		ReviewDAO 	dao				=	new ReviewDAO();
		ReviewVO	vo				=	dao.getRow(idx);
			
		if(vo!=null) 
		{
										req.setAttribute("vo", vo);
					path			+=	"?idx="+idx+"&page="+page+"&p="+products_idx+"&par="+parents_idx
										+"&cat="+category_idx+"&con="+searchCondition+"&word="+searchWord;
				
		}
		else
		{
										log.error("QQQQQQQQ ReviewsViewAction error : vo is empty");
					path			=	"";
		}
		
		return new ActionForward(path, false);
	}

}
