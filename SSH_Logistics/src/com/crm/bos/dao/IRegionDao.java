package com.crm.bos.dao;

import java.util.List;

import com.crm.bos.dao.base.IBaseDao;
import com.crm.bos.domain.Region;

public interface IRegionDao extends IBaseDao<Region> {

	public List<Region> findByQ(String q);
}
