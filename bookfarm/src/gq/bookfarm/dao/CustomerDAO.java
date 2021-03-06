package gq.bookfarm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import gq.bookfarm.vo.CustomerVO;
import gq.bookfarm.vo.OrdersVO;

public class CustomerDAO 
{

	private final	Logger				log		= Logger.getLogger(this.getClass());
	private Connection					con			=	null;
	private PreparedStatement			pstmt		=	null;
	private ResultSet					rs			=	null;
	
	public Connection getConnection()
	{
		
		Context	ctx;
		try{
						ctx	=	new				InitialContext();
			DataSource	ds	=	(DataSource)	ctx.lookup("java:comp/env/jdbc/MySQL");
						con	=					ds.getConnection();
		}catch(Exception e){
			log.error("CustomerDAO	getConnection error : "+e);
		}
		return con;
	}
	
	public void close(Connection con)
	{
		try{
			if(con != null)
				con.close();
		}catch(Exception e){
			log.error("CustomerDAO	close(Connection con) error : "+e);
		}
	}
	
	public void close(PreparedStatement pstmt)
	{
		try{
			if(pstmt != null)
				pstmt.close();
		}catch(Exception e){
			log.error("CustomerDAO	close(PreparedStatement pstmt) error : "+e);
		}
	}
	
	public void close(ResultSet rs)
	{
		try{
			if(rs != null)
				rs.close();
		}catch(Exception e){
			log.error("CustomerDAO	close(ResultSet rs) error : "+e);
		}
	}
	
