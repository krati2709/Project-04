package in.co.rays.proj4.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;


public class TestTimetableModel {
	public static void main(String[] args) throws Exception {
		
		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByName();
//		testSearch();
		testCheckBySubName();
		
	}
	
	public static void testAdd() throws Exception {
		
		TimetableModel model = new TimetableModel();
		TimetableBean bean = new TimetableBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		bean.setSemester("1st sem");
		bean.setCourseId(1);
		bean.setExamDate(sdf.parse("2025-12-26"));
		bean.setExamTime("09:00 AM");
		bean.setSubjectId(1);
		bean.setDescription("Semester Exams");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.add(bean);
		System.out.println("Data Added Successfully");

	}
	
	public static void testUpdate() throws Exception {
		
		TimetableModel model = new TimetableModel();
		TimetableBean bean = model.findByPk(1);
		
		bean.setDescription("Engineering Mathematics 1");
		model.update(bean);
		System.out.println("data updated successfully");
		
	}
	
	public static void testDelete() throws Exception {
		
		TimetableModel model = new TimetableModel();
		TimetableBean bean = new TimetableBean();
		
		bean.setId(2);
		model.delete(bean);
		System.out.println("data deleted successfully");
	}
	
	public static void testFindByPk() {
		TimetableModel model = new TimetableModel();

		TimetableBean existsbean;
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
	
	public static void testCheckBySubName() throws Exception {

		TimetableModel model = new TimetableModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		TimetableBean existsbean = model.checkBySubjectName(1L, 1L, sdf.parse("2025-12-26"));

		if (existsbean != null) {
			System.out.println("name exists");
		} else {
			System.out.println("no record found");
		}

	}
	
	public static void testSearch() throws Exception {

		TimetableModel model = new TimetableModel();
		TimetableBean bean = new TimetableBean();
		bean.setSemester("b");

		List list = model.search(bean, 1, 5);
		Iterator<TimetableBean> it = list.iterator();

		while (it.hasNext()) {
			bean = it.next();
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getSemester());
			System.out.print("\t" + bean.getDescription());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.println("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());

		}

	}
}

