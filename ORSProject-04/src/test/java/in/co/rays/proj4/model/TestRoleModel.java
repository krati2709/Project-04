package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TestRoleModel {
	public static void main(String[] args) throws SQLException {

//		testAdd();
		testUpdate();
//		testDelete();
//		testFindByName();
//		testFindByPk();
		try {
			testSearch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testAdd() {

		RoleBean bean = new RoleBean();
		bean.setName("Kiosk");
		bean.setDescription("Kiosk");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		RoleModel model = new RoleModel();

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
			RoleModel model = new RoleModel();
			RoleBean bean = model.findByPk(2);
			bean.setDescription("Student");
			model.update(bean);
			System.out.println("data updated successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		bean.setId(2);
		try {
			model.delete(bean);
			System.out.println("data deleted successfully");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByName() throws ApplicationException {

		RoleModel model = new RoleModel();

		RoleBean existsbean = model.findByName("hr");

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
	
	RoleModel model = new RoleModel();
	RoleBean bean = new RoleBean();
	bean.setName("h");
//
	
	List list = model.search(bean, 1, 5);
	Iterator<RoleBean> it = list.iterator();
	
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