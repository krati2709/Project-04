package in.co.rays.proj4.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;

public class TestUserModel {
	public static void main(String[] args) throws Exception {
		
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByLogin();
		testSearch();
	}
	
	public static void testAdd() throws ParseException {
		
		UserBean bean = new UserBean();
		UserModel model = new UserModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		bean.setFirstName("Krati");
		bean.setLastName("Trivedi");
		bean.setLogin("krati1@gmail.com");
		bean.setPassword("krati123");
		bean.setConfirmPassword("krati123");
		bean.setDob(sdf.parse("2002-09-27"));
		bean.setMobileNo("9424682927");
		bean.setRoleId(2);
		bean.setGender("female");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
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
			UserModel model = new UserModel();
			UserBean bean = model.findByPk(2);
			bean.setFirstName("Arya");
			model.update(bean);
			System.out.println("data updated successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testDelete() {

		UserBean bean = new UserBean();
		UserModel model = new UserModel();

		bean.setId(2);
		try {
			model.delete(bean);
			System.out.println("data deleted successfully");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testFindByLogin() throws ApplicationException {

		UserModel model = new UserModel();

		UserBean existsbean = model.findByLogin("krati@gmail.com");

		if (existsbean != null) {
			System.out.println("login exists");
		} else {
			System.out.println("no record found");
		}

	}
	
	public static void testFindByPk() {
		UserModel model = new UserModel();

		UserBean existsbean;
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
		
		UserModel model = new UserModel();
		UserBean bean = new UserBean();
		bean.setFirstName("k");
	//
		
		List list = model.search(bean, 1, 5);
		Iterator<UserBean> it = list.iterator();
		
		while (it.hasNext()) {
			bean = it.next();
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getFirstName());
			System.out.print("\t" + bean.getLastName());
			System.out.print("\t" + bean.getLogin());
			System.out.print("\t" + bean.getPassword());
			System.out.print("\t" + bean.getDob());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getRoleId());
			System.out.print("\t" + bean.getGender());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.println("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
			
		}
		
	}

}
