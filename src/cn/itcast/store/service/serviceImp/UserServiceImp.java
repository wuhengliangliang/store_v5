package cn.itcast.store.service.serviceImp;

import java.sql.SQLException;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.dao.daoImp.UserDaoImp;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;

public class UserServiceImp implements UserService {

	@Override
	public void userRegist(User user) throws SQLException {
		// TODO Auto-generated method stub
		//实现注册功能
		UserDao UserDao = new UserDaoImp();
		UserDao.userRegist(user);
	}

	@Override
	public boolean userActive(String code) throws SQLException {
		UserDao UserDao = new UserDaoImp();
		User user = UserDao.userActive(code);
		if(null!=user) {
			//可以根据激活码查询到一个用户
			//修改用户的状态，清除激活码
			user.setState(1);
			user.setCode(null);
			//对数据库执行一次真实的更新操作
			UserDao.updateUser(user);
			return true;
		}else {
			//不可以根据激活码查询到一个用户
			return false;
		}
	}

	@Override
	public User userLogin(User user) throws SQLException {
		//利用异常在模块之间传递数据
		UserDao UserDao = new UserDaoImp();
		User uu = UserDao.userLogin(user);
		if(null==uu) {
			throw new RuntimeException("密码有误");
			
		}else if(uu.getState()==0) {
			throw new RuntimeException("用户未激活");
		}else {
			return uu;
		}
		
	}
	
}
