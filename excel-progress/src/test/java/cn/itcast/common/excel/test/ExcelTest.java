package cn.itcast.common.excel.test;

import cn.itcast.common.excel.ExcelUtils;
import cn.itcast.common.excel.constants.ExcelType;
import cn.itcast.common.excel.model.SheetDataBean;
import cn.itcast.common.excel.model.ValueBean;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * ClassName: ExcelTest  
 * (单元测试Excel导入导出)
 * @author zhangtian  
 * @version
 */
public class ExcelTest {
	
	/**
	 * 导出Excel应对一定量大数据策略1
	 * 分页签Sheet导出海量数据
	 * 问题：workbook中的数据流无法在内存中被清除
	 * 内存问题的该方法解决方案为：大数据时，分Excel导出，即导出多个超量数据Excel
	 */
	@Test
	public void testExportExcel() throws Exception {
		BaseUser u1 = new BaseUser() ;
		u1.setId(UUID.randomUUID().toString());
		u1.setUsername("张田");
		u1.setAddress("园区莲花五区");
		
		BaseUser u2 = new BaseUser() ;
		u2.setId(UUID.randomUUID().toString());
		u2.setUsername("小静静");
		u2.setAddress("崇明岛主");
		
		BaseUser u3 = new BaseUser() ;
		u3.setId(UUID.randomUUID().toString());
		u3.setUsername("王刚");
		u3.setAddress("阳澄湖岛主");
		
		List<BaseUser> appDatas = new ArrayList<BaseUser>() ;
		appDatas.add(u1) ;
		appDatas.add(u2) ;
		appDatas.add(u3) ;
		for(int i = 0; i < 10000; i++) {
			BaseUser u = new BaseUser() ;
			u.setId(UUID.randomUUID().toString());
			u.setUsername("Demo"+(i+1));
			u.setAddress("阳澄湖岛主"+i);
			appDatas.add(u) ;
		}
		
		long startTime = new Date().getTime() ;
		Workbook workbook = ExcelUtils.exportExcelData(appDatas, BaseUser.class, ExcelType.XLS, true, 2500) ;
		OutputStream out = new FileOutputStream(new File("C:\\Users\\zhangtian\\Desktop\\demo11.xlsx")) ;
		System.out.println(new Date().getTime() - startTime);
		workbook.write(out);
		out.flush();
		out.close(); 
		workbook.close();
	}
	
	/**
	 * 导出Excel应对一定量大数据策略2
	 * 分页签Sheet导出海量数据
	 * 导出数据后及时刷新内存
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testExcelImportData() throws FileNotFoundException, SecurityException, NoSuchFieldException, InstantiationException, IllegalAccessException, IOException {
		List<Object> list = ExcelUtils.importExcelData(new FileInputStream("C:\\Users\\zhangtian\\Desktop\\demoError.xls"), ExcelType.XLS, BaseUser.class, 0) ;
		System.out.println(list.size());
		System.out.println(JSONObject.toJSON(list.get(0)));
	}
	
	/**
	 * 导出Excel应对一定量大数据策略1
	 * 分页签Sheet导出海量数据
	 * 问题：workbook中的数据流无法在内存中被清除
	 * 内存问题的该方法解决方案为：大数据时，分Excel导出，即导出多个超量数据Excel
	 */
	@Test
	public void testExportErrorExcel() throws Exception {
		BaseErrorUser u1 = new BaseErrorUser() ;
		u1.setId(new ValueBean(UUID.randomUUID().toString(), false));
		u1.setUsername(new ValueBean("张田", true));
		u1.setAddress(new ValueBean("园区莲花五区", true));
		
		BaseErrorUser u2 = new BaseErrorUser() ;
		u2.setId(new ValueBean(UUID.randomUUID().toString(), false));
		u2.setUsername(new ValueBean("小静静", true));
		u2.setAddress(new ValueBean("崇明岛主", false));
		
		BaseErrorUser u3 = new BaseErrorUser() ;
		u3.setId(new ValueBean(UUID.randomUUID().toString(), false));
		u3.setUsername(new ValueBean("王刚", false));
		u3.setAddress(new ValueBean("阳澄湖主", true));
		
		List<BaseErrorUser> appDatas = new ArrayList<BaseErrorUser>() ;
		appDatas.add(u1) ;
		appDatas.add(u2) ;
		appDatas.add(u3) ;
		for(int i = 0; i < 10000; i++) {
			BaseErrorUser u = new BaseErrorUser() ;
			u.setId(new ValueBean(UUID.randomUUID().toString(), false));
			u.setUsername(new ValueBean("Demo", true));
			u.setAddress(new ValueBean("Demo", false));
			appDatas.add(u) ;
		}
		
		long startTime = new Date().getTime() ;
		Workbook workbook = ExcelUtils.exportErrorExcelData(appDatas, BaseUser.class, ExcelType.XLS, "zhangtian") ;
		OutputStream out = new FileOutputStream(new File("C:\\Users\\zhangtian\\Desktop\\demoError.xls")) ;
		System.out.println(new Date().getTime() - startTime);
		workbook.write(out);
		out.flush();
		out.close(); 
		workbook.close();
	}

