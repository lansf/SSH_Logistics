package com.crm.bos.service;

import java.util.List;

import com.crm.bos.domain.Role;
import com.crm.bos.utils.PageBean;

public interface IRoleService {

	public void save(Role model, String ids);

	public void pageQuery(PageBean pageBean);

	public List<Role> findAll();

}
