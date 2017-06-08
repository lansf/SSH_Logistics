package com.crm.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.crm.bos.service.IDecidedzoneService;
import com.crm.bos.service.IFunctionService;
import com.crm.bos.service.INoticebillService;
import com.crm.bos.service.IRegionService;
import com.crm.bos.service.IRoleService;
import com.crm.bos.service.IStaffService;
import com.crm.bos.service.ISubareaService;
import com.crm.bos.service.IUserService;
import com.crm.bos.service.IWorkordermanageService;
import com.crm.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	@Resource
	protected IUserService userService;
	@Autowired
	protected IStaffService staffService;
	@Autowired
	protected IRegionService regionService;
	@Autowired
	protected ISubareaService subareaService;
	@Autowired
	protected IDecidedzoneService decidedzoneService;
	@Autowired
	protected INoticebillService noticebillService;
	@Autowired
	protected IWorkordermanageService workordermanageService;
	@Autowired
	protected IFunctionService functionService;
	@Autowired
	protected IRoleService roleService;
	protected T model;// 模型对象
	protected PageBean pageBean = new PageBean();
	DetachedCriteria detachedCriteria = null;
	protected int page;// 分页参数
	protected int rows;

	public T getModel() {
		return model;
	}

	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}

	// 模型对象类型在构造方法中动态获得
	public BaseAction() {
		ParameterizedType genericSuperclass = null;
		Type genericSuperclass2 = this.getClass().getGenericSuperclass();
		if(genericSuperclass2 instanceof ParameterizedType){
			genericSuperclass=(ParameterizedType) genericSuperclass2;
		}else {//此时this为shiro的代理对象
			genericSuperclass=(ParameterizedType) this.getClass().getSuperclass().getGenericSuperclass();
		}
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	// 将pageBean对象转为json
	public void writePageBean2Json(PageBean pageBean, String[] excludes) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(pageBean, jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 将list集合转为json
	public void writeList2Json(List list, String[] excludes) throws IOException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(list, jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	
	//将普通对象转为json
	public void writeObject2Json(Object object, String[] excludes)
			throws IOException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
}
