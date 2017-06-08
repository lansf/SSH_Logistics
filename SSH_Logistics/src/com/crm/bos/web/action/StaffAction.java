package com.crm.bos.web.action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.crm.bos.domain.Staff;
import com.crm.bos.service.IStaffService;
import com.crm.bos.utils.PageBean;
import com.crm.bos.web.action.base.BaseAction;

/*
 * 区派员管理action
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {



	// 添加取派员
	public String add() {
		staffService.save(model);
		return "list";
	}

	// 分页获取取派员数据
	public String pageQuery() throws IOException {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		String name=model.getName();
		String telephone=model.getTelephone();
		String station=model.getStation();
		String standard = model.getStandard();
		if(StringUtils.isNotBlank(name)){
			dc.add(Restrictions.like("name", "%"+name+"%"));
		}
		if(StringUtils.isNotBlank(telephone)){
			dc.add(Restrictions.like("telephone", "%"+telephone+"%"));
		}
		if(StringUtils.isNotBlank(station)){
			dc.add(Restrictions.like("station", "%"+station+"%"));
		}
		if(StringUtils.isNotBlank(standard)){
			dc.add(Restrictions.like("standard", "%"+standard+"%"));
		}
		staffService.pageQuery(pageBean);
		// 把pageBean序列化为json

		this.writePageBean2Json(pageBean, new String[] { "detachedCriteria",
				"pageSize", "currentPage", "decidedzones" });
		return NONE;
	}

	// 批量作废
	private String ids;

	public String delete() {
		staffService.deleteBatch(ids);
		return "list";
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	// 批量还原
	public String restore() {
		staffService.restoreBatch(ids);
		return "list";
	}

	// 修改取派员信息
	public String edit() {
		//现根据id查询，再使用model覆盖
		String id = model.getId();
		Staff staff=staffService.findById(id);
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		staffService.update(staff);
		return "list";
	}

	//查询没有作废的取派员,即只查询deltag=0的数据,返回json数据
	public String listajax() throws IOException{
		List<Staff> list=staffService.findListNotDelete();
		String[] excludes=new String[]{"telephone","haspda","deltag","station","standard","decidedzones"};
		this.writeList2Json(list, excludes);
		return NONE;
	}
}
