package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.DepartmentBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TestDepartmentModel {
	public static void main(String[] args) throws Exception {

//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByName();
//		testFindByPk();
		
		testSearch();

	}

	public static void testAdd() {

		DepartmentBean bean = new DepartmentBean();
		bean.setName("hr");
		bean.setDescription("hr");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		DepartmentModel model = new DepartmentModel();

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
			DepartmentModel model = new DepartmentModel();
			DepartmentBean bean = model.findByPk(1);
			bean.setDescription("jdsbfsjbef");
			model.update(bean);
			System.out.println("data updated successfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testDelete() {

		DepartmentBean bean = new DepartmentBean();
		DepartmentModel model = new DepartmentModel();

		bean.setId(6);
		try {
			model.delete(bean);
			System.out.println("data deleted successfully");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void testFindByName() throws ApplicationException {

		DepartmentModel model = new DepartmentModel();

		DepartmentBean existsbean = model.findByName("Computer Science");

		if (existsbean != null) {
			System.out.println("name exists");
		} else {
			System.out.println("no record found");
		}

	}

	public static void testFindByPk() {
		DepartmentModel model = new DepartmentModel();

		DepartmentBean existsbean;
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

		DepartmentModel model = new DepartmentModel();
		DepartmentBean bean = new DepartmentBean();
		bean.setName("c");

		List list = model.search(bean, 1, 5);
		Iterator<DepartmentBean> it = list.iterator();

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