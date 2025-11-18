package in.co.rays.proj4.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;

public class TestCollegeModel {
	public static void main(String[] args) throws Exception {

//		testAdd();
//		testUpdate();
		testDelete();
//		testFindByName();
//		testSearch();
	}

	public static void testAdd() {

		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();

		bean.setName("prestige");
		bean.setAddress("Indore");
		bean.setState("MP");
		bean.setCity("Indore");
		bean.setPhoneNo("9898989898");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		try {
			model.add(bean);
			System.out.println("data added successfully");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testUpdate() {

		try {
			CollegeModel model = new CollegeModel();
			CollegeBean bean = model.findByPk(3);

			bean.setAddress("rau, indore");

			model.update(bean);
			System.out.println("data updated successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void testDelete() throws ApplicationException {

		CollegeModel model = new CollegeModel();
		CollegeBean bean = new CollegeBean();

		bean.setId(4);
		try {
			model.delete(bean);
			System.out.println("data deleted successfully");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testFindByName() throws ApplicationException {

		CollegeModel model = new CollegeModel();

		CollegeBean existsbean = model.findByName("IPS");

		if (existsbean != null) {
			System.out.println("name exists");
		} else {
			System.out.println("no record found");
		}

	}
	
	public static void testFindByPk() {
		RoleModel model = new RoleModel();

		RoleBean existsbean;
		try {
			existsbean = model.findByPk(1);
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
	
	public static void testSearch() throws Exception {
		
		CollegeModel model = new CollegeModel();
		CollegeBean bean = new CollegeBean();
		bean.setName("i");
		
		List list = model.search(bean, 1, 5);
		Iterator<CollegeBean> it = list.iterator();
		
		while (it.hasNext()) {
			bean = it.next();
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getAddress());
			System.out.print("\t" + bean.getState());
			System.out.print("\t" + bean.getCity());
			System.out.print("\t" + bean.getPhoneNo());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.println("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
			
		}

	}
}
