package com.crm.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.crm.bos.dao.IFunctionDao;
import com.crm.bos.dao.base.impl.BaseDaoImpl;
import com.crm.bos.domain.Function;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao{

	//根据用户id查询权限
	public List<Function> findListByUserId(String userid) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r" +
				" LEFT OUTER JOIN r.users u WHERE u.id = ?";
		return this.getHibernateTemplate().find(hql, userid);
	}

	/**
	 * 查询所有的菜单
	 */
	public List<Function> findAllMenu() {
		String hql = "FROM Function f WHERE f.generatemenu = '1' ORDER BY f.zindex DESC";
		return this.getHibernateTemplate().find(hql);
	}

	/**
	 * 根据用户id查询对应的菜单
	 */
	public List<Function> findMenuByUserId(String id) {
		String hql = "SELECT DISTINCT f FROM Function f LEFT OUTER JOIN f.roles r" +
				" LEFT OUTER JOIN r.users u WHERE u.id = ? AND f.generatemenu = '1' ORDER BY f.zindex DESC ";
		return this.getHibernateTemplate().find(hql, id);
	}
}
