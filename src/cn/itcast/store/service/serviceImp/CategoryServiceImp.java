package cn.itcast.store.service.serviceImp;

import java.util.List;

import cn.itcast.store.dao.CategoryDao;
import cn.itcast.store.dao.daoImp.CategoryDaoImp;
import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class CategoryServiceImp implements CategoryService{
	CategoryDao CategoryDao = new CategoryDaoImp();
	@Override
	public List<Category> getAllCats() throws Exception {
		
		return CategoryDao.getAllCats();
		 
	}

	@Override
	public void addCategory(Category c) throws Exception {
		//本质是向mysql中保插入数据库
		CategoryDao.addCategory(c);
		//更新redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}

	
	
}
