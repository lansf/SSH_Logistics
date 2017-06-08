package com.crm.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.bos.dao.IFunctionDao;
import com.crm.bos.domain.Function;
import com.crm.bos.domain.User;
import com.crm.bos.service.IFunctionService;
import com.crm.bos.utils.PageBean;

@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService{

	@Autowired
	private IFunctionDao functionDao;

	public void pageQuery(PageBean pageBean) {
		functionDao.pageQuery(pageBean);
	}

	public List<Function> findAll() {
		return functionDao.findAll();
	}

	public void save(Function model) {
		//需要判断传过来的pid是否是空串，若为空串则将其赋值为null，否则无法插入mysql中
		Function parent = model.getFunction();
		if(parent!=null && parent.getId()!=null&&parent.getId().equals("")){
			model.setFunction(null);
		}
		functionDao.save(model);
	}

	public List<Function> findMenu(User user) {
		List<Function> list=null;
		if(user.getUsername().equals("admin")){
			list=functionDao.findAllMenu();
		}else {
			list=functionDao.findMenuByUserId(user.getId());
		}
		return list;
	}
}
