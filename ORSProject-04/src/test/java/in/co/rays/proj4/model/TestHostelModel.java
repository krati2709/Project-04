package in.co.rays.proj4.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.rays.proj4.bean.HostelBean;

public class TestHostelModel {
	public static void main(String[] args) throws Exception {
		
		testAdd();

	}

	public static void testAdd() throws Exception {

		HostelModel model = new HostelModel();
		HostelBean bean = new HostelBean();

		bean.setName("SVN hostel");
		bean.setType("Girls");
		bean.setCapacity(50);
		bean.setDescription("newwww");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		model.add(bean);
		System.out.println("Data Added Successfully");

	}

}
