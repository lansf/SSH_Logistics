package com.crm.bos.dao.impl;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.crm.bos.dao.IUserDao;
import com.crm.bos.dao.base.impl.BaseDaoImpl;
import com.crm.bos.domain.User;

@Repository
//@Scope("prototype")
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{

	//
	public User findUserByUserNameAndPassword(String username, String password) {
		String hql="FROM User u WHERE u.username = ? AND u.password = ?";
		List<User> list = this.getHibernateTemplate().find(hql, username,password);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	public User findUserByUserName(String username) {
		String hql="FROM User u WHERE u.username = ?";
		List<User> list = this.getHibernateTemplate().find(hql, username);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
