package in.co.rays.proj4.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;


public class TestFacultyModel {
	public static void main(String[] args) throws Exception {
		
		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByName();
//		testSearch();
		
	}
	
	public static void testAdd() throws Exception {
		
		FacultyModel model = new FacultyModel();
		FacultyBean bean = new FacultyBean();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		bean.setFirstName("ram");
		bean.setLastName("sharma");
		bean.setDob(sdf.parse("1997-09-07"));
		bean.setGender("male");
		bean.setMobileNo("9898989898");
		bean.setEmail("ram@gmail.com");
		bean.setCollegeId(1);
		bean.setCourseId(1);
		bean.setSubjectId(1);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.add(bean);
		System.out.println("Data Added Successfully");

	}
	
	public static void testUpdate() throws Exception {
		
		FacultyModel model = new FacultyModel();
		FacultyBean bean = model.findByPk(1);
		
		bean.setLastName("Tripathi");
		model.update(bean);
		System.out.println("data updated successfully");
		
	}
	
	public static void testDelete() throws Exception {
		
		FacultyModel model = new FacultyModel();
		FacultyBean bean = new FacultyBean();
		
		bean.setId(2);
		model.delete(bean);
		System.out.println("data deleted successfully");
	}
	
	public static void testFindByPk() {
		FacultyModel model = new FacultyModel();

		FacultyBean existsbean;
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

		FacultyModel model = new FacultyModel();

		FacultyBean existsbean = model.findByEmail("ram@gmail.com");

		if (existsbean != null) {
			System.out.println("name exists");
		} else {
			System.out.println("no record found");
		}

	}
	
	public static void testSearch() throws Exception {

		FacultyModel model = new FacultyModel();
		FacultyBean bean = new FacultyBean();
		bean.setFirstName("r");

		List list = model.search(bean, 1, 5);
		Iterator<FacultyBean> it = list.iterator();

		while (it.hasNext()) {
			bean = it.next();
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getFirstName());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.println("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}

	}
}