	public int insert(CustomerVO vo)
	{
		int		result	=	0;
		String	sql		=	"insert into customer (username, password, firstname, postcode, address1, "
							+ "address2, phone1, email1, gender, newsletter, birthday, last_login, "
							+ "login_cnt, account_created, on_line) "
							+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), 0, now(), ?)";
		try{
				con		=	getConnection();
				pstmt	=	con.prepareStatement(sql);
							con.setAutoCommit(false);
				
							pstmt.setString(1, vo.getUsername());
							pstmt.setString(2, vo.getPassword());
							pstmt.setString(3, vo.getFirstname());
							pstmt.setString(4, vo.getPostcode());
							pstmt.setString(5, vo.getAddress1());
							pstmt.setString(6, vo.getAddress2());
							pstmt.setString(7, vo.getPhone1());
							pstmt.setString(8, vo.getEmail1());
							pstmt.setString(9, vo.getGender());
							pstmt.setString(10, "X");
							pstmt.setDate(11, vo.getBirthday());
							pstmt.setString(12, "X");
				
				result	=	pstmt.executeUpdate();
				
				if(result>0)
							con.commit();			
		}catch(Exception e){
							log.error("CustomerDAO	"
									+ "insert(CustomerVO vo) error : "+e);
			try{
							con.rollback();
			}catch(SQLException e1){
							log.error("CustomerDAO	SQLException error,"
									+ " con.rollback() is not worked : "+e1);
			}
		}finally{
							close(rs);
							close(pstmt);
							close(con);
		}
		return result;
	}
	
	public int totalRows()
	{
		String	sql			=	"select count(*) from customer";		
		int		total_rows	=	0;
		try{
				con			=	getConnection();
				pstmt		=	con.prepareStatement(sql);
				rs			=	pstmt.executeQuery();
			if(rs.next())
				total_rows	=	rs.getInt(1);
		}catch(SQLException e){
								log.error("CustomerDAO	"
										+ "totalRows() error : "+e);
		}finally{
								close(rs);
								close(pstmt);
								close(con);
		}
		return total_rows;
	}
	
	public CustomerVO getRow(int idx)
	{
		CustomerVO	vo		=		null;
		
		try{
			String 	sql		=		"select * from Customer where idx=?";
					con		=		getConnection();
					pstmt	=		con.prepareStatement(sql);
									pstmt.setInt(1, idx);
					rs		=		pstmt.executeQuery();
			if(rs.next()){
					vo		=	new	CustomerVO();
									vo.setIdx(rs.getInt("idx"));
									vo.setUsername(rs.getString("username"));
									vo.setPassword(rs.getString("password"));
									vo.setFirstname(rs.getString("firstname"));
									vo.setLastname(rs.getString("lastname"));
									vo.setPostcode(rs.getString("postcode"));
									vo.setAddress1(rs.getString("address1"));
									vo.setAddress2(rs.getString("address2"));
									vo.setPhone1(rs.getString("phone1"));
									vo.setEmail1(rs.getString("email1"));
									vo.setGender(rs.getString("gender"));
									vo.setNewsletter(rs.getString("newsletter"));
									vo.setBirthday(rs.getDate("birthday"));
									vo.setGrade(rs.getInt("grade"));
									vo.setLast_login(rs.getDate("last_login"));
									vo.setLogin_cnt(rs.getInt("login_cnt"));
									vo.setAccount_created(rs.getDate("account_created"));
									
			}
		}catch (SQLException e){
									log.error("CustomerDAO	"
											+ "getRow() error : "+e);
		}finally{
									close(rs);
									close(pstmt);
									close(con);
		}		
		return vo;	
	}
	
	public CustomerVO find(String phone1, String firstname)
	{
		CustomerVO	vo		=		null;
		
		try{
			String 	sql		=		"select username, password from Customer where phone1=? and firstname=?";
					con		=		getConnection();
					pstmt	=		con.prepareStatement(sql);
									pstmt.setString(1, phone1);
									pstmt.setString(2, firstname);
					rs		=		pstmt.executeQuery();
			if(rs.next()){
					vo		=	new	CustomerVO();
									vo.setUsername(rs.getString("username"));
									vo.setPassword(rs.getString("password"));
			}
		}catch (SQLException e){
									log.error("CustomerDAO	"
											+ "getRow() error : "+e);
		}finally{
									close(rs);
									close(pstmt);
									close(con);
		}		
		return vo;	
	}

	public CustomerVO pwdCheck(String username, String password)
	{
		CustomerVO	vo		=	null;
		try{
			String	sql		=	"select * from customer where "
								+ "username=? and password=?";
					con		=	getConnection();
					pstmt	=	con.prepareStatement(sql);
								pstmt.setString(1, username);
								pstmt.setString(2, password);
					rs		=	pstmt.executeQuery();
			if(rs.next()){
					vo		=	new	CustomerVO();
								vo.setIdx(rs.getInt("idx"));
								vo.setUsername(rs.getString("username"));
								vo.setPassword(rs.getString("password"));
								vo.setFirstname(rs.getString("firstname"));
								vo.setPostcode(rs.getString("postcode"));
								vo.setAddress1(rs.getString("address1"));
								vo.setAddress2(rs.getString("address2"));
								vo.setPhone1(rs.getString("phone1"));
								vo.setEmail1(rs.getString("email1"));
								vo.setGender(rs.getString("gender"));
								vo.setNewsletter(rs.getString("newsletter"));
								vo.setBirthday(rs.getDate("birthday"));
								vo.setGrade(rs.getInt("grade"));
								vo.setLast_login(rs.getDate("last_login"));
								vo.setLogin_cnt(rs.getInt("login_cnt"));
								vo.setAccount_created(rs.getDate("account_created"));
			}else{
								log.error("CustomerDAO	"
										+ "pwdCheck error : rs.next is not exist!!!!");
			}
		}catch(Exception e){
								log.error("CustomerDAO	"
										+ "pwdCheck error : "+e);
		}finally{
								close(rs);
								close(pstmt);
								close(con);
		}		
		return vo;
	}
	
	public int login(int idx)
	{
		int			result		=	0;
		String		sql			=	"update customer set on_line=?, login_cnt=login_cnt+1 where idx=?";
		try {
					con			=	getConnection();
					pstmt		=	con.prepareStatement(sql);
									pstmt.setString(1, "O");
									pstmt.setInt(2, idx);
									
					result		=	pstmt.executeUpdate();
		}catch(SQLException e){
								log.error("CustomerDAO	"
								+ "login count error : "+e);
		}finally{
								close(pstmt);
								close(con);
		}		
		return result;
	}

	public int logout(int idx)
	{
		int			result		=	0;
		String		sql			=	"update customer set on_line=? where idx=?";
		try {
					con			=	getConnection();
					pstmt		=	con.prepareStatement(sql);
									pstmt.setString(1, "X");
									pstmt.setInt(2, idx);
									
					result		=	pstmt.executeUpdate();
		}catch(SQLException e){
								log.error("CustomerDAO	"
								+ "logout check error : "+e);
		}finally{
								close(pstmt);
								close(con);
		}		
		return result;
	}
	
	public int delete(int idx)
	{
			int		result	=	0;
		try {
					con		=	getConnection();
			String	sql		=	"delete from customer where idx=?";
					pstmt	=	con.prepareStatement(sql);
								pstmt.setInt(1, idx);
					result	=	pstmt.executeUpdate();			
		}catch (SQLException e) {			
								log.error("CustomerDAO	"
										+ "delete error : "+e);
		}finally {
								close(pstmt);
								close(con);
		}		
		return result;
	}

	public int updateRow(int idx, CustomerVO vo)
	{
			String	sql		=	"update customer set username=?, password=?, firstname=?, "
								+ "Postcode=?, Address1=?, Address2=?, Phone1=?, Email1=?, "
								+ "gender=?, Birthday=?, "
								+ "last_login=now() where idx=?";
			int		result	=	0;
		try {
					con		=	getConnection();
					pstmt	=	con.prepareStatement(sql);
								pstmt.setString(1, vo.getUsername());
								pstmt.setString(2, vo.getPassword());
								pstmt.setString(3, vo.getFirstname());
								pstmt.setString(4, vo.getPostcode());
								pstmt.setString(5, vo.getAddress1());
								pstmt.setString(6, vo.getAddress2());
								pstmt.setString(7, vo.getPhone1());
								pstmt.setString(8, vo.getEmail1());
								pstmt.setString(9, vo.getGender());
								pstmt.setDate(10, vo.getBirthday());
								pstmt.setInt(11, idx);
								
					result	=	pstmt.executeUpdate();
		}catch (SQLException e){
								log.error("CustomerDAO	"
										+ "updateRow error : "+e);
		}finally{
								close(pstmt);
								close(con);
		}
		return result;
	}
	
	public String getName(int idx)
	{
		String		name	=		null;
		log.info("정한솔 : getName start");
		try{
			String 	sql		=		"select username from Customer where idx=?";
					con		=		getConnection();
					pstmt	=		con.prepareStatement(sql);
									pstmt.setInt(1, idx);
					rs		=		pstmt.executeQuery();
			if(rs.next()){
					name	=		rs.getString("username");
			}else{
									log.error("CustomerDAO	"
									+ "getName error : Name is not found");
			}
		}catch (SQLException e){
									log.error("CustomerDAO	"
											+ "getName error : "+e);
		}finally{
									close(rs);
									close(pstmt);
									close(con);
		}		
		return name;	
	}
	
	
	public Vector<CustomerVO> customerList(int page, int limit)
	{
		// Calc start record through page;
		int start						= (page - 1) * 10; 
		
		Vector<CustomerVO>	customerList	= new Vector<CustomerVO>();
		
		try {
			log.debug("execute customerList DB work Start.");
			con			=	getConnection();
			String sql	= "select * from Customer order by idx desc limit ?,?";
			pstmt		= con.prepareStatement(sql);
			pstmt		.setInt(1, start);
			pstmt		.setInt(2, limit);
			rs			= pstmt.executeQuery();
			
			while (rs.next()) {
				CustomerVO list = new CustomerVO();
				list.setIdx(rs.getInt("idx"));
				list.setUsername(rs.getString("username"));
				list.setPassword(rs.getString("password"));
				list.setFirstname(rs.getString("firstname"));
				list.setPostcode(rs.getString("postcode"));
				list.setAddress1(rs.getString("address1"));
				list.setAddress2(rs.getString("address2"));
				list.setPhone1(	rs.getString("phone1"));
				list.setEmail1(rs.getString("email1"));
				list.setGender(rs.getString("gender"));
				list.setNewsletter(rs.getString("newsletter"));
				list.setBirthday(rs.getDate("birthday"));
				list.setGrade(rs.getInt("grade"));
				list.setLast_login(rs.getDate("last_login"));
				list.setLogin_cnt(rs.getInt("login_cnt"));
				list.setAccount_created(rs.getDate("account_created"));
				
				customerList.add(list);
			}
		} catch (Exception e) {
			log.fatal("execute customerList DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(con);
			close(pstmt);
		}
		log.debug("execute customerList DB work End.");
		return customerList;
	}
	
	
	public Vector<CustomerVO> findIdx(String searchWord)
	{
		Vector<CustomerVO> list=	new Vector<CustomerVO>();
		
		try{
			String 	sql		=		"select idx from Customer where username like ?";
					con		=		getConnection();
					pstmt	=		con.prepareStatement(sql);
									pstmt.setString(1, "%"+searchWord+"%");
					rs		=		pstmt.executeQuery();
			while(rs.next()){
				CustomerVO	vo		=	new	CustomerVO();
									vo.setIdx(rs.getInt("idx"));
									list.add(vo);
			}
		}catch (SQLException e){
									log.error("CustomerDAO	"
											+ "getRow() error : "+e);
		}finally{
									close(rs);
									close(pstmt);
									close(con);
		}		
		return list;	
	}
	
	
	public int customerCountSearchingRows(String criteria, String searchWord)
	{
		log.debug("execute customerCountSearchingRows DB work Start.");
		int					total_rows	= 0;
		Connection			con			= getConnection();
		PreparedStatement	pstmt		= null;
		ResultSet			rs			= null;
		String				sql			= null;
		
		try {
			if (criteria.equals("address") ) {
				sql	= "select count(*) from customer " +
						"where address1 like ? or address2 like ?" +
						"order by idx desc";
				pstmt		= con.prepareStatement(sql);
				pstmt		.setString(1, "%" + searchWord + "%");
				pstmt		.setString(2, "%" + searchWord + "%");
			}
			else if (criteria.equals("phone")) {
				sql	= "select count(*) from customer " +
						"where phone1 like ? or phone2 like ? or phone3 like ?" +
						"order by idx desc";
				pstmt		= con.prepareStatement(sql);
				pstmt		.setString(1, "%" + searchWord + "%");
				pstmt		.setString(2, "%" + searchWord + "%");
				pstmt		.setString(3, "%" + searchWord + "%");
			}
			else if (criteria.equals("email")) {
				sql	= "select count(*) from customer " +
						"where email1 like ? or email2 like ?" +
						"order by idx desc";
				pstmt		= con.prepareStatement(sql);
				pstmt		.setString(1, "%" + searchWord + "%");
				pstmt		.setString(2, "%" + searchWord + "%");
			}
			else {
				sql	= "select count(*) from customer " +
						"where " +  criteria + " like ? " +
						"order by idx desc";
				pstmt		= con.prepareStatement(sql);
				pstmt		.setString(1, "%" + searchWord + "%");
			}

			rs			= pstmt.executeQuery();
			
			if(rs.next())	
				total_rows	= rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.fatal("execute customerCountSearchingRows DB work failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		log.debug("customerCountSearchingRows DB work End. total_rows= " + total_rows);
		return total_rows;	
	}
	
	
	public Vector<CustomerVO> customerSearch(String criteria, String searchWord, int page, int limit)
	{
		Vector<CustomerVO>	customerList= new Vector<CustomerVO>();
		
		Connection			con			= getConnection();
		ResultSet			result		= null;
		PreparedStatement	pstmt		= null;
		
		// Calc start record through page;
		int start					= (page - 1) * 10; 
		
		try {
			log.debug("execute customerSearch DB work Start.");

			String sql	= "select * from customer " +
							"where " +  criteria + " like ? " +
							"order by idx desc limit ?, ?";
			pstmt		= con.prepareStatement(sql);
			pstmt		.setString(1, "%" + searchWord + "%");
			pstmt		.setInt(2, start);
			pstmt		.setInt(3, limit);
			
			log.debug("execute customerSearch DB work... pstmt.toString()" + pstmt.toString());
			
			result		= pstmt.executeQuery();
			
			while (result.next()) {
				CustomerVO list = new CustomerVO(result.getInt("idx"), 
											result.getString("username"), 
											result.getString("password"), 
											result.getString("firstname"), 
											result.getString("lastname"), 
											result.getString("postcode"), 
											result.getString("address1"),
											result.getString("address2"), 
											result.getString("phone1"), 
											result.getString("phone2"), 
											result.getString("phone3"),
											result.getString("email1"), 
											result.getString("email2"), 
											result.getString("gender"),
											result.getString("newsletter"), 
											result.getDate("birthday"), 
											result.getInt("grade"), 
											result.getDate("last_login"),
											result.getInt("login_cnt"),
											result.getDate("account_created"),
											result.getBoolean("on_line"));
				customerList.add(list);
			}
		} catch (Exception e) {
			log.fatal("execute customerSearch DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		log.debug("execute customerSearch DB work End.");
		return customerList;
	}

	public Vector<CustomerVO> customerSearchByAddr(String searchWord, int page, int limit)
	{
		Vector<CustomerVO>	customerList= new Vector<CustomerVO>();
		
		Connection			con			= getConnection();
		ResultSet			result		= null;
		PreparedStatement	pstmt		= null;
		
		// Calc start record through page;
		int start					= (page - 1) * 10; 
		
		try {
			log.debug("execute customerSearchByAddr DB work Start.");

			String sql	= "select * from customer " +
							"where address1 like ? or address2 like ?" +
							"order by idx desc limit ?, ?";
			pstmt		= con.prepareStatement(sql);
			pstmt		.setString(1, "%" + searchWord + "%");
			pstmt		.setString(2, "%" + searchWord + "%");
			pstmt		.setInt(3, start);
			pstmt		.setInt(4, limit);
			
			log.debug("execute customerSearchByAddr DB work... pstmt.toString()" + pstmt.toString());
			
			result		= pstmt.executeQuery();
			
			while (result.next()) {
				CustomerVO list = new CustomerVO(result.getInt("idx"), 
											result.getString("username"), 
											result.getString("password"), 
											result.getString("firstname"), 
											result.getString("lastname"), 
											result.getString("postcode"), 
											result.getString("address1"),
											result.getString("address2"), 
											result.getString("phone1"), 
											result.getString("phone2"), 
											result.getString("phone3"),
											result.getString("email1"), 
											result.getString("email2"), 
											result.getString("gender"),
											result.getString("newsletter"), 
											result.getDate("birthday"), 
											result.getInt("grade"), 
											result.getDate("last_login"),
											result.getInt("login_cnt"),
											result.getDate("account_created"),
											result.getBoolean("on_line"));
				customerList.add(list);
			}
		} catch (Exception e) {
			log.fatal("execute customerSearchByAddr DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		log.debug("execute customerSearchByAddr DB work End.");
		return customerList;
	}
	public Vector<CustomerVO> customerSearchByPhone(String searchWord, int page, int limit)
	{
		Vector<CustomerVO>	customerList= new Vector<CustomerVO>();
		
		Connection			con			= getConnection();
		ResultSet			result		= null;
		PreparedStatement	pstmt		= null;
		
		// Calc start record through page;
		int start					= (page - 1) * 10; 
		
		try {
			log.debug("execute customerSearchByPhone DB work Start.");

			String sql	= "select * from customer " +
							"where phone1 like ? or phone2 like ? or phone3 like ?" +
							"order by idx desc limit ?, ?";
			pstmt		= con.prepareStatement(sql);
			pstmt		.setString(1, "%" + searchWord + "%");
			pstmt		.setString(2, "%" + searchWord + "%");
			pstmt		.setString(3, "%" + searchWord + "%");
			pstmt		.setInt(4, start);
			pstmt		.setInt(5, limit);
			
			log.debug("execute customerSearchByPhone DB work... pstmt.toString()" + pstmt.toString());
			
			result		= pstmt.executeQuery();
			
			while (result.next()) {
				CustomerVO list = new CustomerVO(result.getInt("idx"), 
											result.getString("username"), 
											result.getString("password"), 
											result.getString("firstname"), 
											result.getString("lastname"), 
											result.getString("postcode"), 
											result.getString("address1"),
											result.getString("address2"), 
											result.getString("phone1"), 
											result.getString("phone2"), 
											result.getString("phone3"),
											result.getString("email1"), 
											result.getString("email2"), 
											result.getString("gender"),
											result.getString("newsletter"), 
											result.getDate("birthday"), 
											result.getInt("grade"), 
											result.getDate("last_login"),
											result.getInt("login_cnt"),
											result.getDate("account_created"),
											result.getBoolean("on_line"));
				customerList.add(list);
			}
		} catch (Exception e) {
			log.fatal("execute customerSearchByPhone DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		log.debug("execute customerSearchByPhone DB work End.");
		return customerList;
	}
	public Vector<CustomerVO> customerSearchByEmail(String searchWord, int page, int limit)
	{
		Vector<CustomerVO>	customerList= new Vector<CustomerVO>();
		
		Connection			con			= getConnection();
		ResultSet			result		= null;
		PreparedStatement	pstmt		= null;
		
		// Calc start record through page;
		int start					= (page - 1) * 10; 
		
		try {
			log.debug("execute customerSearchByEmail DB work Start.");

			String sql	= "select * from customer " +
							"where email1 like ? or email2 like ?" +
							"order by idx desc limit ?, ?";
			pstmt		= con.prepareStatement(sql);
			pstmt		.setString(1, "%" + searchWord + "%");
			pstmt		.setString(2, "%" + searchWord + "%");
			pstmt		.setInt(3, start);
			pstmt		.setInt(4, limit);
			
			log.debug("execute customerSearchByEmail DB work... pstmt.toString()" + pstmt.toString());
			
			result		= pstmt.executeQuery();
			
			while (result.next()) {
				CustomerVO list = new CustomerVO(result.getInt("idx"), 
											result.getString("username"), 
											result.getString("password"), 
											result.getString("firstname"), 
											result.getString("lastname"), 
											result.getString("postcode"), 
											result.getString("address1"),
											result.getString("address2"), 
											result.getString("phone1"), 
											result.getString("phone2"), 
											result.getString("phone3"),
											result.getString("email1"), 
											result.getString("email2"), 
											result.getString("gender"),
											result.getString("newsletter"), 
											result.getDate("birthday"), 
											result.getInt("grade"), 
											result.getDate("last_login"),
											result.getInt("login_cnt"),
											result.getDate("account_created"),
											result.getBoolean("on_line"));
				customerList.add(list);
			}
		} catch (Exception e) {
			log.fatal("execute customerSearchByEmail DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		log.debug("execute customerSearchByEmail DB work End.");
		return customerList;
	}
	
}
