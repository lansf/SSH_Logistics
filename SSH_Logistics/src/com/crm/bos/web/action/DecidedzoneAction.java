package com.crm.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.crm.bos.crm.CustomerService;
import com.crm.bos.domain.Decidedzone;
import com.crm.bos.web.action.base.BaseAction;
import com.crm.domain.Customer;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone>{

	//使用数组接受分区id
	private String[] subareaid;
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	
	//添加分区
	public String add(){
		decidedzoneService.save(model,subareaid);
		return "list";
	}
	//分页查询
	public String pageQuery(){
		decidedzoneService.pageQuery(pageBean);
		String[] excludes=new String[]{"subareas","detachedCriteria","pageSize", "currentPage","decidedzones"};
		this.writePageBean2Json(pageBean, excludes);
		return NONE;
	}
	
	//注入代理对象
	@Autowired
	private CustomerService customerService;
	
	//查询未关联到定区的客户
	public String findnoassociationCustomers() throws IOException{
		List<Customer> customers = customerService.findnoassociationCustomers();
		String[] excludes = new String[]{};
		this.writeList2Json(customers, excludes);
		return NONE;
	}
	//查询已关联到当前定区的客户
	public String findhasassociationCustomers() throws IOException{
		List<Customer> customers = customerService.findhasassociationCustomers(model.getId());
		String[] excludes = new String[]{};
		this.writeList2Json(customers, excludes);
		return NONE;
	}
	
	private Integer[] customerIds;
	//定区关联客户
	public void setCustomerIds(Integer[] customerIds) {
		this.customerIds = customerIds;
	}

	public String assigncustomerstodecidedzone(){
		customerService.assignCustomersToDecidedZone(customerIds, model.getId());
		return "list";
	}
}
