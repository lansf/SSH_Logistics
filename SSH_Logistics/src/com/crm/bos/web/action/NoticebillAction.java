package com.crm.bos.web.action;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.crm.bos.crm.CustomerService;
import com.crm.bos.domain.Noticebill;
import com.crm.bos.web.action.base.BaseAction;
import com.crm.domain.Customer;
//业务通知单
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {

	//注入customer远程访问的代理对象
	@Autowired
	private CustomerService customerService;
	
	public String findCustomerByTelephone() throws IOException{
		Customer customer = customerService.findCustomerByPhoneNumber(model.getTelephone());
		String[] excludes=new String[]{};
		this.writeObject2Json(customer, excludes);
		return NONE;
	}
	//添加业务通知单,尝试自动分单
	public String add(){
		noticebillService.save(model);
		return "addUI";
	}
}
