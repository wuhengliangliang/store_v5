package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.itcast.store.domain.User;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.service.serviceImp.UserServiceImp;
import cn.itcast.store.utils.MailUtils;
import cn.itcast.store.utils.MyBeanUtils;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;


public class UserServlet extends BaseServlet {

	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//返回路径 页面跳转
		return "/jsp/register.jsp";
	}
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//返回路径 页面跳转
		//调用业务层功能：获取全部分类信息，返回集合
		CategoryService CategoryService = new CategoryServiceImp();
		List list = CategoryService.getAllCats();
		//将返回的集合放入request
		request.setAttribute("allCats", list);
		//转发到真实的首页
		return "/jsp/login.jsp";
	}
	/**这个函数需要做一下几件事情
	 * 接收表单的参数
	 * 调用业务层注册功能
	 * 注册成功，想用户邮箱发送信息，跳转页面提示页面
	 * 注册失败，跳转到提示页面
	 */
	public String userRegist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收表单参数
		Map<String,String[]> map = request.getParameterMap();
		User user = new User();
		MyBeanUtils.populate(user, map);
		//为其他用户赋值
		user.setUid(UUIDUtils.getId()); //生成随机码32位防止商品重复
		user.setState(0); //默认状态码
		user.setCode(UUIDUtils.getCode()); // 生成状态码
		System.out.println(user);
		
		//调用业务逻辑层
		UserService UserService = new UserServiceImp();
		try {
			
			UserService.userRegist(user);
			//注册成功，向用户邮箱发送信息，跳转到提示页面
			//发送邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg","用户注册成功，请激活！");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// 注册失败的提示页面
			request.setAttribute("msg","用户注册失败，请重新注册！");
			
		}
		
		return "/jsp/info.jsp";
	}
	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		//获取激活码
		String code = request.getParameter("code");
		//调用业务层激活功能
		UserService UserService = new UserServiceImp();
		boolean flag = UserService.userActive(code);
		//进行激活的信息提示
		if(flag==true) {
			//用户激活成功，向request放入提示信息，转发到登录页面
			request.setAttribute("msg", "用户激活成功，请登录");
			return "/jsp/login.jsp";
		}else {
			//用户激活失败，向request放入提示信息，转发到登录页面
			request.setAttribute("msg", "用户激活失败，请重新激活");
			return "/jsp/info.jsp";
		}
		
	}
	//userLogin 用户登录
	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取用户数据(账户/密码)
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		//调用业务层登录功能
		UserService UserService = new UserServiceImp();
		User user02 = null; //携带用户所有信息
		try {
			//select *from user where username=? and password=?
			user02 = UserService.userLogin(user);
			//登录成功将用户信息放入session中
			request.getSession().setAttribute("loginUser",user02 ); //第一个参数是登录成功放的信息
			//重定向
			response.sendRedirect("/store_v5/index.jsp");
			return null;
		} catch (Exception e) {
			//用户登录失败
			String msg = e.getMessage();
			System.out.println(msg);
			//向request放入失败的信息
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
			
		}
	}
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//清除session 使session失效
		request.getSession().invalidate();
		//重新定向到首页
		response.sendRedirect("/store_v5/index.jsp");
		return null;
	}
}
