package com.crm.bos.service;

import java.util.List;

import com.crm.bos.domain.Region;
import com.crm.bos.utils.PageBean;

public interface IRegionService {

	public void saveBatch(List<Region> list);

	public void pageQuery(PageBean pageBean);

	public List<Region> findAll();

	public List<Region> findByQ(String q);

	public void save(Region model);

	public void deleteBatch(String ids);

}
