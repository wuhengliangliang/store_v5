package cn.itcast.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.utils.JedisUtils;
import cn.itcast.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

public class CategoryServlet extends BaseServlet {
	// findAllCats
	public String  findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取redis中的全部分类信息
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allCats");
		if(jsonStr==null||"".equals(jsonStr)) {
			//调用业务层获取全部分类
			CategoryService CategoryService = new CategoryServiceImp();
			List<Category>list = CategoryService.getAllCats();
			//将全部分类转换为json格式的数据
			jsonStr = JSONArray.fromObject(list).toString();
			//将获取到json格式的数据存入redis中
			jedis.set("allCats",jsonStr);
			
			
			//将全部分类信息响应到客户端
			//告诉浏览器本次响应的数据是JSON格式的字符串
			resp.setContentType("application/json;charset=utf-8");
			resp.getWriter().print(jsonStr);
		}else {
			System.out.println("redis 中的缓存中有数据");
			//将全部分类信息响应到客户端
			//告诉浏览器本次响应的数据是JSON格式的字符串
			resp.setContentType("application/json;charset=utf-8");
			resp.getWriter().print(jsonStr);
		}
		//最后释放jedis
		JedisUtils.closeJedis(jedis);
		
		//由于这是ajax不需要进行重定向或者转发
		
		return null;
	}

}
