package cn.itcast.store.service.serviceImp;

import java.net.ConnectException;
import java.sql.Connection;
import java.util.List;

import cn.itcast.store.dao.OrderDao;
import cn.itcast.store.dao.daoImp.OrderDaoImp;
import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.OrderService;
import cn.itcast.store.utils.JDBCUtils;

public class OrderServiceImp implements OrderService {
	OrderDao orderDao = new OrderDaoImp();
	@Override
	public void saveOrder(Order order) throws Exception {
		/*try {
			//保存订单和订单下所有的订单项（同时成功，失败）
			JDBCUtils.startTransaction();//开启事物
			OrderDao orderDao = new OrderDaoImp();
			orderDao.saveOrder(order); //存订单
			//遍历订单项
			for (OrderItem item : order.getList()) {
				orderDao.saveOrderItem(item);
			}
			JDBCUtils.rollbackAndClose(); //事物回滚
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		Connection conn = null;
		try {
			//获取链接
			conn=JDBCUtils.getConnection();
			//开启事物
			conn.setAutoCommit(false);
			//保存订单
			OrderDao orderDao = new OrderDaoImp();
			orderDao.saveOrder(conn,order); //存订单 里面的参数 conn 保证用的是同一个链接
			for (OrderItem item : order.getList()) {
				orderDao.saveOrderItem(conn,item);
			}
			//提交
			conn.commit();
		} catch (Exception e) {
			//回滚
			conn.rollback();
		}/*finally {
			if(null!=conn) {
				conn.close();
				conn=null;//加快回收
			}
		}*/
		
	}

	@Override
	public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception {
		//1、创建pagemodel对象，目的：计算并且携带分页参数
		//select count(*) from orders where uid = ?
		
		int totalRecords = orderDao.getTotalRecords(user);
		PageModel pm = new PageModel(curNum, totalRecords,3); //最后一个参数 显示多少条
		//2、关联集合 select * from orders where uid=? limit = ?
		List list = orderDao.findMyOrdersWithPage(user,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);//放的是当前用户当前页的订单
		//3、关联url
		pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
		return pm;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
	
		return orderDao.findOrderByOid(oid);
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		orderDao.updateOrder(order);
		
	}

	@Override
	public List<Order> findAllOrders() throws Exception {
		
		return orderDao.findAllOrders();
	}

	@Override
	public List<Order> findAllOrders(String st) throws Exception {
		// TODO Auto-generated method stub
		return orderDao.findAllOrders(st);
	}

}
