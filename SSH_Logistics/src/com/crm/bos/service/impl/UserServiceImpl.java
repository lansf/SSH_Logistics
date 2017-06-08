package com.crm.bos.service.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.bos.dao.IRoleDao;
import com.crm.bos.dao.IUserDao;
import com.crm.bos.domain.Role;
import com.crm.bos.domain.User;
import com.crm.bos.service.IUserService;
import com.crm.bos.utils.MD5Utils;
import com.crm.bos.utils.PageBean;

@Service
@Transactional  //事务
public class UserServiceImpl implements IUserService{

	//注入dao
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IdentityService identityService;
	
	public User login(User model) {
		String password = model.getPassword();
		password= MD5Utils.md5(password);//md5加密
		return userDao.findUserByUserNameAndPassword(model.getUsername(),password);
	}
	public void editPassword(User model) {
		String id=model.getId();//获取修改的用户id
		String password = model.getPassword();
		password= MD5Utils.md5(password);//md5加密
		//userDao.update(model);不正确，因为update会覆盖掉未赋值的字段
		userDao.executeUpdate("editPassword",password,id);
	}
	public void save(User model, String[] roleIds) {
		String password = model.getPassword();
		password= MD5Utils.md5(password);//md5加密
		model.setPassword(password);//设置加密后的密码
		userDao.save(model);
		
		org.activiti.engine.identity.User actUser=new UserEntity(model.getId());
		identityService.saveUser(actUser);//同步到activiti的数据表
		
		for (String roleId : roleIds) {
			Role role=roleDao.findById(roleId);
			model.getRoles().add(role);
			identityService.createMembership(actUser.getId(), role.getName());
		}
		
	}
	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}

}
