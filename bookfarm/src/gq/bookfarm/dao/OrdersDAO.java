package gq.bookfarm.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import gq.bookfarm.vo.OrdersVO;

public class OrdersDAO
{
	private final Logger log = Logger.getLogger(this.getClass());
	
	public Connection getConnection()
	{
		Connection con = null;
		
		try {
			log.debug("DB getConnection Start.");
			Context ctx		= new InitialContext();
			Context envctx	= (Context) ctx.lookup("java:comp/env");
			DataSource ds	= (DataSource) envctx.lookup("jdbc/MySQL");
			
			con = ds.getConnection();
			log.debug("DB getConnection End.");
		} catch (Exception e) {
			log.fatal("DB getConnection Failed !!!!!!!!!!");
			e.printStackTrace();
		}
		
		return con;
	}

	public void close(Connection con)
	{
		try {
			log.debug("DB con close Start.");
			if (con != null)		con.close();
			log.debug("DB con close End.");
		} catch (SQLException e) {
			log.fatal("DB con close Failed.");
			e.printStackTrace();
		}
	}
	
	public void close(PreparedStatement pstmt)
	{
		try {
			log.debug("DB pstmt close Start.");
			if (pstmt != null)		pstmt.close();
			log.debug("DB pstmt close End.");
		} catch (SQLException e) {
			log.fatal("DB pstmt close Failed.");
			e.printStackTrace();
		}
	}
	
	
	public void close(Statement stmt)
	{
		try {
			log.debug("DB stmt close Start.");
			if (stmt != null)		stmt.close();
			log.debug("DB stmt close End.");
		} catch (SQLException e) {
			log.fatal("DB stmt close Failed.");
			e.printStackTrace();
		}
	}
	
	
	public void close(ResultSet rs)
	{
		try {
			log.debug("DB rs close Start.");
			if (rs != null)		rs.close();
			log.debug("DB rsclose End.");
		} catch (SQLException e) {
			log.fatal("DB rs close Failed !!!!!!!!!!");
			e.printStackTrace();
		}
	}
	
