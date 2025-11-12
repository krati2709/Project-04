package in.co.rays.proj4.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;


public class TestCourseModel {
	public static void main(String[] args) throws Exception {
		
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByName();
		testSearch();
		
	}
	
	public static void testAdd() throws Exception {
		
		CourseModel model = new CourseModel();
		CourseBean bean = new CourseBean();
		
		bean.setName("BSC");
		bean.setDuration("3 years");
		bean.setDescription("BSC in Computer Science");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.add(bean);
		System.out.println("Data Added Successfully");

	}
	
	public static void testUpdate() throws Exception {
		
		CourseModel model = new CourseModel();
		CourseBean bean = model.findByPk(2);
		
		bean.setDescription("BSC in Chemistry");
		model.update(bean);
		System.out.println("data updated successfully");
		
	}
	
	public static void testDelete() throws Exception {
		
		CourseModel model = new CourseModel();
		CourseBean bean = new CourseBean();
		
		bean.setId(2);
		model.delete(bean);
		System.out.println("data deleted successfully");
	}
	
	public static void testFindByPk() {
		CourseModel model = new CourseModel();

		CourseBean existsbean;
		try {
			existsbean = model.findByPk(2);
			if (existsbean != null) {
				System.out.println("ID exists");
			} else {
				System.out.println("no record found");
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void testFindByName() throws ApplicationException {

		CourseModel model = new CourseModel();

		CourseBean existsbean = model.findByName("Btech");

		if (existsbean != null) {
			System.out.println("name exists");
		} else {
			System.out.println("no record found");
		}

	}
	
	public static void testSearch() throws Exception {

		CourseModel model = new CourseModel();
		CourseBean bean = new CourseBean();
		bean.setName("b");

		List list = model.search(bean, 1, 5);
		Iterator<CourseBean> it = list.iterator();

		while (it.hasNext()) {
			bean = it.next();
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.println("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}

	}
}

