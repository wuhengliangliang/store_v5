package cn.itcast.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.sun.net.httpserver.HttpServer;

import cn.itcast.store.domain.User;


public class PriviledgeFilter implements Filter {

    public PriviledgeFilter() {
        
    }

	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest myReq = (HttpServletRequest)request;
		//判断当前的session中是否存在已经登录成功的用户
		User user = (User)myReq.getSession().getAttribute("loginUser");
		//如果存在，放行
		if(null!=user) {
			//放行
			chain.doFilter(request, response);
		}else {
			//如果不存在，转入到提示页面
			myReq.setAttribute("msg", "请用户登录后再去访问");
			//转入到提示页面
			myReq.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}
		
		
		//chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
