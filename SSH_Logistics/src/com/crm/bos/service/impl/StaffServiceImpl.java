package com.crm.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.bos.dao.IStaffDao;
import com.crm.bos.domain.Staff;
import com.crm.bos.service.IStaffService;
import com.crm.bos.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService{

	//注入Dao
	@Resource
	private IStaffDao staffDao;
	public void save(Staff model) {
		staffDao.save(model);
	}
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}
	public void deleteBatch(String ids) {
		String[] sids = ids.split(",");
		for (String id : sids) {
			staffDao.executeUpdate("staff.delete", id);
		}
	}
	public void restoreBatch(String ids) {
		String[] sids = ids.split(",");
		for (String id : sids) {
			staffDao.executeUpdate("staff.restore", id);
		}
	}
	public Staff findById(String id) {
		return staffDao.findById(id);
	}
	public void update(Staff staff) {
		staffDao.update(staff);
	}
	public List<Staff> findListNotDelete() {
		DetachedCriteria dc=DetachedCriteria.forClass(Staff.class);
		dc.add(Restrictions.eq("deltag", "0"));
		return staffDao.findByCriteria(dc);
	}

}
