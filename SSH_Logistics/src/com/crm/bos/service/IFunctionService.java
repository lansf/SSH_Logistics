package com.crm.bos.service;

import java.util.List;

import com.crm.bos.domain.Function;
import com.crm.bos.domain.User;
import com.crm.bos.utils.PageBean;

public interface IFunctionService {

	public void pageQuery(PageBean pageBean);

	public List<Function> findAll();

	public void save(Function model);

	public List<Function> findMenu(User user);

}