	/**
	 * 测试自定义获取workbook对象，再重新操作
	 */
	@Test
	public void testMyDefine() throws IOException {
		InputStream in = new FileInputStream("C:\\Users\\zhangtian\\Desktop\\demoError.xls") ;
		Workbook workbook = ExcelUtils.getWorkbook(ExcelType.XLS, in) ;

		BaseUser u1 = new BaseUser() ;
		u1.setId(UUID.randomUUID().toString());
		u1.setUsername("张田");
		u1.setAddress("园区莲花五区");

		BaseUser u2 = new BaseUser() ;
		u2.setId(UUID.randomUUID().toString());
		u2.setUsername("小静静");
		u2.setAddress("崇明岛主");

		BaseUser u3 = new BaseUser() ;
		u3.setId(UUID.randomUUID().toString());
		u3.setUsername("王刚");
		u3.setAddress("阳澄湖岛主");

		List<BaseUser> appDatas = new ArrayList<BaseUser>() ;
		appDatas.add(u1) ;
		appDatas.add(u2) ;
		appDatas.add(u3) ;
		for(int i = 0; i < 100; i++) {
			BaseUser u = new BaseUser() ;
			u.setId(UUID.randomUUID().toString());
			u.setUsername("Demo"+(i+1));
			u.setAddress("阳澄湖岛主"+i);
			appDatas.add(u) ;
		}

		workbook = ExcelUtils.exportExcelDataToOldWorkbook(workbook, appDatas, BaseUser.class, ExcelType.XLS, "zhangtian1") ;
		OutputStream out = new FileOutputStream(new File("C:\\Users\\zhangtian\\Desktop\\demoError.xls")) ;
		workbook.write(out);
		out.flush();
		out.close();
		workbook.close();
	}

	@Test
	public void testExportMultipleSheetExcel() throws Exception {
		BaseUser u1 = new BaseUser() ;
		u1.setId(UUID.randomUUID().toString());
		u1.setUsername("张田");
		u1.setAddress("园区莲花五区");

		BaseUser u2 = new BaseUser() ;
		u2.setId(UUID.randomUUID().toString());
		u2.setUsername("小静静");
		u2.setAddress("崇明岛主");

		BaseUser u3 = new BaseUser() ;
		u3.setId(UUID.randomUUID().toString());
		u3.setUsername("王刚");
		u3.setAddress("阳澄湖岛主");

		List<BaseUser> appDatas = new ArrayList<BaseUser>() ;
		appDatas.add(u1) ;
		appDatas.add(u2) ;
		appDatas.add(u3) ;
		for(int i = 0; i < 10000; i++) {
			BaseUser u = new BaseUser() ;
			u.setId(UUID.randomUUID().toString());
			u.setUsername("Demo"+(i+1));
			u.setAddress("阳澄湖岛主"+i);
			appDatas.add(u) ;
		}

		List<SheetDataBean> sheets = new ArrayList<SheetDataBean>();
		SheetDataBean vo = new SheetDataBean();
		vo.setAppDatas(appDatas);
		vo.setBigData(true);
		vo.setPageSize(2500);
		vo.setClazz(BaseUser.class);
		vo.setSheetNames("baseUser");
		sheets.add(vo);

		BaseCar car1 = new BaseCar();
		car1.setId(UUID.randomUUID().toString());
		car1.setCarBand("本田");

		BaseCar car2 = new BaseCar() ;
		car2.setId(UUID.randomUUID().toString());
		car2.setCarBand("丰田");

		BaseCar car3 = new BaseCar() ;;
		car3.setId(UUID.randomUUID().toString());
		car3.setCarBand("大众");

		List<BaseCar> appDatas2 = new ArrayList<BaseCar>() ;
		appDatas2.add(car1) ;
		appDatas2.add(car2) ;
		appDatas2.add(car3) ;
		for(int i = 0; i < 10000; i++) {
			BaseCar car = new BaseCar() ;
			car.setId(UUID.randomUUID().toString());
			car.setCarBand("丰田" + i);
			appDatas2.add(car) ;
		}


		SheetDataBean vo1 = new SheetDataBean();
		vo1.setAppDatas(appDatas2);
		vo1.setBigData(true);
		vo1.setPageSize(1000);
		vo1.setClazz(BaseCar.class);
		vo1.setSheetNames("baseCar");
		sheets.add(vo1);

		long startTime = new Date().getTime() ;
     	Workbook workbook = ExcelUtils.exportExcelDataWithMultipleSheet(sheets, ExcelType.OTHER) ;
// 		Workbook workbook = ExcelUtils.exportExcelData(appDatas, BaseUser.class, ExcelType.OTHER, true, 2500) ;
		OutputStream out = new FileOutputStream(new File("C:\\Users\\zhangtian\\Desktop\\demo12.xlsx")) ;
		System.out.println(new Date().getTime() - startTime);
		workbook.write(out);
		out.flush();
		out.close();
		workbook.close();
	}
}
