package com.crm.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.bos.dao.IRegionDao;
import com.crm.bos.domain.Region;
import com.crm.bos.service.IRegionService;
import com.crm.bos.utils.PageBean;

@Service
@Transactional
public class RegionServiceImpl implements IRegionService{
	@Resource
	private IRegionDao regionDao;

	public void saveBatch(List<Region> list) {
		for (Region region : list) {
			regionDao.saveOrUpdate(region);
		}
	}

	public void pageQuery(PageBean pageBean) {
		regionDao.pageQuery(pageBean);
	}

	public List<Region> findAll() {
		return regionDao.findAll();
	}

	public List<Region> findByQ(String q) {
		return regionDao.findByQ(q);
	}

	public void save(Region model) {
		regionDao.saveOrUpdate(model);
	}

	public void deleteBatch(String ids) {
		String[] sids = ids.split(",");
		Region region=null;
		for (String id : sids) {
			region=new Region(id);
			regionDao.delete(region);
		}
	}
}
