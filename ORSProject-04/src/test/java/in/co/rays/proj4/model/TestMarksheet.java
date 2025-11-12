package in.co.rays.proj4.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;

public class TestMarksheet {

	public static void main(String[] args) throws Exception {
		
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByRollNo();
		testSearch();
		
	}
	
	public static void testAdd() throws ApplicationException, DuplicateRecordException {
		MarksheetModel model = new MarksheetModel();
		MarksheetBean bean = new MarksheetBean();
		
		bean.setRollNo("1002");
		bean.setStudentId(1);
		bean.setPhysics(41);
		bean.setChemistry(37);
		bean.setMaths(66);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.add(bean);
		
		
	}
	
	public static void testUpdate() throws Exception {
		
		MarksheetModel model = new MarksheetModel();
		MarksheetBean bean = model.findByPk(1L);
		
		bean.setPhysics(67);
		model.update(bean);
		
	}
	
	public static void testDelete() throws Exception {
		
		MarksheetModel model = new MarksheetModel();
		MarksheetBean bean = new MarksheetBean();
		
		bean.setId(2);
		model.delete(bean);
		System.out.println("data deleted successfully");
	}
	
	public static void testFindByRollNo() throws Exception {
		
		MarksheetModel model = new MarksheetModel();
		MarksheetBean existsbean = model.findByRollNo("1004");
		
		if (existsbean != null) {
			System.out.println("name exists");
		} else {
			System.out.println("no record found");
		}
		
	}
	
	public static void testSearch() throws Exception {
		
		MarksheetModel model = new MarksheetModel();
		MarksheetBean bean = new MarksheetBean();
		bean.setName("k");
		
		List list = model.search(bean, 1, 5);
		Iterator<MarksheetBean> it = list.iterator();
		
		while (it.hasNext()) {
			bean = it.next();
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getChemistry());
			System.out.print("\t" + bean.getMaths());
			
		}
		
	}
	
	
}
