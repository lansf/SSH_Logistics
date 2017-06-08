package com.crm.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.crm.bos.dao.IRegionDao;
import com.crm.bos.dao.base.impl.BaseDaoImpl;
import com.crm.bos.domain.Region;

@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao{

	public List<Region> findByQ(String q) {
		String hql = "FROM Region WHERE province LIKE ? OR city LIKE ? OR district LIKE ?";
		return this.getHibernateTemplate().find(hql, "%"+q+"%","%"+q+"%","%"+q+"%");
	}

}
