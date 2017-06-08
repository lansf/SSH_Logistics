package com.crm.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.bos.dao.IDecidedzoneDao;
import com.crm.bos.dao.ISubareaDao;
import com.crm.bos.domain.Decidedzone;
import com.crm.bos.domain.Subarea;
import com.crm.bos.service.IDecidedzoneService;
import com.crm.bos.utils.PageBean;

@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService{

	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private ISubareaDao subareaDao;
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for (String sid : subareaid) {
			Subarea subarea = subareaDao.findById(sid);
			subarea.setDecidedzone(model);//分区关联定区
			
		}
	}
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
	}

}