	public void close(Connection con , PreparedStatement pstmt)
	{
			try {
				log.debug("DB close Start.");
				if (pstmt != null)		pstmt.close();
				if (con != null)		con.close();
				log.debug("DB close End.");
			} catch (SQLException e) {
				log.fatal("DB close Failed.");
				e.printStackTrace();
			}
	}
	
	
	public void close(Connection con , PreparedStatement pstmt, ResultSet rs)
	{
		
		
		try {
			log.debug("DB close Start.");
			if (rs != null)
				rs.close();
			close(con, pstmt);
			log.debug("DB close End.");
		} catch (SQLException e) {
			log.fatal("DB close Failed !!!!!!!!!!");
			e.printStackTrace();
		}
	}
	
		
	public int ordersInsert(int customers_idx, String delivery_name,
			String delivery_postcode, String delivery_address1, String delivery_address2, 
			String delivery_phone1, String delivery_phone2, String delivery_phone3, String delivery_email1, String delivery_email2,
			String billing_name, String billing_postcode, String billing_address1, String billing_address2, 
			String billing_phone1, String billing_phone2, String billing_phone3, String billing_email1, String billing_email2, 
			String payment_method, float final_price)
	{
		int					result	= 0;
		PreparedStatement	pstmt	= null;
		Connection			con		= getConnection();

		// You can use now() sql system function to get the current time
		//Date date		= null;
		//java.util.Date utilDate = new java.util.Date();
		//date = new java.sql.Date(utilDate.getTime());
		
		try {
			log.debug("execute orderInsert DB work Start.");
			con.setAutoCommit(false);
			
			String sql		= "insert into orders (customers_idx, delivery_name, " + 
								"delivery_postcode, delivery_address1, delivery_address2, delivery_phone1, " + 
								"delivery_phone2, delivery_phone3, delivery_email1, delivery_email2," + 
								"billing_name, billing_postcode, billing_address1, " + 
								"billing_address2, billing_phone1, billing_phone2, billing_phone3, " + 
								"billing_email1, billing_email2, payment_method, final_price, last_modified, date_purchased,orders_status,orders_date_finished) " + 
								"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now(),1,now())";
			pstmt			= con.prepareStatement(sql);
			
			pstmt.setInt		(1,customers_idx);
			pstmt.setString		(2,delivery_name);
			pstmt.setString		(3,delivery_postcode);
			pstmt.setString		(4,delivery_address1);
			pstmt.setString		(5,delivery_address2);
			pstmt.setString		(6,delivery_phone1);
			pstmt.setString		(7,delivery_phone2);
			pstmt.setString		(8,delivery_phone3);
			pstmt.setString		(9,delivery_email1);
			pstmt.setString		(10,delivery_email2);
			pstmt.setString		(11,billing_name);
			pstmt.setString		(12,billing_postcode);
			pstmt.setString		(13,billing_address1);
			pstmt.setString		(14,billing_address2);
			pstmt.setString		(15,billing_phone1);
			pstmt.setString		(16,billing_phone2);
			pstmt.setString		(17,billing_phone3);
			pstmt.setString		(18,billing_email1);
			pstmt.setString		(19,billing_email2);
			pstmt.setString		(20,payment_method);
			pstmt.setFloat		(21,final_price);
			
			result			= pstmt.executeUpdate();
			if (result > 0)	con.commit();
			
		} catch (Exception e) {
			log.debug("execute orderInsert DB work Failed!!!!!!!!!!");
			e.printStackTrace();
			try {
				log.debug("execute orderInsert DB work rollbacked!!!!!!!!!!");
				con.rollback();
			} catch (SQLException e1) {
				log.fatal("execute orderInsert DB work rollback failed!!!!!!!!!!");
				e1.printStackTrace();
			}
		} finally {
			close(con, pstmt);
		}
		log.debug("execute orderInsert DB work End.");
		return result;
	}
	
	
	public int ordersDelete(int idx)
	{
		int result = 0;
		Connection			con		= getConnection();
		PreparedStatement	pstmt	= null;

		try {
			log.debug("execute ordersDelete DB work Start.");
			String sql = "delete from orders where idx = ?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, idx);
			
			result = pstmt.executeUpdate();
			log.debug("execute ordersDelete DB work End.");
		} catch (Exception e) {
			log.fatal("execute ordersDelete DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		
		return result;
	}


	public int ordersUpdate(int idx, int orders_status, Date orders_date_finished)
	{
		int					result	= 0;
		PreparedStatement	pstmt	= null;
		Statement			stmt	= null;
		Connection			con		= getConnection();
		String sql					= null;

		
		try {
			log.debug("execute ordersUpdate DB work Start.");
			con.setAutoCommit(false);

			sql = "update orders set last_modified = now(), orders_status = ?, orders_date_finished = ? where idx = ?";
			pstmt	= con.prepareStatement(sql);
			pstmt	.setInt		(1, orders_status);
			pstmt	.setDate	(2, orders_date_finished);
			pstmt	.setInt		(3, idx);

			result			= pstmt.executeUpdate();
			if (result > 0)	con.commit();
			
		} catch (Exception e) {
			log.fatal("execute ordersUpdate DB work Failed!!!!!!!!!!");
			e.printStackTrace();
			try {
				log.debug("execute ordersUpdate DB work rollbacked!!!!!!!!!!");
				con.rollback();
			} catch (SQLException e1) {
				log.fatal("execute ordersUpdate DB work rollback failed!!!!!!!!!!");
				e1.printStackTrace();
			}
		} finally {
			close(con, pstmt);
		}
		log.debug("execute ordersUpdate DB work End.");
		return result;
	}
	
	
	public Vector<OrdersVO> ordersList(int page, int limit)
	{
		// Calc start record through page;
		int start					= (page - 1) * 10; 
		
		Vector<OrdersVO> ordersList	= new Vector<OrdersVO>();
		
		Connection			con		= getConnection();
		ResultSet			result	= null;
		PreparedStatement	pstmt	= null;
		
		try {
			log.debug("execute ordersList DB work Start.");
			String sql	= "select * from Orders order by idx desc limit ?,?";
			pstmt		= con.prepareStatement(sql);
			pstmt		.setInt(1, start);
			pstmt		.setInt(2, limit);
			result		= pstmt.executeQuery();
			
			while (result.next()) {
				OrdersVO list = new OrdersVO(result.getInt("idx"), 
											result.getInt("customers_idx"), 
											result.getString("delivery_name"), 
											result.getString("delivery_postcode"), 
											result.getString("delivery_address1"), 
											result.getString("delivery_address2"), 
											result.getString("delivery_phone1"),
											result.getString("delivery_phone2"), 
											result.getString("delivery_phone3"), 
											result.getString("delivery_email1"), 
											result.getString("delivery_email2"),
											result.getString("billing_name"), 
											result.getString("billing_postcode"), 
											result.getString("billing_address1"),
											result.getString("billing_address2"), 
											result.getString("billing_phone1"), 
											result.getString("billing_phone2"), 
											result.getString("billing_phone3"),
											result.getString("billing_email1"), 
											result.getString("billing_email2"), 
											result.getString("payment_method"), 
											result.getFloat("final_price"), 
											result.getDate("last_modified"),
											result.getDate("date_purchased"), 
											result.getInt("orders_status"), 
											result.getDate("orders_date_finished"));

				ordersList.add(list);
			}
		} catch (Exception e) {
			log.fatal("execute ordersList DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(con, pstmt, result);
		}
		log.debug("execute ordersList DB work End.");
		return ordersList;
	}
	
	public int ordersCountSearchingRows(String criteria, String searchWord)
	{
		log.debug("execute ordersCountSearchingRows DB work Start.");
		int					total_rows	= 0;
		Connection			con			= getConnection();
		PreparedStatement	pstmt		= null;
		ResultSet			rs			= null;
		String				sql			= null;
		
		try {
			sql	= "select count(*) from orders " +
//					"where " +  criteria + " like '%" + searchWord + "%' " +
					"where " +  criteria + " like ? " +
					"order by idx desc";
			pstmt		= con.prepareStatement(sql);
			pstmt		.setString(1, "%" + searchWord + "%");
			rs			= pstmt.executeQuery();
			
			if(rs.next())	
				total_rows	= rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.fatal("execute ordersCountSearchingRows DB work failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		
		log.debug("ordersCountSearchingRows DB work End. total_rows= " + total_rows);
		return total_rows;	
	}
	
	
	public Vector<OrdersVO> ordersSearch(String criteria, String searchWord, int page, int limit)
	{
		Vector<OrdersVO>	ordersList	= new Vector<OrdersVO>();
		
		Connection			con			= getConnection();
		ResultSet			result		= null;
		PreparedStatement	pstmt		= null;
		
		// Calc start record through page;
		int start					= (page - 1) * 10; 
		
		try {
			log.debug("execute ordersSearch DB work Start.");

			String sql	= "select * from orders" +
							"where " +  criteria + " like ? " +
							"order by idx desc limit ?, ?";
			pstmt		= con.prepareStatement(sql);
			pstmt		.setString(1, "%" + searchWord + "%");
			pstmt		.setInt(2, start);
			pstmt		.setInt(3, limit);
			
			log.debug("execute ordersSearch DB work... pstmt.toString()" + pstmt.toString());
			
			result		= pstmt.executeQuery();
			
			while (result.next()) {
				OrdersVO list = new OrdersVO(result.getInt("idx"), 
											result.getInt("customers_idx"), 
											result.getString("delivery_name"), 
											result.getString("delivery_postcode"), 
											result.getString("delivery_address1"), 
											result.getString("delivery_address2"), 
											result.getString("delivery_phone1"),
											result.getString("delivery_phone2"), 
											result.getString("delivery_phone3"), 
											result.getString("delivery_email1"), 
											result.getString("delivery_email2"),
											result.getString("billing_name"), 
											result.getString("billing_postcode"), 
											result.getString("billing_address1"),
											result.getString("billing_address2"), 
											result.getString("billing_phone1"), 
											result.getString("billing_phone2"), 
											result.getString("billing_phone3"),
											result.getString("billing_email1"), 
											result.getString("billing_email2"), 
											result.getString("payment_method"), 
											result.getFloat("final_price"), 
											result.getDate("last_modified"),
											result.getDate("date_purchased"), 
											result.getInt("orders_status"), 
											result.getDate("orders_date_finished"));
				
				ordersList.add(list);
			}
		} catch (Exception e) {
			log.fatal("execute ordersSearch DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(con, pstmt, result);
		}
		log.debug("execute ordersSearch DB work End.");
		return ordersList;
	}
	
	
	public OrdersVO ordersGetRow(int idx)
	{
		OrdersVO			vo		= null;
		
		Connection			con		= getConnection();
		ResultSet			result	= null;
		PreparedStatement	pstmt	= null;
		
		try {
			log.debug("execute ordersGetRow DB work Start.");
			String sql	= "select * from orders where idx = ?";
			pstmt			= con.prepareStatement(sql);
			pstmt.setInt	(1, idx);
			result			= pstmt.executeQuery();			
			if (result.next()) {
				vo = new OrdersVO(result.getInt("idx"), 
								result.getInt("customers_idx"), 
								result.getString("delivery_name"), 
								result.getString("delivery_postcode"), 
								result.getString("delivery_address1"), 
								result.getString("delivery_address2"), 
								result.getString("delivery_phone1"),
								result.getString("delivery_phone2"), 
								result.getString("delivery_phone3"), 
								result.getString("delivery_email1"), 
								result.getString("delivery_email2"),
								result.getString("billing_name"), 
								result.getString("billing_postcode"), 
								result.getString("billing_address1"),
								result.getString("billing_address2"), 
								result.getString("billing_phone1"), 
								result.getString("billing_phone2"), 
								result.getString("billing_phone3"),
								result.getString("billing_email1"), 
								result.getString("billing_email2"), 
								result.getString("payment_method"), 
								result.getFloat("final_price"), 
								result.getDate("last_modified"),
								result.getDate("date_purchased"), 
								result.getInt("orders_status"), 
								result.getDate("orders_date_finished"));
			}
		} catch (Exception e) {
			log.fatal("execute ordersGetRow DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(con, pstmt, result);
		}
		log.debug("execute ordersGetRow DB work End.");
		
		return vo;
	}
	
	
	public int totalRows()
	{
		log.debug("execute orders total_rows DB work Start.");
		int					total_rows	= 0;
		Connection			con			= getConnection();
		PreparedStatement	pstmt		= null;
		ResultSet			rs			= null;
		String				sql			= null;
		
		try {
			sql		= "select count(*) from orders";
			pstmt	= con.prepareStatement(sql);
			rs		= pstmt.executeQuery();
			
			if(rs.next())	
				total_rows	= rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.fatal("execute orders total_rows DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		
		log.debug("execute orders total_rows DB work End. total_rows= " + total_rows);
		
		return total_rows;
	}
	
	
	public int singleCustomertotalRows(int customers_idx)
	{
		log.debug("execute orders single_customer's_total_rows DB work Start.");
		int					total_rows	= 0;
		Connection			con			= getConnection();
		PreparedStatement	pstmt		= null;
		ResultSet			rs			= null;
		String				sql			= null;
		
		try {
			sql		= "select count(*) from orders where customers_idx=?";
			pstmt	= con.prepareStatement(sql);
			pstmt.setInt	(1, customers_idx);
			rs		= pstmt.executeQuery();
			
			if(rs.next())	
				total_rows	= rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.fatal("execute orders total_rows DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		
		log.debug("execute orders total_rows DB work End. total_rows= " + total_rows);
		
		return total_rows;
	}
	
	
	public Vector<OrdersVO> singleCustomerordersList(int customers_idx,int page, int limit)
	{
		// Calc start record through page;
		int start					= (page - 1) * 10; 
		
		Vector<OrdersVO> ordersList	= new Vector<OrdersVO>();
		
		Connection			con		= getConnection();
		ResultSet			result	= null;
		PreparedStatement	pstmt	= null;
		
		try {
			log.debug("execute single_Customer's_orders_List DB work Start.");
			String sql	= "select * from orders where customers_idx=? order by idx desc limit ?,?";
			pstmt		= con.prepareStatement(sql);
			pstmt		.setInt(1, customers_idx);
			pstmt		.setInt(2, start);
			pstmt		.setInt(3, limit);
			result		= pstmt.executeQuery();
			
			while (result.next()) {
				OrdersVO list = new OrdersVO(result.getInt("idx"), 
											result.getInt("customers_idx"), 
											result.getString("delivery_name"), 
											result.getString("delivery_postcode"), 
											result.getString("delivery_address1"), 
											result.getString("delivery_address2"), 
											result.getString("delivery_phone1"),
											result.getString("delivery_phone2"), 
											result.getString("delivery_phone3"), 
											result.getString("delivery_email1"), 
											result.getString("delivery_email2"),
											result.getString("billing_name"), 
											result.getString("billing_postcode"), 
											result.getString("billing_address1"),
											result.getString("billing_address2"), 
											result.getString("billing_phone1"), 
											result.getString("billing_phone2"), 
											result.getString("billing_phone3"),
											result.getString("billing_email1"), 
											result.getString("billing_email2"), 
											result.getString("payment_method"), 
											result.getFloat("final_price"), 
											result.getDate("last_modified"),
											result.getDate("date_purchased"), 
											result.getInt("orders_status"), 
											result.getDate("orders_date_finished"));

				ordersList.add(list);
			}
		} catch (Exception e) {
			log.fatal("execute single_Customer's_orders_List DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(con, pstmt, result);
		}
		log.debug("execute single_Customer's_orders_List DB work End.");
		return ordersList;
	}
	
	
	public Vector<OrdersVO> singleCustomerordersList(int customers_idx)
	{
		Vector<OrdersVO> ordersList	= new Vector<OrdersVO>();
		
		Connection			con		= getConnection();
		ResultSet			result	= null;
		PreparedStatement	pstmt	= null;
		
		try {
			log.debug("execute single_Customer's_orders_List DB work Start.");
			String sql	= "select * from orders where customers_idx=? order by idx desc";
			pstmt		= con.prepareStatement(sql);
			pstmt		.setInt(1, customers_idx);
			result		= pstmt.executeQuery();
			
			while (result.next()) {
				OrdersVO list = new OrdersVO();
								list.setIdx(result.getInt("idx")); 
								list.setCustomers_idx(result.getInt("customers_idx")); 
								list.setFinal_price(result.getFloat("final_price")); 
								list.setLast_modified(result.getDate("last_modified"));
								list.setDate_purchased(result.getDate("date_purchased")); 
								list.setOrders_status(result.getInt("orders_status"));
								list.setOrders_date_finished(result.getDate("orders_date_finished"));
								ordersList.add(list);
			}
		} catch (Exception e) {
			log.fatal("execute single_Customer's_orders_List DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(con, pstmt, result);
		}
		log.debug("execute single_Customer's_orders_List DB work End.");
		return ordersList;
	}
	
	public int getMaxIdx() {
		int					idx		= 0;
		Connection			con		= getConnection();
		ResultSet			result	= null;
		PreparedStatement	pstmt	= null;
		
		try {
			log.debug("execute getMaxIdx DB work Start.");
			String sql	= "select max(idx) from orders;";
			pstmt		= con.prepareStatement(sql);
			result		= pstmt.executeQuery();
			
			while (result.next()) {
							idx		= result.getInt(1);
			}
		} catch (Exception e) {
			log.fatal("execute sgetMaxIdx DB work Failed!!!!!!!!!!");
			e.printStackTrace();
		} finally {
			close(con, pstmt, result);
		}
		log.debug("execute getMaxIdx DB work End.");
		return idx;
	}
}
