package com.crm.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.crm.bos.domain.Function;
import com.crm.bos.domain.User;
import com.crm.bos.utils.BOSContext;
import com.crm.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {

	public String pageQuery() {
		pageBean.setCurrentPage(Integer.parseInt(model.getPage()));//赋给分页数据，否则这个page参数会赋给Function中的page属性
		functionService.pageQuery(pageBean);
		String[] excludes = new String[] { "function", "functions", "roles", "detachedCriteria"};
		this.writePageBean2Json(pageBean, excludes);
		return NONE;
	}
	
	public String listajax() throws IOException{
		List<Function> list=functionService.findAll();
		String[] excludes=new String[]{"function", "functions", "roles"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
	
	public String add(){
		functionService.save(model);
		return "list";
	}
	//根据登录人查询菜单
	public String findMenu() throws IOException{
		User user = BOSContext.getLoginUser();
		List<Function> list=functionService.findMenu(user);
		String[] excludes=new String[]{"functions","roles"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
}
