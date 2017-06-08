package com.crm.bos.service.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.bos.dao.IWorkordermanageDao;
import com.crm.bos.domain.Workordermanage;
import com.crm.bos.service.IWorkordermanageService;
import com.crm.bos.utils.PageBean;

@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService{
	@Autowired
	private IWorkordermanageDao workordermanageDao;
	
	public void save(Workordermanage model) {
		model.setUpdatetime(new Date());
		workordermanageDao.save(model);
	}

	public void pageQuery(PageBean pageBean) {
		workordermanageDao.pageQuery(pageBean);
	}
}
