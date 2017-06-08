package com.crm.bos.dao;

import java.util.List;

import com.crm.bos.dao.base.IBaseDao;
import com.crm.bos.domain.Function;

public interface IFunctionDao extends IBaseDao<Function>{

	public List<Function> findListByUserId(String id);

	public List<Function> findAllMenu();

	public List<Function> findMenuByUserId(String id);

}
