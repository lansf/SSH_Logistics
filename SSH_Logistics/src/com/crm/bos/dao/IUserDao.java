package com.crm.bos.dao;

import com.crm.bos.dao.base.IBaseDao;
import com.crm.bos.domain.User;

public interface IUserDao extends IBaseDao<User>{

	public User findUserByUserNameAndPassword(String username, String password);

	public User findUserByUserName(String username);

}
