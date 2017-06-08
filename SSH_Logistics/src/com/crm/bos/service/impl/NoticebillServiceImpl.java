package com.crm.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.bos.crm.CustomerService;
import com.crm.bos.dao.IDecidedzoneDao;
import com.crm.bos.dao.INoticebillDao;
import com.crm.bos.dao.IWorkbillDao;
import com.crm.bos.domain.Decidedzone;
import com.crm.bos.domain.Noticebill;
import com.crm.bos.domain.Staff;
import com.crm.bos.domain.User;
import com.crm.bos.domain.Workbill;
import com.crm.bos.service.INoticebillService;
import com.crm.bos.utils.BOSContext;

@Service
@Transactional
public class NoticebillServiceImpl implements INoticebillService{

	@Autowired
	private INoticebillDao noticebillDao;
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private IWorkbillDao workbillDao;

	//注入代理对象
	@Autowired
	private CustomerService customerService;

	//保存业务通知单并尝试自动分单
	public void save(Noticebill model) {
		User user = BOSContext.getLoginUser();
		model.setUser(user);//当前登录用户
		noticebillDao.save(model);
		
		String pickaddress = model.getPickaddress();//获取取件地址
		//调用crm根据取件地址匹配定区id
		String decidedzoneId = customerService.findDecidedzoneIdByAddress(pickaddress);
		if(decidedzoneId!=null){
			//查询到id，可以自动分单
			model.setOrdertype("自动分单");
			//根据定区id查询定区和取派员
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			//业务通知单关联取派员
			model.setStaff(staff);
			//为取派员创建一个工单
			Workbill workbill=new Workbill();
			workbill.setAttachbilltimes(0);
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));
			workbill.setNoticebill(model);
			workbill.setPickstate("未取件");
			workbill.setRemark(model.getRemark());
			workbill.setStaff(staff);
			workbill.setType("新单");
			workbillDao.save(workbill);
			//调用短信服务，给派送员发送短信
		}else {
			//未查询到，转入人工调度
			model.setOrdertype("人工分单");
		}
	}
	
}
