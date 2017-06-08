package com.crm.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.crm.bos.domain.Role;
import com.crm.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{

	private String ids;//用来接受权限的id值
	public String add(){
		roleService.save(model,ids);
		return "list";
	}
	
	public String pageQuery(){
		roleService.pageQuery(pageBean);
		String[] excludes=new String[]{"functions","users"};
		this.writePageBean2Json(pageBean, excludes);
		return NONE;
	}
	
	public String listajax() throws IOException{
		List<Role> list=roleService.findAll();
		String[] excludes=new String[]{"functions","users"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
