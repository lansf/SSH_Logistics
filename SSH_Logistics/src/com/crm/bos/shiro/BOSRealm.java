package com.crm.bos.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.crm.bos.dao.IFunctionDao;
import com.crm.bos.dao.IUserDao;
import com.crm.bos.domain.Function;
import com.crm.bos.domain.User;

/**
 * 自定义realm
 * 
 * 
 */
public class BOSRealm extends AuthorizingRealm {
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;
	/**
	 * 认证方法
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//异常1：用户名不存在,return null;
		//异常2：密码错误异常,这两个异常是安全管理器抛出的
		UsernamePasswordToken mytToken=(UsernamePasswordToken) token;
		String username = mytToken.getUsername();//数据库中可能不存在
		User user = userDao.findUserByUserName(username);
		if (user==null) {
			//账号不存在
			return null;
		}else {
			//账号存在，把查出的密码交给安全管理器来比对
			AuthenticationInfo info=new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
			return info;
		}
	}

	/**
	 * 授权方法
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		//根据当前用户获取相应的权限信息,首先获取当前用户
		User user=(User) principals.getPrimaryPrincipal();//获取到的是42行传入SimpleAuthenticationInfo的第一个参数值
		//查询相应的权限
		if(user.getUsername().equals("admin")){
			//查出所有权限并赋予
			List<Function> list = functionDao.findAll();
			for (Function function : list) {
				info.addStringPermission(function.getCode());
			}
		}else {
			//普通用户
			List<Function> list=functionDao.findListByUserId(user.getId());
			for (Function function : list) {
				info.addStringPermission(function.getCode());
			}
		}
		return info;
	}

}
