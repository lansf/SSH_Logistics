package com.crm.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.crm.bos.crm.CustomerService;
import com.crm.bos.domain.User;
import com.crm.bos.utils.MD5Utils;
import com.crm.bos.web.action.base.BaseAction;
import com.crm.domain.Customer;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
// 设置为多例的,在实际访问到相应action时才会创建对应的实例
public class UserAction extends BaseAction<User> {



	
	// 接受验证码
	private String checkcode;// 若未输入验证码，则得到的checkcode为空串而不是null；

	// 如果是Integer checkcode，那么验证码未输入时得到的就是null（空串无法转为Integer类型）

	// 登录验证
	public String login() {
		
		// 从session中获取验证码
		String key = (String) ServletActionContext.getRequest().getSession()
				.getAttribute("key");
		// 判断验证码
		if (StringUtils.isNotBlank(checkcode) && checkcode.equals(key)) {// blank为空，包含空串和null
			/*
			// 验证码输入正确
			// 校验用户名和密码
			User user = userService.login(model);
			if (user != null) {
				// 登录成功
				// 将当前用户放入session中，跳转到首页
				ActionContext.getContext().getSession().put("loginUser", user);
				return "home";
			} else {
				// 登录失败,设置错误提示信息，跳转到登录页面
				this.addActionError(this.getText("loginError"));
				return "login";
			}
			*/
			//使用shiro框架来进行验证
			Subject subject = SecurityUtils.getSubject();//获取当前用户，状态为“未认证”
			String password=MD5Utils.md5(model.getPassword());
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),password);
			try {
				subject.login(token);//认证
				User user = (User) subject.getPrincipal();
				ActionContext.getContext().getSession().put("loginUser", user);
				return "home";
			} catch (UnknownAccountException e) {//账号不存在异常
				e.printStackTrace();
				this.addActionError("账号不存在");
				return "login";
			} catch (IncorrectCredentialsException e) {//密码错误异常
				e.printStackTrace();
				this.addActionError("密码错误");
				return "login";
			}
		} else {// 验证码有误
			this.addActionError(this.getText("checkcodeError"));
			return "login";
		}
	}

	public String logout() {
		// 清理session
		/*
		 * 首先，session
		 * 的作用域是在一个会话期间，多个用户访问服务器，就会有多个session，现在，我就假设有三个用户A、B、C，他们访问服务器
		 * ，分别创建了三个Session，记为S1,S2,S3.。
		 * session.invalidate()，是某一个用户调用的，比如说S1这个用户，调用了这个方法，那么，就只有s1用户的session
		 * 被删除，其他用户的session，跟s1没关系。
		 * session.invalidate()，它实际上调用的是session对象中的destroy方法
		 * ，也就是说你下次要再使用session，得再重新创建。 简单的说，就是没了，而不是值为null
		 * 还有一个用户，访问同一个服务器，关闭浏览器，默认情况下清除所有的session。实际上清除的是自己的这个session
		 * 而已。。。别人的session关它什么事。
		 * 另外，有些情况下，登出并不一定是调用session.invalidate()，有时候，只是把绑定在session中某些数据给清空而已。
		 * session.invalidate()是不能随便调用的，一旦调用，就是所有的数据都会丢失。
		 */
		ServletActionContext.getRequest().getSession().invalidate();
		return "login";
	}

	//修改密码
	public String editPassword() throws IOException{
		//获取当前用户信息
		User user = (User) ActionContext.getContext().getSession().get("loginUser");
		model.setId(user.getId());
		String flag="0";//修改是否成功
		try {
			userService.editPassword(model);
		} catch (Exception e) {
			flag="1";//修改失败
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}
	
	private String[] roleIds;//接受页面提交过来的参数
	
	public String add(){
		userService.save(model,roleIds);
		return "list";
	}
	
	public String pageQuery() throws IOException{
		userService.pageQuery(pageBean);
		String[] excludes=new String[]{"noticebills","roles"};
		this.writePageBean2Json(pageBean, excludes);
		return NONE;
	}
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
}
