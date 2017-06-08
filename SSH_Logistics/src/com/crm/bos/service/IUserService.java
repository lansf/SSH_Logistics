package com.crm.bos.service;

import java.util.List;

import com.crm.bos.domain.User;
import com.crm.bos.utils.PageBean;

public interface IUserService {

	public User login(User model);

	public void editPassword(User model);

	public void save(User model, String[] roleIds);

	public void pageQuery(PageBean pageBean);

}
