package com.crm.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.crm.bos.domain.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/*
 * 自定义的拦截器，实现未登录时自动跳转到登陆页面
 */
public class BosLoginInterceptor extends MethodFilterInterceptor{

	@Override
	protected String doIntercept(ActionInvocation ai) throws Exception {
		User user = (User) ActionContext.getContext().getSession().get("loginUser");
		if(user==null){
			//未登录
			return "login";
		}
		return ai.invoke();//已登录则放行
	}

}
