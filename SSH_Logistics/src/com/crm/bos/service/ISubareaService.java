package com.crm.bos.service;

import java.util.List;

import com.crm.bos.domain.Subarea;
import com.crm.bos.utils.PageBean;

public interface ISubareaService {

	public void save(Subarea model);

	public void pageQuery(PageBean pageBean);

	public List<Subarea> findAll();

	public List<Subarea> findListNotAssociation();

}
