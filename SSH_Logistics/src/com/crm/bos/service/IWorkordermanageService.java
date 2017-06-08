package com.crm.bos.service;

import com.crm.bos.domain.Workordermanage;
import com.crm.bos.utils.PageBean;

public interface IWorkordermanageService {

	public void save(Workordermanage model);

	public void pageQuery(PageBean pageBean);

}
