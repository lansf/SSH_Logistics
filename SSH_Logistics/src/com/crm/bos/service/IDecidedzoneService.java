package com.crm.bos.service;

import com.crm.bos.domain.Decidedzone;
import com.crm.bos.utils.PageBean;

public interface IDecidedzoneService {

	public void save(Decidedzone model, String[] subareaid);

	public void pageQuery(PageBean pageBean);

}
