package com.crm.bos.web.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.crm.bos.domain.Workordermanage;
import com.crm.bos.web.action.base.BaseAction;

/**
 * 工作单管理
 *
 */
@Controller
@Scope
public class WorkordermanageAction extends BaseAction<Workordermanage>{
	public String add() throws IOException{
		String flag = "1";
		try{
			workordermanageService.save(model);
		}catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	//分页查询
	public String pageQuery(){
		workordermanageService.pageQuery(pageBean);
		String[] excludes = new String[]{"prodtimelimit","prodtype","sendername","senderphone","senderaddr","receivername","receiverphone","receiveraddr","feeitemnum","actlweit","vol","managerCheck"};
		this.writePageBean2Json(pageBean, excludes );
		return NONE;
	}
}
