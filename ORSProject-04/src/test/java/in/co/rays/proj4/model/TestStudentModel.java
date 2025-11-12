package in.co.rays.proj4.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;

public class TestStudentModel {
	public static void main(String[] args) throws Exception {

//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByEmail();
		testSearch();

	}

	public static void testAdd() throws ParseException {

		StudentBean bean = new StudentBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		bean.setFirstName("ayushi");
		bean.setLastName("joshi");
		bean.setDob(sdf.parse("1999-01-04"));
		bean.setGender("female");
		bean.setMobileNo("9898989898");
		bean.setEmail("ayushi@gmail.com");
		bean.setCollegeId(1L);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		StudentModel model = new StudentModel();

		try {
			model.add(bean);
			System.out.println("data added successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		try {
			StudentModel model = new StudentModel();
			StudentBean bean = model.findByPk(1);
			bean.setLastName("vyas");
			model.update(bean);
			System.out.println("data updated successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByPk() throws Exception {

		StudentModel model = new StudentModel();

		StudentBean existsBean = model.findByPk(1L);

		if (existsBean != null) {
			System.out.println("pk exist");
		} else {
			System.out.println("no record found");
		}

	}
	
	public static void testDelete() throws ApplicationException {

		StudentModel model = new StudentModel();
		StudentBean bean = new StudentBean();

		bean.setId(4);
		try {
			model.delete(bean);
			System.out.println("data deleted successfully");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testFindByEmail() throws ApplicationException {
	
		StudentModel model = new StudentModel();
		StudentBean existsBean = model.findByEmailId("krati@gmail.com");

		if (existsBean != null) {
			System.out.println("email exist");
		} else {
			System.out.println("no record found");
		}

		
	}
		
	
	
	public static void testSearch() throws Exception {
		
		StudentModel model = new StudentModel();
		StudentBean bean = new StudentBean();
		bean.setFirstName("k");
		
		List list = model.search(bean, 1, 5);
		Iterator<StudentBean> it = list.iterator();
		
		while (it.hasNext()) {
			bean = it.next();
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getFirstName());
			System.out.print("\t" + bean.getLastName());
			System.out.print("\t" + bean.getDob());
			System.out.println("\t" + bean.getGender());
		}

	}
}

