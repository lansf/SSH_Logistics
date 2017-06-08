package com.crm.bos.service.impl;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.bos.dao.IRoleDao;
import com.crm.bos.domain.Function;
import com.crm.bos.domain.Role;
import com.crm.bos.service.IRoleService;
import com.crm.bos.utils.PageBean;
import com.mchange.v1.identicator.Identicator;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService{

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IdentityService identityService;
	
	public void save(Role model, String ids) {
		roleDao.save(model);//变为持久对象
		
		Group group=new GroupEntity(model.getName());
		identityService.saveGroup(group);//将角色数据同步到actitviti的数据表中
		
		String[] functionIds = ids.split(",");
		for (String fid : functionIds) {
			Function function=new Function();
			function.setId(fid);//托管状态,建立关联时必须是持久类关联托管类
			model.getFunctions().add(function);//角色和权限建立关联
		}
	}

	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}

	public List<Role> findAll() {
		return roleDao.findAll();
	}
}
