package com.crm.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.crm.bos.domain.Region;
import com.crm.bos.utils.PinYin4jUtils;
import com.crm.bos.web.action.base.BaseAction;

/**
 * 区域管理
 * 
 * 
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	// 接收上传的文件
	private File myFile;

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	/**
	 * 批量导入
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String importXls() throws Exception {
		String flag = "1";
		// 使用ＰＯＩ解析Ｅｘｃｅｌ文件
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(
					new FileInputStream(myFile));
			// 获得第一个sheet页
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Region> list = new ArrayList<Region>();
			for (Row row : sheet) {
				int rowNum = row.getRowNum();
				if (rowNum == 0) {
					// 第一行，标题行，忽略
					continue;
				}
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = String.valueOf((long) row.getCell(4)
						.getNumericCellValue());
				Region region = new Region(id, province, city, district,
						postcode, null, null, null);
				city = city.substring(0, city.length() - 1);
				String[] stringToPinyin = PinYin4jUtils.stringToPinyin(city);
				String citycode = StringUtils.join(stringToPinyin, "");

				// 简码---->>HBSJZCA
				province = province.substring(0, province.length() - 1);
				district = district.substring(0, district.length() - 1);
				String info = province + city + district;// 河北石家庄长安
				String[] headByString = PinYin4jUtils.getHeadByString(info);
				String shortcode = StringUtils.join(headByString, "");

				region.setCitycode(citycode);
				region.setShortcode(shortcode);
				list.add(region);
			}
			regionService.saveBatch(list);
		} catch (Exception e) {
			e.printStackTrace();
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}

	// 分页查询
	@RequiresPermissions(value="region")//执行这个方法需要region这个权限
	//@RequiresRoles(value="admin")
	public String pageQuery() {
		regionService.pageQuery(pageBean);
		String[] excludes = new String[] { "subareas" };
		this.writePageBean2Json(pageBean, excludes);
		return NONE;
	}

	private String q;
	
	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	// 查询所有区域数据，返回json,支持模糊查询
	public String listajax() throws IOException{
		List<Region> list = null;//regionService.findAll();
		if(StringUtils.isNotBlank(q)){
			list = regionService.findByQ(q.trim());
		}else{
			list = regionService.findAll();
		}
		String[] excludes = new String[]{"subareas","province","city","district","postcode","shortcode","citycode"};
		this.writeList2Json(list, excludes);
		return NONE;
	}

	//添加和修改功能
	public String add(){
		regionService.save(model);
		return "list";
	}
	
	// 批量作废
	private String ids;

	public String delete() {
		regionService.deleteBatch(ids);
		System.out.println();
		return "list";
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
